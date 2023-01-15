package com.leapwise.evalexp.exceptions;


public class OperationNotSupportedException extends RuntimeException {

    public OperationNotSupportedException(final String operationType) {
        super("Operation not supported " + operationType);
    }
}
