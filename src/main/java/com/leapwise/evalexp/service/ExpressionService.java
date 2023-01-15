package com.leapwise.evalexp.service;

import com.leapwise.evalexp.domain.Expression;

import java.util.List;
import java.util.Optional;

public interface ExpressionService {
    Expression save(Expression expression);
    Expression update(Expression expression);
    List<Expression> findAll();
    Optional<Expression> findOne(Long id);
    void delete(Long id);
}
