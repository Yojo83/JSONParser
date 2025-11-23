package yojo.json;

import java.lang.reflect.Field;

import yojo.json.token.Lexer;
import yojo.json.token.TypeParsingException;
import yojo.json.tree.JSONObject;
import yojo.json.tree.TreeParser;
import yojo.json.tree.TreeParserException;

public class JSONParser {
	
	public static boolean SET_WHITE_SPACES = true;
	public static boolean USE_TABS = false;
	public static int INDENTATION = 4;
	
	public static String parseToJSON(Object obj) {
		StringBuilder builder = new StringBuilder();
		
		if(obj == null || obj.getClass().isArray() 
				|| obj instanceof String || obj instanceof Character)
			return null;
		
		try {
			parseObjectToJSON(obj, builder, 0);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
			return null;
		}
		
		return builder.toString();
	}
	
	public static String parseToJSON(JSONObject obj) {
		return obj.parseToJSON();
	}
	
	public static <A> A parseFromJSON(Class<A> c, String json) throws TreeParserException, TypeParsingException {
		return parseFromJSON(json).parseFromJSON(c);
	}
	
	public static JSONObject parseFromJSON(String json) throws TreeParserException {
		return TreeParser.parse(Lexer.parseToTokenList(json));
	}
	
	public static void newLine(StringBuilder builder, int depth) {
		if(!SET_WHITE_SPACES)
			return;
		
		builder.append('\n');
		for(int i = 0; i < depth; ++i) {
			if(USE_TABS) {
				builder.append('\t');
			} else {
				for(int j = 0; j < INDENTATION; ++j) {
					builder.append(" ");
				}
			}
		}
	}
	
	private static void parseObjectToJSON(Object obj, StringBuilder builder, int depth) throws IllegalArgumentException, IllegalAccessException {
		builder.append('{');
		depth++;
		newLine(builder, depth);
		
		Field[] fields = obj.getClass().getFields(); 
		for(int i = 0; i < fields.length; ++i) {
			builder.append('"');
			builder.append(fields[i].getName());
			builder.append("\" : ");
			
			parseValueToJSON(fields[i].get(obj), builder, depth);
			
			if(i+1 < fields.length) {
				builder.append(", ");
				newLine(builder, depth);
			}
		}
		
		depth--;
		newLine(builder, depth);
		builder.append('}');
	}
	

	private static void parseValueToJSON(Object obj, StringBuilder builder, int depth) throws IllegalArgumentException, IllegalAccessException {
		if(obj == null) {
			builder.append("null");
			return;
		}
		if(obj.getClass().isArray()) {
			parseArrayToJSON(obj, builder, depth);
			return;
		}
		if(isNumber(obj)) {
			builder.append(obj.toString());
			return;
		}
		if(obj instanceof String) {
			builder.append('"').append((String) obj).append('"');
			return;
		}
		if(obj instanceof Character) {
			builder.append('"').append((Character) obj).append('"');
			return;
		}
		if(obj instanceof Boolean) {
			builder.append((boolean) obj);
			return;
		}
		parseObjectToJSON(obj, builder, depth);
	}
	
	private static boolean isNumber(Object obj) {
		return 	obj instanceof Byte || 
				obj instanceof Short || 
				obj instanceof Integer || 
				obj instanceof Long;
	}
	
	private static void parseArrayToJSON(Object obj, StringBuilder builder, int depth) throws IllegalArgumentException, IllegalAccessException {
		builder.append('[');
		depth++;
		newLine(builder, depth);
		
		Object[] values = (Object[]) obj; 
		for(int i = 0; i < values.length; ++i) {
			parseValueToJSON(values[i], builder, depth);
			
			if(i+1 < values.length) {
				builder.append(", ");
				newLine(builder, depth);
			}
		}
		
		depth--;
		newLine(builder, depth);
		builder.append(']');
	}
	

}
