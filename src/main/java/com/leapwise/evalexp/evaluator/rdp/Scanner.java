package com.leapwise.evalexp.evaluator.rdp;

import com.leapwise.evalexp.exceptions.ProcessingTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements transformation of input expression string into list of {@link Token} objects.
 */
public class Scanner {
    private static final Logger log = LoggerFactory.getLogger(Scanner.class);

    private static final Map<Character, TokenType> charToTokenTypeMap =
            Map.of(
                    '(', TokenType.LEFT_PARENTHESIS,
                    ')', TokenType.RIGHT_PARENTHESIS,
                    '?', TokenType.QUESTION_MARK,
                    '-', TokenType.MINUS,
                    '+', TokenType.PLUS,
                    '*', TokenType.MULTIPLY,
                    '/', TokenType.DIVIDE,
                    ':', TokenType.COLON
            );

    private static final Map<String, TokenType> stringToTokenTypeMap =
            Map.of(
                    "null", TokenType.NULL,
                    "true", TokenType.TRUE,
                    "false", TokenType.FALSE,
                    "or", TokenType.OR,
                    "and", TokenType.AND
            );
    private final StreamTokenizer tokenizer;

    private final List<Token> tokenList = new ArrayList<>();

    public Scanner(final String expression) {
        this.tokenizer = new StreamTokenizer(new StringReader(expression));
        this.tokenizer.ordinaryChar('/');
    }

    public List<Token> readTokens() throws IOException, ProcessingTokenException {
        while (true) {
            tokenizer.nextToken();
            log.trace("Token {} {} {}", tokenizer.ttype, tokenizer.sval, tokenizer.nval);
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_EOF -> {
                    tokenList.add(createToken(TokenType.END_OF_EXPRESSION));
                    return tokenList;
                }
                case '(', ')', '?', '-', '+', '/', '*', ':' ->
                        tokenList.add(createToken(charToTokenTypeMap.get((char) tokenizer.ttype)));
                case '>' -> processComparisonToken(TokenType.GREATER_OR_EQUAL, TokenType.GREATER);
                case '<' -> processComparisonToken(TokenType.LESS_OR_EQUAL, TokenType.LESS);
                case '!' -> processComparisonToken(TokenType.NOT_EQUAL, TokenType.NEGATE);
                case '"', '\'' -> tokenList.add(new Token(TokenType.STRING, null, tokenizer.sval));
                case '=' -> processLogicalToken('=', TokenType.EQUAL, "Expecting ==");
                case '&' -> processLogicalToken('&', TokenType.AND, "Expecting &&");
                case '|' -> processLogicalToken('|', TokenType.OR, "Expecting ||");
                case StreamTokenizer.TT_WORD -> tokenList.add(scanWord());
                case StreamTokenizer.TT_NUMBER -> tokenList.add(scanNumber());
                default -> throw new ProcessingTokenException("Unrecognized token: ", tokenizer.ttype, tokenizer.sval);
            }
        }
    }

    private void processLogicalToken(final char charToken, final TokenType logicalTokenType,
                                     final String message) throws IOException {
        if (tokenizer.nextToken() == charToken) {
            tokenList.add(createToken(logicalTokenType));
        } else {
            throw new ProcessingTokenException(message, tokenizer.ttype, "");
        }
    }

    private void processComparisonToken(final TokenType comparisonWithEqualTokenType,
                                        final TokenType comparisonTokenType) throws IOException {
        if (tokenizer.nextToken() == '=') {
            tokenList.add(createToken(comparisonWithEqualTokenType));
        } else {
            tokenizer.pushBack();
            tokenList.add(createToken(comparisonTokenType));
        }
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    private Token createToken(final TokenType tokenType) {
        return new Token(tokenType, null, null);
    }

    private Token scanNumber() {
        return ((Double) tokenizer.nval).toString().endsWith(".0") ?
                new Token(TokenType.INTEGER, tokenizer.sval, (int) tokenizer.nval) :
                new Token(TokenType.DOUBLE, tokenizer.sval, tokenizer.nval);
    }

    private Token scanWord() {
        final TokenType tokenType = stringToTokenTypeMap.get(tokenizer.sval.toLowerCase());
        return tokenType == null ?
                new Token(TokenType.IDENTIFIER, tokenizer.sval, null) :
                createToken(tokenType);
    }
}