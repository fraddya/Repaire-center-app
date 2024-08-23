package com.rcs.dto.request.job;


import com.rcs.domain.base.BaseSearchRequest;
import com.rcs.enums.GenderType;
import com.rcs.enums.JobStatus;
import com.rcs.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobSearchRequest extends BaseSearchRequest {

    private String token;

    private Long id;

    private LocalDateTime jobDateAndTime;

    private JobStatus jobStatus;

    private Long customerId;

    private Long assignEmployeeId;

    private String sortProperty = "lastModifiedAt";

    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}
