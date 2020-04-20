package rabbitmq.connection;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionHandler {
    private final Connection connection;
    private final Channel channel;

    public ConnectionHandler(String hostName, String exchangeName, BuiltinExchangeType exchangeType) throws TimeoutException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostName);
        this.connection = connectionFactory.newConnection();
        this.channel = this.connection.createChannel();
        this.channel.exchangeDeclare(exchangeName, exchangeType);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}
