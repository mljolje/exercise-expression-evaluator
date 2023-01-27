package com.leapwise.evalexp.evaluator.rdp;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
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
import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import com.leapwise.evalexp.exceptions.ProcessingTokenException;

import java.io.IOException;
import java.util.Map;

/**
 * Evaluator of expressions against JSON objects using Recursive Decent Parser
 * developed inside {@link Parser}
 */
public class Evaluator {
    private final String jsonObject;

    private boolean looseMode = false;

    public Evaluator(final String jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Enable loose mode of evaluator used for validating expression which contains null valued JSON object references
     * Currently only used in arithmetic operations
     *
     * @param looseMode Loose mode, default mode is FALSE
     * @return
     */
    public Evaluator looseMode(final boolean looseMode) {
        this.looseMode = looseMode;
        return this;
    }

    /**
     * Evaluate expression on JSON object
     *
     * @param expression
     * @return Expression object
     * @throws IOException
     * @throws ProcessingTokenException
     */
    public Expression evaluate(final String expression) throws IOException, ProcessingTokenException {
        return evaluate(
                new Parser(
                        new Scanner(expression)
                                .readTokens())
                        .parse());
    }

    public Boolean evaluateToBoolean(final String expression)
            throws IOException, ProcessingTokenException, OperationNotSupportedException {
        if (evaluate(expression) instanceof Literal literal && literal.value() instanceof Boolean booleanValue) {
            return booleanValue;
        } else {
            throw new OperationNotSupportedException("Cannot get result in boolean value: " + expression);
        }
    }

    private Expression evaluate(final Expression expression)
            throws ProcessingTokenException, OperationNotSupportedException {
        return switch (expression) {
            case Literal literal -> literal;
            case Variable variable -> evaluate(evaluateVariable(variable));
            case Comparison comparison -> evaluate(evaluateComparison(comparison));
            case Equality equality -> evaluate(evaluateEquality(equality));
            case Group group -> evaluate(group.innerExpression());
            case Logical logical -> evaluate(evaluateLogical(logical));
            case Ternary ternary -> evaluate(evaluateTernary(ternary));
            case Unary unary -> evaluate(evaluateUnary(unary));
            case ArithmeticAddSub arithmeticAddSub -> evaluate(evaluateAddSub(arithmeticAddSub));
            case ArithmeticMulDiv arithmeticMulDiv -> evaluate(evaluateMulDiv(arithmeticMulDiv));
        };
    }

    private Expression evaluateMulDiv(ArithmeticMulDiv arithmeticMulDiv) {
        return evaluateArithmetics(arithmeticMulDiv.left(), arithmeticMulDiv.operator().type(), arithmeticMulDiv.right());
    }

    private Expression evaluateAddSub(ArithmeticAddSub arithmeticAddSub) {
        return evaluateArithmetics(arithmeticAddSub.left(), arithmeticAddSub.operator().type(), arithmeticAddSub.right());
    }

    private Expression evaluateArithmetics(Expression leftExpr, TokenType operation, Expression rightExpr) {
        var left = evaluate(leftExpr);
        var right = evaluate(rightExpr);
        if (left instanceof Literal leftLiteral && right instanceof Literal rightLiteral) {
            if (looseMode && (leftLiteral.value() == null || rightLiteral.value() == null)) {
                return new Literal<>(1);
            } else if (leftLiteral.value() instanceof Number leftNum
                    && rightLiteral.value() instanceof Number rightNum) {
                return switch (operation) {
                    case PLUS -> new Literal<>(leftNum.doubleValue() + rightNum.doubleValue());
                    case MINUS -> new Literal<>(leftNum.doubleValue() - rightNum.doubleValue());
                    case MULTIPLY -> new Literal<>(leftNum.doubleValue() * rightNum.doubleValue());
                    case DIVIDE -> new Literal<>(leftNum.doubleValue() / rightNum.doubleValue());
                    default ->
                            throw new OperationNotSupportedException(operation + " inside arithmetic +,-,/,* expression");
                };
            }
        }
        throw new OperationNotSupportedException(operation
                + " on types " + left.toString() + ", " + right.toString());
    }

    private Expression evaluateLogical(final Logical logical) {
        var left = evaluate(logical.left());
        var right = evaluate(logical.right());
        if (left instanceof Literal leftLiteral
                && leftLiteral.value() instanceof Boolean leftBoolean
                && right instanceof Literal rightLiteral
                && rightLiteral.value() instanceof Boolean rightBoolean) {
            return switch (logical.operator().type()) {
                case OR -> new Literal<>(leftBoolean || rightBoolean);
                case AND -> new Literal<>(leftBoolean && rightBoolean);
                default -> throw new OperationNotSupportedException(logical.operator().type().toString());
            };
        } else {
            throw new OperationNotSupportedException(logical.operator().type().toString());
        }
    }

    private Expression evaluateComparison(final Comparison comparison) {
        Literal leftLiteral = (Literal) evaluate(comparison.left());
        Literal rightLiteral = (Literal) evaluate(comparison.right());
        if (leftLiteral.value() == null || rightLiteral.value() == null) {
            return new Literal<>(false);
        }
        if (leftLiteral.value() instanceof Number leftNum
                && rightLiteral.value() instanceof Number rightNum) {
            return switch (comparison.operator().type()) {
                case GREATER -> new Literal<>(leftNum.doubleValue() > rightNum.doubleValue());
                case GREATER_OR_EQUAL -> new Literal<>(leftNum.doubleValue() >= rightNum.doubleValue());
                case LESS -> new Literal<>(leftNum.doubleValue() < rightNum.doubleValue());
                case LESS_OR_EQUAL -> new Literal<>(leftNum.doubleValue() <= rightNum.doubleValue());
                default -> throw new OperationNotSupportedException(comparison.operator().type().toString());
            };
        } else {
            throw new OperationNotSupportedException(
                    comparison.operator().type().toString() +
                            " on left: " + leftLiteral.value() +
                            ", right: " + rightLiteral.value());
        }
    }

    private Expression evaluateUnary(final Unary unary) {
        var rightExpressionResult = evaluate(unary.right());
        if (rightExpressionResult instanceof Literal literalResult) {
            return switch (unary.operator().type()) {
                case NEGATE -> {
                    if (literalResult.value() instanceof Boolean literalBoolResult) {
                        yield new Literal<>(!literalBoolResult);
                    } else {
                        throw new OperationNotSupportedException(unary.operator().type().toString());
                    }
                }
                case MINUS -> {
                    if (literalResult.value() instanceof Integer literalIntResult) {
                        yield new Literal<>(-1 * literalIntResult);
                    } else if (literalResult.value() instanceof Double literalDoubleResult) {
                        yield new Literal<>(-1 * literalDoubleResult);
                    } else {
                        throw new OperationNotSupportedException(unary.operator().type().toString());
                    }
                }
                default -> throw new OperationNotSupportedException(unary.operator().type().toString());
            };
        } else {
            throw new OperationNotSupportedException(unary.operator().type().toString());
        }
    }

    private Expression evaluateVariable(final Variable variable) {
        Object variableValue = null;
        try {
            variableValue = JsonPath.read(jsonObject, variable.variableToken().lexeme());
        } catch (PathNotFoundException exception) {
            // In case not existing values inside JSON object
        }
        return switch (variableValue) {
            case null -> new Literal<>(null);
            case String s -> new Literal<>(s);
            case Integer i -> new Literal<>(i);
            case Double d -> new Literal<>(d);
            case Boolean b -> new Literal<>(b);
            case Map m -> new Literal<>(m.toString());
            default -> throw new OperationNotSupportedException("Unsupported variable value type " +
                    variableValue.getClass().toString());
        };
    }

    private Expression evaluateEquality(final Equality equality) {
        var left = evaluate(equality.left());
        var right = evaluate(equality.right());
        return switch (equality.operator().type()) {
            case EQUAL -> new Literal<>(left.equals(right));
            case NOT_EQUAL -> new Literal<>(!left.equals(right));
            default -> throw new OperationNotSupportedException(equality.operator().type().toString());
        };
    }

    private Expression evaluateTernary(final Ternary ternary) {
        var conditionResult = evaluate(ternary.condition());
        if (conditionResult instanceof Literal literalResult
                && literalResult.value() instanceof Boolean literalBoolResult) {
            return literalBoolResult ? ternary.trueBranchExpression() : ternary.falseBranchExpression();
        } else {
            throw new OperationNotSupportedException(ternary.condition().toString());
        }
    }
}