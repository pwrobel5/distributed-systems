package rabbitmq.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.model.Message;

import java.io.IOException;

public class MessageSender {
    private final Channel channel;
    private final String routingKey;
    private final String exchangeName;

    public MessageSender(Connection connection, String routingKey, String exchangeName) throws IOException {
        this.channel = connection.createChannel();
        this.routingKey = routingKey;
        this.exchangeName = exchangeName;
    }

    public void sendMessage(Message message) throws IOException {
        channel.basicPublish(exchangeName, routingKey, null, message.toByteArray());
    }
}
