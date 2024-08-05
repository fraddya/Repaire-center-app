package com.rcs.controller;

import com.rcs.domain.Job;
import com.rcs.domain.base.PagingListResponseWrapper;
import com.rcs.domain.base.SingleItemResponseWrapper;
import com.rcs.domain.criteria.JobCriteria;
import com.rcs.dto.request.job.JobCreateRequest;
import com.rcs.dto.request.job.JobSearchRequest;
import com.rcs.dto.request.job.JobUpdateRequest;
import com.rcs.dto.response.job.JobCreateResponse;
import com.rcs.dto.response.job.JobViewResponse;
import com.rcs.mapper.JobMapper;
import com.rcs.service.JobService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper modelMapper;

    @PostMapping("${app.endpoint.jobCreate}")
    public ResponseEntity<SingleItemResponseWrapper<JobCreateResponse>> create(@RequestBody JobCreateRequest request) {
        Job job = modelMapper.mapToJob(request);
        Job persistedJob = jobService.create(job);
        JobCreateResponse response = modelMapper.mapToUpdateResponse(persistedJob);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.jobSearch}")
    public ResponseEntity<PagingListResponseWrapper<JobViewResponse>> retrieve(JobSearchRequest request) {
        JobCriteria criteria = modelMapper.mapToCriteria(request);
        Page<Job> results = jobService.search(criteria);
        List<JobViewResponse> responses = modelMapper.mapToSearchResponse(results.getContent());
        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());
        return new ResponseEntity<>(new PagingListResponseWrapper<>(responses, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.jobView}")
    public ResponseEntity<SingleItemResponseWrapper<JobViewResponse>> retrieve(@PathVariable Long id) {
        Job persistedJob = jobService.view(id);
        JobViewResponse response = modelMapper.mapToViewResponse(persistedJob);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.jobDelete}")
    public ResponseEntity<SingleItemResponseWrapper<JobCreateResponse>> delete(@PathVariable Long id) {
        Job persistedJob = jobService.delete(id);
        JobCreateResponse response = modelMapper.mapToUpdateResponse(persistedJob);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @PutMapping("${app.endpoint.jobUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<JobCreateResponse>> update(@PathVariable Long id, @RequestBody JobUpdateRequest request) {
        Job job = modelMapper.mapToUpdate(request);
        job.setId(id);
        Job persistedJob = jobService.update(job);
        JobCreateResponse response = modelMapper.mapToUpdateResponse(persistedJob);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }
}
