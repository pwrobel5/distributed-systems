package rabbitmq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MessageReceiver implements Runnable {
    private final Channel channel;
    private final String queueName;
    private final java.util.function.Consumer<Message> messageConsumer;
    private static final String EXCHANGE_NAME = "space_exchange";

    public MessageReceiver(Connection connection, String routingKey, java.util.function.Consumer<Message> messageConsumer) throws IOException {
        this.channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        this.queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        this.messageConsumer = messageConsumer;
    }

    public MessageReceiver(Connection connection, String routingKey, String queueName, java.util.function.Consumer<Message> messageConsumer) throws IOException {
        this.channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        this.queueName = queueName;
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        this.messageConsumer = messageConsumer;
    }

    @Override
    public void run() {
        try {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    Message message = Message.fromByteArray(body);
                    if (message != null) {
                        messageConsumer.accept(message);
                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            channel.basicQos(1);
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
