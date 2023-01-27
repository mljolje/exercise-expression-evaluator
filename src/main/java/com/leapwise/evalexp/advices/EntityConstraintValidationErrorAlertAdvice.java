package com.leapwise.evalexp.advices;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class EntityConstraintValidationErrorAlertAdvice {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String constraintViolationExceptionAlertHandler(ConstraintViolationException ex) {
        return "Constraint violations -> " + buildValidationErrors(ex.getConstraintViolations());
    }

    private String buildValidationErrors(
            Set<ConstraintViolation<?>> violations) {
        return violations.
                stream().
                map(violation ->
                        StreamSupport.stream(
                                        violation.getPropertyPath().spliterator(), false).
                                reduce((first, second) -> second).
                                orElse(null).
                                toString() + ": " + violation.getMessage()
                ).collect(Collectors.joining(", "));
    }
}
