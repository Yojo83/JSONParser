package yojo.json.tree;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import yojo.json.JSONParser;

public class JSONObject implements JSONValue {
	
	public final HashMap<String, JSONValue> items = new HashMap<>();

	@Override
	public JSONValueType getType() {
		return JSONValueType.OBJECT;
	}

	@Override
	public String parseToJSON() {
		StringBuilder builder = new StringBuilder();
		parseToJSON(builder, 0);
		return builder.toString();
	}

	@Override
	public void parseToJSON(StringBuilder builder, int depth) {
		builder.append('{');
		depth++;
		JSONParser.newLine(builder, depth);
		
		int depthArg = depth;
		
		items.forEach((name, value) -> {
			builder.append('"');
			builder.append(name);
			builder.append("\" : ");
			
			value.parseToJSON(builder, depthArg);
			
			builder.append(", ");
			JSONParser.newLine(builder, depthArg);
		});
		
		int newLineCharacters = JSONParser.USE_TABS ? 
				depth + 1 : 1 + JSONParser.INDENTATION * depth;
		
		builder.replace(builder.length()-(2 + newLineCharacters), builder.length(), "");
		
		depth--;
		JSONParser.newLine(builder, depth);
		builder.append('}');
	}

	@Override
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException {
		A out;
		try {
			out = c.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		for(Field field : c.getFields()) {
			try {
				field.set(out, items.get(field.getName()).parseFromJSON(field.getType()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return out;
	}
}
