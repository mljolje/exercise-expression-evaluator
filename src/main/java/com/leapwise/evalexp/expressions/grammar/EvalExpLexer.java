// Generated from java-escape by ANTLR 4.11.1
package com.leapwise.evalexp.expressions.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class EvalExpLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, ANDJS=3, ORJS=4, NOT=5, TRUE=6, FALSE=7, GT=8, GE=9, LT=10, 
		LE=11, EQ=12, NEQ=13, LPAREN=14, RPAREN=15, DECIMAL=16, INTEGER=17, IDENTIFIER=18, 
		SPACE=19, NULL=20, STRING=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"AND", "OR", "ANDJS", "ORJS", "NOT", "TRUE", "FALSE", "GT", "GE", "LT", 
			"LE", "EQ", "NEQ", "LPAREN", "RPAREN", "DECIMAL", "INTEGER", "IDENTIFIER", 
			"SPACE", "NULL", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'AND'", "'OR'", "'&&'", "'||'", "'!'", "'true'", "'false'", "'>'", 
			"'>='", "'<'", "'<='", "'=='", "'!='", "'('", "')'", null, null, null, 
			null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AND", "OR", "ANDJS", "ORJS", "NOT", "TRUE", "FALSE", "GT", "GE", 
			"LT", "LE", "EQ", "NEQ", "LPAREN", "RPAREN", "DECIMAL", "INTEGER", "IDENTIFIER", 
			"SPACE", "NULL", "STRING"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public EvalExpLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "EvalExp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0015\u008d\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0003\u000f[\b\u000f\u0001\u000f\u0004\u000f^\b\u000f\u000b"+
		"\u000f\f\u000f_\u0001\u000f\u0001\u000f\u0004\u000fd\b\u000f\u000b\u000f"+
		"\f\u000fe\u0001\u0010\u0003\u0010i\b\u0010\u0001\u0010\u0004\u0010l\b"+
		"\u0010\u000b\u0010\f\u0010m\u0001\u0011\u0001\u0011\u0005\u0011r\b\u0011"+
		"\n\u0011\f\u0011u\t\u0011\u0001\u0012\u0004\u0012x\b\u0012\u000b\u0012"+
		"\f\u0012y\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u0087\b\u0014\n\u0014\f\u0014\u008a\t\u0014\u0001\u0014\u0001"+
		"\u0014\u0000\u0000\u0015\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'"+
		"\u0014)\u0015\u0001\u0000\u0005\u0001\u000009\u0003\u0000AZ__az\u0005"+
		"\u0000..09AZ__az\u0003\u0000\t\n\f\r  \u0003\u0000\n\n\r\r\"\"\u0095\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0001+\u0001\u0000\u0000\u0000\u0003/\u0001\u0000\u0000\u0000\u00052"+
		"\u0001\u0000\u0000\u0000\u00075\u0001\u0000\u0000\u0000\t8\u0001\u0000"+
		"\u0000\u0000\u000b:\u0001\u0000\u0000\u0000\r?\u0001\u0000\u0000\u0000"+
		"\u000fE\u0001\u0000\u0000\u0000\u0011G\u0001\u0000\u0000\u0000\u0013J"+
		"\u0001\u0000\u0000\u0000\u0015L\u0001\u0000\u0000\u0000\u0017O\u0001\u0000"+
		"\u0000\u0000\u0019R\u0001\u0000\u0000\u0000\u001bU\u0001\u0000\u0000\u0000"+
		"\u001dW\u0001\u0000\u0000\u0000\u001fZ\u0001\u0000\u0000\u0000!h\u0001"+
		"\u0000\u0000\u0000#o\u0001\u0000\u0000\u0000%w\u0001\u0000\u0000\u0000"+
		"\'}\u0001\u0000\u0000\u0000)\u0082\u0001\u0000\u0000\u0000+,\u0005A\u0000"+
		"\u0000,-\u0005N\u0000\u0000-.\u0005D\u0000\u0000.\u0002\u0001\u0000\u0000"+
		"\u0000/0\u0005O\u0000\u000001\u0005R\u0000\u00001\u0004\u0001\u0000\u0000"+
		"\u000023\u0005&\u0000\u000034\u0005&\u0000\u00004\u0006\u0001\u0000\u0000"+
		"\u000056\u0005|\u0000\u000067\u0005|\u0000\u00007\b\u0001\u0000\u0000"+
		"\u000089\u0005!\u0000\u00009\n\u0001\u0000\u0000\u0000:;\u0005t\u0000"+
		"\u0000;<\u0005r\u0000\u0000<=\u0005u\u0000\u0000=>\u0005e\u0000\u0000"+
		">\f\u0001\u0000\u0000\u0000?@\u0005f\u0000\u0000@A\u0005a\u0000\u0000"+
		"AB\u0005l\u0000\u0000BC\u0005s\u0000\u0000CD\u0005e\u0000\u0000D\u000e"+
		"\u0001\u0000\u0000\u0000EF\u0005>\u0000\u0000F\u0010\u0001\u0000\u0000"+
		"\u0000GH\u0005>\u0000\u0000HI\u0005=\u0000\u0000I\u0012\u0001\u0000\u0000"+
		"\u0000JK\u0005<\u0000\u0000K\u0014\u0001\u0000\u0000\u0000LM\u0005<\u0000"+
		"\u0000MN\u0005=\u0000\u0000N\u0016\u0001\u0000\u0000\u0000OP\u0005=\u0000"+
		"\u0000PQ\u0005=\u0000\u0000Q\u0018\u0001\u0000\u0000\u0000RS\u0005!\u0000"+
		"\u0000ST\u0005=\u0000\u0000T\u001a\u0001\u0000\u0000\u0000UV\u0005(\u0000"+
		"\u0000V\u001c\u0001\u0000\u0000\u0000WX\u0005)\u0000\u0000X\u001e\u0001"+
		"\u0000\u0000\u0000Y[\u0005-\u0000\u0000ZY\u0001\u0000\u0000\u0000Z[\u0001"+
		"\u0000\u0000\u0000[]\u0001\u0000\u0000\u0000\\^\u0007\u0000\u0000\u0000"+
		"]\\\u0001\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000"+
		"\u0000_`\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000ac\u0005.\u0000"+
		"\u0000bd\u0007\u0000\u0000\u0000cb\u0001\u0000\u0000\u0000de\u0001\u0000"+
		"\u0000\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000f \u0001"+
		"\u0000\u0000\u0000gi\u0005-\u0000\u0000hg\u0001\u0000\u0000\u0000hi\u0001"+
		"\u0000\u0000\u0000ik\u0001\u0000\u0000\u0000jl\u0007\u0000\u0000\u0000"+
		"kj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000"+
		"\u0000mn\u0001\u0000\u0000\u0000n\"\u0001\u0000\u0000\u0000os\u0007\u0001"+
		"\u0000\u0000pr\u0007\u0002\u0000\u0000qp\u0001\u0000\u0000\u0000ru\u0001"+
		"\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000"+
		"t$\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000vx\u0007\u0003\u0000"+
		"\u0000wv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000yw\u0001\u0000"+
		"\u0000\u0000yz\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{|\u0006"+
		"\u0012\u0000\u0000|&\u0001\u0000\u0000\u0000}~\u0005n\u0000\u0000~\u007f"+
		"\u0005u\u0000\u0000\u007f\u0080\u0005l\u0000\u0000\u0080\u0081\u0005l"+
		"\u0000\u0000\u0081(\u0001\u0000\u0000\u0000\u0082\u0088\u0005\"\u0000"+
		"\u0000\u0083\u0087\b\u0004\u0000\u0000\u0084\u0085\u0005\"\u0000\u0000"+
		"\u0085\u0087\u0005\"\u0000\u0000\u0086\u0083\u0001\u0000\u0000\u0000\u0086"+
		"\u0084\u0001\u0000\u0000\u0000\u0087\u008a\u0001\u0000\u0000\u0000\u0088"+
		"\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089"+
		"\u008b\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008b"+
		"\u008c\u0005\"\u0000\u0000\u008c*\u0001\u0000\u0000\u0000\n\u0000Z_eh"+
		"msy\u0086\u0088\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}