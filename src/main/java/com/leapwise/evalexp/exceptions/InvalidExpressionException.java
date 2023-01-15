package com.leapwise.evalexp.exceptions;

public class InvalidExpressionException extends RuntimeException {

    public InvalidExpressionException(final String expression) {
        super("Invalid expression: '" + expression + "'");
    }
}
