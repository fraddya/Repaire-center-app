package com.rcs.dto.request.job;


import com.rcs.enums.JobStatus;
import com.rcs.enums.RepairerItemStatus;
import com.rcs.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobCreateRequest {

    @NotNull(message = "jobDate And Time is required.")
    private LocalDateTime jobDateAndTime;

    private JobStatus jobStatus;

    @NotNull(message = "job Description is required.")
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
    }
    @Data
    public static class VehicleData {
        private Long id;
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
        }
    }
}
