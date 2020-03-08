import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private String clientNick;
    private BufferedReader input;
    private PrintWriter output;
    private Map<String, ClientHandler> clientThreads;

    private final static String OVERCROWDED_MESSAGE = "Server is overcrowded, try again later";
    private final static String NICK_ALREADY_TAKEN_MESSAGE = "This nick is already taken, try another one";
    private final static String ACCEPT_CLIENT_MESSAGE = "Accepted connection";
    private final static String EXIT_COMMAND = "QUIT";

    public ClientHandler(Socket clientSocket, Map<String, ClientHandler> clientThreads) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.clientThreads = clientThreads;
    }

    public boolean isConnectionPossible(int currentThreadsNumber, int maximumThreadsNumber) throws IOException {
        boolean result = true;
        String clientNick = input.readLine();

        if (currentThreadsNumber == maximumThreadsNumber) {
            output.println(OVERCROWDED_MESSAGE);
            result = false;
        } else if (this.clientThreads.containsKey(clientNick)) {
            output.println(NICK_ALREADY_TAKEN_MESSAGE);
            result = false;
        } else if (!clientNick.equals(EXIT_COMMAND)) {
            this.clientNick = clientNick;
            clientThreads.put(clientNick, this);
            output.println(ACCEPT_CLIENT_MESSAGE);
            System.out.println("Client " + clientNick + " connected");
        }

        return result;
    }

    public void sendMessage(String message) {
        this.output.println(message);
    }

    @Override
    public void run() {
        try {
            String message = input.readLine();
            while(!message.equals(EXIT_COMMAND)) {
                String formattedMessage = String.format("[%s] %s", clientNick, message);
                for(ClientHandler clientHandler : clientThreads.values()) {
                    if (clientHandler != this) clientHandler.sendMessage(formattedMessage);
                }

                message = input.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error with reading a message");
        }

        System.out.println("Client " + clientNick + " disconnected");
        this.clientThreads.remove(clientNick);
    }
}
