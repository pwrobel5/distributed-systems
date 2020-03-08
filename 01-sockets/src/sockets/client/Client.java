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
    private final String MULTICAST_ADDRESS = "230.0.0.0";
    private final int MULTICAST_PORT_NUMBER = 4446;

    private Socket tcpSocket;
    private PrintWriter tcpSocketOutput;
    private BufferedReader tcpSocketInput;

    private DatagramSocket udpSocket;
    private MulticastSocket multicastSocket;

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

    private byte[] loadFile(boolean writeHeader) throws IOException {
        final char DATA_UDP_MESSAGE = 'M';
        System.out.print("Enter path to file with message: ");

        String filePath = consoleInput.readLine();
        File inputFile = new File(filePath);

        ByteArrayOutputStream messageStream = new ByteArrayOutputStream();
        if(writeHeader) messageStream.write(DATA_UDP_MESSAGE);
        messageStream.write(("[" + nick + "]:\n").getBytes());
        messageStream.write(Files.readAllBytes(inputFile.toPath()));
        return messageStream.toByteArray();
    }

    private void sendUdp(String hostName, Integer hostPortNumber, boolean isMulticast) {

        try {
            byte[] message = loadFile(!isMulticast);

            InetAddress address = InetAddress.getByName(hostName);
            DatagramPacket sendPacket = new DatagramPacket(message, message.length, address, hostPortNumber);

            udpSocket.send(sendPacket);

            String myMsg = new String(Arrays.copyOfRange(message, 1, message.length));
            System.out.println(myMsg);
        } catch (UnknownHostException e) {
            System.out.println("Error with UDP connection");
        } catch (IOException e) {
            System.out.println("Incorrect file path!");
        }
    }

    private void sendMessages() {
        final String UDP_COMMAND = "U";
        final String MULTICAST_COMMAND = "M";
        String prompt = String.format("[%s]: ", nick);
        String readInput = "";

        try {
            while (!readInput.trim().equals(EXIT_COMMAND)) {
                readInput = consoleInput.readLine();

                while (readInput.trim().equals(UDP_COMMAND)) {
                    System.out.println("Entering UDP mode");
                    sendUdp(HOST_NAME, HOST_PORT_NUMBER, false);
                    readInput = consoleInput.readLine();
                }

                while (readInput.trim().equals(MULTICAST_COMMAND)) {
                    System.out.println("Entering multicast mode");
                    sendUdp(MULTICAST_ADDRESS, MULTICAST_PORT_NUMBER, true);
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

            client.multicastSocket = new MulticastSocket(client.MULTICAST_PORT_NUMBER);
            InetAddress multicastAddress = InetAddress.getByName(client.MULTICAST_ADDRESS);
            client.multicastSocket.joinGroup(multicastAddress);

            client.connect();
            if (client.nick != null) {
                ThreadPoolExecutor listenersExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
                listenersExecutor.submit(new TCPListener(client.tcpSocketInput));
                listenersExecutor.submit(new UDPListener(client.udpSocket));
                listenersExecutor.submit(new UDPListener(client.multicastSocket));
                client.sendMessages();
            }

            client.tcpSocket.close();
            client.udpSocket.close();
        } catch (IOException e) {
            System.out.println("Error with opening a socket!");
        }
    }
}
