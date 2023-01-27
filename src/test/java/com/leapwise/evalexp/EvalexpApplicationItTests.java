package com.leapwise.evalexp;

import com.leapwise.evalexp.config.AsyncSyncConfiguration;
import com.leapwise.evalexp.config.EmbeddedSQL;
import com.leapwise.evalexp.domain.ExpressionEntity;
import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.rest.ExpressionResource;
import com.leapwise.evalexp.service.dto.ExpressionEntityDTO;
import com.leapwise.evalexp.service.mappeer.ExpressionDtoMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static com.leapwise.evalexp.TestConstants.EXPRESSION_COMPLEX_VALUE;
import static com.leapwise.evalexp.TestConstants.EXPRESSION_DEFAULT_VALUE;
import static com.leapwise.evalexp.TestConstants.EXPRESSION_NEGATIVE_VALUE;
import static com.leapwise.evalexp.TestConstants.EXPRESSION_UPDATED_VALUE;
import static com.leapwise.evalexp.TestConstants.JSON_OBJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {EvalexpApplication.class, AsyncSyncConfiguration.class})
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@Transactional
class EvalexpApplicationItTests {
    public static final String INVALID_EXPRESSION = "< test == \"test\"";
    private static final String DEFAULT_NAME = "DefaultName";
    private static final String UPDATED_NAME = "UpdatedName";
    private static final String ENTITY_API_URL = "/expression";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String EVALUATE_API_URL = "/evaluate";
    private static final String EVALUATE_JS_API_URL = "/evaluate/js";
    private static final String EVALUATE_ANTLR_API_URL = "/evaluate/antlr";
    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    @Autowired
    private ExpressionResource expressionResource;
    @Autowired
    private ExpressionRepository expressionRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private MockMvc restExpressionMockMvc;
    @Autowired
    private ExpressionDtoMapper expressionDtoMapper;
    private ExpressionEntity expression;

    public static ExpressionEntity createEntity() {
        return new ExpressionEntity().name(DEFAULT_NAME).value(EXPRESSION_DEFAULT_VALUE);
    }

    public static ExpressionEntity createUpdatedEntity() {
        return new ExpressionEntity().name(UPDATED_NAME).value(EXPRESSION_UPDATED_VALUE);
    }

    @Test
    void contextLoads() {
        assertThat(expressionResource).isNotNull();
    }

    @BeforeEach
    public void initTest() {
        expression = createEntity();
    }

    private void assertEvaluationResult(String evaluateApiUrl) throws Exception {
        ExpressionEntity negativeExpression = new ExpressionEntity().name(DEFAULT_NAME).value(EXPRESSION_NEGATIVE_VALUE);
        expressionRepository.saveAndFlush(negativeExpression);
        restExpressionMockMvc
                .perform(post(evaluateApiUrl + "/" + negativeExpression.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJECT))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    private void assertResponseStatus(String apiUrl, String expression, ResultMatcher resultMatcher) throws Exception {
        ExpressionEntity emptyExpression = new ExpressionEntity().name(DEFAULT_NAME).value(expression);
        expressionRepository.saveAndFlush(emptyExpression);
        restExpressionMockMvc
                .perform(post(apiUrl + "/" + emptyExpression.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJECT))
                .andExpect(resultMatcher);
    }


    @Test
    void createExpression() throws Exception {
        int databaseSizeBeforeCreate = expressionRepository.findAll().size();
        restExpressionMockMvc
                .perform(post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestJsonConvUtil.convertToJsonBytes(expressionDtoMapper.toDto(expression))))
                .andExpect(status().isCreated());

        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeCreate + 1);
        ExpressionEntity testExpression = expressionList.get(expressionList.size() - 1);
        assertThat(testExpression.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExpression.getValue()).isEqualTo(EXPRESSION_DEFAULT_VALUE);
    }

    @Test
    void createInvalidExpression() throws Exception {
        ExpressionEntityDTO invalidExpression = new ExpressionEntityDTO(null, DEFAULT_NAME, INVALID_EXPRESSION);
        restExpressionMockMvc
                .perform(post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestJsonConvUtil.convertToJsonBytes(invalidExpression)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createExpressionWithExistingId() throws Exception {
        expression.setId(1L);

        int databaseSizeBeforeCreate = expressionRepository.findAll().size();

        restExpressionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(TestJsonConvUtil
                                .convertToJsonBytes(expressionDtoMapper.toDto(expression))))
                .andExpect(status().isBadRequest());

        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllExpressions() throws Exception {
        expressionRepository.saveAndFlush(expression);

        restExpressionMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(expression.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].value").value(hasItem(EXPRESSION_DEFAULT_VALUE)));
    }

    @Test
    void getExpression() throws Exception {
        expressionRepository.saveAndFlush(expression);

        // Get the expression
        restExpressionMockMvc
                .perform(get(ENTITY_API_URL_ID, expression.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(expression.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.value").value(EXPRESSION_DEFAULT_VALUE));
    }

    @Test
    void getNonExistingExpression() throws Exception {
        restExpressionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingExpression() throws Exception {
        expressionRepository.saveAndFlush(expression);

        int databaseSizeBeforeUpdate = expressionRepository.findAll().size();

        ExpressionEntity updatedExpression = expressionRepository.findById(expression.getId()).get();
        em.detach(updatedExpression);
        updatedExpression.name(UPDATED_NAME).value(EXPRESSION_UPDATED_VALUE);

        restExpressionMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, updatedExpression.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestJsonConvUtil.convertToJsonBytes(
                                        expressionDtoMapper.toDto(updatedExpression)))
                )
                .andExpect(status().isOk());

        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
        ExpressionEntity testExpression = expressionList.get(expressionList.size() - 1);
        assertThat(testExpression.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExpression.getValue()).isEqualTo(EXPRESSION_UPDATED_VALUE);
    }

    @Test
    void putInvalidExistingExpression() throws Exception {
        expressionRepository.saveAndFlush(expression);
        ExpressionEntity updatedExpression = expressionRepository.findById(expression.getId()).get();
        em.detach(updatedExpression);
        updatedExpression.name(UPDATED_NAME).value(INVALID_EXPRESSION);
        restExpressionMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, updatedExpression.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestJsonConvUtil.convertToJsonBytes(
                                        expressionDtoMapper.toDto(updatedExpression)))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void putNonExistingExpression() throws Exception {
        int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
        expression.setId(count.incrementAndGet());

        restExpressionMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, expression.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestJsonConvUtil.convertToJsonBytes(
                                        expressionDtoMapper.toDto(expression)))
                )
                .andExpect(status().isBadRequest());

        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExpression() throws Exception {
        int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
        expression.setId(count.incrementAndGet());

        restExpressionMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestJsonConvUtil.convertToJsonBytes(
                                        expressionDtoMapper.toDto(expression)))
                )
                .andExpect(status().isBadRequest());

        // Validate the Expression in the database
        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExpression() throws Exception {
        int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
        expression.setId(count.incrementAndGet());

        restExpressionMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(TestJsonConvUtil.convertToJsonBytes(
                                expressionDtoMapper.toDto(expression))))
                .andExpect(status().isMethodNotAllowed());

        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExpression() throws Exception {
        expressionRepository.saveAndFlush(expression);
        int sizeBeforeDeletion = expressionRepository.findAll().size();
        restExpressionMockMvc
                .perform(delete(ENTITY_API_URL_ID, expression.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        List<ExpressionEntity> expressionList = expressionRepository.findAll();
        assertThat(expressionList).hasSize(sizeBeforeDeletion - 1);
    }

    @Test
    void evaluateExpression() throws Exception {
        expressionRepository.saveAndFlush(expression);
        restExpressionMockMvc
                .perform(post(EVALUATE_API_URL + "/" + expression.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJECT))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void evaluateExpressionNegative() throws Exception {
        assertEvaluationResult(EVALUATE_API_URL);
    }


    @Test
    void evaluateExpressionOverJs() throws Exception {
        expressionRepository.saveAndFlush(expression);
        restExpressionMockMvc
                .perform(post(EVALUATE_JS_API_URL + "/" + expression.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJECT))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void evaluateExpressionOverJsNegative() throws Exception {
        assertEvaluationResult(EVALUATE_JS_API_URL);
    }

    @Test
    void evaluateExpressionOverAntlrNegative() throws Exception {
        assertEvaluationResult(EVALUATE_ANTLR_API_URL);
    }

    @Test
    void evaluateEmptyExpression() throws Exception {
        assertResponseStatus(EVALUATE_API_URL, "", status().isBadRequest());
    }

    @Test
    void evaluateInvalidExpression() throws Exception {
        assertResponseStatus(EVALUATE_API_URL, INVALID_EXPRESSION, status().isBadRequest());
    }

    @Test
    void evaluateComplexExpression() throws Exception {
        assertResponseStatus(EVALUATE_API_URL, EXPRESSION_COMPLEX_VALUE, status().isOk());
    }

    @Test
    void evaluateComplexExpressionOverAntlr() throws Exception {
        assertResponseStatus(EVALUATE_ANTLR_API_URL, EXPRESSION_COMPLEX_VALUE, status().isOk());
    }

    @Test
    void evaluateInvalidJson() throws Exception {
        expressionRepository.saveAndFlush(expression);
        restExpressionMockMvc
                .perform(post(EVALUATE_API_URL + "/" + expression.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(ExpressionResource.JSON_OBJECT_NOT_VALID)));
    }
}
