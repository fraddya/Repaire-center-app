package com.rcs.dto.response.user;

import com.rcs.dto.request.user.UserUpdateRequest;
import com.rcs.enums.GenderType;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserSearchResponse {

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

    private List<VehicleData> vehicle;

    @Data
    public static class VehicleData {
        private Long id;
        private String vehicleNo;
        private String model;
        private String year;
        private String color;
        private String type;
        private String description;
    }
}
