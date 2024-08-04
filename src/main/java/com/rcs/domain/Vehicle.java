package com.rcs.domain;

import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import com.rcs.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class Vehicle extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String vehicleNo;
    private String model;
    private String year;
    private String color;
    private String type;
    private String description;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
