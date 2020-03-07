import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final static String EXIT_COMMAND = "QUIT";

    private static String readNick(BufferedReader in, PrintWriter output) {
        String nick = null;
        System.out.print("Enter your nick: ");
        try {
            nick = in.readLine();

            while(nick.trim().isEmpty()) {
                System.out.println("Incorrect nick!");
                nick = in.readLine();
            }

            output.println(nick);
        } catch (IOException e) {
            System.out.println("Error with reading nick!");
        }

        return nick;
    }

    private static void communicate(String clientNick, BufferedReader consoleInput, BufferedReader input, PrintWriter output) {
        String prompt = String.format("[%s]: ", clientNick);
        String readInput;

        try {
            System.out.print(prompt);
            readInput = consoleInput.readLine();

            while(!readInput.trim().equals(EXIT_COMMAND)) {
                output.println(readInput);

                String response = input.readLine();
                System.out.println("Received response: " + response);

                System.out.print(prompt);
                readInput = consoleInput.readLine();
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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            String clientNick = readNick(consoleIn, out);
            if(clientNick != null) communicate(clientNick, consoleIn, in, out);
        } catch (IOException e) {
            System.out.println("Error with opening a socket!");
        }
    }
}
