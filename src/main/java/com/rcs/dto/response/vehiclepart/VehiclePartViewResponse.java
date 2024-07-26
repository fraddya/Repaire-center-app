package com.rcs.dto.response.vehiclepart;


import com.rcs.dto.request.vehiclepart.VehiclePartCreateRequest;
import com.rcs.enums.Status;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class VehiclePartViewResponse {

    private Long id;

    private String partCode;

    private String name;

    private BigDecimal price;

    private String description;

    private BrandData brand;
    @Data
    public static class BrandData {
        private Long id;
        private String name;
    }

    private Status status;

}
