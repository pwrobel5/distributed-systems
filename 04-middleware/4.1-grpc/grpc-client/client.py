import datetime
import logging
import signal
import threading
import time

import grpc

import event_notification_pb2
import event_notification_pb2_grpc

WELCOME_TEXT = "gRPC notification client"
SUBSCRIPTION_MENU_TEXT = "Choose subscription:\n\ta - cultural event subscription\n\tb - weather forecast " \
                         "subscription\n\tc - begin subscription\n\tx - exit\n"
continue_cultural_stream_reading = True
continue_weather_stream_reading = True
cultural_subscription_thread = None
weather_subscription_thread = None
channel = None


def timestamp_to_datetime(value):
    return datetime.datetime.fromtimestamp(value.seconds)


def read_cultural_event_types(types_list=None):
    if types_list is None:
        types_list = []

    print("Available cultural event types, choose one:")
    valid_values = event_notification_pb2.CulturalEventType.values()
    end_reading = False

    while not end_reading:
        for event_type in event_notification_pb2.CulturalEventType.items():
            print("{} - {}".format(event_type[1], event_type[0].lower()))
        print("x - end")

        chosen_option = input("Choose: ")
        if chosen_option.lower() == "x":
            end_reading = True
        else:
            chosen_option = int(chosen_option)
            if chosen_option not in valid_values:
                print("Wrong option chosen!")
            else:
                types_list.append(event_notification_pb2.CulturalEventType.keys()[chosen_option])

    return types_list


def read_cultural_subscription_details():
    result = read_cultural_event_types()
    print("Chosen options:")
    for option in result:
        print("\t{}".format(option))

    accept = input("Accept? [Y/N]: ")
    if accept.lower() == "y":
        return result
    else:
        return None


def read_cities(cities_list=None):
    if cities_list is None:
        cities_list = []

    print("Choose cities for weather forecast subscription")
    end_reading = False

    while not end_reading:
        city_name = input("Enter city name: ")
        city_country = input("Enter city country: ")
        cities_list.append(event_notification_pb2.City(name=city_name, country=city_country))

        read_next = input("Read another? [Y/N]: ")
        if read_next.lower() != "y":
            end_reading = True

    return cities_list


def read_weather_subscription_details():
    result = read_cities()
    print("Chosen cities:")
    for city in result:
        print("\t{}, {}".format(city.name, city.country))

    accept = input("Accept? [Y/N]: ")
    if accept.lower() == "y":
        return result
    else:
        return None


def read_cultural_stream(cultural_subscription_details, stub):
    global continue_cultural_stream_reading

    failed_connections = 0
    reconnection_interval = 10

    logging.info("Starting cultural events stream thread")

    while continue_cultural_stream_reading:
        try:
            cultural_subscription = event_notification_pb2.CulturalNewsletterSubscription(
                types=cultural_subscription_details)
            for notification in stub.SubscribeCulturalNewsletter(cultural_subscription):
                failed_connections = 0
                reconnection_interval = 10
                logging.info("Cultural event notification received")
                print_cultural_event_notification(notification)
        except grpc.RpcError as rpc_error_call:
            code = rpc_error_call.code()
            if code == grpc.StatusCode.INVALID_ARGUMENT:
                logging.error("Invalid cultural subscription arguments sent!")
                continue_cultural_stream_reading = False
            elif code == grpc.StatusCode.UNAVAILABLE:
                failed_connections += 1
                if failed_connections % 10 == 0:
                    reconnection_interval /= 2
                    reconnection_interval = max(reconnection_interval, 1)

                logging.error("Cultural newsletter service unavailable, trying to reconnect...")
                time.sleep(reconnection_interval)
            elif code == grpc.StatusCode.UNIMPLEMENTED:
                logging.error("Unimplemented cultural newsletter on server")
                continue_cultural_stream_reading = False
            elif code == grpc.StatusCode.CANCELLED:
                logging.info("Cancelled connection")
                continue_cultural_stream_reading = False
            else:
                logging.error("Unknown error: " + str(code))
                continue_cultural_stream_reading = False

    logging.info("Ending cultural subscription")


def print_cultural_event_notification(notification):
    res_string = "Cultural event"
    event_type = event_notification_pb2.CulturalEventType.keys()[event_notification_pb2.CulturalEventType.values()
        .index(notification.type)]
    res_string += "\n\tTitle: {}\n\tType: {}\n\tDate: {}\n".format(notification.title, event_type,
                                                                   timestamp_to_datetime(notification.date).strftime(
                                                                       "%Y-%m-%d %H:%M"))
    print(res_string + "\n")


def read_weather_stream(weather_subscription_details, stub):
    global continue_weather_stream_reading

    failed_connections = 0
    reconnection_interval = 10

    logging.info("Starting weather stream thread")

    while continue_weather_stream_reading:
        try:
            weather_subscription = event_notification_pb2.WeatherForecastSubscription(
                cities=weather_subscription_details)
            for forecast in stub.SubscribeWeatherForecast(weather_subscription):
                logging.info("Received weather forecast")
                print_weather_forecast(forecast)
        except grpc.RpcError as rpc_error_call:
            code = rpc_error_call.code()
            if code == grpc.StatusCode.INVALID_ARGUMENT:
                logging.error("Invalid weather forecast arguments sent!")
                continue_weather_stream_reading = False
            elif code == grpc.StatusCode.UNAVAILABLE:
                failed_connections += 1
                if failed_connections % 10 == 0:
                    reconnection_interval /= 2
                    reconnection_interval = max(reconnection_interval, 1)

                logging.error("Weather forecast service unavailable, trying to reconnect...")
                time.sleep(reconnection_interval)
            elif code == grpc.StatusCode.UNIMPLEMENTED:
                logging.error("Unimplemented weather forecast on server")
                continue_weather_stream_reading = False
            elif code == grpc.StatusCode.CANCELLED:
                logging.info("Cancelled connection")
                continue_weather_stream_reading = False
            else:
                logging.error("Unknown error: " + str(code))
                continue_weather_stream_reading = False

    logging.info("Ending weather subscription")


def print_weather_forecast(forecast):
    res_string = "Weather forecast"
    res_string += "\n\tCity: {}, {}".format(forecast.city.name, forecast.city.country)
    res_string += "\n\tDate: {}".format(timestamp_to_datetime(forecast.day).strftime("%Y-%m-%d"))
    for forecast_point in forecast.forecastList:
        forecast_type = event_notification_pb2.ForecastType.keys()[
            event_notification_pb2.ForecastType.values().index(forecast_point.type)]
        res_string += "\n\t\tHour: {}: {}".format(timestamp_to_datetime(forecast_point.time).strftime("%H:%M"),
                                                  forecast_type)
    print(res_string + "\n")


# separate threads for reading from streams are needed, ref: https://github.com/grpc/grpc/issues/9280
def begin_subscription(cultural_subscription_details, weather_subscription_details, host="localhost:50055"):
    global channel
    channel = grpc.insecure_channel(host)

    global cultural_subscription_thread, weather_subscription_thread

    stub = event_notification_pb2_grpc.NotificationsStub(channel)
    if cultural_subscription_details is not None:
        cultural_subscription_thread = threading.Thread(target=read_cultural_stream,
                                                        args=(cultural_subscription_details, stub))
        cultural_subscription_thread.start()

    if weather_subscription_details is not None:
        weather_subscription_thread = threading.Thread(target=read_weather_stream,
                                                       args=(weather_subscription_details, stub))
        weather_subscription_thread.start()

    if cultural_subscription_thread is not None:
        cultural_subscription_thread.join()
    if weather_subscription_thread is not None:
        weather_subscription_thread.join()

    channel.close()


def sigint_handler(signum, frame):
    logging.info("Received SIGINT signal")
    global continue_cultural_stream_reading, continue_weather_stream_reading
    continue_cultural_stream_reading = False
    continue_weather_stream_reading = False

    if channel is not None:
        logging.info("Closing channel")
        channel.close()

    if cultural_subscription_thread is not None:
        cultural_subscription_thread.join()

    if weather_subscription_thread is not None:
        weather_subscription_thread.join()

    exit(0)


def main():
    print(WELCOME_TEXT)
    go_next = False

    logging.root.setLevel(logging.NOTSET)
    logging.basicConfig(level=logging.NOTSET)
    signal.signal(signal.SIGINT, sigint_handler)

    cultural_subscription_details = None
    weather_subscription_details = None

    while not go_next:
        subscription_type = input(SUBSCRIPTION_MENU_TEXT)

        if subscription_type.lower() == "a":
            cultural_subscription_details = read_cultural_subscription_details()
        elif subscription_type.lower() == "b":
            weather_subscription_details = read_weather_subscription_details()
        elif subscription_type.lower() == "c":
            go_next = True
        elif subscription_type.lower() == "x":
            exit(0)
        else:
            print("Invalid option chosen!")

    host = "localhost:50055"
    default_connection = input("Connect to localhost:50055? [Y/N]: ")
    if default_connection.lower() != "y":
        host = input("Enter host name in form address:port: ")

    begin_subscription(cultural_subscription_details, weather_subscription_details, host)


if __name__ == '__main__':
    main()
