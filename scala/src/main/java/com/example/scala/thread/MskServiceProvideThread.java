package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.scala.socket.MBMSUserServiceSocket;

public class MskServiceProvideThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(MskServiceProvideThread.class);

    private MBMSUserServiceSocket provideService;

    public MskServiceProvideThread(MBMSUserServiceSocket provideService) {
        this.provideService = provideService;

    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1; i++) {
                provideService.sendUserServiceToUE();
                logger.info("Provide Service" + i);
                Thread.sleep(2000);

            }

        } catch (Exception e) {

            e.printStackTrace();
            logger.info("Error are found in Thread" + e.getMessage());
        }
    }

}
