package com.leapwise.evalexp;

import com.leapwise.evalexp.config.AsyncSyncConfiguration;
import com.leapwise.evalexp.config.EmbeddedSQL;
import com.leapwise.evalexp.domain.Expression;
import com.leapwise.evalexp.repository.ExpressionRepository;
import com.leapwise.evalexp.rest.ExpressionResource;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static com.leapwise.evalexp.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = { EvalexpApplication.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
class EvalexpApplicationItTests {
	public static final String INVALID_EXPRESSION = "< test == \"test\"";
	@Autowired
	private ExpressionResource expressionResource;

	@Test
	void contextLoads() {
		assertThat(expressionResource).isNotNull();
	}

	private static final String DEFAULT_NAME = "DefaultName";
	private static final String UPDATED_NAME = "UpdatedName";

	private static final String ENTITY_API_URL = "/expression";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static final String EVALUATE_API_URL = "/evaluate";
	private static final String EVALUATE_JS_API_URL = "/evaluate/js";


	private static final Random random = new Random();
	private static final AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

	@Autowired
	private ExpressionRepository expressionRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restExpressionMockMvc;

	private Expression expression;

	public static Expression createEntity() {
		return new Expression().name(DEFAULT_NAME).value(EXPRESSION_DEFAULT_VALUE);
	}

	public static Expression createUpdatedEntity() {
		return new Expression().name(UPDATED_NAME).value(EXPRESSION_UPDATED_VALUE);
	}

	@BeforeEach
	public void initTest() {
		expression = createEntity();
	}

	@Test
	@Transactional
	void createExpression() throws Exception {
		int databaseSizeBeforeCreate = expressionRepository.findAll().size();
		restExpressionMockMvc
				.perform(post(ENTITY_API_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestJsonConvUtil.convertToJsonBytes(expression)))
				.andExpect(status().isCreated());

		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeCreate + 1);
		Expression testExpression = expressionList.get(expressionList.size() - 1);
		assertThat(testExpression.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testExpression.getValue()).isEqualTo(EXPRESSION_DEFAULT_VALUE);
	}

	@Test
	@Transactional
	void createInvalidExpression() throws Exception {
		Expression invalidExpression = new Expression().name(DEFAULT_NAME).value(INVALID_EXPRESSION);
		restExpressionMockMvc
				.perform(post(ENTITY_API_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestJsonConvUtil.convertToJsonBytes(invalidExpression)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	void createExpressionWithExistingId() throws Exception {
		expression.setId(1L);

		int databaseSizeBeforeCreate = expressionRepository.findAll().size();

		restExpressionMockMvc
				.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestJsonConvUtil.convertToJsonBytes(expression)))
				.andExpect(status().isBadRequest());

		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
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
	@Transactional
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
	@Transactional
	void getNonExistingExpression() throws Exception {
		restExpressionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	void putExistingExpression() throws Exception {
		expressionRepository.saveAndFlush(expression);

		int databaseSizeBeforeUpdate = expressionRepository.findAll().size();

		Expression updatedExpression = expressionRepository.findById(expression.getId()).get();
		em.detach(updatedExpression);
		updatedExpression.name(UPDATED_NAME).value(EXPRESSION_UPDATED_VALUE);

		restExpressionMockMvc
				.perform(
						put(ENTITY_API_URL_ID, updatedExpression.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestJsonConvUtil.convertToJsonBytes(updatedExpression))
				)
				.andExpect(status().isOk());

		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
		Expression testExpression = expressionList.get(expressionList.size() - 1);
		assertThat(testExpression.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testExpression.getValue()).isEqualTo(EXPRESSION_UPDATED_VALUE);
	}

	@Test
	@Transactional
	void putInvalidExistingExpression() throws Exception {
		expressionRepository.saveAndFlush(expression);
		Expression updatedExpression = expressionRepository.findById(expression.getId()).get();
		em.detach(updatedExpression);
		updatedExpression.name(UPDATED_NAME).value(INVALID_EXPRESSION);
		restExpressionMockMvc
				.perform(
						put(ENTITY_API_URL_ID, updatedExpression.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestJsonConvUtil.convertToJsonBytes(updatedExpression))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	void putNonExistingExpression() throws Exception {
		int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
		expression.setId(count.incrementAndGet());

		restExpressionMockMvc
				.perform(
						put(ENTITY_API_URL_ID, expression.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestJsonConvUtil.convertToJsonBytes(expression))
				)
				.andExpect(status().isBadRequest());

		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void putWithIdMismatchExpression() throws Exception {
		int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
		expression.setId(count.incrementAndGet());

		restExpressionMockMvc
				.perform(
						put(ENTITY_API_URL_ID, count.incrementAndGet())
								.contentType(MediaType.APPLICATION_JSON)
								.content(TestJsonConvUtil.convertToJsonBytes(expression))
				)
				.andExpect(status().isBadRequest());

		// Validate the Expression in the database
		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void putWithMissingIdPathParamExpression() throws Exception {
		int databaseSizeBeforeUpdate = expressionRepository.findAll().size();
		expression.setId(count.incrementAndGet());

		restExpressionMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestJsonConvUtil.convertToJsonBytes(expression)))
				.andExpect(status().isMethodNotAllowed());

		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void deleteExpression() throws Exception {
		expressionRepository.saveAndFlush(expression);
		int sizeBeforeDeletion = expressionRepository.findAll().size();
		restExpressionMockMvc
				.perform(delete(ENTITY_API_URL_ID, expression.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		List<Expression> expressionList = expressionRepository.findAll();
		assertThat(expressionList).hasSize(sizeBeforeDeletion - 1);
	}

	@Test
	@Transactional
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
	@Transactional
	void evaluateExpressionNegative() throws Exception {
		Expression negativeExpression = new Expression().name(DEFAULT_NAME).value(EXPRESSION_NEGATIVE_VALUE);
		expressionRepository.saveAndFlush(negativeExpression);
		restExpressionMockMvc
				.perform(post(EVALUATE_API_URL + "/" + negativeExpression.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON_OBJECT))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	@Transactional
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
	@Transactional
	void evaluateExpressionOverJsNegative() throws Exception {
		Expression negativeExpression = new Expression().name(DEFAULT_NAME).value(EXPRESSION_NEGATIVE_VALUE);
		expressionRepository.saveAndFlush(negativeExpression);
		restExpressionMockMvc
				.perform(post(EVALUATE_JS_API_URL + "/" + negativeExpression.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON_OBJECT))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	@Transactional
	void evaluateEmptyExpression() throws Exception {
		Expression emptyExpression = new Expression().name(DEFAULT_NAME).value("");
		expressionRepository.saveAndFlush(emptyExpression);
		restExpressionMockMvc
				.perform(post(EVALUATE_API_URL + "/" + emptyExpression.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON_OBJECT))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	void evaluateInvalidExpression() throws Exception {
		Expression invalidExpression = new Expression().name(DEFAULT_NAME).value(INVALID_EXPRESSION);
		expressionRepository.saveAndFlush(invalidExpression);
		restExpressionMockMvc
				.perform(post(EVALUATE_API_URL + "/" + invalidExpression.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON_OBJECT))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	void evaluateComplexExpression() throws Exception {
		Expression complexExpression = new Expression().name(DEFAULT_NAME).value(EXPRESSION_COMPLEX_VALUE);
		expressionRepository.saveAndFlush(complexExpression);
		restExpressionMockMvc
				.perform(post(EVALUATE_API_URL + "/" + complexExpression.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON_OBJECT))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional
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
