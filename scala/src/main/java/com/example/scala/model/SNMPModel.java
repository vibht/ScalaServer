package com.example.scala.model;

import org.snmp4j.smi.OID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SNMPModel {
    private String Ids;
    private OID OIds;
    private String values;
    private Object summary;
    private String description;
}
