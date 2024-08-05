package com.rcs.dto.response.job;


import com.rcs.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobViewResponse {

    private Long id;

    private LocalDateTime jobDateAndTime;

    private JobStatus jobStatus;

    private String jobDescription;

    private BigInteger estimateTime;

    private BigDecimal estimatePrice;

    private BigDecimal actualPrice;

    private List<RepairerItemsData> repairerItems;

    private VehicleData vehicle;
    private Status status;
    private UserData customer;
    private UserData assignEmployee;
    @Data
    public static class UserData {
        private Long id;
        private String firstName;
        private String lastName;
        private String contactNo;
        private Integer age;
        private GenderType genderType;
        private String email;
        private UserType role;
    }
    @Data
    public static class VehicleData {
        private Long id;
        private String vehicleNo;
        private String model;
        private String year;
        private String color;
        private String type;
        private String description;
        private BrandData brand;
        @Data
        public static class BrandData {
            private Long id;
            private String name;
            private String code;
            private String description;
        }
    }
    @Data
    public static class RepairerItemsData {
        private Long id;
        private String description;
        private Integer quantity;
        private BigDecimal estimatePrice;
        private RepairerItemStatus status;
        private VehiclePartData part;
        @Data
        public static class VehiclePartData {
            private Long id;
            private String partCode;
            private String name;
            private BigDecimal price;
            private VehicleData.BrandData brand;
        }
    }
}
