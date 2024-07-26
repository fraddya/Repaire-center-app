package com.rcs.mapper;

import com.rcs.domain.VehiclePart;
import com.rcs.dto.request.vehiclepart.VehiclePartCreateRequest;
import com.rcs.dto.response.vehiclepart.VehiclePartCreateResponse;
import com.rcs.dto.response.vehiclepart.VehiclePartViewResponse;
import com.rcs.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",imports = { Status.class})
public interface VehiclePartMapper {

    VehiclePart mapToCreate(VehiclePartCreateRequest vehiclePartCreateRequest);

    VehiclePartViewResponse mapToViewResponse(VehiclePart persistedVehiclePart);

    VehiclePartCreateResponse mapToCreateResponse(VehiclePart persistedVehiclePart);

    List<VehiclePartViewResponse> mapToViewResponse(List<VehiclePart> persistedVehicleParts);
}
