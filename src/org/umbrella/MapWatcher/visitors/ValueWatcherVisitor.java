package org.umbrella.MapWatcher.visitors;

import java.util.LinkedList;

import org.umbrella.MapWatcher.ValueWatcher;
import org.umbrella.MapWatcher.ValueWatcher.DataPair;

public interface ValueWatcherVisitor <T>{
	public T visit(ValueWatcher in_watcher);
	
	public T visit_attrs(LinkedList<ValueWatcher> in_attrs);
	
	public T visit_vector(LinkedList<ValueWatcher> in_vector);
	
	public T visit_map(LinkedList<DataPair> in_map);
	
	public void before_visit();
	
	public void after_visit();
}
