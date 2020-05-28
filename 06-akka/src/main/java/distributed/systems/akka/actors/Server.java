package distributed.systems.akka.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.AskTimeoutException;
import distributed.systems.akka.constants.Constants;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.PriceResult;
import distributed.systems.akka.messages.ShopPriceResult;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

import static akka.pattern.Patterns.ask;

public class Server extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final static SupervisorStrategy strategy =
            new AllForOneStrategy(10, Duration.ofMinutes(1), DeciderBuilder
                    .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.restart())
                    .build());

    private final static Duration TIMEOUT = Duration.ofMillis(Constants.TIMEOUT_MILLIS);

    @Override
    public void preStart() {
        context().actorOf(Props.create(Shop.class), "shop1");
        context().actorOf(Props.create(Shop.class), "shop2");
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> {
                    log.debug("Got price request for product %s\n", request.getProductName());
                    CompletableFuture<Object> price1 = ask(context().child("shop1").get(), request, TIMEOUT).toCompletableFuture();
                    CompletableFuture<Object> price2 = ask(context().child("shop2").get(), request, TIMEOUT).toCompletableFuture();

                    AtomicReference<Double> bestPrice = new AtomicReference<>(Double.MAX_VALUE);
                    Lock lock = new ReentrantLock();

                    ActorRef client = getSender();

                    BiConsumer<Object, Throwable> consumer = (response, exception) -> {
                        if (exception != null) {
                            if (exception.getClass() == AskTimeoutException.class) {
                                log.debug("Timeout passed");
                            } else {
                                log.error("Error occured");
                                System.err.println(exception.getMessage());
                            }
                        } else {
                            ShopPriceResult shopPriceResult = (ShopPriceResult) response;
                            log.debug("Price for %s received: %f\n", shopPriceResult.getProductName(), shopPriceResult.getPrice());

                            lock.lock();
                            try {
                                bestPrice.set(Math.min(shopPriceResult.getPrice(), bestPrice.get()));
                            } finally {
                                lock.unlock();
                            }
                        }
                    };

                    price1 = price1.whenComplete(consumer);
                    price2 = price2.whenComplete(consumer);

                    CompletableFuture.allOf(price1, price2).whenComplete((result, exception) -> {
                        log.debug("Sent value: " + bestPrice.toString());
                        client.tell(new PriceResult(request.getProductName(), bestPrice.get()), getSelf());
                    });
                })
                .matchAny(o -> log.info("Server received an unrecognized message"))
                .build();
    }
}
