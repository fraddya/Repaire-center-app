package com.rcs.enums;

public enum RestApiResponseStatus {

    OK("OK", 200),
    VALIDATION_FAILURE("CLIENT_ERROR", 400),
    SUBSCRIPTION_ERROR("SUBSCRIPTION_ERROR", 402),
    ERROR("SYSTEM_ERROR", 500),
    NOT_FOUND("CLIENT_ERROR", 404);

    private String status;
    private Integer code;
    private String message;

    private RestApiResponseStatus(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    private RestApiResponseStatus(String status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return this.status + ":" + this.code;
    }
}
