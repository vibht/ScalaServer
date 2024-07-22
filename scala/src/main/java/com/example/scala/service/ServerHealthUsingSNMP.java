package com.example.scala.service;

import java.util.ArrayList;
import java.util.List;
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

import com.example.scala.model.SNMPModel;

@Service
public class ServerHealthUsingSNMP {

    private static final Logger logger = LogManager.getLogger(ServerHealthUsingSNMP.class);

    private boolean isFalg = false;

    public ServerHealthUsingSNMP() {

        // sendHealthCheckInfoToClient();
        // getCupLoad();
        // getRamInfo();
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

    public SNMPModel getCupLoad() {

        SNMPModel returnValues = new SNMPModel();
        List<SNMPModel> cpuInfoList = new ArrayList<>();
        Snmp snmp = null;
        TransportMapping<UdpAddress> transport = null;

        try {
            transport = new DefaultUdpTransportMapping();
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

            snmp = new Snmp(transport);
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

                        data = responsePDU.getVariableBindings();

                        cpuInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.10.1.3.1"),
                                "1 Minutes CPU Load", errorStatusText));
                        cpuInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.10.1.3.2"),
                                "5 Minutes CPU Load", errorStatusText));
                        cpuInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.10.1.3.3"),
                                "15 Minutes CPU Load", errorStatusText));

                        for (SNMPModel info : cpuInfoList) {
                            logger.info("CPU Load: {}", info);
                        }

                        logger.info("CPU Load: In 1 Minute: {} | 5 Minutes: {} | 15 Minutes: {}",
                                data.get(0).getVariable(), data.get(1).getVariable(), data.get(2).getVariable());

                        // logger.info("CPU Load: In 1 Minute {} : 5 Minutes {} : 15 Minutes {} "
                        // + responsePDU.getVariableBindings());
                    } else {
                        logger.info("Error: Request Failed");
                        logger.info("Error Status = {}", errorStatus);
                        logger.info("Error Index = {}", errorIndex);
                        logger.info("Error Status Text = {} ", errorStatusText);
                    }
                } else {
                    logger.info("Error: Response PDU is null");
                }

            } else {
                logger.info("Error: Agent Timeout...");
            }
            snmp.close();

            isFalg = true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("The Data is Not Found SomeThing went wrong!! {}", e.getMessage());

        } finally {
            try {
                if (snmp != null)
                    snmp.close();
                if (transport != null)
                    transport.close();
            } catch (Exception e) {
                logger.error("Error closing resources: {}", e.getMessage(), e);
            }
        }
        return returnValues;

    }

    public SNMPModel getRamInfo() {
        SNMPModel returnValues = new SNMPModel();
        List<SNMPModel> ramInfoList = new ArrayList<>();
        TransportMapping<UdpAddress> transport = null;
        Snmp snmp = null;

        try {
            transport = new DefaultUdpTransportMapping();
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

            snmp = new Snmp(transport);
            ResponseEvent response = snmp.send(pdu, target);

            if (response != null) {
                logger.info("Got Response from Agent");
                PDU responsePDU = response.getResponse();

                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    int errorIndex = responsePDU.getErrorIndex();
                    String errorStatusText = responsePDU.getErrorStatusText();

                    if (errorStatus == PDU.noError) {
                        ramInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.4.5.0"), "Total Ram",
                                errorStatusText));
                        ramInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.4.6.0"),
                                "Total Used Ram", errorStatusText));
                        ramInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.4.11.0"),
                                "Total Ram Free", errorStatusText));
                        ramInfoList.add(setRamInfoFromPDU(responsePDU, new OID(".1.3.6.1.4.1.2021.4.13.0"),
                                "Total Shared Ram", errorStatusText));

                        for (SNMPModel info : ramInfoList) {
                            logger.info("RAM Info: {}", info);
                        }
                    } else {
                        logger.error("Error: Request Failed - Status = {}, Index = {}, Text = {}", errorStatus,
                                errorIndex, errorStatusText);
                    }
                } else {
                    logger.error("Error: Response PDU is null");
                }
            } else {
                logger.error("Error: Agent Timeout...");
            }
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage(), e);
        } finally {
            try {
                if (snmp != null)
                    snmp.close();
                if (transport != null)
                    transport.close();
            } catch (Exception e) {
                logger.error("Error closing resources: {}", e.getMessage(), e);
            }
        }
        return returnValues;
    }

    private SNMPModel setRamInfoFromPDU(PDU responsePDU, OID oid, String id, String errorStatusText) {
        SNMPModel returnValues = new SNMPModel();
        VariableBinding vb = getSpecificVariableBinding(responsePDU, oid);
        if (vb != null) {
            returnValues.setDescription(errorStatusText);
            returnValues.setOIds(vb.getOid());
            returnValues.setValues(vb.getVariable().toString());
            returnValues.setIds(id);
            returnValues.setSummary(vb);
        }
        return returnValues;
    }

    private VariableBinding getSpecificVariableBinding(PDU responsePDU, OID oid) {
        for (VariableBinding vb : responsePDU.getVariableBindings()) {
            if (vb.getOid().equals(oid)) {
                return vb;
            }
        }
        return null;
    }

}
