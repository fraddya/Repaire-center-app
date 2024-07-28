package com.rcs.domain;

import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import com.rcs.enums.JobStatus;
import com.rcs.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Job extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDateTime jobDateAndTime;

    private JobStatus jobStatus;

    private String jobDescription;

    private BigInteger estimateTime;

    private BigDecimal estimatePrice;

    private BigDecimal actualPrice;

    @OneToMany
    private List<RepairerItems> repairerItems;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    //need to add repairer estimate
    //also need to add repairer items
    private Status status;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "assign_employee_id")
    private User assignEmployee;
}