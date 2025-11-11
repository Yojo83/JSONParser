package yojo.json.token;

import java.util.ArrayList;

public class Lexer {
	
	public static ArrayList<Token> parseToTokenList(String json){
		ArrayList<Token> out = new ArrayList<>();
		boolean expectString = false;
		boolean expectNumber = false;
		boolean expectBoolean = false;
		boolean stringCommand = false;
		StringBuilder currentString = new StringBuilder();
		
		for(char c : json.toCharArray()) {
			if(Character.isWhitespace(c))
				continue;
			
			if(expectString) {
				if(stringCommand) {
					currentString.append('\\').append(c);
					continue;
				}
				
				if(c == '"') {
					expectString = false;
					out.add(new StringToken(currentString.toString()));
					currentString = new StringBuilder();
					continue;
				}
				
				currentString.append(c);
				continue;
			}

			if(number(c)) {
				expectNumber = true;
				currentString.append(c);
				continue;
			} 
			if(expectNumber) {
				expectNumber = false;
				out.add(new NumberToken(currentString.toString()));
				currentString = new StringBuilder();
			}
			
			if(letter(c)) {
				expectBoolean = true;
				currentString.append(c);
				continue;
			} 
			if(expectBoolean) {
				expectBoolean = false;
				String val = currentString.toString();
				currentString = new StringBuilder();
				if(val.toLowerCase().equals("null"))
					out.add(Token.NULL);
				else
					out.add(new BooleanToken(Boolean.parseBoolean(val)));
			}
			
			switch(c) {
			case '(': out.add(Token.OPEN_BRACKET); break;
			case ')': out.add(Token.CLOSE_BRACKET); break;
			case '{': out.add(Token.OPEN_CURLY); break;
			case '}': out.add(Token.CLOSE_CURLY); break;
			case '[': out.add(Token.OPEN_Array); break;
			case ']': out.add(Token.CLOSE_ARRAY); break;
			case ':': out.add(Token.DOUBLE_POINT); break;
			case ',': out.add(Token.COMMA); break;
			case '"': expectString = true;
			}
		}
		
		return out;
	}

	private static boolean letter(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
	}

	private static boolean number(char c) {
		return '0' <= c && c <= '9';
	}
}
