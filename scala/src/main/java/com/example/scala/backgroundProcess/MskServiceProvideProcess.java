package com.example.scala.backgroundProcess;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.scala.helper.CustomRejectionHandler;
import com.example.scala.helper.CustomThreadFactory;
import com.example.scala.socket.MBMSUserServiceSocket;
import com.example.scala.thread.MskServiceProvideThread;

@Service
public class MskServiceProvideProcess {

    private static final Logger logger = LogManager.getLogger(MskServiceProvideProcess.class);

    private final ThreadPoolExecutor executor;

    @Autowired
    private MBMSUserServiceSocket sendService;

    public MskServiceProvideProcess() {
        this.executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                new CustomThreadFactory("null"), new CustomRejectionHandler());
                taskScheduled();

    }

    @Scheduled(fixedRate = 5000)
    public void taskScheduled() {
        logger.info("Scheduled task started");

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
                logger.info("");
                executor.execute(new MskServiceProvideThread(sendService));
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("error are found in Process" + e.getMessage());
                Thread.currentThread().interrupt();
            }

        });

    }

}
