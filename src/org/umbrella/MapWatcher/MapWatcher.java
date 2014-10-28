package org.umbrella.MapWatcher;

import java.util.TreeMap;

import org.umbrella.MapWatcher.Test.TestClass;

public class MapWatcher {
	public static void print_inherit_tree(Class<?> in_class){
		if(in_class != null){
			System.out.println(in_class.getSimpleName());
			print_inherit_tree(in_class.getSuperclass());
		}
	}
	
	public static void main(String[] args){
		TypeDealer.init_types();
		TreeMap<String, TestClass> test_collection = new TreeMap<>();
		print_inherit_tree(test_collection.getClass());
		for(Integer i = 0; i < 10; i++){
			test_collection.put(i.toString(), new TestClass(i.toString()));
		}
		ValueWatcher watcher_01 = new ValueWatcher(test_collection, "test_collection");
		watcher_01.std_out_print();
	}
}
