package com.rcs.domain.criteria;

import com.rcs.domain.Job;
import lombok.Data;

@Data
public class JobCriteria extends Job {

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
