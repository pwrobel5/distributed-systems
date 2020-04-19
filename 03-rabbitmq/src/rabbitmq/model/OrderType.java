package rabbitmq.model;

public enum OrderType {
    CARGO, PEOPLE, ORBIT;

    public static OrderType determineOrderType(String input) {
        switch (input.toLowerCase()) {
            case "a":
                return OrderType.PEOPLE;
            case "b":
                return OrderType.CARGO;
            case "c":
                return OrderType.ORBIT;
            default:
                return null;
        }
    }
}
