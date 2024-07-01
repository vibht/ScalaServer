package com.example.scala.entity;

import java.util.List;

import com.example.scala.helper.ObjectConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MTK {
    @Id
    private String mtkId;
    private List<String> transportService;
    @Convert(converter = ObjectConverter.class)
    private Object valueOfService;
    private List<String> rtpSession;
    private List<String> fluteChannel;

}