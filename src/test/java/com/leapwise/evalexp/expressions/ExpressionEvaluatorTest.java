package com.leapwise.evalexp.expressions;

import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import org.junit.jupiter.api.Test;

import static com.leapwise.evalexp.TestConstants.EXPRESSION_DEFAULT_VALUE;
import static com.leapwise.evalexp.TestConstants.JSON_OBJECT;
import static org.assertj.core.api.Assertions.assertThat;

class ExpressionEvaluatorTest {

    @Test
    void evaluate() throws OperationNotSupportedException {
        assertThat(
                new ExpressionEvaluatorVisitor(EXPRESSION_DEFAULT_VALUE, JSON_OBJECT)
                        .evaluate())
                .isTrue();
    }
}