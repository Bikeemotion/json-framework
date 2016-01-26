/**
 * Copyright (C) Bikeemotion
 * 2014
 *
 * The reproduction, transmission or use of this document or its contents is not
 * permitted without express written authorization. All rights, including rights
 * created by patent grant or registration of a utility model or design, are
 * reserved. Modifications made to this document are restricted to authorized
 * personnel only. Technical specifications and features are binding only when
 * specifically and expressly agreed upon in a written contract.
 */
package com.bikeemotion.json;

import com.bikeemotion.common.exception.BusinessException;

public class Exceptions {

  public static class InvalidDataTypeException extends BusinessException {
    public InvalidDataTypeException() {
    }

    public InvalidDataTypeException(String message) {
      super(message);
    }

    public InvalidDataTypeException(Throwable cause) {
      super(cause);
    }
  }

  public static class MandatoryValueExpectationFailedException extends
      BusinessException {
    public MandatoryValueExpectationFailedException() {
    }

    public MandatoryValueExpectationFailedException(String message) {
      super(message);
    }

    public MandatoryValueExpectationFailedException(Throwable cause) {
      super(cause);
    }
  }

  public static class UniqueValueExpectationFailedException extends
      BusinessException {
    public UniqueValueExpectationFailedException() {
    }

    public UniqueValueExpectationFailedException(String message) {
      super(message);
    }

    public UniqueValueExpectationFailedException(Throwable cause) {
      super(cause);
    }
  }

  public static class UniqueValueUnknownScopeTypeException extends
      BusinessException {
    public UniqueValueUnknownScopeTypeException() {
    }

    public UniqueValueUnknownScopeTypeException(String message) {
      super(message);
    }

    public UniqueValueUnknownScopeTypeException(Throwable cause) {
      super(cause);
    }
  }

  public static class UnknownDataTypeException extends BusinessException {
    public UnknownDataTypeException() {
    }

    public UnknownDataTypeException(String message) {
      super(message);
    }

    public UnknownDataTypeException(Throwable cause) {
      super(cause);
    }
  }

  public static class MalformedStrongTypedNodeException extends
      BusinessException {
    public MalformedStrongTypedNodeException() {
    }

    public MalformedStrongTypedNodeException(String message) {
      super(message);
    }

    public MalformedStrongTypedNodeException(Throwable cause) {
      super(cause);
    }
  }

  public static class MalformedSearchableNodeException extends
      BusinessException {
    public MalformedSearchableNodeException() {
    }

    public MalformedSearchableNodeException(String message) {
      super(message);
    }

    public MalformedSearchableNodeException(Throwable cause) {
      super(cause);
    }
  }

  public static class MinValueExpectationFailedException extends
      BusinessException {
    public MinValueExpectationFailedException() {
    }

    public MinValueExpectationFailedException(String message) {
      super(message);
    }

    public MinValueExpectationFailedException(Throwable cause) {
      super(cause);
    }
  }

  public static class MaxValueExpectationFailedException extends
      BusinessException {
    public MaxValueExpectationFailedException() {
    }

    public MaxValueExpectationFailedException(String message) {
      super(message);
    }

    public MaxValueExpectationFailedException(Throwable cause) {
      super(cause);
    }
  }

  public static class NumberPrecisionExpectationFailedException extends
      BusinessException {
    public NumberPrecisionExpectationFailedException() {
    }

    public NumberPrecisionExpectationFailedException(String message) {
      super(message);
    }

    public NumberPrecisionExpectationFailedException(Throwable cause) {
      super(cause);
    }
  }
  
  public static class InvalidEmptyObjectException extends
      BusinessException {
    public InvalidEmptyObjectException() {
    }

    public InvalidEmptyObjectException(String message) {
      super(message);
    }

    public InvalidEmptyObjectException(Throwable cause) {
      super(cause);
    }
  }
}
