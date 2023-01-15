package com.leapwise.evalexp.exceptions;

public class BadRequestAlertException extends RuntimeException {
    public BadRequestAlertException(final String message, final String details) {
        super(message + ": " + details);
    }
}
