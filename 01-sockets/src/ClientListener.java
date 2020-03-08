import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener implements Runnable {
    private BufferedReader input;

    public ClientListener(BufferedReader input) {
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
        } catch (IOException e) {
            System.out.println("Error with reading message from server");
        }

        System.out.println("Server disconnected");
        System.exit(0);
    }
}
