package yojo.json.tree;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import yojo.json.JSONParser;
import yojo.json.token.TypeParsingException;

public class JSONObject implements JSONValue {
	
	public final HashMap<String, JSONValue> items = new HashMap<>();

	@Override
	public JSONValueType getType() {
		return JSONValueType.OBJECT;
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
		builder.append('{');
		whiteData.depth++;
		whiteData.newLine(builder);
		
		items.forEach((name, value) -> {
			builder.append('"');
			builder.append(name);
			builder.append("\" : ");
			
			value.parseToJSON(builder, whiteData);
			
			builder.append(", ");
			whiteData.newLine(builder);
		});
		
		builder.replace(builder.length()-(2 + whiteData.getNewLineCharacters()), 
				builder.length(), "");
		
		whiteData.depth--;
		whiteData.newLine(builder);
		builder.append('}');
	}

	@Override
	public <A> A parseFromJSON(Class<A> c) throws TreeParserException, TypeParsingException {
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
				if(items.get(field.getName()) != null)
					field.set(out, items.get(field.getName()).parseFromJSON(field.getType()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return out;
	}
}
