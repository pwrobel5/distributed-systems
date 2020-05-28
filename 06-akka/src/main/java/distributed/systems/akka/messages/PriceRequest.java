package distributed.systems.akka.messages;

import akka.actor.ActorRef;

public class PriceRequest {
    private final String productName;
    private final ActorRef server;

    public PriceRequest(String productName, ActorRef server) {
        this.productName = productName;
        this.server = server;
    }

    public String getProductName() {
        return productName;
    }

    public ActorRef getServer() {
        return server;
    }
}
