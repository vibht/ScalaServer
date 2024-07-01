package com.example.scala.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.stereotype.Service;

@Service
public class MBMSUserService {

    public String userServiceAuthenticate() {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    public String sendUserServiceToUE() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            int port = 9876;
            String payLoadMessage = "hii";
            byte[] dataBuffer = payLoadMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(dataBuffer, dataBuffer.length, address, port);
            socket.send(packet);

        } catch (Exception e) {

        }
        return null;
    }

}
