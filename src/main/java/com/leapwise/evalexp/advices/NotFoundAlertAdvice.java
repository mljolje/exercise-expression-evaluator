package com.leapwise.evalexp.advices;

import com.leapwise.evalexp.exceptions.NotFoundAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAlertAdvice {
    @ResponseBody
    @ExceptionHandler(NotFoundAlertException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundAlertHandler(NotFoundAlertException ex) {
        return ex.getMessage();
    }
}
