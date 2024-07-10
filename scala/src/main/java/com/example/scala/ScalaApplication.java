package com.example.scala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.scala.backgroundProcess.MskMonitorProcess;
import com.example.scala.thread.xmppSendServiceRequestThread;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ScalaApplication {
	@Autowired
	private xmppSendServiceRequestThread XmppSendServiceRequest;

	public static void main(String[] args) {
		SpringApplication.run(ScalaApplication.class, args);
		// XmppSendServiceRequest.getRequestServiceFromClientUsingXmpp();
	
	}

	@Bean
	public String poolName() {
		return "my-custom-pool"; // Replace with your desired pool name
	}
}
