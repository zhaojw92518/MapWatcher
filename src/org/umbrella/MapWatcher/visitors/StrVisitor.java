package org.umbrella.MapWatcher.visitors;

import java.util.LinkedList;

import org.umbrella.MapWatcher.ValueWatcher;
import org.umbrella.MapWatcher.ValueWatcher.DataPair;

public class StrVisitor implements ValueWatcherVisitor<String> {
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
	
	private ValueWatcher bind_watcher = null;
	
	public StrVisitor(ValueWatcher in_watcher) {
		bind_watcher = in_watcher;
	}
	
	@Override
	public String visit(ValueWatcher in_watcher) {
		indent_println(bind_watcher.toString());
		return null;
	}

	@Override
	public String visit_attrs(LinkedList<ValueWatcher> in_attrs) {
		for(ValueWatcher cur_attr: in_attrs){
			bind_watcher.run_visitor(this);
		}
		return null;
	}

	@Override
	public String visit_vector(LinkedList<ValueWatcher> in_vector) {
		for(ValueWatcher cur_obj: in_vector){
			bind_watcher.run_visitor(this);
		}
		return null;
	}

	@Override
	public String visit_map(LinkedList<DataPair> in_map) {
		Integer i = 0;
		for(DataPair cur_pair: in_map){
			indent_println("[" + i.toString() + "]");
			inc_indent();
			cur_pair.key.run_visitor(this);;
			cur_pair.value.run_visitor(this);;
			dec_indent();
			i++;
		}
		return null;
	}

	@Override
	public void before_visit() {
		inc_indent();
		
	}

	@Override
	public void after_visit() {
		dec_indent();
	}

}
