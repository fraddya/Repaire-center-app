package com.rcs.domain.criteria;

import com.rcs.domain.Job;
import lombok.Data;

@Data
public class JobCriteria extends Job {

    private Long customerId;

    private Long assignEmployeeId;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
