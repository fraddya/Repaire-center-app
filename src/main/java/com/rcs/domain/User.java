package com.rcs.domain;


import com.rcs.domain.base.CreateModifyAwareBaseEntity;
import com.rcs.enums.GenderType;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class User extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String firstName;
    private String lastName;
    private String contactNo;
    private LocalDate dateJoin;
    private Integer age;
    private GenderType genderType;
    private String nic;
    private String nationality;
    private String image;
    private String religion;
    private String email;
    private LocalDateTime userLogging;
    private String passWord;
    private UserType role;
    private Status status;

    @OneToMany(mappedBy = "user" ,cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Vehicle> vehicle;
}
