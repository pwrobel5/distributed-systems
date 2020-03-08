package sockets.server;

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
            Map<String, TCPHandler> clientThreads = new HashMap<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                TCPHandler TCPHandler = new TCPHandler(clientSocket, clientThreads);

                if (TCPHandler.isConnectionPossible(clientThreadsExecutor.getPoolSize(), clientThreadsExecutor.getMaximumPoolSize())) {
                    clientThreadsExecutor.submit(TCPHandler);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to open a socket!");
        }
    }
}
