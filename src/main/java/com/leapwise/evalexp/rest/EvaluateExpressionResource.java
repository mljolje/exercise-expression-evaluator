package com.leapwise.evalexp.rest;

import com.leapwise.evalexp.config.Constants;
import com.leapwise.evalexp.domain.enumeration.ExpressionEngineType;
import com.leapwise.evalexp.exceptions.BadRequestAlertException;
import com.leapwise.evalexp.exceptions.EvaluationErrorException;
import com.leapwise.evalexp.exceptions.NotFoundAlertException;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import com.leapwise.evalexp.service.ExpressionEntityService;
import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;
import com.leapwise.evalexp.service.impl.JsEvaluateExpressionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/evaluate")
public class EvaluateExpressionResource {

    private static final String EXPRESSION_WITH_ID = "Expression with ID ";
    private static final String JSON_OBJECT_NOT_VALID = "JSON object not valid";
    private final Logger log = LoggerFactory.getLogger(EvaluateExpressionResource.class);

    private final EvaluateExpressionService rdpEvaluateExpressionService;
    private final EvaluateExpressionService jsEvaluateExpressionService;
    private final EvaluateExpressionService antlrEvaluateExpressionService;

    private final ExpressionEntityService expressionEntityService;

    /**
     * Constructor with injection of all three supported types of evaluations services as well service for handling
     * expression entities in database solution
     *
     * @param rdpEvaluateExpressionService
     * @param antlrEvaluateExpressionService
     * @param jsEvaluateExpressionService
     * @param expressionEntityService
     */
    public EvaluateExpressionResource(
            @Qualifier(Constants.RECURSIVE_DESCENT) final EvaluateExpressionService rdpEvaluateExpressionService,
            @Qualifier(Constants.ANTLR) final EvaluateExpressionService antlrEvaluateExpressionService,
            @Qualifier(Constants.JAVASCRIPT) final EvaluateExpressionService jsEvaluateExpressionService,
            final ExpressionEntityService expressionEntityService) {
        this.rdpEvaluateExpressionService = rdpEvaluateExpressionService;
        this.jsEvaluateExpressionService = jsEvaluateExpressionService;
        this.antlrEvaluateExpressionService = antlrEvaluateExpressionService;
        this.expressionEntityService = expressionEntityService;
    }

    /**
     * Evaluate expression on JSON object using simple recursive descent parser
     * Using POST instead of GET due to non-empty body as recommended
     *
     * @param expressionId the id of expression stored in DB
     * @param jsonObject   JSON object in string format
     * @return Results of evaluation in boolean value
     */
    @Operation(summary = "Evaluate expression on JSON object (as request body) using simple Recursive descent parser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed expression on JSON object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON object or expression execution",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Expression not found",
                    content = @Content)})
    @PostMapping("/{expressionId}")
    public ResponseEntity<Boolean> evaluateExpression(
            @PathVariable Long expressionId,
            @RequestBody String jsonObject) {
        return callEvaluationService(expressionId, jsonObject, ExpressionEngineType.RECURSIVE_DESCENT);
    }

    /**
     * Evaluate expression on JSON object using simple recursive descent parser
     * Using POST instead of GET due to non-empty body as recommended
     *
     * @param expressionId the id of expression stored in DB
     * @param jsonObject   JSON object in string format
     * @return
     */
    @Operation(summary = "Evaluate expression on JSON object (as request body) using ANTLR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed expression on JSON object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON object or expression execution",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Expression not found",
                    content = @Content)})
    @PostMapping("/antlr/{expressionId}")
    public ResponseEntity<Boolean> evaluateExpressionUsingAntlr(
            @PathVariable Long expressionId,
            @RequestBody String jsonObject) {
        return callEvaluationService(expressionId, jsonObject, ExpressionEngineType.ANTLR);
    }

    /**
     * Evaluate expression on JSON object using JavaScript engine
     * Using POST instead of GET due to non-empty body as recommended
     *
     * @param expressionId the id of expression stored in DB
     * @param jsonObject   JSON object in string format
     * @return
     */
    @Operation(summary = "Evaluate expression on JSON object (as request body) using Java Script engine "
            + JsEvaluateExpressionServiceImpl.JAVA_SCRIPT_ENGINE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed expression on JSON object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON object or expression execution",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Expression not found",
                    content = @Content)})
    @PostMapping("/js/{expressionId}")
    public ResponseEntity<Boolean> evaluateExpressionUsingJs(
            @PathVariable Long expressionId,
            @RequestBody String jsonObject) {
        return callEvaluationService(expressionId, jsonObject, ExpressionEngineType.JAVASCRIPT);
    }

    private ResponseEntity<Boolean> callEvaluationService(
            final Long expressionId, final String jsonObject, ExpressionEngineType expressionEngineType) {
        log.debug("REST request to evaluate Expression : {} on {}, using: {}",
                expressionId, jsonObject, expressionEngineType);
        if (rdpEvaluateExpressionService.isJsonObjectInvalid(jsonObject)) {
            throw new BadRequestAlertException(JSON_OBJECT_NOT_VALID, jsonObject);
        }
        final Optional<ExpressionEntityDTO> expression = expressionEntityService.findOne(expressionId);
        return expression
                .map(expressionObject -> {
                    if (StringUtils.isEmpty(expressionObject.value())) {
                        throw new EvaluationErrorException("Empty expression entity");
                    }
                    return switch (expressionEngineType) {
                        case ANTLR -> ResponseEntity.ok().body(
                                antlrEvaluateExpressionService.evaluate(expressionObject.value(), jsonObject));
                        case RECURSIVE_DESCENT -> ResponseEntity.ok().body(
                                rdpEvaluateExpressionService.evaluate(expressionObject.value(), jsonObject));
                        case JAVASCRIPT -> ResponseEntity.ok().body(
                                jsEvaluateExpressionService.evaluate(expressionObject.value(), jsonObject));
                    };
                })
                .orElseThrow(() -> new NotFoundAlertException(EXPRESSION_WITH_ID + expressionId));
    }

}
