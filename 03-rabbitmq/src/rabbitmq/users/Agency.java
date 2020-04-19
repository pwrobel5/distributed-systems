package rabbitmq.users;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.utils.Message;
import rabbitmq.utils.MessageSender;
import rabbitmq.utils.OrderType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static rabbitmq.utils.OrderType.determineOrderType;

public class Agency {
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "Enter order type:\na - people transportation\n" +
            "b - cargo transportation\nc - send satellite to the orbit\nexit - end program";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Registration of new Agency");
        System.out.print("Enter Agency name: ");
        String agencyName = reader.readLine();

        Map<OrderType, MessageSender> senders = new HashMap<>();
        senders.put(OrderType.CARGO, new MessageSender(connection, "order." + OrderType.CARGO.toString().toLowerCase()));
        senders.put(OrderType.ORBIT, new MessageSender(connection, "order." + OrderType.ORBIT.toString().toLowerCase()));
        senders.put(OrderType.PEOPLE, new MessageSender(connection, "order." + OrderType.PEOPLE.toString().toLowerCase()));

        System.out.println("Agency registration completed\nWelcome " + agencyName);

        String input = "";
        while (!input.equalsIgnoreCase(EXIT_COMMAND)) {
            System.out.println(MENU_TEXT);
            input = reader.readLine();
            OrderType orderType = determineOrderType(input);

            if (orderType != null) {
                System.out.print("Enter order id: ");
                String orderNumber = reader.readLine();

                Message message = new Message(agencyName, orderNumber);
                senders.get(orderType).sendMessage(message);
                System.out.println("Order completed!");
            } else if (!input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("Invalid order type!");
            }
        }

        channel.close();
        connection.close();
    }
}
