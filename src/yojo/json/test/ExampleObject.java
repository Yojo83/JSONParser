package yojo.json.test;

public class ExampleObject {

	
	public String name = "hello";
	public int number = 5;
	public Integer[] intArray = new Integer[] {3, 4, 5};
	public ExampleObject[] objectArray = new ExampleObject[] {null};
	public TestObject test = new TestObject();
	public TestEnum e = TestEnum.TEST1;
	public TestEnum en = TestEnum.TEST2;
	
	public ExampleObject() {
		
	}

	public void print() {
		System.out.println("Hello World");
	}
}
