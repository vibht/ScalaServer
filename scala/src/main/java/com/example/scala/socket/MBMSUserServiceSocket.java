package com.example.scala.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MBMSUserServiceSocket {
    private static final Logger logger = LogManager.getLogger(MBMSUserServiceSocket.class);

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
            e.printStackTrace();
            logger.info("Errro are found When Service is Send To UE {}", e.getMessage());

        }
        return null;
    }

}
