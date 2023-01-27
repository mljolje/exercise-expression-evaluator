package com.leapwise.evalexp.service.impl;

import com.leapwise.evalexp.config.Constants;
import com.leapwise.evalexp.evaluator.antlr.ExpressionEvaluatorVisitor;
import com.leapwise.evalexp.exceptions.EvaluationErrorException;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import org.springframework.stereotype.Service;

@Service(Constants.ANTLR)
public class AntlrEvaluateExpressionServiceImpl implements EvaluateExpressionService {

    @Override
    public boolean evaluate(final String expression, final String jsonObject) throws EvaluationErrorException {
        try {
            return new ExpressionEvaluatorVisitor(expression, jsonObject).evaluate();
        } catch (Exception e) {
            throw new EvaluationErrorException(e.getMessage(), e);
        }
    }

    @Override
    public boolean evaluateInLooseMode(final String expression, final String jsonObject) throws EvaluationErrorException {
        return evaluate(expression, jsonObject);
    }

}
