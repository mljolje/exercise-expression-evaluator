package com.leapwise.evalexp.evaluator.rdp;

import com.leapwise.evalexp.evaluator.rdp.expressions.ArithmeticAddSub;
import com.leapwise.evalexp.evaluator.rdp.expressions.ArithmeticMulDiv;
import com.leapwise.evalexp.evaluator.rdp.expressions.Comparison;
import com.leapwise.evalexp.evaluator.rdp.expressions.Equality;
import com.leapwise.evalexp.evaluator.rdp.expressions.Expression;
import com.leapwise.evalexp.evaluator.rdp.expressions.Group;
import com.leapwise.evalexp.evaluator.rdp.expressions.Literal;
import com.leapwise.evalexp.evaluator.rdp.expressions.Logical;
import com.leapwise.evalexp.evaluator.rdp.expressions.Ternary;
import com.leapwise.evalexp.evaluator.rdp.expressions.Unary;
import com.leapwise.evalexp.evaluator.rdp.expressions.Variable;
import com.leapwise.evalexp.exceptions.ProcessingTokenException;

import java.util.List;

/**
 * Parser class implements simple version of Recursive descent parser.
 * Grammar covered is shown below:
 * <pre>
 *      expression -> ternary
 *      ternary -> logical { '?' ternary : ternary}
 *      logical -> equality { ('&&' | '|| ') equality }
 *      equality -> comparison { ('==' | '!= ') comparison }
 *      comparison -> arithmetics { ('+' | '-' | '*' | '/') arithmetics }
 *      arithmetics -> unary { ('+' | '-' | '*' | '/') unary }
 *      unary -> { ('!' | '-') unary | primary }
 *      primary -> '(' expression ')' | INTEGER | DOUBLE | STRING | true | false | null | IDENTIFIER
 * </pre>
 */
public class Parser {
    private final Token[] tokens;
    private int currentTokenPosition = 0;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens.toArray(new Token[]{});
    }

    public Expression parse() throws ProcessingTokenException {
        return this.expression();
    }

    private Expression expression() throws ProcessingTokenException {
        return this.ternary();
    }

    private Expression ternary() throws ProcessingTokenException {
        Expression expression = this.or();

        if (this.match(TokenType.QUESTION_MARK)) {
            var trueBranch = this.ternary();
            this.consumeTokenIfType(TokenType.COLON, "Expect colon ':' after ternary condition.");
            var falseBranch = this.ternary();
            return new Ternary(expression, trueBranch, falseBranch);
        }

        return expression;
    }

    private Expression or() throws ProcessingTokenException {
        Expression expr = and();

        while (match(TokenType.OR)) {
            Token operator = previous();
            Expression right = and();
            expr = new Logical(expr, operator, right);
        }

        return expr;
    }

    private Expression and() throws ProcessingTokenException {
        Expression expr = equality();

        while (match(TokenType.AND)) {
            Token operator = previous();
            Expression right = equality();
            expr = new Logical(expr, operator, right);
        }

        return expr;
    }

    private Expression equality() throws ProcessingTokenException {
        Expression expr = comparison();

        while (match(TokenType.NOT_EQUAL, TokenType.EQUAL)) {
            Token operator = previous();
            Expression right = comparison();
            expr = new Equality(expr, operator, right);
        }

        return expr;
    }

    private Expression comparison() throws ProcessingTokenException {
        Expression expr = arithmeticAddSub();

        while (match(TokenType.GREATER, TokenType.GREATER_OR_EQUAL, TokenType.LESS, TokenType.LESS_OR_EQUAL)) {
            Token operator = previous();
            Expression right = arithmeticAddSub();
            expr = new Comparison(expr, operator, right);
        }

        return expr;
    }

    private Expression arithmeticAddSub() throws ProcessingTokenException {
        Expression expr = arithmeticMulDiv();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = arithmeticMulDiv();
            expr = new ArithmeticAddSub(expr, operator, right);
        }

        return expr;

    }

    private Expression arithmeticMulDiv() throws ProcessingTokenException {
        Expression expr = unary();

        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            Token operator = previous();
            Expression right = unary();
            expr = new ArithmeticMulDiv(expr, operator, right);
        }

        return expr;
    }

    private Expression unary() throws ProcessingTokenException {
        if (match(TokenType.NEGATE, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = unary();
            return new Unary(operator, right);
        }
        return primary();
    }

    private Expression primary() throws ProcessingTokenException {

        if (match(TokenType.DOUBLE, TokenType.INTEGER, TokenType.STRING)) {
            return new Literal<>(previous().literal());
        } else if (match(TokenType.IDENTIFIER)) {
            return new Variable(previous());
        } else if (match(TokenType.FALSE)) {
            return new Literal<>(false);
        } else if (match(TokenType.TRUE)) {
            return new Literal<>(true);
        } else if (match(TokenType.NULL)) {
            return new Literal<>(null);
        } else if (match(TokenType.LEFT_PARENTHESIS)) {
            Expression expr = expression();
            consumeTokenIfType(TokenType.RIGHT_PARENTHESIS, "Expect ')' after expression.");
            return new Group(expr);
        }
        throw new ProcessingTokenException(this.current().type().toString(), this.currentTokenPosition, "Expect expression after that token.");
    }


    private void consumeTokenIfType(TokenType tokenType, final String errorMessageInCaseOfUnexpectedToken)
            throws ProcessingTokenException {
        if (current().type().equals(tokenType)) {
            this.next();
            return;
        }
        throw new ProcessingTokenException(this.current().type().toString(), this.currentTokenPosition, errorMessageInCaseOfUnexpectedToken);
    }

    private Token current() {
        return this.tokens[this.currentTokenPosition];
    }

    private void next() {
        if (!this.isEndOfExpression()) {
            this.currentTokenPosition++;
        }
    }

    private Token previous() {
        return this.tokens[this.currentTokenPosition - 1];
    }

    private boolean isEndOfExpression() {
        return current().type().equals(TokenType.END_OF_EXPRESSION);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (isCurrentTokenType(type)) {
                next();
                return true;
            }
        }
        return false;
    }

    private boolean isCurrentTokenType(TokenType type) {
        if (isEndOfExpression()) {
            return false;
        }
        return current().type().equals(type);
    }

}
