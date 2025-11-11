package yojo.json.token;

import yojo.json.tree.JSONValue;
import yojo.json.tree.TreeParserException;

public class StringToken extends Token implements JSONValue{

	public final String string;
	
	protected StringToken(String string) {
		super(TokenType.STRING);
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
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException {
		if(c.equals(String.class))
			return (A) string;
		if(c.equals(char.class) || c.equals(Character.class))
			return (A) (Character) string.charAt(0);
		throw new TreeParserException();
	}
}
