package com.leapwise.evalexp.advices;

import com.leapwise.evalexp.exceptions.NotFoundAlertException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundAlertAdviceTest {

    final String NOT_FOUND_MESSAGE = "Not found messsage";

    @Test
    void notFoundAlertHandler() {
        assertThat(new NotFoundAlertAdvice()
                .notFoundAlertHandler(new NotFoundAlertException(NOT_FOUND_MESSAGE)))
                .isEqualTo("Not found : " + NOT_FOUND_MESSAGE);
    }
}