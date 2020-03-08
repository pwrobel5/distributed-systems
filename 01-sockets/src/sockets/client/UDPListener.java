package sockets.client;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPListener implements Runnable {
    private DatagramSocket udpSocket;
    private static final int BUFFER_SIZE = 1024;

    public UDPListener(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {
        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        while (true) {
            try {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivedPacket);

                String msg = new String(receivedPacket.getData());
                System.out.println(msg);
            } catch (IOException e) {
                System.out.println("Error with reading UDP data!");
            }
        }
    }
}