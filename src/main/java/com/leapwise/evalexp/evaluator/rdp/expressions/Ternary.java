package com.leapwise.evalexp.evaluator.rdp.expressions;

public record Ternary(Expression condition, Expression trueBranchExpression,
                      Expression falseBranchExpression) implements Expression {

}
