package rabbitmq.users;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;
import rabbitmq.connection.ConnectionHandler;
import rabbitmq.model.Message;
import rabbitmq.model.MessageType;
import rabbitmq.model.OrderType;
import rabbitmq.queue.MessageReceiver;
import rabbitmq.queue.MessageSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static rabbitmq.model.OrderType.determineOrderType;

public class Agency {
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "Enter order type:\na - people transportation\n" +
            "b - cargo transportation\nc - send satellite to the orbit\nexit - end program\n[AGENCY] ";
    private static final String ORDER_EXCHANGE_NAME = "space_exchange";
    private static final String ADMIN_EXCHANGE_NAME = "admin_exchange";

    private static void processMessage(Message message) {
        String sender = message.getSender();
        String body = message.getMessageBody();
        MessageType messageType = message.getMessageType();
        System.out.print("[" + messageType.toString() + "] ");

        if (messageType == MessageType.ADMIN_MESSAGE) {
            System.out.println(body);
        } else {
            System.out.println(sender + " finished order number " + body);
        }
    }

    public static void main(String[] args) throws Exception {
        ConnectionHandler connectionHandler = new ConnectionHandler("localhost", ORDER_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Connection connection = connectionHandler.getConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("[AGENCY] Registration of new Agency");
        System.out.print("[AGENCY] Enter Agency name: ");
        String agencyName = reader.readLine();

        Map<OrderType, MessageSender> senders = new HashMap<>();
        senders.put(OrderType.CARGO, new MessageSender(connection, "order." + OrderType.CARGO.toString().toLowerCase(), ORDER_EXCHANGE_NAME));
        senders.put(OrderType.ORBIT, new MessageSender(connection, "order." + OrderType.ORBIT.toString().toLowerCase(), ORDER_EXCHANGE_NAME));
        senders.put(OrderType.PEOPLE, new MessageSender(connection, "order." + OrderType.PEOPLE.toString().toLowerCase(), ORDER_EXCHANGE_NAME));

        Consumer<Message> messageConsumer = Agency::processMessage;

        String queueName = "agency." + agencyName.toLowerCase();
        MessageReceiver messageReceiver = new MessageReceiver(connection, queueName, queueName, messageConsumer, ORDER_EXCHANGE_NAME);
        messageReceiver.bindAnotherKey("admin.agencies", ADMIN_EXCHANGE_NAME);
        messageReceiver.bindAnotherKey("admin.all", ADMIN_EXCHANGE_NAME);
        new Thread(messageReceiver).start();

        System.out.println("[AGENCY] Agency registration completed, welcome " + agencyName);

        String input = "";
        while (!input.equalsIgnoreCase(EXIT_COMMAND)) {
            System.out.print(MENU_TEXT);
            input = reader.readLine();
            OrderType orderType = determineOrderType(input);

            if (orderType != null) {
                System.out.print("[AGENCY] Enter order id: ");
                String orderNumber = reader.readLine();

                Message message = new Message(agencyName, orderNumber, MessageType.ORDER);
                senders.get(orderType).sendMessage(message);
                System.out.println("[AGENCY] Order sent!");
            } else if (!input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("[AGENCY] Invalid order type!");
            }
        }

        connectionHandler.closeConnection();
    }
}
