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
import com.example.scala.thread.MskServiceConsumedThread;

@Service
public class MskServiceConsumedProcess {
    private static final Logger logger = LogManager.getLogger(MskServiceConsumedProcess.class);

    @Autowired
    private MBMSUserServiceSocket service;


    private final ThreadPoolExecutor executor;

    public MskServiceConsumedProcess() {
        this.executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                new CustomThreadFactory("null"), new CustomRejectionHandler());

        TaskSchedule();

    }

    @Scheduled(fixedRate = 5000)
    private void TaskSchedule() {
        executor.execute(() -> {
            try {
                logger.info("Task Schedule is Start..");
                executor.execute(new MskServiceConsumedThread(service));

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Thread.currentThread().interrupt();
                logger.info("The Error are found In ConsumedProcess" + e.getMessage());
            }

        });
    }

}
