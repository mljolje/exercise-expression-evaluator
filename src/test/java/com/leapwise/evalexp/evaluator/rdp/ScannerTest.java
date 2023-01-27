package com.leapwise.evalexp.evaluator.rdp;

import com.leapwise.evalexp.TestConstants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerTest {

    @Test
    void parse() throws IOException {
        final Scanner scanner = new Scanner(TestConstants.EXPRESSION_COMPLEX_VALUE);
        scanner.readTokens();
        assertThat(scanner.getTokenList()).hasSize(54);
        assertThat(scanner.getTokenList().stream().filter(t -> TokenType.STRING.equals(t.type())).collect(Collectors.toList())).hasSize(2);
    }
}