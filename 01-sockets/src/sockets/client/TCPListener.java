package sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class TCPListener implements Runnable {
    private BufferedReader input;

    public TCPListener(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        String readMessage;

        try {
            readMessage = input.readLine();
            while (readMessage != null) {
                System.out.println(readMessage);
                readMessage = input.readLine();
            }
        } catch(SocketException e) {
            System.out.println("Lost TCP connection");
        } catch (IOException e) {
            System.out.println("Error with reading TCP message from server");
        }

        System.out.println("Server disconnected");
        System.exit(0);
    }
}
