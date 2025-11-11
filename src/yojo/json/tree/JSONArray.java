package yojo.json.tree;

import java.lang.reflect.Array;
import java.util.ArrayList;

import yojo.json.JSONParser;

public class JSONArray implements JSONValue {

	public final ArrayList<JSONValue> values = new ArrayList<>();

	@Override
	public JSONValueType getType() {
		return JSONValueType.ARRAY;
	}

	@Override
	public String parseToJSON() {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, 0);
		return builder.toString();
	}

	@Override
	public void parseToJSON(StringBuilder builder, int depth) {
		builder.append('[');
		depth++;
		JSONParser.newLine(builder, depth);
		
		for(int i = 0; i < values.size(); ++i) {
			values.get(i).parseToJSON(builder, depth);
			
			if(i+1 < values.size()) {
				builder.append(", ");
				JSONParser.newLine(builder, depth);
			}
		}
		
		depth--;
		JSONParser.newLine(builder, depth);
		builder.append(']');
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException {
		if(!c.isArray())
			throw new TreeParserException();
			
		A out;
		try {
			out = (A) Array.newInstance(c.componentType(), values.size());
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		Object[] arr = (Object[]) out;
		
		for(int i = 0; i < values.size(); ++i) {
			arr[i] = values.get(i).parseFromJSON(c.componentType());
		}
		
		return out;
	}

}
