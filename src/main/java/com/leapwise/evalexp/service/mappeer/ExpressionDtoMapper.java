package com.leapwise.evalexp.service.mappeer;

import com.leapwise.evalexp.domain.ExpressionEntity;
import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExpressionDtoMapper {
    private final Logger log = LoggerFactory.getLogger(ExpressionDtoMapper.class);

    public ExpressionEntity toEntity(ExpressionEntityDTO dto) {
        log.debug("Converting DTO {} to Entity", dto);
        if (dto == null) {
            return null;
        }

        ExpressionEntity expressionEntity = new ExpressionEntity();

        expressionEntity.setId(dto.id());
        expressionEntity.setName(dto.name());
        expressionEntity.setValue(dto.value());

        return expressionEntity;
    }

    public ExpressionEntityDTO toDto(ExpressionEntity entity) {
        log.debug("Converting Entity {} to DTO", entity);
        if (entity == null) {
            return null;
        }
        return new ExpressionEntityDTO(entity.getId(), entity.getName(), entity.getValue());
    }

}

