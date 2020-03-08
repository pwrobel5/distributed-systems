package sockets.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Set;

public class UDPHandler implements Runnable {
    private Set<Pair<InetAddress, Integer>> clientUdpSockets;
    private DatagramSocket udpSocket;
    private static final int BUFFER_SIZE = 1024;

    public UDPHandler(Set<Pair<InetAddress, Integer>> clientUdpSockets, int portNumber) throws SocketException {
        this.clientUdpSockets = clientUdpSockets;
        this.udpSocket = new DatagramSocket(portNumber);
    }

    @Override
    public void run() {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];

        while (true) {
            try {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivePacket);

                byte[] receivedData = receivePacket.getData();
                byte messageType = receivedData[0];
                InetAddress clientAddress = receivePacket.getAddress();
                Integer clientPort = receivePacket.getPort();
                Pair<InetAddress, Integer> senderData = new Pair<>(clientAddress, clientPort);

                if (messageType == 'H') {
                    clientUdpSockets.add(senderData);
                    System.out.println("[UDP] Client at " + clientAddress.toString() + ":" + clientPort + " connected");
                } else if (messageType == 'M') {
                    byte[] sendData = Arrays.copyOfRange(receivedData, 1, receivedData.length);

                    for (Pair<InetAddress, Integer> currentClient : clientUdpSockets) {
                        if (!currentClient.equals(senderData)) {
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, currentClient.getAddress(), currentClient.getPort());
                            udpSocket.send(sendPacket);
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("Error with reading from UDP socket!");
            }
        }
    }
}
