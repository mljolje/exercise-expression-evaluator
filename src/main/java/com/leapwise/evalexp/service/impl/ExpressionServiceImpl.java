package com.leapwise.evalexp.service.impl;

import com.leapwise.evalexp.domain.Expression;
import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.service.ExpressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpressionServiceImpl implements ExpressionService {

    private final Logger log = LoggerFactory.getLogger(ExpressionServiceImpl.class);

    private final ExpressionRepository expressionRepository;

    public ExpressionServiceImpl(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @Override
    public Expression save(Expression expression) {
        log.debug("Request to save Expression : {}", expression);
        return expressionRepository.save(expression);
    }

    @Override
    public Expression update(Expression expression) {
        log.debug("Request to update Expression : {}", expression);
        return expressionRepository.save(expression);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expression> findAll() {
        log.debug("Request to get all Expressions");
        return expressionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Expression> findOne(Long id) {
        log.debug("Request to get Expression : {}", id);
        return expressionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expression : {}", id);
        expressionRepository.deleteById(id);
    }
}

