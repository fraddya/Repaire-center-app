package com.rcs.repository;


import com.rcs.domain.Brand;
import com.rcs.domain.VehiclePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiclePartRepository extends JpaRepository<VehiclePart, Long>, QuerydslPredicateExecutor<VehiclePart> {
    List<VehiclePart> findAllByBrand(Brand brand);
}
