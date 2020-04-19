package rabbitmq.utils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class MessageSender {
    private final Channel channel;
    private final String routingKey;
    private static final String EXCHANGE_NAME = "space_exchange";

    public MessageSender(Connection connection, String routingKey) throws IOException {
        this.channel = connection.createChannel();
        this.routingKey = routingKey;
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    }

    public void sendMessage(Message message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.toByteArray());
    }
}
