package middleware.generators;

import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import middleware.events.City;
import middleware.events.ForecastType;
import middleware.events.WeatherForecastNotification;
import middleware.events.WeatherForecastSubscription;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.google.protobuf.util.Timestamps.fromMillis;

public class WeatherForecastGenerator {
    private final List<City> subscribedCities;
    private final Random random;
    private Timestamp timestamp;

    private static final List<ForecastType> FORECAST_TYPES =
            List.of(ForecastType.values());
    private static final int SECONDS_IN_DAY = 86400;
    private static final int FORECASTS_FOR_DAY = 3;
    private static final Duration TIME_DURATION = Duration.newBuilder().setSeconds(SECONDS_IN_DAY).build();
    private static final Duration HOUR_DAY_DURATION = Duration.newBuilder().setSeconds(28800).build();

    public WeatherForecastGenerator(WeatherForecastSubscription request) {
        subscribedCities = new ArrayList<>(request.getCitiesList());
        random = new Random();
        timestamp = fromMillis(System.currentTimeMillis());
    }

    public List<WeatherForecastNotification> getForecast() {
        int randomMax = FORECAST_TYPES.size() - 1; // the last element in the list is UNRECOGNIZED which is invalid to send
        List<WeatherForecastNotification> result = new LinkedList<>();
        timestamp = Timestamps.add(timestamp, TIME_DURATION);
        for (City city : subscribedCities) {
            List<WeatherForecastNotification.Forecast> forecasts = new LinkedList<>();
            Timestamp forecastHour = Timestamps.add(timestamp, HOUR_DAY_DURATION);

            for (int i = 0; i < FORECASTS_FOR_DAY; i++) {
                ForecastType forecastType = FORECAST_TYPES.get(random.nextInt(randomMax));
                WeatherForecastNotification.Forecast forecast = WeatherForecastNotification.Forecast.newBuilder().setTime(forecastHour).setType(forecastType).build();
                forecasts.add(forecast);

                forecastHour = Timestamps.add(forecastHour, HOUR_DAY_DURATION);
            }

            WeatherForecastNotification forecastNotification = WeatherForecastNotification.newBuilder().setCity(city).setDay(timestamp).addAllForecastList(forecasts).build();
            result.add(forecastNotification);
        }

        return result;
    }
}
