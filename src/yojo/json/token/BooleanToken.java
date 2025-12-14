package yojo.json.token;

import yojo.json.JSONParser;
import yojo.json.tree.JSONValue;

public class BooleanToken extends Token implements JSONValue{

	public final boolean token;
	
	protected BooleanToken(boolean token) {
		super(TokenType.BOOLEAN, Boolean.toString(token));
		this.token = token;
	}

	@Override
	public JSONValueType getType() {
		return JSONValueType.BOOLEAN;
	}

	@Override
	public String parseToJSON() {
		return Boolean.toString(token);
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
		builder.append(token);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TypeParsingException {
		if(!(c.equals(Boolean.class) || c.equals(boolean.class)))
			throw new TypeParsingException(c.getName() + " is not a boolean class");
		return (A) (Boolean) token;
	}

}
