package distributed.systems.akka.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.AskTimeoutException;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.ShopPriceResult;
import distributed.systems.akka.utils.Constants;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.Patterns.ask;

public class Shop extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final static SupervisorStrategy strategy =
            new AllForOneStrategy(Constants.STRATEGY_MAX_NUMBER_OF_ENTRIES, Duration.ofMinutes(Constants.STRATEGY_TIME_RANGE_MINUTES), DeciderBuilder
                    .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.restart())
                    .build());

    final static Duration timeout = Duration.ofMillis(Constants.TIMEOUT_MILLIS);

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> {
                    ActorRef requestSender = getSender();
                    CompletableFuture<Object> priceResult = ask(getContext().actorOf(Props.create(PriceSearcher.class)), request, timeout).toCompletableFuture();
                    priceResult.whenComplete((response, exception) -> {
                        if (exception != null) {
                            if (exception.getClass() == AskTimeoutException.class) {
                                log.debug("Timeout expired");
                            } else {
                                log.error("Error occured");
                                System.err.println(exception.getMessage());
                            }
                        } else {
                            log.debug("Price for %s received: %f, children number: %d\n", ((ShopPriceResult) response).getProductName(), ((ShopPriceResult) response).getPrice(), context().children().size());
                            requestSender.tell(response, getSelf());
                        }
                    });
                })
                .matchAny(o -> log.info("Shop received an unrecognized message"))
                .build();
    }
}