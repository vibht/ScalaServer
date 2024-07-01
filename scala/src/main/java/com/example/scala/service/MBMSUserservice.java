package com.example.scala.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scala.entity.Userservice;
import com.example.scala.repositoty.UserserviceRepository;

@Service
public class MBMSUserservice {

    @Autowired
    private UserserviceRepository userserviceRepository;

    public Userservice saveUserService(Userservice model) {
        Userservice values = new Userservice();
        try {
            String realServiceName = model.getServiceName();
            if (!realServiceName.isEmpty() && realServiceName != null) {
                values.setUserServiceId(model.getUserServiceId());
                values.setServiceName(model.getServiceName());
                values.setLstOfId(model.getLstOfId());
                // values.setMsk(model.getMsk());

                return userserviceRepository.save(values);

            }
            return values;

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'saveUserService'");
        }

    }

    public Userservice getSpecificService(String id) {
        Userservice userService = null;
        try {
            Optional<Userservice> uu = userserviceRepository.findById(id);
            if (uu.isPresent()) {
                userService.setUserServiceId(uu.get().getUserServiceId());
                userService.setServiceName(uu.get().getServiceName());
                userService.setMsk(uu.get().getMsk());
                userService.setLstOfId(uu.get().getLstOfId());

                return userService;

            }
            return userService;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'getSpecificService'");
        }

    }

}