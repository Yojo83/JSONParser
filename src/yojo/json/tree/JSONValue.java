package yojo.json.tree;

public interface JSONValue {
	
	public static final JSONValue NULL = new JSONNullValue();
	
	public JSONValueType getType();
	public String parseToJSON();
	public void parseToJSON(StringBuilder builder, int depth);
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException;
	
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
		public void parseToJSON(StringBuilder builder, int depth) {
			builder.append("null");
		}

		@Override
		public <A> A parseFromJSON(Class<A> c) {
			return null;
		}
		
	}

}
