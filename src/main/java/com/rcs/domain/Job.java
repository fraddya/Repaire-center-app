package com.rcs.domain;

import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import com.rcs.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    //need to add repairer estimate
    //also need to add repairer items
    private String status;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "assign_employee_id")
    private User assignEmployee;
}
