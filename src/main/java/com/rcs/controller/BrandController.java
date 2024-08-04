package com.rcs.controller;


import com.rcs.domain.Brand;
import com.rcs.domain.base.ListResponseWrapper;
import com.rcs.domain.base.SingleItemResponseWrapper;
import com.rcs.dto.request.brand.BrandCreateRequest;
import com.rcs.dto.response.brand.BrandSearchResponse;
import com.rcs.mapper.BrandMapper;
import com.rcs.service.BrandService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Schema(name = "BrandController",defaultValue = "create/delete/suggession")
public class BrandController {
    @Autowired
    private BrandService service;

    @Autowired
    private BrandMapper modelMapper;


    @PostMapping("${app.endpoint.brandsCreate}")
    public ResponseEntity<SingleItemResponseWrapper<BrandSearchResponse>> create(@RequestBody BrandCreateRequest request) {
        Brand brand = modelMapper.mapToCreate(request);

        Brand persistedBrand = service.createBrand(brand);

        BrandSearchResponse response = modelMapper.mapToViewResponse(persistedBrand);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.brandsSuggestion}")
    public ResponseEntity<ListResponseWrapper<BrandSearchResponse>> suggestion() {
        List<Brand> results = service.getAllBrands();
        List<BrandSearchResponse> vehicles = modelMapper.mapToSuggestion(results);
        return new ResponseEntity<>(
                new ListResponseWrapper<>(vehicles), HttpStatus.OK);
    }

    /*@DeleteMapping("${app.endpoint.brandsDelete}")
    public ResponseEntity<SingleItemResponseWrapper<GeneralCreateResponse>> delete(@PathVariable Long id) {
        log.info("brandDb ====> {}",id);
        Brand brand = service.delete(id);
        GeneralCreateResponse response = modelMapper.mapToDeleteResponse(brand);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response, MessageConfig.getMessage("brand.delete")), HttpStatus.OK);
    }*/
}
