package rabbitmq.queue;

import com.rabbitmq.client.*;
import rabbitmq.model.Message;

import java.io.IOException;

public class MessageReceiver implements Runnable {
    private final Channel channel;
    private final java.util.function.Consumer<Message> messageConsumer;
    private final String routingKey;
    private final String queueName;

    public MessageReceiver(Connection connection, String queueName, String routingKey, java.util.function.Consumer<Message> messageConsumer, String exchangeName) throws IOException {
        this.channel = connection.createChannel();
        this.routingKey = routingKey;
        this.messageConsumer = messageConsumer;
        this.queueName = queueName;

        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void bindAnotherKey(String newKey, String newExchangeName) throws IOException {
        channel.queueBind(queueName, newExchangeName, newKey);
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
