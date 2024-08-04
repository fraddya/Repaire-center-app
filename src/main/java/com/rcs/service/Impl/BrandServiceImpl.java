package com.rcs.service.Impl;


import com.rcs.domain.Brand;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.enums.Status;
import com.rcs.repository.BrandRepository;
import com.rcs.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository repository;

    @Transactional
    @Override
    public Brand createBrand(Brand brand) {
        Brand brandPersisted = repository.findByName(brand.getName());
        if (brandPersisted != null) {
            throw new ComplexValidationException("BrandCreateRequest", "Brand already registered");
        }
        brand.setStatus(Status.ACTIVE);
        return repository.save(brand);
    }
    @Transactional
    @Override
    public Brand delete(Long id) {
        Optional<Brand> brandPersisted = repository.findById(id);
        if(brandPersisted.isPresent()) {
            Brand brandDb = brandPersisted.get();
            brandDb.setStatus(Status.DELETED);
            return repository.save(brandDb);
        } else {
            throw new ComplexValidationException("BrandDeleteRequest", "Invalid Brand id");
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<Brand> getAllBrands() {
        return repository.findAll();
    }
}
