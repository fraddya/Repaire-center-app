package com.rcs.dto.response.job;


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
public class JobCreateResponse {
    private Long id;

    private LocalDateTime jobDateAndTime;

    private JobStatus jobStatus;

    private String jobDescription;

    private BigInteger estimateTime;

    private BigDecimal estimatePrice;

    private BigDecimal actualPrice;

}
