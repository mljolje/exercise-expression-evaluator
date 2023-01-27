package com.leapwise.evalexp.domain.enumeration;

import com.leapwise.evalexp.config.Constants;

public enum ExpressionEngineType {
    ANTLR(Constants.ANTLR),
    RECURSIVE_DESCENT(Constants.RECURSIVE_DESCENT),
    JAVASCRIPT(Constants.JAVASCRIPT);
    public final String label;

    ExpressionEngineType(final String label) {
        this.label = label;
    }
}
