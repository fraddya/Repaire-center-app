package com.rcs.mapper;

import com.rcs.domain.Job;
import com.rcs.domain.criteria.JobCriteria;
import com.rcs.dto.request.job.JobCreateRequest;
import com.rcs.dto.request.job.JobSearchRequest;
import com.rcs.dto.request.job.JobUpdateRequest;
import com.rcs.dto.response.job.JobCreateResponse;
import com.rcs.dto.response.job.JobViewResponse;
import com.rcs.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",imports = {Status.class})
public interface JobMapper {
    Job mapToJob(JobCreateRequest job);

    Job mapToUpdate(JobUpdateRequest job);

    Job mapToJobUpdate(JobUpdateRequest request);

    JobCriteria mapToCriteria(JobSearchRequest request);

    List<JobViewResponse> mapToSearchResponse(List<Job> content);

    JobViewResponse mapToViewResponse(Job job);

    JobCreateResponse mapToUpdateResponse(Job jobUpdate);
}
