import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("CHAT TCP SERVER");
        final int portNumber = 4444;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Client nick: " + in.readLine());
            String msg = in.readLine();

            while (msg != null) {
                System.out.println("Received msg: " + msg);
                out.println("Hello");
                msg = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to open a socket!");
        }
    }
}
