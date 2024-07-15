package com.example.scala.service;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Service;

@Service
public class ServerHealthUsingSNMP {

    private static final Logger logger = LogManager.getLogger(ServerHealthUsingSNMP.class);

    private boolean isFalg = false;

    public ServerHealthUsingSNMP() {

        // sendHealthCheckInfoToClient();
        getCupLoad();
        getRamInfo(); 
    }

    public boolean sendHealthCheckInfoToClient() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("The Data is Not Found SomeThing went wrong!! {}", e.getMessage());
            return isFalg;
        }
        return isFalg;

    }

    public Vector<? extends VariableBinding> getCupLoad() {

        try {
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            CommunityTarget target = new CommunityTarget();
            target.setAddress(new UdpAddress("192.168.20.178/161"));
            target.setCommunity(new OctetString("public"));
            target.setVersion(SnmpConstants.version2c);
            target.setRetries(2);
            target.setTimeout(1500);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.10.1.3.1")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.10.1.3.2")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.10.1.3.3")));
            pdu.setType(PDU.GET);

            Snmp snmp = new Snmp(transport);
            logger.info("Sending Request To Agent");
            ResponseEvent response = snmp.send(pdu, target);

            // Process Agent Response
            if (response != null) {
                logger.info("Got Response from Agent");
                PDU responsePDU = response.getResponse();
                Vector<? extends VariableBinding> data = responsePDU.getVariableBindings();

                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    int errorIndex = responsePDU.getErrorIndex();
                    String errorStatusText = responsePDU.getErrorStatusText();

                    if (errorStatus == PDU.noError) {

                        logger.info("CPU Load: In 1 Minute {} : 5 Minutes {} : 15 Minutes {} "
                                + responsePDU.getVariableBindings());
                    } else {
                        logger.info("Error: Request Failed");
                        logger.info("Error Status = {}", errorStatus);
                        logger.info("Error Index = {}", errorIndex);
                        logger.info("Error Status Text = {} ", errorStatusText);
                    }
                } else {
                    logger.info("Error: Response PDU is null");
                }
                return data;
            } else {
                logger.info("Error: Agent Timeout...");
            }
            snmp.close();

            isFalg = true;
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("The Data is Not Found SomeThing went wrong!! {}", e.getMessage());
            return null;
        }

    }

    public Vector<? extends VariableBinding> getRamInfo() {
        try {

            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
            transport.listen();

            CommunityTarget target = new CommunityTarget();
            target.setAddress(new UdpAddress("192.168.20.178/161"));
            target.setCommunity(new OctetString("public"));
            target.setRetries(2);
            target.setVersion(SnmpConstants.version2c);
            target.setTimeout(1500);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.4.5.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.4.6.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.4.11.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2021.4.13.0")));
            pdu.setType(PDU.GET);

            Snmp snmp = new Snmp(transport);
            ResponseEvent response = snmp.send(pdu, target);

            if (response != null) {
                logger.info("Got Response from Agent");
                PDU responsePDU = response.getResponse();
                Vector<? extends VariableBinding> data = responsePDU.getVariableBindings();

                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    int errorIndex = responsePDU.getErrorIndex();
                    String errorStatusText = responsePDU.getErrorStatusText();

                    if (errorStatus == PDU.noError) {

                        logger.info(
                                "RAM Info: In Total Ram {} : Total Used Ram {} : Total Ram Free{} : Total Shared Ram {} "
                                        + responsePDU.getVariableBindings());
                    } else {
                        logger.info("Error: Request Failed");
                        logger.info("Error Status = {}", errorStatus);
                        logger.info("Error Index = {}", errorIndex);
                        logger.info("Error Status Text = {} ", errorStatusText);
                    }
                } else {
                    logger.info("Error: Response PDU is null");
                }
                return data;
            } else {
                logger.info("Error: Agent Timeout...");
            }
            snmp.close();

            isFalg = true;
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("The Data is Not Found SomeThing went wrong!! {}", e.getMessage());
            return null;
        }

    }
}
