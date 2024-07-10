package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;

@Service
public class xmppSendServiceRequestThread implements Runnable {
    public xmppSendServiceRequestThread(){
        run();
    
    }

    private static final Logger logger = LogManager.getLogger(xmppSendServiceRequestThread.class);

    public String getRequestServiceFromClientUsingXmpp() {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword("user4", "123456")
                    .setXmppDomain("gui.coraltele.com")
                    .setHost("gui.coraltele.com")
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.required) // Change security mode
                    .build();

            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect().login();

            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            EntityBareJid jid = JidCreate.entityBareFrom("user3@gui.coraltele.com");
            Chat chat = chatManager.chatWith(jid);

            Message message = new Message(jid, Message.Type.chat);
            message.setBody("Hello, this is a message from user1!");
            chat.send(message);

            connection.disconnect();
            return "The Message Request Service is Send Successfully";

        } catch (Exception e) {
            logger.info("Error are found when sending Xmpp Service Request to client {}", e.getMessage());

        }
        return "aaaaaa";
    }

    @Override
    public void run() {
       String aa =  getRequestServiceFromClientUsingXmpp();
       System.out.println("________"+aa);
    }

}
