package com.rcs.dto.request.brand;


import com.rcs.enums.Status;
import lombok.Data;

@Data
public class BrandCreateRequest {
    private String name;
    private String code;
    private String description;
    private Status status;
}
