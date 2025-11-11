package yojo.json.tree;

import java.util.List;

import yojo.json.token.*;
import yojo.json.token.Token.TokenType;

public class TreeParser {

	public static JSONObject parse(List<Token> tokens) throws TreeParserException {
		TreeParser parser = new TreeParser(tokens);
		JSONObject out = parser.parseObject();
		if(parser.i != tokens.size())
			throw new TreeParserException();
		return out;
	}
	
	private final List<Token> tokens;
	private int i = 0;
	
	private TreeParser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	private JSONObject parseObject() throws TreeParserException {
		if(!tokens.get(i++).equals(Token.OPEN_CURLY))
			throw new TreeParserException();
		
		JSONObject out = new JSONObject();
		
		while(i < tokens.size() && !tokens.get(i).equals(Token.CLOSE_CURLY)) {
			if(tokens.get(i).type != TokenType.STRING)
				throw new TreeParserException();
			
			String name = ((StringToken) tokens.get(i++)).string;
			
			if(!tokens.get(i++).equals(Token.DOUBLE_POINT))
				throw new TreeParserException();
			
			JSONValue value = parseValue();
			
			out.items.put(name, value);
			
			if(tokens.get(i).equals(Token.COMMA))
				++i;
		}

		if(!tokens.get(i++).equals(Token.CLOSE_CURLY))
			throw new TreeParserException();
		return out;
	}

	private JSONValue parseValue() throws TreeParserException {
		if(tokens.get(i).equals(Token.NULL)) {
			i++;
			return JSONValue.NULL;
		}
		if(tokens.get(i).equals(Token.OPEN_CURLY)) {
			return parseObject();
		}
		if(tokens.get(i).equals(Token.OPEN_Array)) {
			return parseArray();
		}
		if(tokens.get(i).type == TokenType.BOOLEAN) {
			return (BooleanToken) tokens.get(i++);
		}
		if(tokens.get(i).type == TokenType.NUMBER) {
			return (NumberToken) tokens.get(i++);
		}
		if(tokens.get(i).type == TokenType.STRING) {
			return (StringToken) tokens.get(i++);
		}
		throw new TreeParserException();
	}

	private JSONArray parseArray() throws TreeParserException {
		if(!tokens.get(i++).equals(Token.OPEN_Array))
			throw new TreeParserException();
		
		JSONArray out = new JSONArray();
		
		while(i < tokens.size() && !tokens.get(i).equals(Token.CLOSE_ARRAY)) {
			out.values.add(parseValue());

			if(tokens.get(i).equals(Token.COMMA))
				++i;
		}

		if(!tokens.get(i++).equals(Token.CLOSE_ARRAY))
			throw new TreeParserException();
		return out;
	}

}
