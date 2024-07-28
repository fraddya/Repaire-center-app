package com.rcs.domain;

import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table
public class RepairerItems extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String description;
    private Integer quantity;
    private BigDecimal estimatePrice;
    private String status;

    @OneToOne
    @JoinColumn(name = "part_id")
    private VehiclePart part;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}