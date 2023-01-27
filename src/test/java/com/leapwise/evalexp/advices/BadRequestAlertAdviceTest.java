package com.leapwise.evalexp.advices;

import com.leapwise.evalexp.exceptions.BadRequestAlertException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BadRequestAlertAdviceTest {

    final static String BAD_REQUEST_MESSAGE = "Bad request message";
    final static String BAD_REQUEST_DETAILS = "Bad request details";

    @Test
    void badRequestAlertHandler() {
        assertThat(new BadRequestAlertAdvice()
                .badRequestAlertHandler(new BadRequestAlertException(BAD_REQUEST_MESSAGE, BAD_REQUEST_DETAILS)))
                .isEqualTo(BAD_REQUEST_MESSAGE + ": " + BAD_REQUEST_DETAILS);
    }
}