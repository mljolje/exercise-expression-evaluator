package com.leapwise.evalexp.exceptions;

public class NotFoundAlertException extends RuntimeException {
    public NotFoundAlertException(final String message) {
        super("Not found : " + message);
    }
}
