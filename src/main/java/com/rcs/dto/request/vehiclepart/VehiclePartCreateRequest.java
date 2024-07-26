package com.rcs.dto.request.vehiclepart;


import com.rcs.domain.Brand;
import com.rcs.enums.Status;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class VehiclePartCreateRequest {

    private Long id;

    private String partCode;

    private String name;

    private BigDecimal price;

    private String description;

    private BrandData brand;
    @Data
    public static class BrandData {
        private Long id;
    }

    private Status status;

}
