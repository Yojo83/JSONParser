package yojo.json.test;

import yojo.json.JSONParser;
import yojo.json.tree.TreeParserException;


public class Tester {
	
	private static final String json = "{\r\n"
			+ "    \"name\" : \"hello\", \r\n"
			+ "    \"number\" : 5, \r\n"
			+ "    \"intArray\" : [\r\n"
			+ "        3, \r\n"
			+ "        4, \r\n"
			+ "        5\r\n"
			+ "    ], \r\n"
			+ "    \"objectArray\" : [\r\n"
			+ "        null\r\n"
			+ "    ], \r\n"
			+ "    \"longg\" : 83\r\n"
			+ "}";

	public static void main(String[] args) {
		try {
			ExampleObject test = new ExampleObject();
			String testS = JSONParser.parseToJSON(test);
			System.out.println(testS);
			test = JSONParser.parseFromJSON(ExampleObject.class, testS);
			System.out.println(JSONParser.parseToJSON(test));
		} catch (TreeParserException e) {
			e.printStackTrace();
		}
	}

}
