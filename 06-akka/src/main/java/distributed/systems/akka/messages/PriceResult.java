package distributed.systems.akka.messages;

public class PriceResult {
    private final String productName;
    private final double price;

    public PriceResult(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}
