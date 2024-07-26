package com.rcs.mapper;

import com.rcs.domain.Brand;
import com.rcs.dto.request.brand.BrandCreateRequest;
import com.rcs.dto.response.brand.BrandSearchResponse;
import com.rcs.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",imports = { Status.class})
public interface BrandMapper {
    Brand mapToCreate(BrandCreateRequest request);

    BrandSearchResponse mapToViewResponse(Brand persistedBrand);

    List<BrandSearchResponse> mapToSuggestion(List<Brand> results);

    //GeneralCreateResponse mapToDeleteResponse(Brand brand);
}
