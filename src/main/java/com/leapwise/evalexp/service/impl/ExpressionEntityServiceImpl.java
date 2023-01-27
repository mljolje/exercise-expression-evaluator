package com.leapwise.evalexp.service.impl;

import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.service.ExpressionEntityService;
import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;
import com.leapwise.evalexp.service.mappeer.ExpressionDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpressionEntityServiceImpl implements ExpressionEntityService {

    private final Logger log = LoggerFactory.getLogger(ExpressionEntityServiceImpl.class);

    private final ExpressionRepository expressionRepository;

    private final ExpressionDtoMapper expressionDtoMapper;

    public ExpressionEntityServiceImpl(ExpressionRepository expressionRepository, ExpressionDtoMapper expressionDtoMapper) {
        this.expressionRepository = expressionRepository;
        this.expressionDtoMapper = expressionDtoMapper;
    }

    @Override
    public ExpressionEntityDTO save(ExpressionEntityDTO expressionDto) {
        log.debug("Request to save Expression : {}", expressionDto);
        return expressionDtoMapper.toDto(
                expressionRepository.save(
                        expressionDtoMapper.toEntity(expressionDto)));
    }

    @Override
    public ExpressionEntityDTO update(ExpressionEntityDTO expressionDto) {
        log.debug("Request to update Expression : {}", expressionDto);
        return save(expressionDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpressionEntityDTO> findAll() {
        log.debug("Request to get all Expressions");
        return expressionRepository.findAll().stream().map(expressionDtoMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExpressionEntityDTO> findOne(Long id) {
        log.debug("Request to get Expression : {}", id);
        return expressionRepository.findById(id).map(expressionDtoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expression : {}", id);
        expressionRepository.deleteById(id);
    }
}

