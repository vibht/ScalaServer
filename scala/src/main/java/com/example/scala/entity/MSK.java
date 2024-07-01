package com.example.scala.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MSK {
    @Id
    private String mskId;
    private List<String> service;
    private String domainId;

    @OneToMany
    @JoinColumn(name = "msk_id") // Assuming 'msk_id' is the foreign key column
    private List<MTK> mtk;  // Changed to List<MTKModel>
}
