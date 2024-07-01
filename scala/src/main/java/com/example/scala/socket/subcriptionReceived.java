package com.example.scala.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

@Service
public class subcriptionReceived {

    public boolean recieveSubscription() throws UnknownHostException {
        DatagramSocket socket = null;
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 89899;
        Boolean flag = false;
        try {
            byte[] bufferreceiver = new byte[1024];
            DatagramPacket packetReceive = new DatagramPacket(bufferreceiver, bufferreceiver.length);

            socket.receive(packetReceive);
            String receivePacket = new String(packetReceive.getData(), 0, packetReceive.getLength());

            System.out.println("return packet is" + receivePacket);
            flag = true;

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
    }

}
