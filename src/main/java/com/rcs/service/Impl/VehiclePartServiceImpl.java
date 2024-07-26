package com.rcs.service.Impl;


import com.rcs.domain.VehiclePart;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.enums.Status;
import com.rcs.repository.VehiclePartRepository;
import com.rcs.service.VehiclePartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VehiclePartServiceImpl implements VehiclePartService {

    @Autowired
    private VehiclePartRepository vehiclePartRepository;

    @Transactional
    @Override
    public VehiclePart create(VehiclePart vehiclePart) {
        vehiclePart.setStatus(Status.ACTIVE);
        return vehiclePartRepository.save(vehiclePart);
    }

    @Transactional
    @Override
    public VehiclePart update(VehiclePart vehiclePart) {
        Optional<VehiclePart> optionalVehiclePart = vehiclePartRepository.findById(vehiclePart.getId());
        if (!optionalVehiclePart.isPresent()) {
            throw new ComplexValidationException("vehicle part retrieve", "Invalid vehicle part id");
        }
        VehiclePart persistedVehiclePart = optionalVehiclePart.get();
        updateFields(vehiclePart, persistedVehiclePart);
        return vehiclePartRepository.save(persistedVehiclePart);
    }

    private void updateFields(VehiclePart vehiclePart, VehiclePart persistedVehiclePart) {
        if (vehiclePart.getPartCode() != null) persistedVehiclePart.setPartCode(vehiclePart.getPartCode());
        if (vehiclePart.getName() != null) persistedVehiclePart.setName(vehiclePart.getName());
        if (vehiclePart.getPrice() != null) persistedVehiclePart.setPrice(vehiclePart.getPrice());
        if (vehiclePart.getDescription() != null) persistedVehiclePart.setDescription(vehiclePart.getDescription());
        if (vehiclePart.getBrand() != null) persistedVehiclePart.setBrand(vehiclePart.getBrand());
    }


    @Transactional
    @Override
    public VehiclePart delete(Long id) {
        Optional<VehiclePart> vehiclePartPersisted = vehiclePartRepository.findById(id);
        if (vehiclePartPersisted.isPresent()) {
            VehiclePart vehiclePart = vehiclePartPersisted.get();
            vehiclePart.setStatus(Status.DELETED);
            return vehiclePartRepository.save(vehiclePart);
        }else {
            log.error("VehiclePart with id {} not found", id);
            throw new RuntimeException("VehiclePart with id " + id + " not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehiclePart> findAll(VehiclePart vehiclePart) {
        return vehiclePartRepository.findAllByBrand(vehiclePart.getBrand());
    }

    @Transactional(readOnly = true)
    @Override
    public VehiclePart view(Long id) {
        Optional<VehiclePart> vehiclePartPersisted = vehiclePartRepository.findById(id);
        if (vehiclePartPersisted.isPresent()) {
            return vehiclePartPersisted.get();
        } else {
            log.error("VehiclePart with id {} not found", id);
            throw new RuntimeException("VehiclePart with id " + id + " not found");
        }
    }
}
