package sockets.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
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
            Set<Pair<InetAddress, Integer>> clientUdpSockets = new HashSet<>();

            UDPHandler udpHandler = new UDPHandler(clientUdpSockets, portNumber);
            ExecutorService udpListenerExecutor = Executors.newSingleThreadExecutor();
            udpListenerExecutor.submit(udpHandler);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                TCPHandler TCPHandler = new TCPHandler(clientSocket, clientThreads);

                if (TCPHandler.isConnectionPossible(clientThreadsExecutor.getPoolSize(), clientThreadsExecutor.getMaximumPoolSize())) {
                    clientThreadsExecutor.submit(TCPHandler);
                }
            }
        } catch (IOException e) {
            System.out.println("Server error!");
        }
    }
}
