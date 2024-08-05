package com.rcs.service.Impl;

import com.querydsl.core.BooleanBuilder;
import com.rcs.domain.Job;
import com.rcs.domain.QJob;
import com.rcs.domain.RepairerItems;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.domain.criteria.JobCriteria;
import com.rcs.enums.Status;
import com.rcs.repository.JobRepository;
import com.rcs.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository repository;

    @Transactional
    @Override
    public Job create(Job job) {
        job.setStatus(Status.ACTIVE);
        for (RepairerItems repairerItems : job.getRepairerItems()) {
            repairerItems.setJob(job);
        }
        return repository.save(job);
    }

    @Transactional
    @Override
    public Job update(Job job) {
        Optional<Job> jobPersisted = repository.findById(job.getId());
        if (!jobPersisted.isPresent()) {
            throw new ComplexValidationException("Job retrieval", "Cannot find any Job for this id");
        } else {
            Job jobDb = jobPersisted.get();
            updateFields(job, jobDb);
            updateRepairerItems(job, jobDb);
            return repository.save(jobDb);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Job view(Long id) {
        Optional<Job> jobPersisted = repository.findById(id);
        if (!jobPersisted.isPresent()) {
            throw new ComplexValidationException("JobViewRequest", "Invalid Job id");
        }
        return jobPersisted.get();
    }

    @Transactional
    @Override
    public Job delete(Long id) {
        Optional<Job> jobPersisted = repository.findById(id);
        if (jobPersisted.isPresent()) {
            Job job = jobPersisted.get();
            job.setStatus(Status.DELETED);
            return repository.save(job);
        } else {
            throw new ComplexValidationException("JobDeleteRequest", "Invalid Job id");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Job> search(JobCriteria criteria) {
        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<Job> jobs = null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (criteria.getCustomerId() != null) {
            booleanBuilder.and(QJob.job.customer().id.eq(criteria.getCustomerId()));
        }
        if (criteria.getAssignEmployeeId() != null) {
            booleanBuilder.and(QJob.job.assignEmployee().id.eq(criteria.getAssignEmployeeId()));
        }
        if (criteria.getJobStatus() != null) {
            booleanBuilder.and(QJob.job.jobStatus.eq(criteria.getJobStatus()));
        }

        if (booleanBuilder.hasValue()) {
            jobs = repository.findAll(booleanBuilder, page);
        } else {
            jobs = repository.findAll(page);
        }
        return jobs;
    }

    private void updateFields(Job job, Job jobDb) {
        if (job.getJobDateAndTime() != null) {
            jobDb.setJobDateAndTime(job.getJobDateAndTime());
        }
        if (job.getJobStatus() != null) {
            jobDb.setJobStatus(job.getJobStatus());
        }
        if (job.getJobDescription() != null) {
            jobDb.setJobDescription(job.getJobDescription());
        }
        if (job.getEstimateTime() != null) {
            jobDb.setEstimateTime(job.getEstimateTime());
        }
        if (job.getEstimatePrice() != null) {
            jobDb.setEstimatePrice(job.getEstimatePrice());
        }
        if (job.getActualPrice() != null) {
            jobDb.setActualPrice(job.getActualPrice());
        }
        if (job.getStatus() != null) {
            jobDb.setStatus(job.getStatus());
        }
        if (job.getCustomer() != null) {
            jobDb.setCustomer(job.getCustomer());
        }
        if (job.getAssignEmployee() != null) {
            jobDb.setAssignEmployee(job.getAssignEmployee());
        }
        if (job.getVehicle() != null) {
            jobDb.setVehicle(job.getVehicle());
        }
    }

    private void updateRepairerItems(Job job, Job jobDb) {
        List<RepairerItems> newRepairerItems = new ArrayList<>();
        List<RepairerItems> deletedRepairerItems = new ArrayList<>();

        if (job.getRepairerItems() != null && !job.getRepairerItems().isEmpty()) {
            if (job.getRepairerItems().size() >= jobDb.getRepairerItems().size()) {
                for (RepairerItems updatedRepairerItem : job.getRepairerItems()) {
                    if (updatedRepairerItem.getId() == null) {
                        updatedRepairerItem.setJob(jobDb);
                        newRepairerItems.add(updatedRepairerItem);
                    } else {
                        Optional<RepairerItems> repairerItemFound = jobDb.getRepairerItems().stream()
                                .filter(item -> item.getId().equals(updatedRepairerItem.getId())).findFirst();
                        if (repairerItemFound.isPresent()) {
                            updateExistingRepairerItem(repairerItemFound.get(), updatedRepairerItem);
                        } else {
                            throw new ComplexValidationException("Repairer item update", "Invalid repairer item id. " + updatedRepairerItem.getId());
                        }
                    }
                }
            } else {
                for (RepairerItems existingRepairerItem : jobDb.getRepairerItems()) {
                    Optional<RepairerItems> repairerItemFound = job.getRepairerItems().stream()
                            .filter(item -> item.getId().equals(existingRepairerItem.getId())).findFirst();
                    if (repairerItemFound.isPresent()) {
                        updateExistingRepairerItem(existingRepairerItem, repairerItemFound.get());
                    } else {
                        deletedRepairerItems.add(existingRepairerItem);
                    }
                }
            }
            jobDb.getRepairerItems().addAll(newRepairerItems);
            jobDb.getRepairerItems().removeAll(deletedRepairerItems);
        }
    }

    private void updateExistingRepairerItem(RepairerItems existingRepairerItem, RepairerItems updatedRepairerItem) {
        if (updatedRepairerItem.getDescription() != null) existingRepairerItem.setDescription(updatedRepairerItem.getDescription());
        if (updatedRepairerItem.getQuantity() != null) existingRepairerItem.setQuantity(updatedRepairerItem.getQuantity());
        if (updatedRepairerItem.getEstimatePrice() != null) existingRepairerItem.setEstimatePrice(updatedRepairerItem.getEstimatePrice());
        if (updatedRepairerItem.getStatus() != null) existingRepairerItem.setStatus(updatedRepairerItem.getStatus());
        if (updatedRepairerItem.getPart() != null) existingRepairerItem.setPart(updatedRepairerItem.getPart());
    }
}
