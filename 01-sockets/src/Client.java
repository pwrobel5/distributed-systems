import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private final static String EXIT_COMMAND = "QUIT";
    private final static char ACCEPTING_MESSAGE_BEGINNING = 'A';

    private static String readNick(BufferedReader consoleInput, BufferedReader socketInput, PrintWriter socketOutput) {
        String nick = null;

        System.out.print("Enter your nick: ");
        try {
            nick = consoleInput.readLine();

            while (nick.trim().isEmpty()) {
                System.out.println("Incorrect nick!");
                System.out.print("Enter your nick: ");
                nick = consoleInput.readLine();
            }

            socketOutput.println(nick);

            if (nick.equals(EXIT_COMMAND)) return null;
            String response = socketInput.readLine();
            System.out.println(response);
            if (response.charAt(0) != ACCEPTING_MESSAGE_BEGINNING) nick = null;

        } catch (IOException e) {
            System.out.println("Error with reading nick!");
        }

        return nick;
    }

    private static void sendMessages(String clientNick, BufferedReader consoleInput, PrintWriter socketOutput) {
        String prompt = String.format("[%s]: ", clientNick);
        String readInput = "";

        try {
            while (!readInput.trim().equals(EXIT_COMMAND)) {
                readInput = consoleInput.readLine();
                System.out.println(prompt + readInput);
                socketOutput.println(readInput);
            }
        } catch (IOException e) {
            System.out.println("Error with reading message!");
        }
        System.out.println("Closing Client chat");
    }

    public static void main(String[] args) {
        System.out.println("CHAT CLIENT");
        String hostName = "localhost";
        final int portNumber = 4444;

        try (Socket socket = new Socket(hostName, portNumber)) {
            PrintWriter socketOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String clientNick = readNick(consoleInput, socketInput, socketOutput);
            if (clientNick != null) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(new ClientListener(socketInput));
                sendMessages(clientNick, consoleInput, socketOutput);
            }
        } catch (IOException e) {
            System.out.println("Error with opening a socket!");
        }
    }
}
