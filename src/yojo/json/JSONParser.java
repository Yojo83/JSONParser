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
		return parseToJSON(obj, new JSONParser());
	}

	public static String parseToJSON(Object obj, boolean setWhiteSpaces) {
		return parseToJSON(obj, new JSONParser(setWhiteSpaces));
	}

	public static String parseToJSON(Object obj, boolean setWhiteSpaces, 
			boolean useTabs) {
		return parseToJSON(obj, new JSONParser(setWhiteSpaces, useTabs));
	}

	public static String parseToJSON(Object obj, boolean setWhiteSpaces, 
			boolean useTabs, int indentation) {
		return parseToJSON(obj, new JSONParser(setWhiteSpaces, useTabs, indentation));
	}
	
	public static String parseToJSON(JSONObject obj) {
		return obj.parseToJSON();
	}

	public static String parseToJSON(JSONObject obj, boolean setWhiteSpaces) {
		return obj.parseToJSON(setWhiteSpaces);
	}

	public static String parseToJSON(JSONObject obj, boolean setWhiteSpaces, 
			boolean useTabs) {
		return obj.parseToJSON(setWhiteSpaces, useTabs);
	}

	public static String parseToJSON(JSONObject obj, boolean setWhiteSpaces, 
			boolean useTabs, int indentation) {
		return obj.parseToJSON(setWhiteSpaces, useTabs, indentation);
	}
	
	public static <A> A parseFromJSON(Class<A> c, String json) throws TreeParserException, TypeParsingException {
		return parseFromJSON(json).parseFromJSON(c);
	}
	
	public static JSONObject parseFromJSON(String json) throws TreeParserException {
		return TreeParser.parse(Lexer.parseToTokenList(json));
	}

	
	
	
	
	private final boolean setWhiteSpaces;
	private final boolean useTabs;
	private final int indentation;
	public int depth = 0;

	public JSONParser() {
		this(SET_WHITE_SPACES);
	}

	public JSONParser(boolean setWhiteSpaces) {
		this(setWhiteSpaces, USE_TABS);
	}

	public JSONParser(boolean setWhiteSpaces, boolean useTabs) {
		this(setWhiteSpaces, useTabs, INDENTATION);
	}

	public JSONParser(boolean setWhiteSpaces, boolean useTabs, int indentation) {
		this.indentation = indentation;
		this.useTabs = useTabs;
		this.setWhiteSpaces = setWhiteSpaces;
	}

	public void newLine(StringBuilder builder) {
		if(!setWhiteSpaces)
			return;
		
		builder.append('\n');
		for(int i = 0; i < depth; ++i) {
			if(useTabs) {
				builder.append('\t');
			} else {
				for(int j = 0; j < indentation; ++j) {
					builder.append(" ");
				}
			}
		}
	}
	
	public int getNewLineCharacters() {
		if(!setWhiteSpaces)
			return 0;
	
		if(useTabs) {
			return 1 + depth;
		} else {
			return 1 + depth * indentation;
		}
	}

	private static String parseToJSON(Object obj, JSONParser parser) {
		StringBuilder builder = new StringBuilder();
		
		if(obj == null || obj.getClass().isArray() 
				|| obj instanceof String || obj instanceof Character)
			return null;
		
		try {
			parser.parseObjectToJSON(obj, builder);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
			return null;
		}
		
		return builder.toString();
	}

	private void parseObjectToJSON(Object obj, StringBuilder builder) throws IllegalArgumentException, IllegalAccessException {
		builder.append('{');
		depth++;
		newLine(builder);
		
		Field[] fields = obj.getClass().getFields(); 
		for(int i = 0; i < fields.length; ++i) {
			builder.append('"');
			builder.append(fields[i].getName());
			builder.append("\" : ");
			
			parseValueToJSON(fields[i].get(obj), builder);
			
			if(i+1 < fields.length) {
				builder.append(", ");
				newLine(builder);
			}
		}
		
		depth--;
		newLine(builder);
		builder.append('}');
	}
	

	private void parseValueToJSON(Object obj, StringBuilder builder) throws IllegalArgumentException, IllegalAccessException {
		if(obj == null) {
			builder.append("null");
			return;
		}
		if(obj.getClass().isArray()) {
			parseArrayToJSON(obj, builder);
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
		if(obj.getClass().isEnum()){
			builder.append('"').append((obj).toString()).append('"');
			return;
		}
		parseObjectToJSON(obj, builder);
	}
	
	private static boolean isNumber(Object obj) {
		return 	obj instanceof Byte || 
				obj instanceof Short || 
				obj instanceof Integer || 
				obj instanceof Long;
	}
	
	private void parseArrayToJSON(Object obj, StringBuilder builder) throws IllegalArgumentException, IllegalAccessException {
		builder.append('[');
		depth++;
		newLine(builder);
		
		Object[] values = (Object[]) obj; 
		for(int i = 0; i < values.length; ++i) {
			parseValueToJSON(values[i], builder);
			
			if(i+1 < values.length) {
				builder.append(", ");
				newLine(builder);
			}
		}
		
		depth--;
		newLine(builder);
		builder.append(']');
	}
	

}
