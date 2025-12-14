package yojo.json.tree;

import yojo.json.JSONParser;
import yojo.json.token.TypeParsingException;

public interface JSONValue {
	
	public static final JSONValue NULL = new JSONNullValue();
	
	public JSONValueType getType();
	public String parseToJSON();
	public String parseToJSON(boolean setWhiteSpaces);
	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs);
	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs, int indentation);
	public void parseToJSON(StringBuilder builder, JSONParser whiteData);
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException, TypeParsingException;
	
	public static enum JSONValueType{
		ARRAY,
		OBJECT,
		NUMBER,
		BOOLEAN,
		STRING,
		NULL;
	}
	
	public static class JSONNullValue implements JSONValue{

		private JSONNullValue() {}
		
		@Override
		public JSONValueType getType() {
			return JSONValueType.NULL;
		}
		
		@Override
		public String parseToJSON() {
			return "null";
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
			builder.append("null");
		}

		@Override
		public <A> A parseFromJSON(Class<A> c) {
			return null;
		}

	}

}
