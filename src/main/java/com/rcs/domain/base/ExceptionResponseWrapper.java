package com.rcs.domain.base;


import com.rcs.enums.RestApiResponseStatus;

public class ExceptionResponseWrapper extends BaseResponseWrapper {
    public ExceptionResponseWrapper(String message) {
        super(RestApiResponseStatus.NOT_FOUND);
        this.message=message;
    }
}
