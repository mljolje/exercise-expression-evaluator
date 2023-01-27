package com.leapwise.evalexp.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leapwise.evalexp.exceptions.EvaluationErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface EvaluateExpressionService {
    Logger log = LoggerFactory.getLogger(EvaluateExpressionService.class);

    boolean evaluate(final String expression, final String jsonObject) throws EvaluationErrorException;

    boolean evaluateInLooseMode(final String expression, final String jsonObject) throws EvaluationErrorException;

    default void validateExpression(final String expression) throws EvaluationErrorException {
        final String emptyJsonObj = "{}";
        try {
            this.evaluateInLooseMode(expression, emptyJsonObj);
        } catch (EvaluationErrorException e) {
            throw new EvaluationErrorException("Invalid expression: " + e.getMessage(), e.getInnerException());
        }
    }

    default boolean isJsonObjectInvalid(final String jsonObject) {
        try {
            new ObjectMapper().readTree(jsonObject);
        } catch (JacksonException e) {
            log.debug("Invalid JSON object", e);
            return true;
        }
        return false;
    }
}
