package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.scala.socket.MBMSUserServiceSocket;

@Component
public class MskServiceProvideThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(MskServiceProvideThread.class);

    private final MBMSUserServiceSocket provideService;

    @Autowired
    public MskServiceProvideThread(MBMSUserServiceSocket provideService) {
        this.provideService = provideService;
    }

    @Override
    public void run() {
        try {
            System.out.println("Provider thread started...");
            long sum = this.provideService.provideAddPerform();
            System.out.println("Provider performed addition, sum: " + sum);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Errors found in Thread: " + e.getMessage());
        }
    }
}
