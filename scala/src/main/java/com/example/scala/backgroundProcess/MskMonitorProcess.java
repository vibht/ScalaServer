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
import com.example.scala.repositoty.UserserviceRepository;
import com.example.scala.thread.MskMonitorThread;



@Service
public class MskMonitorProcess {
    private static final Logger logger = LogManager.getLogger(MskMonitorProcess.class);

    @Autowired
    private UserserviceRepository userService;

    private final ThreadPoolExecutor executor;

    public MskMonitorProcess() {
        this.executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new CustomThreadFactory(),
                new CustomRejectionHandler());
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        logger.info("Scheduled task started");

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
                new MskMonitorThread(userService);
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception", e);
                Thread.currentThread().interrupt();
            }
            logger.info("Task in Process: " + Thread.currentThread().getName());
        });

        logger.info("Scheduled task finished");
    }
}
