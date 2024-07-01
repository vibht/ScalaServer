package com.example.scala.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SendIpToClient {

    @PostConstruct
    public boolean sendIpToServer() {
        boolean flag = false;
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            int port = 54321;
            String message = "ip : '127.0.0.1',dest:'127.0.0.1', active:true, apiCredentials:'CR001A6YX789',port:12345";
            byte[] sendData = message.getBytes();
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(packet);
            flag = true;
            if(Boolean.TRUE.equals(flag)){
                
                byte[] bufferreceiver = new byte[1024];
                DatagramPacket packetReceive = new DatagramPacket(bufferreceiver, bufferreceiver.length);

                socket.receive(packetReceive);
                String receivePacket =new String(packetReceive.getData(), 0 , packetReceive.getLength());

                System.out.println("return packet is"+receivePacket);
            }
            return flag;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error are found" + e.getMessage());
            return flag;

        }

    }

}
