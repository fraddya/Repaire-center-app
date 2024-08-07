package com.rcs.service;

import com.rcs.domain.Job;
import com.rcs.domain.criteria.JobCriteria;
import org.springframework.data.domain.Page;

public interface JobService {

    Job create(Job job);

    Job update(Job job);

    Job taskDone(Job job);

    Job view(Long id);

    Job delete(Long id);

    Page<Job> search(JobCriteria criteria);
}
