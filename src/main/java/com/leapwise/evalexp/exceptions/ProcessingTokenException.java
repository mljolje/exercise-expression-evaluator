package com.leapwise.evalexp.exceptions;

public class ProcessingTokenException extends RuntimeException {
    public ProcessingTokenException(final String message, final int tokenType, final String tokenStringValue) {
        super(message + tokenType + ", string value: " + tokenStringValue);
    }
}
