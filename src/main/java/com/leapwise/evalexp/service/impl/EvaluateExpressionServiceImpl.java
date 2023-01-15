package com.leapwise.evalexp.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import com.leapwise.evalexp.expressions.ExpressionEvaluatorVisitor;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Map;

@Service
public class EvaluateExpressionServiceImpl implements EvaluateExpressionService {

    public static final String NULL_WITH_SPACE = "null ";
    public static final String JAVA_SCRIPT_ENGINE = "nashorn";
    private final ScriptEngine javaScriptEngine;

    public EvaluateExpressionServiceImpl() {
        this.javaScriptEngine = new ScriptEngineManager().getEngineByName(JAVA_SCRIPT_ENGINE);
    }

    @Override
    public Boolean evaluate(final String expression, final String jsonObject) throws OperationNotSupportedException {
        return new ExpressionEvaluatorVisitor(expression, jsonObject).evaluate();
    }

    @Override
    public Boolean evaluateUsingJSEngine(final String expression, final String jsonObject) throws IOException, ScriptException {
        return (Boolean) javaScriptEngine.eval(transformToJsString(expression, jsonObject));
    }

    /**
     * Supports only tokens as example: AND, OR, &&, ||, ==, !=, <, >
     * @param expression
     * @param jsonObject
     * @return
     * @throws IOException
     */
    private String transformToJsString(final String expression, final String jsonObject) throws IOException {
        final StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(expression));
        final StringBuilder parsedExpressionBuilder = new StringBuilder();
        while (true) {
            tokenizer.nextToken();
            if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
                break;
            }
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                parseWord(jsonObject, tokenizer, parsedExpressionBuilder);
            } else if (tokenizer.ttype == '"') {
                parsedExpressionBuilder.append("'").append(tokenizer.sval).append("' ");
            } else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                parsedExpressionBuilder.append(tokenizer.nval).append(" ");
            } else if (tokenizer.ttype == '=') {
                tokenizer.nextToken();
                parsedExpressionBuilder.append("== ");
            } else if (tokenizer.ttype == '&') {
                tokenizer.nextToken();
                parsedExpressionBuilder.append("&& ");
            } else if (tokenizer.ttype == '!') {
                tokenizer.nextToken();
                parsedExpressionBuilder.append("!= ");
            } else if (tokenizer.ttype == '|') {
                tokenizer.nextToken();
                parsedExpressionBuilder.append("|| ");
            } else {
                parsedExpressionBuilder.append((char) tokenizer.ttype).append(" ");
            }
        }
        return parsedExpressionBuilder.toString();
    }

    private void parseWord(String jsonObject, StreamTokenizer tokenizer, StringBuilder parsedExpressionBuilder) {
        if (tokenizer.sval.equals("null")) {
            parsedExpressionBuilder.append(NULL_WITH_SPACE);
        } else if (tokenizer.sval.equalsIgnoreCase("OR")) {
            parsedExpressionBuilder.append("|| ");
        } else if (tokenizer.sval.equalsIgnoreCase("AND")) {
            parsedExpressionBuilder.append("&& ");
        } else {
            parseJsonValue(jsonObject, tokenizer, parsedExpressionBuilder);
        }
    }

    private void parseJsonValue(String jsonObject, StreamTokenizer tokenizer, StringBuilder parsedExpressionBuilder) {
        try {
            final Object jsonValue = JsonPath.read(jsonObject, tokenizer.sval);
            if (jsonValue == null) {
                parsedExpressionBuilder.append(NULL_WITH_SPACE);
            } else if (jsonValue instanceof String || jsonValue instanceof Map) {
                parsedExpressionBuilder.append("'").append(jsonValue).append("' ");
            } else {
                parsedExpressionBuilder.append(jsonValue).append(" ");
            }
        } catch (PathNotFoundException exception) {
            parsedExpressionBuilder.append(NULL_WITH_SPACE);
        }
    }
}
