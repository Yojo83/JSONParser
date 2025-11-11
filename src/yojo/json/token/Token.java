package yojo.json.token;

public class Token {

	public static final Token OPEN_BRACKET = new Token();
	public static final Token CLOSE_BRACKET = new Token();
	public static final Token OPEN_Array = new Token();
	public static final Token CLOSE_ARRAY = new Token();
	public static final Token OPEN_CURLY = new Token();
	public static final Token CLOSE_CURLY = new Token();
	public static final Token COMMA = new Token();
	public static final Token DOUBLE_POINT = new Token();
	public static final Token NULL = new Token();
	
	public final TokenType type;
	
	private Token() {
		type = TokenType.KEY;
	}
	
	protected Token(TokenType type) {
		this.type = type;
	}
	
	public static enum TokenType{
		STRING,
		NUMBER,
		BOOLEAN,
		KEY;
	}
	
	
}
