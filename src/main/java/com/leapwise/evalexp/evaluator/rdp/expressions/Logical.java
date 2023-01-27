package com.leapwise.evalexp.evaluator.rdp.expressions;

import com.leapwise.evalexp.evaluator.rdp.Token;

public record Logical(Expression left, Token operator, Expression right) implements Expression {

}
