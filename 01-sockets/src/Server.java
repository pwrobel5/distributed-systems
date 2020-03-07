import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final static int MAX_CLIENTS = 10;
    private final static String OVERCROWDED_MESSAGE = "Server is overcrowded, try again later";
    private final static String NICK_ALREADY_TAKEN_MESSAGE = "This nick is already taken, try another one";
    private final static String ACCEPT_CLIENT_MESSAGE = "Accepted connection";

    public static void main(String[] args) {
        System.out.println("CHAT TCP SERVER");
        final int portNumber = 4444;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            ThreadPoolExecutor clientThreadsExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_CLIENTS);
            Map<String, Socket> clientSockets = new HashMap<>();

            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                if (clientThreadsExecutor.getPoolSize() == clientThreadsExecutor.getMaximumPoolSize()) {
                    out.println(OVERCROWDED_MESSAGE);
                } else {
                    String clientNick = in.readLine();
                    if (clientSockets.containsKey(clientNick)) {
                        out.println(NICK_ALREADY_TAKEN_MESSAGE);
                    } else {
                        clientSockets.put(clientNick, clientSocket);
                        ClientThread clientThread = new ClientThread(clientNick);
                        clientThreadsExecutor.submit(clientThread);
                        System.out.println("Client " + clientNick + " connected");
                        out.println(ACCEPT_CLIENT_MESSAGE);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to open a socket!");
        }
    }
}
