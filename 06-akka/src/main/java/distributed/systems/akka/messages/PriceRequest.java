package distributed.systems.akka.messages;

public class PriceRequest {
    private final String productName;

    public PriceRequest(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
