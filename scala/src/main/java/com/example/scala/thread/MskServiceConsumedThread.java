package com.example.scala.thread;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.scala.socket.MBMSUserServiceSocket;

@Service
public class MskServiceConsumedThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(MskServiceConsumedThread.class);

    private MBMSUserServiceSocket service;
   

    public MskServiceConsumedThread(MBMSUserServiceSocket service) {
        this.service = service;

    }

    public void ConsumedServiceInClient() throws Exception {
        for (int i = 0; i < 1; i++) {
            
            logger.info("consumed Service" + i);
            service.RecieveClientAckno();
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
