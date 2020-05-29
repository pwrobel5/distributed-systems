package distributed.systems.akka.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.PriceResult;
import distributed.systems.akka.utils.Constants;

public class Client extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> request.getServer().tell(request, getSelf()))
                .match(PriceResult.class, result -> {
                    Double price = result.getPrice();
                    String productName = result.getProductName();
                    int queriesNumber = result.getQueriesNumber();

                    String message;
                    if (price.compareTo(Double.MAX_VALUE) == 0) {
                        message = String.format("No prices found for %s", productName);
                    } else {
                        message = String.format("Price found for %s, value: %f", productName, price);
                    }

                    if (queriesNumber != Constants.NO_QUERY_RESULTS) {
                        message = String.format("%s. Queries number: %d", message, queriesNumber);
                    }

                    System.out.println(message);
                })
                .matchAny(o -> log.info("Received unrecognized message"))
                .build();
    }
}
