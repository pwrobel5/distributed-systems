package rabbitmq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageReceiver implements Runnable {
    private final Channel channel;
    private final String queueName;
    private static final String EXCHANGE_NAME = "space_exchange";

    public MessageReceiver(Connection connection, String routingKey) throws IOException {
        this.channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        this.queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
    }

    public MessageReceiver(Connection connection, String routingKey, String queueName) throws IOException {
        this.channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        this.queueName = queueName;
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
    }

    @Override
    public void run() {
        try {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("Received: " + message);
                }
            };

            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
