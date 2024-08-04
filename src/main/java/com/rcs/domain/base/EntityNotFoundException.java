package com.rcs.domain.base;


public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }

  public EntityNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
