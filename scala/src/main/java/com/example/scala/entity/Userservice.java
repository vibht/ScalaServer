package com.example.scala.entity;

import java.util.List;

import com.example.scala.model.MSKModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Userservice {
    @Id
    private String userServiceId;
    private String serviceName;
    private List<String> lstOfId;

    @ManyToOne
    private MSK msk; // Changed to @ManyToOne

    @PrePersist
    public void setServiceName() {
        if (serviceName == null) {
            serviceName = "All Service Is Provided";
        }
    }

}
