package distributed.systems.akka.messages;

public class PriceResult {
    private final String productName;
    private final double price;
    private final int queriesNumber;

    public PriceResult(String productName, double price, int queriesNumber) {
        this.productName = productName;
        this.price = price;
        this.queriesNumber = queriesNumber;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQueriesNumber() {
        return queriesNumber;
    }
}
