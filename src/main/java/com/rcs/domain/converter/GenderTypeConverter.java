package com.rcs.domain.converter;


import com.rcs.enums.GenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, String> {

    @Override
    public String convertToDatabaseColumn(GenderType attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public GenderType convertToEntityAttribute(String dbData) {
        return dbData != null ? GenderType.getEnum(dbData) : null;
    }
}
