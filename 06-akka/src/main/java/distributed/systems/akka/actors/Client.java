package distributed.systems.akka.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.PriceResult;

public class Client extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> request.getServer().tell(request, getSelf()))
                .match(PriceResult.class, result -> {
                    Double price = result.getPrice();
                    String productName = result.getProductName();
                    if (price.compareTo(Double.MAX_VALUE) == 0) {
                        System.out.printf("No prices found for %s\n", productName);
                    } else {
                        System.out.printf("Price found for %s, value: %f\n", productName, price);
                    }
                })
                .matchAny(o -> log.info("Received unrecognized message"))
                .build();
    }
}
