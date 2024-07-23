package com.example.scala.service;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scala.model.SNMPModel;

@Service
public class sendSystemHealthToClientActiveMq {

    static List<SNMPModel> lstValue = new ArrayList<>();

    public static void main(String[] args) {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = null;
        try {

            connection = factory.createConnection("root", "root");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("TEST.QUEUE");

            MessageProducer producer = session.createProducer(queue);

            ServerHealthUsingSNMP snmp = new ServerHealthUsingSNMP();

            SNMPModel cpu = snmp.getCupLoad();
            SNMPModel ram = snmp.getRamInfo();
            System.out.println("========== CPU ==============="+cpu);
            System.out.println("================= RAM ================="+ram);
            lstValue.add(cpu);
            lstValue.add(ram);

            TextMessage message = session.createTextMessage(lstValue.toString());
            producer.send(message);

            System.out.println("Message sent: " + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
