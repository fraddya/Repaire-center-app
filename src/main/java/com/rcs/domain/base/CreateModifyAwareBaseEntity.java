package com.rcs.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public abstract class CreateModifyAwareBaseEntity {

    @Column(
            name = "created_by"
    )
    private Long createdBy = null;
    @LastModifiedBy
    @Column(
            name = "last_modified_by"
    )
    private Long lastModifiedBy = null;
    private LocalDate createdDate = LocalDate.now();
    @Column(
            name = "created_at"
    )
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(
            name = "last_modified_at"
    )
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public CreateModifyAwareBaseEntity() {
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
