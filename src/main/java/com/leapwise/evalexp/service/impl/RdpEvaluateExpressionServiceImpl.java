package com.leapwise.evalexp.service.impl;

import com.leapwise.evalexp.config.Constants;
import com.leapwise.evalexp.evaluator.rdp.Evaluator;
import com.leapwise.evalexp.exceptions.EvaluationErrorException;
import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import com.leapwise.evalexp.exceptions.ProcessingTokenException;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service(Constants.RECURSIVE_DESCENT)
public class RdpEvaluateExpressionServiceImpl implements EvaluateExpressionService {
    @Override
    public boolean evaluate(final String expression, final String jsonObject) throws EvaluationErrorException {
        try {
            return new Evaluator(jsonObject).evaluateToBoolean(expression);
        } catch (IOException | ProcessingTokenException | OperationNotSupportedException e) {
            throw new EvaluationErrorException(e.getMessage(), e);
        }
    }

    @Override
    public boolean evaluateInLooseMode(String expression, String jsonObject) throws EvaluationErrorException {
        try {
            return new Evaluator(jsonObject).looseMode(true).evaluateToBoolean(expression);
        } catch (IOException | ProcessingTokenException | OperationNotSupportedException e) {
            throw new EvaluationErrorException(e.getMessage(), e);
        }
    }
}