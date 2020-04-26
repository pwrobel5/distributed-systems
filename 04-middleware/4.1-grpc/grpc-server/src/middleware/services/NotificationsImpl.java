package middleware.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import middleware.events.*;
import middleware.generators.CulturalEventGenerator;
import middleware.generators.WeatherForecastGenerator;

import java.util.List;
import java.util.logging.Logger;

public class NotificationsImpl extends NotificationsGrpc.NotificationsImplBase {
    private static final int notificationInterval = 5000;
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void subscribeCulturalNewsletter(CulturalNewsletterSubscription request, StreamObserver<CulturalEventNotification> responseObserver) {
        try {
            CulturalEventGenerator generator = new CulturalEventGenerator(request);
            logger.info("New client connection on Cultural Newsletter");

            while (true) {
                CulturalEventNotification nextNotification = generator.getEvent();
                responseObserver.onNext(nextNotification);
                logger.info("Sent cultural newsletter");
                try {
                    Thread.sleep(notificationInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.warning("Interrupted sleep");
                }
            }
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid arguments sent!").asRuntimeException());
            logger.warning("Connection to client failed - incorrect request");
        }
    }

    @Override
    public void subscribeWeatherForecast(WeatherForecastSubscription request, StreamObserver<WeatherForecastNotification> responseObserver) {
        WeatherForecastGenerator generator = new WeatherForecastGenerator(request);
        logger.info("New client connection on Weather Forecast");

        while (true) {
            List<WeatherForecastNotification> nextNotificationsList = generator.getForecast();
            for (WeatherForecastNotification nextNotification : nextNotificationsList) {
                responseObserver.onNext(nextNotification);
            }
            logger.info("Sent weather forecasts");
            try {
                Thread.sleep(notificationInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.warning("Interrupted sleep");
            }
        }
    }
}