package com.example.scala.helper;

import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Component;

@Component
public class CustomThreadFactory implements  ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
   
        try {
            Thread th =new Thread(r);
            th.setPriority(Thread.NORM_PRIORITY);
            th.setDaemon(false);
            return th;
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Unimplemented method 'newThread'");
        }
       
    }
    
}