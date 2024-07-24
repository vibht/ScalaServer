package com.example.scala.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import com.example.scala.model.SNMPModel;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class sendSystemHealthToClientActiveMq {

    static List<SNMPModel> lstValue = new ArrayList<>();

    public static void sendToClientSystemHealthReport(String[] args) {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = null;
        try {
            connection = factory.createConnection("root", "root");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("SNMP.QUEUE");

            // Create a MessageProducer from the Session to the Queue
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            ServerHealthUsingSNMP snmp = new ServerHealthUsingSNMP();

            SNMPModel cpu = snmp.getCupLoad();
            SNMPModel ram = snmp.getRamInfo();
            System.out.println("========== CPU ===============" + cpu);
            System.out.println("================= RAM =================" + ram);
            lstValue.add(cpu);
            lstValue.add(ram);

            TextMessage message = session.createTextMessage(lstValue.toString());
            producer.send(message);

            System.out.println("Message sent: " + message.getText());

            // Use the correct confirmation queue name
            Queue confirmationQueue = session.createQueue("CONFIRMATION.SNMP.QUEUE");

            MessageConsumer consumer = session.createConsumer(confirmationQueue);

            // Wait for a confirmation message
            Message confirmationMessage = consumer.receive(20000); // Wait for 20 seconds

            if (confirmationMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) confirmationMessage;
                String confirmationText = textMessage.getText();
                System.out.println("Received confirmation: " + confirmationText);
            } else if (confirmationMessage == null) {
                System.out.println("No confirmation message received within the given timeout.");
            } else {
                System.out.println("Received non-text confirmation message: " + confirmationMessage);
            }

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
