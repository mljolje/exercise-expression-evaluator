package com.leapwise.evalexp.evaluator.antlr;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.leapwise.evalexp.evaluator.antlr.grammar.EvalExpBaseVisitor;
import com.leapwise.evalexp.evaluator.antlr.grammar.EvalExpErrorListener;
import com.leapwise.evalexp.evaluator.antlr.grammar.EvalExpLexer;
import com.leapwise.evalexp.evaluator.antlr.grammar.EvalExpParser;
import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionEvaluatorVisitor extends EvalExpBaseVisitor<Object> {
    private final Logger log = LoggerFactory.getLogger(ExpressionEvaluatorVisitor.class);
    private final String jsonObject;
    private final String expression;

    public ExpressionEvaluatorVisitor(final String expression, final String jsonObject) {
        this.jsonObject = jsonObject;
        this.expression = expression;
    }

    @Override
    public Object visitIntegerExpression(EvalExpParser.IntegerExpressionContext ctx) {
        return Integer.valueOf(ctx.INTEGER().getText());
    }

    @Override
    public Object visitNullExpression(EvalExpParser.NullExpressionContext ctx) {
        return null;
    }

    @Override
    public Object visitStringExpression(EvalExpParser.StringExpressionContext ctx) {
        final String stringValue = ctx.STRING().getText();
        return stringValue.substring(1, stringValue.length() - 1);
    }

    @Override
    public Object visitParse(EvalExpParser.ParseContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Object visitDecimalExpression(EvalExpParser.DecimalExpressionContext ctx) {
        return Double.valueOf(ctx.DECIMAL().getText());
    }

    @Override
    public Object visitIdentifierExpression(EvalExpParser.IdentifierExpressionContext ctx) {
        try {
            return JsonPath.read(jsonObject, ctx.IDENTIFIER().getText());
        } catch (PathNotFoundException exception) {
            return null;
        }
    }

    @Override
    public Object visitNotExpression(EvalExpParser.NotExpressionContext ctx) {
        return !((Boolean) this.visit(ctx.expression()));
    }

    @Override
    public Object visitParenExpression(EvalExpParser.ParenExpressionContext ctx) {
        return super.visit(ctx.expression());
    }

    @Override
    public Object visitComparatorExpression(EvalExpParser.ComparatorExpressionContext ctx) {
        if (ctx.op.EQ() != null) {
            return execEqualityOperator(ctx, false);
        } else if (ctx.op.NEQ() != null) {
            return execEqualityOperator(ctx, true);
        } else if (ctx.op.LE() != null) {
            if (isAnyNumNull(ctx)) {
                return false;
            }
            return asNumber(ctx.left).doubleValue() <= asNumber(ctx.right).doubleValue();
        } else if (ctx.op.GE() != null) {
            if (isAnyNumNull(ctx)) {
                return false;
            }
            return asNumber(ctx.left).doubleValue() >= asNumber(ctx.right).doubleValue();
        } else if (ctx.op.LT() != null) {
            if (isAnyNumNull(ctx)) {
                return false;
            }
            return asNumber(ctx.left).doubleValue() < asNumber(ctx.right).doubleValue();
        } else if (ctx.op.GT() != null) {
            if (isAnyNumNull(ctx)) {
                return false;
            }
            return asNumber(ctx.left).doubleValue() > asNumber(ctx.right).doubleValue();
        }
        throw new OperationNotSupportedException("Comparator operator " + ctx.op.getText());
    }

    private boolean execEqualityOperator(EvalExpParser.ComparatorExpressionContext ctx, final boolean negate) {
        var left = this.visit(ctx.left);
        var right = this.visit(ctx.right);
        return negate != (left == null || right == null ? left == right : left.equals(right));
    }

    private boolean isAnyNumNull(EvalExpParser.ComparatorExpressionContext ctx) {
        final Number left = asNumber(ctx.left);
        final Number right = asNumber(ctx.right);
        return left == null || right == null;
    }

    @Override
    public Object visitBinaryExpression(EvalExpParser.BinaryExpressionContext ctx) {
        if (ctx.op.AND() != null || ctx.op.ANDJS() != null) {
            return asBoolean(ctx.left) && asBoolean(ctx.right);
        } else if (ctx.op.OR() != null || ctx.op.ORJS() != null) {
            return asBoolean(ctx.left) || asBoolean(ctx.right);
        }
        throw new OperationNotSupportedException("Binary operator " + ctx.op.getText());
    }

    @Override
    public Object visitBoolExpression(EvalExpParser.BoolExpressionContext ctx) {
        return Boolean.valueOf(ctx.getText());
    }

    private boolean asBoolean(EvalExpParser.ExpressionContext ctx) {
        return (boolean) visit(ctx);
    }

    private Number asNumber(EvalExpParser.ExpressionContext ctx) {
        return (Number) visit(ctx);
    }

    public Boolean evaluate() {
        log.debug("Evaluating {} on expression {}", jsonObject, expression);
        var parser = new EvalExpParser(
                new CommonTokenStream(
                        new EvalExpLexer(CharStreams.fromString(expression))));
        parser.addErrorListener(new EvalExpErrorListener());

        return (Boolean) visit(parser.parse());
    }

}
