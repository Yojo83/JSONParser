package yojo.json.token;

import yojo.json.tree.JSONValue;
import yojo.json.tree.TreeParserException;

public class BooleanToken extends Token implements JSONValue{

	public final boolean token;
	
	protected BooleanToken(boolean token) {
		super(TokenType.BOOLEAN);
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
	public void parseToJSON(StringBuilder builder, int depth) {
		builder.append(token);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException {
		if(!(c.equals(Boolean.class) || c.equals(boolean.class)))
			throw new TreeParserException();
		return (A) (Boolean) token;
	}

}
