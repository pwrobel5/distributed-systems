package sockets.client;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {

    private final String EXIT_COMMAND = "QUIT";
    private final String HOST_NAME = "localhost";
    private final int HOST_PORT_NUMBER = 4444;

    private Socket tcpSocket;
    private PrintWriter tcpSocketOutput;
    private BufferedReader tcpSocketInput;

    private DatagramSocket udpSocket;

    private BufferedReader consoleInput;

    private String nick;

    private void connect() {
        final String HELLO_UDP_MESSAGE = "H";
        final char ACCEPTING_MESSAGE_BEGINNING = 'A';

        System.out.print("Enter your nick: ");
        try {
            nick = consoleInput.readLine();

            while (nick.trim().isEmpty()) {
                System.out.println("Incorrect nick!");
                System.out.print("Enter your nick: ");
                nick = consoleInput.readLine();
            }

            tcpSocketOutput.println(nick);

            if (nick.equals(EXIT_COMMAND)) {
                nick = null;
                return;
            }

            String response = tcpSocketInput.readLine();
            System.out.println(response);

            if (response.charAt(0) != ACCEPTING_MESSAGE_BEGINNING) nick = null;
            else {
                InetAddress serverAddress = InetAddress.getByName(HOST_NAME);
                byte[] sendBuffer = HELLO_UDP_MESSAGE.getBytes();
                DatagramPacket helloUdpPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, HOST_PORT_NUMBER);
                udpSocket.send(helloUdpPacket);
            }

        } catch (IOException e) {
            System.out.println("Error with reading nick!");
        }
    }

    private void sendUdp(BufferedReader consoleInput) {
        final char DATA_UDP_MESSAGE = 'M';

        System.out.println("Entering UDP mode");
        System.out.print("Enter path to file with message: ");

        try {
            String filePath = consoleInput.readLine();
            File inputFile = new File(filePath);

            ByteArrayOutputStream messageStream = new ByteArrayOutputStream();
            messageStream.write(DATA_UDP_MESSAGE);
            messageStream.write(("[" + nick + "]:\n").getBytes());
            messageStream.write(Files.readAllBytes(inputFile.toPath()));
            byte[] message = messageStream.toByteArray();

            InetAddress address = InetAddress.getByName(HOST_NAME);
            DatagramPacket sendPacket = new DatagramPacket(message, message.length, address, HOST_PORT_NUMBER);

            udpSocket.send(sendPacket);

            String myMsg = new String(Arrays.copyOfRange(message, 1, message.length));
            System.out.println(myMsg);
        } catch(UnknownHostException e) {
            System.out.println("Error with UDP connection");
        } catch (IOException e) {
            System.out.println("Incorrect file path!");
        }
    }

    private void sendMessages() {
        final String UDP_COMMAND = "U";
        String prompt = String.format("[%s]: ", nick);
        String readInput = "";

        try {
            while (!readInput.trim().equals(EXIT_COMMAND)) {
                readInput = consoleInput.readLine();

                while(readInput.trim().equals(UDP_COMMAND)) {
                    sendUdp(consoleInput);
                    readInput = consoleInput.readLine();
                }

                System.out.println(prompt + readInput);
                tcpSocketOutput.println(readInput);
            }
        } catch (IOException e) {
            System.out.println("Error with sending message!");
        }
        System.out.println("Closing Client chat");
    }

    public static void main(String[] args) {
        System.out.println("CHAT CLIENT");
        Client client = new Client();

        try {
            client.tcpSocket = new Socket(client.HOST_NAME, client.HOST_PORT_NUMBER);
            client.tcpSocketOutput = new PrintWriter(client.tcpSocket.getOutputStream(), true);
            client.tcpSocketInput = new BufferedReader(new InputStreamReader(client.tcpSocket.getInputStream()));
            client.consoleInput = new BufferedReader(new InputStreamReader(System.in));

            client.udpSocket = new DatagramSocket();

            client.connect();
            if (client.nick != null) {
                ThreadPoolExecutor listenersExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
                listenersExecutor.submit(new TCPListener(client.tcpSocketInput));
                listenersExecutor.submit(new UDPListener(client.udpSocket));
                client.sendMessages();
            }

            client.tcpSocket.close();
            client.udpSocket.close();
        } catch (IOException e) {
            System.out.println("Error with opening a socket!");
        }
    }
}
