package com.example.scala.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserserviceModel {
 
    private String userServiceId;
    private String serviceName;
    private List<String> lstOfId;
    private MSKModel msk; // Changed to @ManyToOne
     
}
