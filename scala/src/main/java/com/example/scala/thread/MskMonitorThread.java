package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.scala.repositoty.UserserviceRepository;

public class MskMonitorThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(MskMonitorThread.class);

    private final UserserviceRepository userService;
    private String command;

    public MskMonitorThread(UserserviceRepository userService, String command) {
        this.userService = userService;
        this.command = command;
    }

    public void processCommand()throws InterruptedException {
        int count = 0;

        while (count <= 3) {
            try {
                Thread.sleep(2000);
                logger.info("Hello------------ Count: " + count);
                count++;
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                logger.error("Exception in thread", e);
                throw new UnsupportedOperationException("Unimplemented method 'run'", e);
            }
        }
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + " Start. Command = " + command);
        try {
            processCommand();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(Thread.currentThread().getName() + " End.");
    }
}
