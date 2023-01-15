package com.leapwise.evalexp.advices;

import com.leapwise.evalexp.exceptions.BadRequestAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestAlertAdvice {
    @ResponseBody
    @ExceptionHandler(BadRequestAlertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequestAlertHandler(BadRequestAlertException ex) {
        return ex.getMessage();
    }
}
