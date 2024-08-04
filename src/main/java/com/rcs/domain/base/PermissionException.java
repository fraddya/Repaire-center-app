package com.rcs.domain.base;

import java.util.List;

public class PermissionException extends BaseException {

    private static final long serialVersionUID = 4502802173447729724L;

    private String field;

    private String code;

    private List<ValidationFailure> validationFailures;

    public PermissionException(String field, String code) {
        this.field = field;
        this.code = code;
    }

    public PermissionException(List<ValidationFailure> validationFailures) {
        this.validationFailures = validationFailures;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public List<ValidationFailure> getValidationFailures() {
        return validationFailures;
    }
}

