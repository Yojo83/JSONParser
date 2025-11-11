# Yojos JSON Parser

JSON Parser for Java in a .jar File.

## Usage
The class JSONParser in yojo.json provides the methods parseToJSON and parseFromJSON.
Both are applyable for a generic class/object or for the buildin JSONObject class.
The Whitespaces of the json output can be adjusted while Whitespaces in the input are ignored.

### generic classes
To parse an object to a json string you call "public static String parseToJSON(Object obj)". 
This will transform all public attributes of obj to name value pairs and returns a json string.
This supports arrays, other abjects, boolean, characters, strings and numbers, which are converted to their respective json values.

To parse a json string to an object you can call "public static <A> A parseFromJSON(Class<A> c, String json)".
The name value pairs of the json string will be parsed to an newly created object of the class c.
The fields in c must be public and must not be final and the constructor must be public and must not have parameters.
If there is an additional name value pair in the json string, it is ignored.
If there is no value in the jsonstring for a field a NullPointerException is thrown.
If the type of a field is different from the type in the json string, a null value is given.

For both methods: an array of primitives must be declared via the Object representation of the primitive or else an exception is thrown.

### JSONObject
JSONObject's, JSONArray's and JSONValue's can be used to construct a json string. 
JSONValues are JSONObject's, JSONArray's, StringToken's, BooleanToken's, NumberToken's and NullToken's.
JSONObject's consist of an HashMap with name and JSONValue's pairs, JSONArray's consist of an ArrayList of JSONValues and the tokens represent number, boolean, string or null values.

To parse an JSONObject to a json string you call "public static String parseToJSON(Object obj)" or "JSONObject.parseToJSON()".
This can throw a treeParserException.
This returns the json structure of the object to a json string.


To parse a json string to a JSONObject you can call "public static JSONObject parseFromJSON(String json)".
This can throw a treeParserException.
This will convert the json string to an JSONObject representing the structure of the json.

### Whitespaces
There are three public static attributes to JSONParser to control the generation of whitespaces in the json String (with defaults): SET_WHITE_SPACES (true), USE_TABS (false) and INDENTATION (4).
If SET_WHITE_SPACES is false, no new lines or indentations are created, but there still might be spaces in the final string.
If USE_TABS is true tab characters are set at the beginning of a new line. 
The amount depends of the depth of the json.
If USE_TABS is false INDENTATION dictates how many spaces are used instead of a tab character.

### TreeParserException
If there is any problem that is not resolved by another exception a TreeParserException is thrown.
This will currently not provide any further information except the StrackTrace.

## In Planning
- better exception handling
- support for enums


