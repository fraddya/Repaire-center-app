package com.rcs.service;


import com.rcs.domain.Brand;

import java.util.List;

public interface BrandService {
    Brand createBrand(Brand brand);

    List<Brand> getAllBrands();

    Brand delete(Long id);
}
