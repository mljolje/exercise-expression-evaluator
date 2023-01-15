package com.leapwise.evalexp.service;

import com.leapwise.evalexp.exceptions.OperationNotSupportedException;

import javax.script.ScriptException;
import java.io.IOException;

public interface EvaluateExpressionService {
    Boolean evaluate(final String expression, final String jsonObject) throws OperationNotSupportedException;
    Boolean evaluateUsingJSEngine(final String expression, final String jsonObject) throws ScriptException, IOException;
}
