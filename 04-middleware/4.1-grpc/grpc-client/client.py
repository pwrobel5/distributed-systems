from event_notification_pb2 import CulturalEventType, City

WELCOME_TEXT = "gRPC notification client"
SUBSCRIPTION_MENU_TEXT = "Choose subscription:\n\ta - cultural event subscription\n\tb - weather forecast " \
                         "subscription\n\tc - begin subscription\n\tx - exit\n"


def read_cultural_event_types(types_list=None):
    if types_list is None:
        types_list = []

    print("Available cultural event types, choose one:")
    valid_values = CulturalEventType.values()
    end_reading = False

    while not end_reading:
        for event_type in CulturalEventType.items():
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
                types_list.append(CulturalEventType.keys()[chosen_option])

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
        cities_list.append(City(name=city_name, country=city_country))

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


def begin_subscription(cultural_subscription_details, weather_subscription_details):
    pass


def main():
    print(WELCOME_TEXT)
    go_next = False

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

    begin_subscription(cultural_subscription_details, weather_subscription_details)


if __name__ == '__main__':
    main()
