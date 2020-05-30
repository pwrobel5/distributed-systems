package distributed.systems.akka.messages;

import distributed.systems.akka.utils.Constants;

public class PriceResult {
    private String message;

    public PriceResult(String productName, Double price, int queriesNumber) {
        if (price.compareTo(Double.MAX_VALUE) == 0) {
            message = String.format("No prices found for %s", productName);
        } else {
            message = String.format("Price found for %s, value: %f", productName, price);
        }

        if (queriesNumber != Constants.NO_QUERY_RESULTS) {
            message = String.format("%s. Queries number: %d", message, queriesNumber);
        }
    }

    public String getMessage() {
        return message;
    }
}
