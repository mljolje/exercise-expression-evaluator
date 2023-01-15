// Generated from java-escape by ANTLR 4.11.1
package com.leapwise.evalexp.expressions.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EvalExpParser}.
 */
public interface EvalExpListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EvalExpParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(EvalExpParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link EvalExpParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(EvalExpParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(EvalExpParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(EvalExpParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDecimalExpression(EvalExpParser.DecimalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDecimalExpression(EvalExpParser.DecimalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(EvalExpParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(EvalExpParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(EvalExpParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(EvalExpParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(EvalExpParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(EvalExpParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(EvalExpParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(EvalExpParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(EvalExpParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(EvalExpParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(EvalExpParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(EvalExpParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparatorExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComparatorExpression(EvalExpParser.ComparatorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparatorExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComparatorExpression(EvalExpParser.ComparatorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntegerExpression(EvalExpParser.IntegerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerExpression}
	 * labeled alternative in {@link EvalExpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntegerExpression(EvalExpParser.IntegerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EvalExpParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(EvalExpParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link EvalExpParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(EvalExpParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link EvalExpParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(EvalExpParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link EvalExpParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(EvalExpParser.BinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link EvalExpParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(EvalExpParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link EvalExpParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(EvalExpParser.BoolContext ctx);
}