package com.example.scala.backgroundProcess;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

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
                new CustomThreadFactory("pool-num"),
                new CustomRejectionHandler());
        scheduleTask();
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        logger.info("Scheduled task started");

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
                // MskMonitorThread task = new MskMonitorThread(userService, "Sample Command");
                logger.info("Submitting task to executor");
                executor.execute(new MskMonitorThread(userService, "Sample Command"));
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception", e);
                Thread.currentThread().interrupt();
            }
            logger.info("Task in Process: " + Thread.currentThread().getName());
        });

        logger.info("Scheduled task finished");
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Shutting down executor service");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
