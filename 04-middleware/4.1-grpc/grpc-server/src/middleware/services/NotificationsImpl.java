package middleware.services;

import io.grpc.stub.StreamObserver;
import middleware.events.*;
import middleware.generators.CulturalEventGenerator;
import middleware.generators.WeatherForecastGenerator;

import java.util.List;

public class NotificationsImpl extends NotificationsGrpc.NotificationsImplBase {
    private static final int notificationInterval = 5000;

    @Override
    public void subscribeCulturalNewsletter(CulturalNewsletterSubscription request, StreamObserver<CulturalEventNotification> responseObserver) {
        CulturalEventGenerator generator = new CulturalEventGenerator(request);

        while (true) {
            CulturalEventNotification nextNotification = generator.getEvent();
            responseObserver.onNext(nextNotification);
        }
    }

    @Override
    public void subscribeWeatherForecast(WeatherForecastSubscription request, StreamObserver<WeatherForecastNotification> responseObserver) {
        WeatherForecastGenerator generator = new WeatherForecastGenerator(request);

        while (true) {
            List<WeatherForecastNotification> nextNotificationsList = generator.getForecast();
            for (WeatherForecastNotification nextNotification : nextNotificationsList) {
                responseObserver.onNext(nextNotification);
            }
        }
    }
}