package com.rcs.domain.converter;


import com.rcs.enums.RepairerItemStatus;
import com.rcs.enums.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RepairerItemStatusConverter implements AttributeConverter<RepairerItemStatus, String> {

    @Override
    public String convertToDatabaseColumn(RepairerItemStatus repairerItemStatus) {
        return repairerItemStatus != null ? repairerItemStatus.getValue() : null;
    }

    @Override
    public RepairerItemStatus convertToEntityAttribute(String s) {
        return s != null ? RepairerItemStatus.getEnum(s) : null;
    }

}
