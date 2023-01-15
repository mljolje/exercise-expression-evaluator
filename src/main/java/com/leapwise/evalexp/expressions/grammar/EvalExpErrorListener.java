package com.leapwise.evalexp.expressions.grammar;

import com.leapwise.evalexp.exceptions.ProcessingTokenException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class EvalExpErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new ProcessingTokenException("Invalid Expression: " + charPositionInLine, line, msg);
    }


}
