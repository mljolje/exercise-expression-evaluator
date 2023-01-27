package com.leapwise.evalexp.evaluator.rdp;

import com.leapwise.evalexp.TestConstants;
import com.leapwise.evalexp.evaluator.rdp.expressions.Literal;
import com.leapwise.evalexp.exceptions.OperationNotSupportedException;
import com.leapwise.evalexp.exceptions.ProcessingTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class EvaluatorTest {

    Evaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new Evaluator(TestConstants.JSON_OBJECT);
    }

    private void assertEqual(String expression, Object expectedResult) throws IOException {
        assertThat(evaluator.evaluate(expression))
                .isEqualTo(new Literal<>(expectedResult));
    }

    @Test
    void evaluateEquality() throws IOException {
        assertEqual("customer.firstName == \"JOHN\"", true);
    }

    @Test
    void evaluateTernary() throws IOException {
        assertEqual("null != customer.firstName ? 56 : 78", 56);
    }

    @Test
    void evaluateTernaryElse() throws IOException {
        assertEqual("null == customer.firstName ? 56 : 78", 78);
    }

    @Test
    void evaluateTernaryAndEquality() throws IOException {
        assertEqual("((null != customer.firstName ? 56 : 78) == 56) == true", true);
    }

    @Test
    void evaluateTernaryAndComparison() throws IOException {
        assertEqual("(null != customer.firstName ? 56 : 78) >= 45.45", true);
    }

    @Test
    void evaluateTernaryAndComparisonNeg() throws IOException {
        assertEqual("(null != customer.firstName ? 56 : 78) < customer.sharePrice", false);
    }

    @Test
    void evaluateUnary() throws IOException {
        assertEqual("-(-5)", 5);
    }

    @Test
    void evaluateNegate() throws IOException {
        assertEqual("!(5 == 5) == false", true);
    }


    @Test
    void evaluateMix() throws IOException {
        assertEqual("5689 <= (5468 + 589.5/21*65.56 - 865) == true", true);
    }

    @Test
    void evaluateLogical() throws IOException {
        assertEqual("-(-5) < 6 || null == null", true);
    }

    @Test
    void evaluateLogicalNot() throws IOException {
        assertEqual("-(-5) < 6 && null != unknown.variable", false);
    }

    @Test
    void evaluateBoolean() throws IOException {
        assertEqual("(customer.address.zipCode != 1234) == customer.married", false);
    }

    @Test
    void evaluateComplex() throws IOException {
        assertEqual(TestConstants.EXPRESSION_COMPLEX_VALUE, false);
    }

    @Test
    void evaluateArithmetics() throws IOException {
        assertEqual("5 + 10 - 8 * 2 / 4", 11);
    }

    @Test
    void evaluateArithmeticsAndLogical() throws IOException {
        assertEqual("((10 / 5) == 2) == true", true);
    }

    @Test
    void evaluateInvalidTokenException() {
        assertThrows(ProcessingTokenException.class, () -> evaluator.evaluate("(wrong"));
    }

    @Test
    void evaluateInvalidComparisonException() {
        assertThrows(OperationNotSupportedException.class, () -> evaluator.evaluate("5 > \"5\""));
    }

}