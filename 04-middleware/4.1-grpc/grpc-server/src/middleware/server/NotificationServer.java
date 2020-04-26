package middleware.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import middleware.services.NotificationsImpl;

import java.io.IOException;
import java.util.logging.Logger;

public class NotificationServer {
    private static final Logger logger = Logger.getLogger(NotificationServer.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 50055;
        server = ServerBuilder.forPort(port)
                .addService(new NotificationsImpl())
                .build()
                .start();
        logger.info("Server started, listening on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server");
                NotificationServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null)
            server.shutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null)
            server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final NotificationServer server = new NotificationServer();
        server.start();
        server.blockUntilShutdown();
    }
}
