package com.example.scala.helper;

import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomThreadFactory implements ThreadFactory {
    private String poolName;
    private int threadNum = 0;

    public CustomThreadFactory(@Autowired String poolName) {
        this.poolName = poolName;

    }

    @Override
    public Thread newThread(Runnable r) {

        try {
            Thread th = new Thread(r, poolName + "-" + threadNum++);
            th.setPriority(Thread.NORM_PRIORITY);
            th.setDaemon(false);
            return th;

        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Unimplemented method 'newThread'");
        }

    }

}