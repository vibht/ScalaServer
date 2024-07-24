package com.example.scala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.scala.service.ServerHealthUsingSNMP;
import com.example.scala.service.sendSystemHealthToClientActiveMq;
import com.example.scala.thread.xmppSendServiceRequestThread;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ScalaApplication {
	@Autowired
	private xmppSendServiceRequestThread XmppSendServiceRequest;

	// @Autowired
	// private ServerHealthUsingSNMP serverHealthUsingSNMP;

	public static void main(String[] args) {

		ServerHealthUsingSNMP serverHealthUsingSNMP = new ServerHealthUsingSNMP();

        Thread firstAppThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendSystemHealthToClientActiveMq.sendToClientSystemHealthReport(null);
            }
        });

        firstAppThread.start();

		SpringApplication.run(ScalaApplication.class, args);
		// XmppSendServiceRequest.getRequestServiceFromClientUsingXmpp();

	}

	@Bean
	public String poolName() {
		return "my-custom-pool"; // Replace with your desired pool name
	}
}
