package com.example.scala.thread;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MskServiceConsumedThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(MskServiceConsumedThread.class);

    public MskServiceConsumedThread() {

    }

    public void ConsumedServiceInClient() throws InterruptedException {
        for (int i = 0; i <= 10; i++) {
            logger.info("consumed Service" + i);
            Thread.sleep(2000);
        }
    }

    @Override
    public void run() {
        try {
            ConsumedServiceInClient();

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error are found" + e.getMessage());
        }
    }

}
