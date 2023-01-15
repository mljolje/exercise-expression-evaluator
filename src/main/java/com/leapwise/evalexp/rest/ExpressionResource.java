package com.leapwise.evalexp.rest;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leapwise.evalexp.domain.Expression;
import com.leapwise.evalexp.exceptions.BadRequestAlertException;
import com.leapwise.evalexp.exceptions.InvalidExpressionException;
import com.leapwise.evalexp.exceptions.NotFoundAlertException;
import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import com.leapwise.evalexp.service.ExpressionService;
import com.leapwise.evalexp.service.impl.EvaluateExpressionServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ExpressionResource {

    public static final String EXPRESSION_WITH_ID = "Expression with ID ";
    public static final String JSON_OBJECT_NOT_VALID = "JSON object not valid";
    public static final String BAD_REQUEST = "Bad request";
    private final Logger log = LoggerFactory.getLogger(ExpressionResource.class);

    private static final String EXPRESSION = "Expression";

    private final ExpressionService expressionService;

    private final EvaluateExpressionService evaluateExpressionService;

    private final ExpressionRepository expressionRepository;

    public ExpressionResource(
            ExpressionService expressionService,
            EvaluateExpressionService evaluateExpressionService,
            ExpressionRepository expressionRepository
    ) {
        this.expressionService = expressionService;
        this.evaluateExpressionService = evaluateExpressionService;
        this.expressionRepository = expressionRepository;
    }

    /**
     * {@code POST  /expressions} : Create a new expression.
     *
     * @param expression the expression to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expression, or with status {@code 400 (Bad Request)} if the expression has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Stores new expression into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully creates and stores new expression.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Expression.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid expression.",
                    content = @Content)})
    @PostMapping("/expression")
    public ResponseEntity<Expression> createExpression(@RequestBody Expression expression) throws URISyntaxException {
        log.debug("REST request to save Expression : {}", expression);
        if (expression.getId() != null) {
            throw new BadRequestAlertException("A new expression cannot already have an ID", "ID exists " + expression.getId());
        }
        if (isExpressionInvalid(expression.getValue())) {
            throw new BadRequestAlertException("Expression is not valid", expression.getValue());
        }

        Expression result = expressionService.save(expression);
        return ResponseEntity.created(new URI("/api/expressions/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /expression/:id} : Updates an existing expression.
     *
     * @param id         the id of the expression to save.
     * @param expression the expression to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expression,
     * or with status {@code 400 (Bad Request)} if the expression is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expression couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expression/{id}")
    public ResponseEntity<Expression> updateExpression(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody Expression expression
    ) throws URISyntaxException {
        log.debug("REST request to update Expression : {}, {}", id, expression);
        if (expression.getId() == null) {
            throw new BadRequestAlertException(EXPRESSION + " invalid ID", "ID is null");
        }
        if (!Objects.equals(id, expression.getId())) {
            throw new BadRequestAlertException(EXPRESSION + " invalid ID", "Invalid ID");
        }

        if (!expressionRepository.existsById(id)) {
            throw new BadRequestAlertException(EXPRESSION + " not found", "ID not found");
        }

        if (isExpressionInvalid(expression.getValue())) {
            throw new BadRequestAlertException("Expression is not valid", expression.getValue());
        }

        Expression result = expressionService.update(expression);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/expression")
    public List<Expression> getAllExpressions() {
        log.debug("REST request to get all Expressions");
        return expressionService.findAll();
    }

    @GetMapping("/expression/{id}")
    public ResponseEntity<Expression> getExpression(@PathVariable Long id) {
        log.debug("REST request to get Expression : {}", id);
        Optional<Expression> expression = expressionService.findOne(id);
        return expression
                .map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new NotFoundAlertException(EXPRESSION_WITH_ID + id));
    }

    /**
     * {@code DELETE  /expressions/:id} : delete the "id" expression.
     *
     * @param id the id of the expression to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expression/{id}")
    public ResponseEntity<Void> deleteExpression(@PathVariable Long id) {
        log.debug("REST request to delete Expression : {}", id);
        expressionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Evaluate expression on JSON object using simple recursive descent parser
     * Using POST instead of GET due to non-empty body as recommended
     *
     * @param expressionId the id of expression stored in DB
     * @param jsonObject   JSON object in string format
     * @return
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
    @PostMapping("/evaluate/{expressionId}")
    public ResponseEntity<Boolean> evaluateExpression(
            @PathVariable Long expressionId,
            @RequestBody String jsonObject) {
        return callEvaluationService(expressionId, jsonObject, false);
    }

    /**
     * Evaluate expression on JSON object using JavaScript engine
     * Using POST instead of GET due to non-empty body as recommended
     *
     * @param expressionId the id of expression stored in DB
     * @param jsonObject   JSON object in string format
     * @return
     */
    @Operation(summary = "Evaluate expression on JSON object (as request body) using Java Script engine " + EvaluateExpressionServiceImpl.JAVA_SCRIPT_ENGINE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed expression on JSON object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON object or expression execution",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Expression not found",
                    content = @Content)})
    @PostMapping("/evaluate/js/{expressionId}")
    public ResponseEntity<Boolean> evaluateExpressionUsingJs(
            @PathVariable Long expressionId,
            @RequestBody String jsonObject) {
        return callEvaluationService(expressionId, jsonObject, true);
    }

    private ResponseEntity<Boolean> callEvaluationService(final Long expressionId, final String jsonObject, boolean useJsEngine) {
        log.debug("REST request to evaluate Expression : {} on {}, jsEngineActive: {}", expressionId, jsonObject, useJsEngine);
        if (isJsonObjectInvalid(jsonObject)) {
            throw new BadRequestAlertException(JSON_OBJECT_NOT_VALID, jsonObject);
        }
        final Optional<Expression> expression = expressionService.findOne(expressionId);
        return expression
                .map(expressionObject -> {
                    try {
                        if (StringUtils.isEmpty(expressionObject.getValue())) {
                            throw new InvalidExpressionException(expressionObject.getValue());
                        }
                        return ResponseEntity.ok().body(
                                useJsEngine ?
                                        evaluateExpressionService.evaluateUsingJSEngine(expressionObject.getValue(), jsonObject) :
                                        evaluateExpressionService.evaluate(expressionObject.getValue(), jsonObject)
                        );
                    } catch (RuntimeException | ScriptException | IOException e) {
                        throw new BadRequestAlertException(BAD_REQUEST, e.getMessage());
                    }
                })
                .orElseThrow(() -> new NotFoundAlertException(EXPRESSION_WITH_ID + expressionId));
    }

    private boolean isJsonObjectInvalid(final String jsonObject) {
        try {
            new ObjectMapper().readTree(jsonObject);
        } catch (JacksonException e) {
            log.debug("Invalid JSON object", e);
            return true;
        }
        return false;
    }

    private boolean isExpressionInvalid(final String expression) {
        try {
            final String emptyJsonObj = "{}";
            evaluateExpressionService.evaluate(expression, emptyJsonObj);
            return false;
        } catch (RuntimeException e) {
            log.info("Invalid expression {}", e.getMessage());
            return true;
        }
    }
}
