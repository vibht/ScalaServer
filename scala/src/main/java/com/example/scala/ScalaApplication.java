package com.example.scala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.scala.backgroundProcess.MskMonitorProcess;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ScalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScalaApplication.class, args);
	}

	@Bean
	public String poolName() {
		return "my-custom-pool"; // Replace with your desired pool name
	}
}
