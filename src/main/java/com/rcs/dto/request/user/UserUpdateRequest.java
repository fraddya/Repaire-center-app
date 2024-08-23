package com.rcs.dto.request.user;



import com.rcs.enums.GenderType;
import com.rcs.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRequest {

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

    private List<VehicleData> vehicle;

    @Data
    public static class VehicleData {
        private Long id;
        @NotNull(message = "Vehicle No is required.")
        private String vehicleNo;
        @NotNull(message = "Model is required.")
        private String model;
        @NotNull(message = "Year is required.")
        private String year;
        @NotNull(message = "Color is required.")
        private String color;
        @NotNull(message = "Type is required.")
        private String type;
        private String description;
        private BrandData brand;

        @Data
        public static class BrandData {
            private Long id;
        }
    }
}
