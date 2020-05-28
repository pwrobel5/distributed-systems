package distributed.systems.akka.messages;

public class ShopPriceResult {
    private final String productName;
    private final double price;

    public ShopPriceResult(String productName, double price) {
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
