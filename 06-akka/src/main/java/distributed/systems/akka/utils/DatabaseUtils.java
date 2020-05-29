package distributed.systems.akka.utils;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import java.sql.*;

public class DatabaseUtils {
    public static Connection getDatabaseConnection() {
        Connection connection = null;
        SQLiteConfig config = new SQLiteConfig();
        config.setOpenMode(SQLiteOpenMode.FULLMUTEX);

        try {
            connection = DriverManager.getConnection(Constants.DATABASE_URL, config.toProperties());
        } catch (SQLException e) {
            System.err.println("Error with getting database connection");
            System.err.println(e.getMessage());
        }

        return connection;
    }

    public static void createHistoryTable() {
        String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "product_name text PRIMARY KEY," +
                "queries_number integer NOT NULL" +
                ");", Constants.DATABASE_TABLE_NAME);

        try (Connection connection = getDatabaseConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.err.println("Error with creating query_history table");
            System.err.println(e.getMessage());
        }
    }
}
