package rabbitmq.users;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.model.Message;
import rabbitmq.model.MessageType;
import rabbitmq.model.OrderType;
import rabbitmq.queue.MessageReceiver;
import rabbitmq.queue.MessageSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static rabbitmq.model.OrderType.determineOrderType;

public class Carrier {
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "Choose order types (max 2):\na - people transportation\n" +
            "b - cargo transportation\nc - send satellite to the orbit\nexit - end program\n";
    private static final String ORDER_EXCHANGE_NAME = "space_exchange";
    private static final String ADMIN_EXCHANGE_NAME = "admin_exchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ORDER_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Registration of new carrier");
        System.out.print("Enter carrier name: ");
        String carrierName = reader.readLine();

        Set<OrderType> handledTypes = new HashSet<>();

        Consumer<Message> messageConsumer = message -> {
            String sender = message.getSender();
            String body = message.getMessageBody();
            MessageType messageType = message.getMessageType();
            System.out.print("[" + messageType.toString() + "] ");

            if (messageType == MessageType.ADMIN_MESSAGE) {
                System.out.println(body);
            } else {
                System.out.println(" Received order number " + body + " from " + sender);

                try {
                    Thread.sleep(Integer.parseInt(body) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Completed order " + body);
                Message notification = new Message(carrierName, body, MessageType.NOTIFICATION);

                try {
                    MessageSender messageSender = new MessageSender(connection, "agency." + sender.toLowerCase(), ORDER_EXCHANGE_NAME);
                    messageSender.sendMessage(notification);
                } catch (IOException e) {
                    System.out.println("Cannot send notification to agency!");
                    e.printStackTrace();
                }
            }
        };

        String input = "";
        while (handledTypes.size() != 2) {
            System.out.println(MENU_TEXT);
            input = reader.readLine();
            OrderType orderType = determineOrderType(input);

            if (orderType != null) {
                handledTypes.add(orderType);
            } else if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                channel.close();
                connection.close();
                System.exit(0);
            } else {
                System.out.println("Invalid order type!");
            }
        }

        for (OrderType orderType : handledTypes) {
            String queueName = "order." + orderType.toString().toLowerCase();
            new Thread(new MessageReceiver(connection, queueName, queueName, messageConsumer, ORDER_EXCHANGE_NAME)).start();
        }

        String adminQueueName = "carrier." + carrierName;
        MessageReceiver messageReceiver = new MessageReceiver(connection, adminQueueName, "admin.carriers", messageConsumer, ADMIN_EXCHANGE_NAME);
        messageReceiver.bindAnotherKey("admin.all", ADMIN_EXCHANGE_NAME);
        new Thread(messageReceiver).start();

        while (!input.equalsIgnoreCase(EXIT_COMMAND)) {
            input = reader.readLine();
        }

        channel.close();
        connection.close();
    }
}
