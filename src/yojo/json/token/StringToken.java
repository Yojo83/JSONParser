package yojo.json.token;

import yojo.json.tree.JSONValue;

public class StringToken extends Token implements JSONValue{

	public final String string;
	
	protected StringToken(String string) {
		super(TokenType.STRING, '"' + string + '"');
		this.string = string;
	}

	@Override
	public JSONValueType getType() {
		return JSONValueType.STRING;
	}

	@Override
	public String parseToJSON() {
		return '"' + string + '"';
	}

	@Override
	public void parseToJSON(StringBuilder builder, int depth) {
		builder.append('"').append(string).append('"');
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TypeParsingException {
		if(c.equals(String.class))
			return (A) string;
		if(c.equals(char.class) || c.equals(Character.class))
			return (A) (Character) string.charAt(0);
		throw new TypeParsingException(c.getName() + " is not a string class");
	}
}
