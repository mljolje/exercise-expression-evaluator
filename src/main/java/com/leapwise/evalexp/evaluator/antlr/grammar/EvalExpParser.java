// Generated from java-escape by ANTLR 4.11.1
package com.leapwise.evalexp.evaluator.antlr.grammar;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class EvalExpParser extends Parser {
    public static final int
            AND = 1, OR = 2, ANDJS = 3, ORJS = 4, NOT = 5, TRUE = 6, FALSE = 7, GT = 8, GE = 9, LT = 10,
            LE = 11, EQ = 12, NEQ = 13, LPAREN = 14, RPAREN = 15, DECIMAL = 16, INTEGER = 17, IDENTIFIER = 18,
            SPACE = 19, NULL = 20, STRING = 21;
    public static final int
            RULE_parse = 0, RULE_expression = 1, RULE_comparator = 2, RULE_binary = 3,
            RULE_bool = 4;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\u0004\u0001\u00150\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" +
                    "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001" +
                    "\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u001b\b\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0005\u0001%\b\u0001\n\u0001\f\u0001(\t\u0001\u0001" +
                    "\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001" +
                    "\u0004\u0000\u0001\u0002\u0005\u0000\u0002\u0004\u0006\b\u0000\u0003\u0001" +
                    "\u0000\b\r\u0001\u0000\u0001\u0004\u0001\u0000\u0006\u00073\u0000\n\u0001" +
                    "\u0000\u0000\u0000\u0002\u001a\u0001\u0000\u0000\u0000\u0004)\u0001\u0000" +
                    "\u0000\u0000\u0006+\u0001\u0000\u0000\u0000\b-\u0001\u0000\u0000\u0000" +
                    "\n\u000b\u0003\u0002\u0001\u0000\u000b\f\u0005\u0000\u0000\u0001\f\u0001" +
                    "\u0001\u0000\u0000\u0000\r\u000e\u0006\u0001\uffff\uffff\u0000\u000e\u000f" +
                    "\u0005\u000e\u0000\u0000\u000f\u0010\u0003\u0002\u0001\u0000\u0010\u0011" +
                    "\u0005\u000f\u0000\u0000\u0011\u001b\u0001\u0000\u0000\u0000\u0012\u0013" +
                    "\u0005\u0005\u0000\u0000\u0013\u001b\u0003\u0002\u0001\t\u0014\u001b\u0003" +
                    "\b\u0004\u0000\u0015\u001b\u0005\u0012\u0000\u0000\u0016\u001b\u0005\u0015" +
                    "\u0000\u0000\u0017\u001b\u0005\u0010\u0000\u0000\u0018\u001b\u0005\u0011" +
                    "\u0000\u0000\u0019\u001b\u0005\u0014\u0000\u0000\u001a\r\u0001\u0000\u0000" +
                    "\u0000\u001a\u0012\u0001\u0000\u0000\u0000\u001a\u0014\u0001\u0000\u0000" +
                    "\u0000\u001a\u0015\u0001\u0000\u0000\u0000\u001a\u0016\u0001\u0000\u0000" +
                    "\u0000\u001a\u0017\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000\u0000" +
                    "\u0000\u001a\u0019\u0001\u0000\u0000\u0000\u001b&\u0001\u0000\u0000\u0000" +
                    "\u001c\u001d\n\b\u0000\u0000\u001d\u001e\u0003\u0004\u0002\u0000\u001e" +
                    "\u001f\u0003\u0002\u0001\t\u001f%\u0001\u0000\u0000\u0000 !\n\u0007\u0000" +
                    "\u0000!\"\u0003\u0006\u0003\u0000\"#\u0003\u0002\u0001\b#%\u0001\u0000" +
                    "\u0000\u0000$\u001c\u0001\u0000\u0000\u0000$ \u0001\u0000\u0000\u0000" +
                    "%(\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000" +
                    "\u0000\'\u0003\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000)*\u0007" +
                    "\u0000\u0000\u0000*\u0005\u0001\u0000\u0000\u0000+,\u0007\u0001\u0000" +
                    "\u0000,\u0007\u0001\u0000\u0000\u0000-.\u0007\u0002\u0000\u0000.\t\u0001" +
                    "\u0000\u0000\u0000\u0003\u001a$&";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public EvalExpParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "parse", "expression", "comparator", "binary", "bool"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'AND'", "'OR'", "'&&'", "'||'", "'!'", "'true'", "'false'", "'>'",
                "'>='", "'<'", "'<='", "'=='", "'!='", "'('", "')'", null, null, null,
                null, "'null'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "AND", "OR", "ANDJS", "ORJS", "NOT", "TRUE", "FALSE", "GT", "GE",
                "LT", "LE", "EQ", "NEQ", "LPAREN", "RPAREN", "DECIMAL", "INTEGER", "IDENTIFIER",
                "SPACE", "NULL", "STRING"
        };
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "java-escape";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final ParseContext parse() throws RecognitionException {
        ParseContext _localctx = new ParseContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_parse);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(10);
                expression(0);
                setState(11);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ExpressionContext expression() throws RecognitionException {
        return expression(0);
    }

    private ExpressionContext expression(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
        ExpressionContext _prevctx = _localctx;
        int _startState = 2;
        enterRecursionRule(_localctx, 2, RULE_expression, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(26);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LPAREN: {
                        _localctx = new ParenExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(14);
                        match(LPAREN);
                        setState(15);
                        expression(0);
                        setState(16);
                        match(RPAREN);
                    }
                    break;
                    case NOT: {
                        _localctx = new NotExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(18);
                        match(NOT);
                        setState(19);
                        expression(9);
                    }
                    break;
                    case TRUE:
                    case FALSE: {
                        _localctx = new BoolExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(20);
                        bool();
                    }
                    break;
                    case IDENTIFIER: {
                        _localctx = new IdentifierExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(21);
                        match(IDENTIFIER);
                    }
                    break;
                    case STRING: {
                        _localctx = new StringExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(22);
                        match(STRING);
                    }
                    break;
                    case DECIMAL: {
                        _localctx = new DecimalExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(23);
                        match(DECIMAL);
                    }
                    break;
                    case INTEGER: {
                        _localctx = new IntegerExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(24);
                        match(INTEGER);
                    }
                    break;
                    case NULL: {
                        _localctx = new NullExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(25);
                        match(NULL);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(38);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(36);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                                case 1: {
                                    _localctx = new ComparatorExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((ComparatorExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(28);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(29);
                                    ((ComparatorExpressionContext) _localctx).op = comparator();
                                    setState(30);
                                    ((ComparatorExpressionContext) _localctx).right = expression(9);
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((BinaryExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(32);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(33);
                                    ((BinaryExpressionContext) _localctx).op = binary();
                                    setState(34);
                                    ((BinaryExpressionContext) _localctx).right = expression(8);
                                }
                                break;
                            }
                        }
                    }
                    setState(40);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public final ComparatorContext comparator() throws RecognitionException {
        ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_comparator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(41);
                _la = _input.LA(1);
                if (!(((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final BinaryContext binary() throws RecognitionException {
        BinaryContext _localctx = new BinaryContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_binary);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(43);
                _la = _input.LA(1);
                if (!(((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final BoolContext bool() throws RecognitionException {
        BoolContext _localctx = new BoolContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_bool);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(45);
                _la = _input.LA(1);
                if (!(_la == TRUE || _la == FALSE)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 1:
                return expression_sempred((ExpressionContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 8);
            case 1:
                return precpred(_ctx, 7);
        }
        return true;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ParseContext extends ParserRuleContext {
        public ParseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(EvalExpParser.EOF, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_parse;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterParse(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitParse(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor) return ((EvalExpVisitor<? extends T>) visitor).visitParse(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExpressionContext extends ParserRuleContext {
        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExpressionContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        public void copyFrom(ExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BinaryExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public BinaryContext op;
        public ExpressionContext right;

        public BinaryExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public BinaryContext binary() {
            return getRuleContext(BinaryContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterBinaryExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitBinaryExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitBinaryExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DecimalExpressionContext extends ExpressionContext {
        public DecimalExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode DECIMAL() {
            return getToken(EvalExpParser.DECIMAL, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterDecimalExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitDecimalExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitDecimalExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StringExpressionContext extends ExpressionContext {
        public StringExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode STRING() {
            return getToken(EvalExpParser.STRING, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterStringExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitStringExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitStringExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BoolExpressionContext extends ExpressionContext {
        public BoolExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public BoolContext bool() {
            return getRuleContext(BoolContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterBoolExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitBoolExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitBoolExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NullExpressionContext extends ExpressionContext {
        public NullExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode NULL() {
            return getToken(EvalExpParser.NULL, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterNullExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitNullExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitNullExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IdentifierExpressionContext extends ExpressionContext {
        public IdentifierExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(EvalExpParser.IDENTIFIER, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterIdentifierExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitIdentifierExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitIdentifierExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NotExpressionContext extends ExpressionContext {
        public NotExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode NOT() {
            return getToken(EvalExpParser.NOT, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterNotExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitNotExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitNotExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ParenExpressionContext extends ExpressionContext {
        public ParenExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LPAREN() {
            return getToken(EvalExpParser.LPAREN, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(EvalExpParser.RPAREN, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterParenExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitParenExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitParenExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ComparatorExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ComparatorContext op;
        public ExpressionContext right;

        public ComparatorExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public ComparatorContext comparator() {
            return getRuleContext(ComparatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterComparatorExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitComparatorExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitComparatorExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IntegerExpressionContext extends ExpressionContext {
        public IntegerExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode INTEGER() {
            return getToken(EvalExpParser.INTEGER, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterIntegerExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitIntegerExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor)
                return ((EvalExpVisitor<? extends T>) visitor).visitIntegerExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ComparatorContext extends ParserRuleContext {
        public ComparatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode GT() {
            return getToken(EvalExpParser.GT, 0);
        }

        public TerminalNode GE() {
            return getToken(EvalExpParser.GE, 0);
        }

        public TerminalNode LT() {
            return getToken(EvalExpParser.LT, 0);
        }

        public TerminalNode LE() {
            return getToken(EvalExpParser.LE, 0);
        }

        public TerminalNode EQ() {
            return getToken(EvalExpParser.EQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(EvalExpParser.NEQ, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterComparator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitComparator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor) return ((EvalExpVisitor<? extends T>) visitor).visitComparator(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BinaryContext extends ParserRuleContext {
        public BinaryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode AND() {
            return getToken(EvalExpParser.AND, 0);
        }

        public TerminalNode OR() {
            return getToken(EvalExpParser.OR, 0);
        }

        public TerminalNode ANDJS() {
            return getToken(EvalExpParser.ANDJS, 0);
        }

        public TerminalNode ORJS() {
            return getToken(EvalExpParser.ORJS, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_binary;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterBinary(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitBinary(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor) return ((EvalExpVisitor<? extends T>) visitor).visitBinary(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BoolContext extends ParserRuleContext {
        public BoolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode TRUE() {
            return getToken(EvalExpParser.TRUE, 0);
        }

        public TerminalNode FALSE() {
            return getToken(EvalExpParser.FALSE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_bool;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).enterBool(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EvalExpListener) ((EvalExpListener) listener).exitBool(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EvalExpVisitor) return ((EvalExpVisitor<? extends T>) visitor).visitBool(this);
            else return visitor.visitChildren(this);
        }
    }
}