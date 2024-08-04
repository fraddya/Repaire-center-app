package com.rcs.dto.request.user;


import com.rcs.enums.GenderType;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotNull(message = "FirstName is required.")
    private String firstName;

    @NotNull(message = "LastName is required.")
    private String lastName;

    @NotNull(message = "Contact No is required.")
    private String contactNo;

    private String dateJoin;

    @NotNull(message = "Age is required.")
    private Integer age;

    @NotNull(message = "Gender Type is required.")
    private GenderType genderType;

    @NotNull(message = "Nic is required.")
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
