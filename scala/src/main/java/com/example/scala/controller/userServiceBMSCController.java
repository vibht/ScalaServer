package com.example.scala.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scala.entity.Userservice;
import com.example.scala.service.MBMSUserservice;

@RestController
@RequestMapping("/api")
public class userServiceBMSCController {

    @Autowired
    private MBMSUserservice userService;

    private static final Logger logger = LogManager.getLogger(userServiceBMSCController.class);

    @PostMapping("/userService")
    public ResponseEntity<Userservice> saveUserService(@RequestBody Userservice model) {
        try {
            Userservice lstValue = userService.saveUserService(model);
            logger.info("The real Value is" + lstValue);
            return ResponseEntity.of(Optional.of(lstValue));

        } catch (Exception e) {

            e.printStackTrace();
            logger.info("Error are occurs" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/getUserService")
    public ResponseEntity<Userservice> getSpecificService(@PathVariable("id") String Id) {
        try {
            Userservice values = userService.getSpecificService(Id);
            return ResponseEntity.of(Optional.of(values));
        } catch (Exception e) {
            logger.info("These Specific Service is not found In this Id:" + Id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}