package com.rcs.service;


import com.rcs.domain.VehiclePart;

import java.util.List;

public interface VehiclePartService {

    VehiclePart create(VehiclePart vehiclePart);

    VehiclePart update(VehiclePart vehiclePart);

    VehiclePart delete(Long id);

    List<VehiclePart> findAll(VehiclePart vehiclePart);

    VehiclePart view(Long id);
}
