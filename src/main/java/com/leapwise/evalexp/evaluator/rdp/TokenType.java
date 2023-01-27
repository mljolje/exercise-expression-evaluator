package com.leapwise.evalexp.evaluator.rdp;

/**
 * Enumeration of all supported token types used for parsing expressions
 */
public enum TokenType {
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,

    MINUS,
    PLUS,

    MULTIPLY,
    DIVIDE,

    QUESTION_MARK,
    COLON,
    NEGATE,
    EQUAL,
    NOT_EQUAL,
    GREATER,
    GREATER_OR_EQUAL,
    LESS,
    LESS_OR_EQUAL,

    AND,
    OR,

    NULL,
    TRUE,
    FALSE,

    IDENTIFIER,
    STRING,
    INTEGER,
    DOUBLE,

    END_OF_EXPRESSION
}
