package distributed.systems.akka.messages;

public class ShopPriceResult {
    private final double price;

    public ShopPriceResult(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
