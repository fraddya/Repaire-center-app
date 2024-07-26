package com.rcs.controller;


import com.rcs.domain.VehiclePart;
import com.rcs.domain.base.ListResponseWrapper;
import com.rcs.domain.base.SingleItemResponseWrapper;
import com.rcs.dto.request.vehiclepart.VehiclePartCreateRequest;
import com.rcs.dto.response.vehiclepart.VehiclePartCreateResponse;
import com.rcs.dto.response.vehiclepart.VehiclePartViewResponse;
import com.rcs.mapper.VehiclePartMapper;
import com.rcs.service.VehiclePartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class VehiclePartController {

    @Autowired
    private VehiclePartService vehiclePartService;

    @Autowired
    private VehiclePartMapper vehiclePartMapper;

    @PostMapping("${app.endpoint.vehiclePartsCreate}")
    public ResponseEntity<SingleItemResponseWrapper<VehiclePartCreateResponse>> create(@RequestBody VehiclePartCreateRequest request) {

        VehiclePart vehiclePart = vehiclePartMapper.mapToCreate(request);

        VehiclePart persistedVehiclePart = vehiclePartService.create(vehiclePart);

        VehiclePartCreateResponse response = vehiclePartMapper.mapToCreateResponse(persistedVehiclePart);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @DeleteMapping("${app.endpoint.vehiclePartsDelete}")
    public ResponseEntity<SingleItemResponseWrapper<VehiclePartCreateResponse>> delete(@PathVariable Long id) {
        log.info("delete: {}", id);
        VehiclePart vehiclePart = vehiclePartService.delete(id);
        VehiclePartCreateResponse response = vehiclePartMapper.mapToCreateResponse(vehiclePart);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.vehiclePartsSuggestion}")
    public ResponseEntity<ListResponseWrapper<VehiclePartViewResponse>> suggestion(VehiclePartCreateRequest request) {
        VehiclePart vehiclePart = vehiclePartMapper.mapToCreate(request);
        List<VehiclePart> results = vehiclePartService.findAll(vehiclePart);
        List<VehiclePartViewResponse> vehicleParts = vehiclePartMapper.mapToViewResponse(results);
        return new ResponseEntity<>(new ListResponseWrapper<>(vehicleParts), HttpStatus.OK);
    }

    @PutMapping("${app.endpoint.vehiclePartsUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<VehiclePartCreateResponse>> update(@PathVariable Long id, @RequestBody VehiclePartCreateRequest request) {
        VehiclePart vehiclePart = vehiclePartMapper.mapToCreate(request);
        vehiclePart.setId(id);
        VehiclePart persistedVehiclePart = vehiclePartService.update(vehiclePart);
        VehiclePartCreateResponse response = vehiclePartMapper.mapToCreateResponse(persistedVehiclePart);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.vehiclePartsView}")
    public ResponseEntity<SingleItemResponseWrapper<VehiclePartViewResponse>> retrieve(@PathVariable Long id) {
        VehiclePart persistedVehiclePart = vehiclePartService.view(id);
        VehiclePartViewResponse response = vehiclePartMapper.mapToViewResponse(persistedVehiclePart);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

}
