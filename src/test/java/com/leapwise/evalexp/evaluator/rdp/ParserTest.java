package com.leapwise.evalexp.evaluator.rdp;

import com.leapwise.evalexp.evaluator.rdp.expressions.Ternary;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {
    @Test
    void parse() throws IOException {

        Scanner scanner = new Scanner("customer.firstName != null ? true : false");
        scanner.readTokens();
        Parser parser = new Parser(scanner.getTokenList());
        assertThat(parser.parse()).isInstanceOf(Ternary.class);
    }
}