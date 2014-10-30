package org.umbrella.MapWatcher;

import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;

import org.umbrella.MapWatcher.SwingUI.WatcherUI;
import org.umbrella.MapWatcher.Test.TestClass;
import org.umbrella.MapWatcher.visitors.StrVisitor;
import org.umbrella.MapWatcher.visitors.TreeVisitor;

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
		StrVisitor str_visitor = new StrVisitor();
		watcher_01.run_visitor(str_visitor);
		//TreeVisitor tree_visitor = new TreeVisitor();
		//new WatcherUI(300, 400, (DefaultMutableTreeNode) watcher_01.run_visitor(tree_visitor));
	}
	
	private ValueWatcher local_value_watcher = null;
	public MapWatcher(Object in_obj, String in_name){
		local_value_watcher = new ValueWatcher(in_obj, in_name);
	}
	
	public void std_print_watcher(){
		StrVisitor str_visitor = new StrVisitor();
		local_value_watcher.run_visitor(str_visitor);
	}
	
	public void swing_ui_watcher(int width, int height){
		TreeVisitor tree_visitor = new TreeVisitor();
		new WatcherUI(width, height, 
				(DefaultMutableTreeNode) local_value_watcher.run_visitor(tree_visitor));
	}
	
	public void swing_ui_watcher(){
		TreeVisitor tree_visitor = new TreeVisitor();
		new WatcherUI(300, 400, 
				(DefaultMutableTreeNode) local_value_watcher.run_visitor(tree_visitor));
	}
}
