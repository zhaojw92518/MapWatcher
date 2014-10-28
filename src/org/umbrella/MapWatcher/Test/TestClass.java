package org.umbrella.MapWatcher.Test;



public class TestClass {
	public int my_int = 255;
	private double my_double;
	public String my_str = null;
	protected TestClass my_reflect;
	public TestEnum my_enum = TestEnum.aaa;
	
	public TestClass(){
		
	}
	
	public TestClass(String in_str){
		my_str = in_str;
	}
}
