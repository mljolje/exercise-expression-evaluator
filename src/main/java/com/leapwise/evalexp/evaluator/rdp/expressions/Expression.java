package com.leapwise.evalexp.evaluator.rdp.expressions;

public sealed interface Expression permits
        ArithmeticAddSub,
        ArithmeticMulDiv,
        Comparison,
        Equality,
        Group,
        Literal,
        Logical,
        Ternary,
        Unary,
        Variable {
}
