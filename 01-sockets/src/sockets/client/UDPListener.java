package sockets.client;

import java.net.DatagramSocket;

public class UDPListener implements Runnable {
    private DatagramSocket udpSocket;

    public UDPListener(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {

    }
}
