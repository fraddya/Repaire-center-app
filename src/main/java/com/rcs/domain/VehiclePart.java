package com.rcs.domain;


import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import com.rcs.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table
@Getter
@Setter
public class VehiclePart extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String partCode;

    private String name;

    private BigDecimal price;

    private String description;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Status status;

}
