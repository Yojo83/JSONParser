package yojo.json.tree;

import java.util.List;

import yojo.json.token.*;
import yojo.json.token.Token.TokenType;

public class TreeParser {

	public static JSONObject parse(List<Token> tokens) throws TreeParserException {
		TreeParser parser = new TreeParser(tokens);
		JSONObject out = parser.parseObject();
		if(parser.i != tokens.size())
			throw new TreeParserException("JSON ended, but there are characters left");
		return out;
	}
	
	private final List<Token> tokens;
	private int i = 0;
	
	private TreeParser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	private JSONObject parseObject() throws TreeParserException {
		if(i >= tokens.size())
			throw new TreeParserException("expected \"{\", but found no more Tokens");
		
		if(!tokens.get(i++).equals(Token.OPEN_CURLY))
			throw new TreeParserException("expected \"{\" at token " + --i + ". Found " + tokens.get(i).toString() + " instead");
		
		JSONObject out = new JSONObject();
		
		while(!tokens.get(i).equals(Token.CLOSE_CURLY)) {
			if(tokens.get(i).type != TokenType.STRING)
				throw new TreeParserException("expected String at token " + i + ". Found " + tokens.get(i).toString() + " instead");
			
			String name = ((StringToken) tokens.get(i++)).string;
			
			if(!tokens.get(i++).equals(Token.DOUBLE_POINT))
				throw new TreeParserException("expected \":\" at token " + --i + ". Found " + tokens.get(i).toString() + " instead");
			
			JSONValue value = parseValue();

			if(i >= tokens.size())
				throw new TreeParserException("expected \"}\" or \",\", but found no more Tokens");
			
			if(!tokens.get(i).equals(Token.CLOSE_CURLY) && !tokens.get(i).equals(Token.COMMA))
				throw new TreeParserException("expected \"}\" or \",\" at token " + i + ". Found " + tokens.get(i).toString() + " instead");
			
			if(tokens.get(i).equals(Token.COMMA) && ++i >= tokens.size())
					throw new TreeParserException("expected key String, but found no more Tokens");
			
			out.items.put(name, value);
		}

		if(!tokens.get(i++).equals(Token.CLOSE_CURLY))
			throw new TreeParserException("expected \"}\" at token " + --i + ". Found " + tokens.get(i).toString() + " instead");
		return out;
	}

	private JSONValue parseValue() throws TreeParserException {
		if(i >= tokens.size())
			throw new TreeParserException("expected value, but found no more Tokens");
		
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
		throw new TreeParserException("expected a value at token " + i + ": " + tokens.get(i).toString());
	}

	private JSONArray parseArray() throws TreeParserException {
		if(i >= tokens.size())
			throw new TreeParserException("expected \"[\", but found no more Tokens");
		
		if(!tokens.get(i++).equals(Token.OPEN_Array))
			throw new TreeParserException("expected \"[\" at token " + --i + ". Found " + tokens.get(i).toString() + " instead");
		
		JSONArray out = new JSONArray();
		
		while(!tokens.get(i).equals(Token.CLOSE_ARRAY)) {
			out.values.add(parseValue());

			if(i >= tokens.size())
				throw new TreeParserException("expected \"]\" or \",\", but found no more Tokens");
			
			if(!tokens.get(i).equals(Token.CLOSE_ARRAY) && !tokens.get(i).equals(Token.COMMA))
				throw new TreeParserException("expected \"]\" or \",\" at token " + i + ". Found " + tokens.get(i).toString() + " instead");
			
			if(tokens.get(i).equals(Token.COMMA) && ++i >= tokens.size())
					throw new TreeParserException("expected value, but found no more Tokens");
		}

		if(!tokens.get(i++).equals(Token.CLOSE_ARRAY))
			throw new TreeParserException("expected \"]\" at token " + --i + ". Found " + tokens.get(i).toString() + " instead");
		return out;
	}

}
