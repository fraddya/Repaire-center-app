package com.rcs.domain.converter;


import com.rcs.enums.JobStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JobStatusConverter implements AttributeConverter<JobStatus, String> {

    @Override
    public String convertToDatabaseColumn(JobStatus attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public JobStatus convertToEntityAttribute(String dbData) {
        return dbData != null ? JobStatus.getEnum(dbData) : null;
    }
}
