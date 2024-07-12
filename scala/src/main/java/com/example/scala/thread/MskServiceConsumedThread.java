package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.scala.socket.MBMSUserServiceSocket;

@Component
public class MskServiceConsumedThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(MskServiceConsumedThread.class);

    private final MBMSUserServiceSocket service;

    @Autowired
    public MskServiceConsumedThread(MBMSUserServiceSocket service) {
        this.service = service;
    }

    public void consumedServiceInClient() throws Exception {
        System.out.println("Consumed Started ...");
        long result = this.service.consumed();
        System.out.println("Consumed Result: " + result);
    }

    @Override
    public void run() {
        try {
            consumedServiceInClient();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Errors found: " + e.getMessage());
        }
    }
}
