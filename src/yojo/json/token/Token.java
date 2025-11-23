package yojo.json.token;

public class Token {

	public static final Token OPEN_BRACKET = new Token("(");
	public static final Token CLOSE_BRACKET = new Token(")");
	public static final Token OPEN_Array = new Token("[");
	public static final Token CLOSE_ARRAY = new Token("]");
	public static final Token OPEN_CURLY = new Token("{");
	public static final Token CLOSE_CURLY = new Token("}");
	public static final Token COMMA = new Token(",");
	public static final Token DOUBLE_POINT = new Token(":");
	public static final Token NULL = new Token("null");
	
	public final TokenType type;
	private final String name;
	
	private Token(String name) {
		type = TokenType.KEY;
		this.name = name;
	}
	
	protected Token(TokenType type, String asString) {
		this.type = type;
		this.name = asString;
	}
	
	
	@Override
	public String toString() {
		return name;
	}
	
	public static enum TokenType{
		STRING,
		NUMBER,
		BOOLEAN,
		KEY;
	}
	
	
	
}
