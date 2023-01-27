package com.leapwise.evalexp.exceptions;

public class ProcessingTokenException extends RuntimeException {
    public ProcessingTokenException(final String message, final int tokenPosition, final String reason) {
        super("Syntax error at processing token: " + message + ", at position: " + tokenPosition + ", reason: " + reason);
    }
}
