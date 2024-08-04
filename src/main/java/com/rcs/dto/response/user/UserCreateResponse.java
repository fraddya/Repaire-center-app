package com.rcs.dto.response.user;

import com.rcs.enums.GenderType;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import lombok.Data;

@Data
public class UserCreateResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String contactNo;
    private String dateJoin;
    private Integer age;
    private GenderType genderType;
    private String nic;
    private String nationality;
    private String image;
    private String religion;
    private String email;
    private String userLogging;
    private String passWord;
    private UserType role;
    private Status status;
}
