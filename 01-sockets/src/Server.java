import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final static int MAX_CLIENTS = 10;

    public static void main(String[] args) {
        System.out.println("CHAT TCP SERVER");
        final int portNumber = 4444;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            ThreadPoolExecutor clientThreadsExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_CLIENTS);
            Map<String, ClientHandler> clientThreads = new HashMap<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientThreads);

                if (clientHandler.isConnectionPossible(clientThreadsExecutor.getPoolSize(), clientThreadsExecutor.getMaximumPoolSize())) {
                    clientThreadsExecutor.submit(clientHandler);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to open a socket!");
        }
    }
}
