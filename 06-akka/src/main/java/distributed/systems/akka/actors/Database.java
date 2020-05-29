package distributed.systems.akka.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import distributed.systems.akka.messages.DatabaseResult;
import distributed.systems.akka.messages.PriceRequest;
import distributed.systems.akka.utils.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static distributed.systems.akka.utils.DatabaseUtils.getDatabaseConnection;

public class Database extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private int readQueriesNumber(String productName) {
        int result = 0;
        String sqlQuery = String.format("SELECT * FROM %s WHERE product_name = '%s';", Constants.DATABASE_TABLE_NAME, productName);

        try (Connection connection = getDatabaseConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                result = resultSet.getInt("queries_number");
            }

        } catch (SQLException e) {
            System.err.println("Error with reading from database");
            System.err.println(e.getMessage());
        }

        return result;
    }

    private void updateQueriesNumber(String productName, int currentValue) {
        String sqlQuery;

        if (currentValue++ == 0) {
            sqlQuery = String.format("INSERT INTO %s (product_name, queries_number) VALUES ('%s', %d);", Constants.DATABASE_TABLE_NAME, productName, currentValue);
        } else {
            sqlQuery = String.format("UPDATE %s SET queries_number = %d WHERE product_name = '%s';", Constants.DATABASE_TABLE_NAME, currentValue, productName);
        }

        try (Connection connection = getDatabaseConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.err.println("Error with writing to database");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PriceRequest.class, request -> {
                    String productName = request.getProductName();
                    log.debug("Got request to database for product %s", productName);
                    int queriesNumber = readQueriesNumber(productName);

                    DatabaseResult response = new DatabaseResult(queriesNumber);
                    getSender().tell(response, getSelf());
                    log.debug("Sent result for product %s, equal %d", productName, queriesNumber);
                    updateQueriesNumber(productName, queriesNumber);

                    context().stop(getSelf());
                })
                .matchAny(o -> log.info("Received an unrecognized message"))
                .build();
    }
}
