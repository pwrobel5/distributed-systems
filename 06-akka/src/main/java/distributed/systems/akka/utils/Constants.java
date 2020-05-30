package distributed.systems.akka.utils;

public class Constants {
    public static final int STRATEGY_MAX_NUMBER_OF_ENTRIES = 10;
    public static final int STRATEGY_TIME_RANGE_MINUTES = 1;

    public static final int TIMEOUT_MILLIS = 300;

    public static final int SEARCH_TIME_LOWER_LIMIT = 100;
    public static final int SEARCH_TIME_UPPER_LIMIT = 500;

    public static final double PRICE_MIN = 1.0;
    public static final double PRICE_MAX = 10.0;

    public static final String DATABASE_URL = "jdbc:sqlite:queries.db";
    public static final String DATABASE_TABLE_NAME = "query_history";

    public static final int NO_QUERY_RESULTS = -1;

    public static final String ACTOR_SYSTEM_NAME = "ex6_system";
    public static final String HTTP_ACTOR_SYSTEM_NAME = "ex6_http";

    public static final String HTTP_HOST_NAME = "localhost";
    public static final int HTTP_PORT = 8080;
    public static final int HTTP_TERMINATION_TIMEOUT = 3;
    public static final int HTTP_ACTOR_REQUEST_TIMEOUT = 1;

    private Constants() {
    }
}
