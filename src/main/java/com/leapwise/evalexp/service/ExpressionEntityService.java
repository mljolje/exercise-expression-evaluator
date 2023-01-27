package com.leapwise.evalexp.service;

import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;

import java.util.List;
import java.util.Optional;

public interface ExpressionEntityService {
    ExpressionEntityDTO save(ExpressionEntityDTO expression);

    ExpressionEntityDTO update(ExpressionEntityDTO expression);

    List<ExpressionEntityDTO> findAll();

    Optional<ExpressionEntityDTO> findOne(Long id);

    void delete(Long id);
}
