package com.leapwise.evalexp.evaluator.rdp;

/**
 * Token record encapsulates {@link TokenType}, lexeme and literal produced during scanning process in {@link Scanner}
 *
 * @param type    {@link TokenType}
 * @param lexeme  {@link String}
 * @param literal {@link Object}
 */
public record Token(TokenType type, String lexeme, Object literal) {
}
