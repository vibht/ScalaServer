package com.example.scala.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MBMSUserServiceSocket {
    private static final Logger logger = LogManager.getLogger(MBMSUserServiceSocket.class);

    private boolean isFlag = false;

    public String userServiceAuthenticate() {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    // if: isflag = true : RecieveClientAckno
    // if: isFalg = false : sendUserServiceToUE

    // @PostConstruct
    synchronized public String sendUserServiceToUE() throws Exception {
        try {
            if (isFlag) {

                wait();
            }
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            int port = 9876;
            String payLoadMessage = "hii";
            byte[] dataBuffer = payLoadMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(dataBuffer, dataBuffer.length, address, port);
            socket.send(packet);
            return payLoadMessage;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Errro are found When Service is Send To UE {}", e.getMessage());
            throw new Exception();

        } finally {
            isFlag = true;
            notify();
        }

    }

    synchronized public String RecieveClientAckno() throws InterruptedException, Exception {
        try {
            if (!isFlag) {
                wait();

            }
            DatagramSocket socket = new DatagramSocket(54321);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            logger.info("Receive Acknowledgment To Client Side {} ..", message);
            return message;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error are Found In Server Side Received Acknowledgment");
            throw new Exception("Error are found");
        } finally {
            isFlag = false;
            notify();

        }
    }

}
