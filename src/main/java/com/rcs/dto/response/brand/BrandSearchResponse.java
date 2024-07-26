package com.rcs.dto.response.brand;


import com.rcs.enums.Status;
import lombok.Data;

@Data
public class BrandSearchResponse {
    private Long id;
    private String name;

    private Status status;
}
