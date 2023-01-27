package com.leapwise.evalexp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Expression DTO")
public record ExpressionEntityDTO(
        @Schema(description = "id of expression")
        Long id,
        @Schema(description = "name of expression, cannot be null or empty")
        String name,
        @Schema(description = "vale of expression, cannot be null or empty, max size 10000")
        String value)
        implements Serializable {
}
