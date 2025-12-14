package yojo.json.token;

import yojo.json.JSONParser;
import yojo.json.tree.JSONValue;

public class NumberToken extends Token implements JSONValue{

	public final String number;
	
	protected NumberToken(String number) {
		super(TokenType.NUMBER, number);
		this.number = number;
	}

	@Override
	public JSONValueType getType() {
		return JSONValueType.NUMBER;
	}
	
	@Override
	public String parseToJSON() {
		return number;
	}

	@Override
	public String parseToJSON(boolean setWhiteSpaces) {
		return parseToJSON();
	}

	@Override
	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs) {
		return parseToJSON();
	}

	@Override
	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs, int indentation) {
		return parseToJSON();
	}

	@Override
	public void parseToJSON(StringBuilder builder, JSONParser whiteData) {
		builder.append(number);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TypeParsingException {
		if(c.equals(Byte.class) || c.equals(byte.class)) {
			return (A) (Byte) Byte.parseByte(number);
		}
		if(c.equals(Short.class) || c.equals(short.class)) {
			return (A) (Short) Short.parseShort(number);
		}
		if(c.equals(Integer.class) || c.equals(int.class)) {
			return (A) (Integer) Integer.parseInt(number);
		}
		if(c.equals(Long.class) || c.equals(long.class)) {
			return (A) (Long) Long.parseLong(number);
		}
		throw new TypeParsingException(c.getName() + " is not a number class");
	}

}
