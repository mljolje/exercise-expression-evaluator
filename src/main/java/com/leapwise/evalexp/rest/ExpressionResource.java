package com.leapwise.evalexp.rest;

import com.leapwise.evalexp.config.Constants;
import com.leapwise.evalexp.exceptions.BadRequestAlertException;
import com.leapwise.evalexp.exceptions.NotFoundAlertException;
import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.service.EvaluateExpressionService;
import com.leapwise.evalexp.service.ExpressionEntityService;
import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/expression")
public class ExpressionResource {
    public static final String JSON_OBJECT_NOT_VALID = "JSON object not valid";
    private static final String EXPRESSION_WITH_ID = "Expression with ID ";
    private static final String EXPRESSION = "Expression";
    private final Logger log = LoggerFactory.getLogger(ExpressionResource.class);
    private final ExpressionEntityService expressionService;

    private final EvaluateExpressionService evaluateExpressionService;

    private final ExpressionRepository expressionRepository;

    public ExpressionResource(
            final ExpressionEntityService expressionService,
            final ExpressionRepository expressionRepository,
            @Qualifier(Constants.RECURSIVE_DESCENT) final EvaluateExpressionService evaluateExpressionService
    ) {
        this.expressionService = expressionService;
        this.evaluateExpressionService = evaluateExpressionService;
        this.expressionRepository = expressionRepository;
    }

    /**
     * {@code POST  /expression} : Create a new expression.
     *
     * @param expression the expression to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expression,
     * or with status {@code 400 (Bad Request)} if the expression has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Stores new expression into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully creates and stores new expression.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpressionEntityDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid expression.",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Model limitations violated (e.g. size).",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred.",
                    content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<ExpressionEntityDTO> createExpression(@RequestBody ExpressionEntityDTO expression)
            throws URISyntaxException {
        log.debug("REST request to save Expression : {}", expression);
        if (expression.id() != null) {
            throw new BadRequestAlertException("A new expression cannot already have an ID",
                    "ID exists " + expression.id());
        }

        evaluateExpressionService.validateExpression(expression.value());

        ExpressionEntityDTO result = expressionService.save(expression);
        return ResponseEntity.created(new URI("/api/expressions/" + result.id())).body(result);
    }

    /**
     * {@code PUT  /expression/:id} : Updates an existing expression.
     *
     * @param id         the id of the expression to save.
     * @param expression the expression to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expression,
     * or with status {@code 400 (Bad Request)} if the expression is not valid,
     * or with status {@code 422 (Unprocessable Entity)} if the expression cannot be stored due to model limitations (e.g. size),
     * or with status {@code 500 (Internal Server Error)} if the expression couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Updates expression into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updates expression.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpressionEntityDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid expression.",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Model limitations violated (e.g. size).",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExpressionEntityDTO> updateExpression(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ExpressionEntityDTO expression
    ) throws URISyntaxException {
        log.debug("REST request to update Expression : {}, {}", id, expression);
        if (expression.id() == null) {
            throw new BadRequestAlertException(EXPRESSION + " invalid ID", "ID is null");
        }
        if (!Objects.equals(id, expression.id())) {
            throw new BadRequestAlertException(EXPRESSION + " invalid ID", "Invalid ID");
        }

        if (!expressionRepository.existsById(id)) {
            throw new BadRequestAlertException(EXPRESSION + " not found", "ID not found");
        }

        evaluateExpressionService.validateExpression(expression.value());

        ExpressionEntityDTO result = expressionService.update(expression);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /expression} : get list af all expression entities {@link ExpressionEntityDTO}.
     *
     * @return List of expression entities {@link ExpressionEntityDTO}
     */
    @Operation(summary = "Retrieves all expressions in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieves all expressions.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred",
                    content = @Content)
    })
    @GetMapping("")
    public List<ExpressionEntityDTO> getAllExpressions() {
        log.debug("REST request to get all Expressions");
        return expressionService.findAll();
    }

    /**
     * {@code GET  /expression/:id} : get expression by "id".
     *
     * @param id the id of the expression to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expression,
     * or with status {@code 404 (Not found)} if the expression with {@param id} does not exists.
     */
    @Operation(summary = "Retrieves expression based on provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieves expression.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpressionEntityDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Expression with provided id not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExpressionEntityDTO> getExpression(@PathVariable Long id) {
        log.debug("REST request to get Expression : {}", id);
        Optional<ExpressionEntityDTO> expression = expressionService.findOne(id);
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
    @Operation(summary = "Deletes expression based on provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deletes expression.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpression(@PathVariable Long id) {
        log.debug("REST request to delete Expression : {}", id);
        expressionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
