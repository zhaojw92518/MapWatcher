package org.umbrella.MapWatcher.visitors;

import java.util.LinkedList;

import org.umbrella.MapWatcher.ValueWatcher;
import org.umbrella.MapWatcher.ValueWatcher.CollectionType;
import org.umbrella.MapWatcher.ValueWatcher.DataPair;

public class StrVisitor implements ValueWatcherVisitor {
	private static StringBuffer indent_str = new StringBuffer();
	private static void inc_indent(){
		indent_str.append('\t');
	}
	private static void dec_indent(){
		indent_str.deleteCharAt(indent_str.length() - 1);
	}
	
	private void indent_println(String in_str){
		System.out.println(indent_str + in_str);
	}
	
	
	public StrVisitor() {
		
	}
	
	@Override
	public Object visit(String in_name, 
			Class<?> in_class, 
			Object in_obj,
			CollectionType in_collection_type,
			LinkedList<ValueWatcher> in_attrs, 
			LinkedList<ValueWatcher> in_vector,
			LinkedList<DataPair> in_map) {
		indent_println(in_name + " " + in_class.getSimpleName() + " " + in_obj.toString());
		inc_indent();
		if(in_collection_type == CollectionType.none){
			visit_attrs(in_attrs);
		}
		else if(in_collection_type == CollectionType.list ||
				in_collection_type == CollectionType.set){
			visit_vector(in_vector);
		}
		else if(in_collection_type == CollectionType.map){
			visit_map(in_map);
		}
		dec_indent();
		return null;
	}

	public void visit_attrs(LinkedList<ValueWatcher> in_attrs) {
		for(ValueWatcher cur_attr: in_attrs){
			cur_attr.run_visitor(this);
		}
	}

	public void visit_vector(LinkedList<ValueWatcher> in_vector) {
		for(ValueWatcher cur_obj: in_vector){
			cur_obj.run_visitor(this);
		}
	}

	public void visit_map(LinkedList<DataPair> in_map) {
		Integer i = 0;
		for(DataPair cur_pair: in_map){
			indent_println("[" + i.toString() + "]");
			//inc_indent();
			cur_pair.key.run_visitor(this);;
			cur_pair.value.run_visitor(this);;
			//dec_indent();
			i++;
		}
	}
}
