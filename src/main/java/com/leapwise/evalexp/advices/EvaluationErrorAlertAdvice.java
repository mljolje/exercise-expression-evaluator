package com.leapwise.evalexp.advices;

import com.leapwise.evalexp.exceptions.EvaluationErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EvaluationErrorAlertAdvice {
    private static final Logger log = LoggerFactory.getLogger(EvaluationErrorAlertAdvice.class);

    @ResponseBody
    @ExceptionHandler(EvaluationErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String evaluationErrorAlertHandler(EvaluationErrorException ex) {
        log.error(ex.getMessage(), ex);
        log.error("Inner exception", ex.getInnerException());
        return ex.getMessage();
    }
}
