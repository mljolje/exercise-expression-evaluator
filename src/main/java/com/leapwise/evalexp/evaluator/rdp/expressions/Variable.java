package com.leapwise.evalexp.evaluator.rdp.expressions;

import com.leapwise.evalexp.evaluator.rdp.Token;

public record Variable(Token variableToken) implements Expression {
}
