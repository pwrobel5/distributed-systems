package rabbitmq.users;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.utils.MessageReceiver;
import rabbitmq.utils.OrderType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static rabbitmq.utils.OrderType.determineOrderType;

public class Carrier {
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "Choose order types (max 2):\na - people transportation\n" +
            "b - cargo transportation\nc - send satellite to the orbit\nexit - end program";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Registration of new carrier");
        System.out.print("Enter carrier name: ");
        String carrierName = reader.readLine();

        Set<OrderType> handledTypes = new HashSet<>();

        while (handledTypes.size() != 2) {
            System.out.println(MENU_TEXT);
            String input = reader.readLine();
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
            new Thread(new MessageReceiver(connection, queueName, queueName)).start();
        }

        /*
        channel.close();
        connection.close();
        */
    }
}
