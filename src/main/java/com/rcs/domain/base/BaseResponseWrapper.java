package com.rcs.domain.base;


import com.rcs.enums.RestApiResponseStatus;

public class BaseResponseWrapper {

    public static BaseResponseWrapper OK;
    public String status;
    public Integer statusCode;
    public String message;

    public BaseResponseWrapper() {
    }

    public BaseResponseWrapper(RestApiResponseStatus restApiResponseStatus) {
        this.status = restApiResponseStatus.getStatus();
        this.statusCode = restApiResponseStatus.getCode();
        this.message = restApiResponseStatus.getMessage();
    }

    public static BaseResponseWrapper getOK() {
        return OK;
    }

    public static void setOK(BaseResponseWrapper OK) {
        BaseResponseWrapper.OK = OK;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    static {
        OK = new BaseResponseWrapper(RestApiResponseStatus.OK);
    }
}
