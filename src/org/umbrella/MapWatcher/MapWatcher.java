package org.umbrella.MapWatcher;

import java.util.TreeMap;

import org.umbrella.MapWatcher.Test.TestClass;
import org.umbrella.MapWatcher.visitors.StrVisitor;

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
		for(Integer i = 0; i < 10; i++){
			test_collection.put(i.toString(), new TestClass(i.toString()));
		}
		ValueWatcher watcher_01 = new ValueWatcher(test_collection, "test_collection");
		//watcher_01.std_out_print();
		StrVisitor str_visitor = new StrVisitor();
		watcher_01.run_visitor(str_visitor);
		new WatcherUI(300, 400);
	}
}
