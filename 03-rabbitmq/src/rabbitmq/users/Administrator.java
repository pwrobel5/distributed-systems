package rabbitmq.users;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;
import rabbitmq.connection.ConnectionHandler;
import rabbitmq.model.Message;
import rabbitmq.model.MessageType;
import rabbitmq.queue.MessageReceiver;
import rabbitmq.queue.MessageSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Administrator {
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "Choose correct option:\na - send message to all agencies\n" +
            "b - send message to all carriers\nc - send message to all agencies and carriers\nexit - exit administrator panel\n[ADMINISTRATOR] ";
    private static final String ORDER_EXCHANGE_NAME = "space_exchange";
    private static final String ADMIN_EXCHANGE_NAME = "admin_exchange";

    private static void processMessage(Message message) {
        String sender = message.getSender();
        String body = message.getMessageBody();
        String messageType = message.getMessageType().toString();

        System.out.println("[" + messageType + "] From: " + sender + ", order number: " + body);
    }

    public static void main(String[] args) throws Exception {
        ConnectionHandler connectionHandler = new ConnectionHandler("localhost", ADMIN_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Connection connection = connectionHandler.getConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("[ADMINISTRATOR] Connection established\nWelcome to administrator panel");
        String input = "";

        Consumer<Message> messageConsumer = Administrator::processMessage;
        new Thread(new MessageReceiver(connection, "admin", "#", messageConsumer, ORDER_EXCHANGE_NAME)).start();

        Map<String, MessageSender> senders = new HashMap<>();
        senders.put("a", new MessageSender(connection, "admin.agencies", ADMIN_EXCHANGE_NAME));
        senders.put("b", new MessageSender(connection, "admin.carriers", ADMIN_EXCHANGE_NAME));
        senders.put("c", new MessageSender(connection, "admin.all", ADMIN_EXCHANGE_NAME));

        while (!input.equalsIgnoreCase(EXIT_COMMAND)) {
            System.out.print(MENU_TEXT);
            input = reader.readLine();

            String key = input.toLowerCase();
            if (senders.containsKey(key)) {
                System.out.print("[ADMINISTRATOR] Enter message: ");
                input = reader.readLine();

                Message message = new Message("Admin", input, MessageType.ADMIN_MESSAGE);
                senders.get(key).sendMessage(message);
            } else if (!key.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("[ADMINISTRATOR] Invalid command!");
            }
        }

        connectionHandler.closeConnection();
    }
}
