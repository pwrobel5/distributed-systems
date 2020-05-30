package distributed.systems.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.messages.PriceResult;

public class Client extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final ActorRef server;

    public Client(ActorRef server) {
        super();
        this.server = server;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> server.tell(request, getSelf()))
                .match(PriceResult.class, result -> System.out.println(result.getMessage()))
                .matchAny(o -> log.info("Received unrecognized message"))
                .build();
    }
}
