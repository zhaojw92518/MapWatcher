package org.umbrella.MapWatcher;

public class MapWatcher {
	public static void main(String[] args){
		TypeDealer.init_types();
		TestClass test_class = new TestClass();
		ValueWatcher my_watcher = new ValueWatcher(
				test_class, 
				test_class.getClass(), 
				test_class.getClass(), 
				"test_class");
		my_watcher.std_out_print();
	}
}
