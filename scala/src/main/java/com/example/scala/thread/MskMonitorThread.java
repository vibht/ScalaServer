package com.example.scala.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.scala.repositoty.UserserviceRepository;

public class MskMonitorThread implements Runnable {
   private static final Logger logger = LogManager.getLogger(MskMonitorThread.class);

   private UserserviceRepository userServiceRepo;

   public MskMonitorThread(UserserviceRepository userService) {
      run();
      this.userServiceRepo =userService;
   }

   @Override
   public void run() {
      Integer count = 0;

      while (count <= 3) {

         try {
            logger.info("Hello------------");
            count++;
         } catch (Exception e) {
            logger.error("Exception in thread", e);
            throw new UnsupportedOperationException("Unimplemented method 'run'", e);
         }
      }
   }
}
