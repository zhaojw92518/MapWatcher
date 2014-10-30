package org.umbrella.MapWatcher.visitors;

import java.util.LinkedList;

import org.umbrella.MapWatcher.ValueWatcher;
import org.umbrella.MapWatcher.ValueWatcher.CollectionType;
import org.umbrella.MapWatcher.ValueWatcher.DataPair;

public interface ValueWatcherVisitor{
	public Object visit(
			String in_name, 
			Class<?> in_class, 
			Object in_obj,
			CollectionType in_collection_type,
			LinkedList<ValueWatcher> in_attrs, 
			LinkedList<ValueWatcher> in_vector,
			LinkedList<DataPair> in_map);
}
