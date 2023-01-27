package com.leapwise.evalexp.exceptions;

public class EvaluationErrorException extends RuntimeException {

    private final Exception innerException;

    public EvaluationErrorException(final String message, final Exception innerException) {
        super(message);
        this.innerException = innerException;
    }

    public EvaluationErrorException(final String message) {
        super(message);
        this.innerException = null;
    }

    public Exception getInnerException() {
        return innerException;
    }


    @Override
    public String toString() {
        return super.toString() + ", details: " + innerException.getMessage();
    }
}
