package com.example.scala.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scala.entity.Userservice;

@Repository
public interface UserserviceRepository extends JpaRepository<Userservice,String>{
    
}
