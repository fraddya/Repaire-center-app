package com.rcs.domain.base;

public class ServiceException extends RuntimeException {

  public ServiceException(final String errorMessage) {
    super(errorMessage);
  }

  public ServiceException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }
}