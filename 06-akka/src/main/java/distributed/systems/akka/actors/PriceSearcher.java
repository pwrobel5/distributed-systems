package distributed.systems.akka.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.ShopPriceResult;
import distributed.systems.akka.utils.Constants;

import java.util.Random;

public class PriceSearcher extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Random random = new Random();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> {
                    int sleepingTime = Constants.SEARCH_TIME_LOWER_LIMIT + random.nextInt(Constants.SEARCH_TIME_UPPER_LIMIT - Constants.SEARCH_TIME_LOWER_LIMIT + 1);
                    Thread.sleep(sleepingTime);

                    double price = Constants.PRICE_MIN + (Constants.PRICE_MAX - Constants.PRICE_MIN) * random.nextDouble();
                    getSender().tell(new ShopPriceResult(request.getProductName(), price), getSelf());
                    context().stop(getSelf());
                })
                .matchAny(o -> log.info("Received an unrecognized message"))
                .build();
    }
}
