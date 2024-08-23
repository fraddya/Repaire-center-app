package com.rcs.service.Impl;

import com.querydsl.core.BooleanBuilder;
import com.rcs.domain.Job;
import com.rcs.domain.QJob;
import com.rcs.domain.RepairerItems;
import com.rcs.domain.VehiclePart;
import com.rcs.domain.base.ComplexValidationException;
import com.rcs.domain.criteria.JobCriteria;
import com.rcs.enums.JobStatus;
import com.rcs.enums.RepairerItemStatus;
import com.rcs.enums.Status;
import com.rcs.repository.JobRepository;
import com.rcs.repository.VehiclePartRepository;
import com.rcs.service.EmailService;
import com.rcs.service.JobService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository repository;
    @Autowired
    private VehiclePartRepository vehiclePartRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public Job create(Job job) {
        //log.info("Job created: {}", job);
        VehiclePart vehiclePart = new VehiclePart();
        job.setStatus(Status.ACTIVE);
        job.setJobDateAndTime(LocalDateTime.now());
        job.setJobStatus(JobStatus.NEWREQUEST);
        for (RepairerItems repairerItems : job.getRepairerItems()) {
            repairerItems.setJob(job);
            if (repairerItems.getPart() != null) {
                vehiclePart = vehiclePartRepository.findById(repairerItems.getPart().getId()).get();
                repairerItems.setEstimatePrice(vehiclePart.getPrice());
                repairerItems.setQuantity(1);
                repairerItems.setDescription("test");
            }
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

    @Transactional
    @Override
    public Job taskDone(Job job) {
        Job jobDb = view(job.getId());
        List<RepairerItems> repairerItemsList = new ArrayList<>();
        if (jobDb == null) {
            throw new ComplexValidationException("Job retrieval", "Cannot find any Job for this id");
        }
        updateRepairerItems(job, jobDb);
        for (RepairerItems repairerItems : jobDb.getRepairerItems()) {
            if ( repairerItems.getStatus() == null || !repairerItems.getStatus().equals(RepairerItemStatus.DONE)) {
                repairerItemsList.add(repairerItems);
            }
        }
        if (repairerItemsList.size() == 0) {
            jobDb.setJobStatus(JobStatus.COMPLETE);

            //implement send email
            sendEmail(jobDb);
        }
        return repository.save(jobDb);
    }

    private void sendEmail(Job jobDb) {
        log.info("Invoice sent Start: {}");
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<h1 style='text-align: center;'>Invoice</h1>");
        htmlContent.append("<p>Invoice ID: ").append(jobDb.getId()).append("</p>");
        htmlContent.append("<p>Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("</p>");
        htmlContent.append("<p>Job Place Date: ").append(jobDb.getJobDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("</p>");
        htmlContent.append("<p>Repairer Done By: ").append(jobDb.getAssignEmployee().getFirstName()).append("</p>");
        htmlContent.append("<table>");
        htmlContent.append("<tr><th>Item</th><th>Description</th></tr>");
        for (RepairerItems item : jobDb.getRepairerItems()) {
            htmlContent.append("<tr>")
                    .append("<td>").append(item.getPart().getName()).append("</td>")
                    //.append("<td>").append(item.getPart().getPrice()).append("</td>")
                    .append("<td>").append(item.getDescription()).append("</td>")
                    .append("</tr>");
        }
        htmlContent.append("</table>");
        htmlContent.append("<p>Total: ").append(jobDb.getEstimatePrice()).append("</p>");
        htmlContent.append("<p style='text-align: center;'>Thanks For Using Our Service</p><p style='text-align: center;'>Team RCS</p>");

        //htmlContent.toString();

        try {
            emailService.sendInvoiceEmail(jobDb.getCustomer().getEmail(), "Your Invoice", htmlContent.toString());
            //return ResponseEntity.ok("Invoice sent successfully");
            log.info("Invoice sent End: {}");
        } catch (MessagingException e) {
            log.error("Invoice sent failed: {}", e.getMessage());
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send invoice");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Job view(Long id) {
        Optional<Job> jobPersisted = repository.findById(id);
        if (!jobPersisted.isPresent()) {
            throw new ComplexValidationException("JobViewRequest", "Invalid Job id");
        }
        jobPersisted.get().getRepairerItems().size();
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
        BooleanBuilder booleanBuilder = new BooleanBuilder(QJob.job.status.ne(Status.DELETED));

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
        for (Job job : jobs) {
            job.getRepairerItems().size();
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
