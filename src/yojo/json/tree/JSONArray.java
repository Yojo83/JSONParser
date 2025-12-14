package yojo.json.tree;

import java.lang.reflect.Array;
import java.util.ArrayList;

import yojo.json.JSONParser;
import yojo.json.token.TypeParsingException;

public class JSONArray implements JSONValue {

	public final ArrayList<JSONValue> values = new ArrayList<>();

	@Override
	public JSONValueType getType() {
		return JSONValueType.ARRAY;
	}

	@Override
	public String parseToJSON() {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, new JSONParser());
		return builder.toString();
	}

	public String parseToJSON(boolean setWhiteSpaces) {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, new JSONParser(setWhiteSpaces));
		return builder.toString();
	}

	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs) {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, new JSONParser(setWhiteSpaces, useTabs));
		return builder.toString();
	}

	public String parseToJSON(boolean setWhiteSpaces, boolean useTabs, int indentation) {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, new JSONParser(setWhiteSpaces, useTabs, indentation));
		return builder.toString();
	}

	@Override
	public void parseToJSON(StringBuilder builder, JSONParser whiteData) {
		builder.append('[');
		whiteData.depth++;
		whiteData.newLine(builder);
		
		for(int i = 0; i < values.size(); ++i) {
			values.get(i).parseToJSON(builder, whiteData);
			
			if(i+1 < values.size()) {
				builder.append(", ");
				whiteData.newLine(builder);
			}
		}
		
		whiteData.depth--;
		whiteData.newLine(builder);
		builder.append(']');
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException, TypeParsingException {
		if(!c.isArray())
			throw new TreeParserException(c.getName() + " is not an array class");
			
		A out;
		try {
			out = (A) Array.newInstance(c.componentType(), values.size());
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		Object[] arr = (Object[]) out;
		
		for(int i = 0; i < values.size(); ++i) {
			if(values.get(i) == null)
				arr[i] = null;
			else
				arr[i] = values.get(i).parseFromJSON(c.componentType());
		}
		
		return out;
	}

}
