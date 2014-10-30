package org.umbrella.MapWatcher.visitors;

import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.umbrella.MapWatcher.ValueWatcher;
import org.umbrella.MapWatcher.ValueWatcher.CollectionType;
import org.umbrella.MapWatcher.ValueWatcher.DataPair;

public class TreeVisitor implements ValueWatcherVisitor {

	@Override
	public Object visit(String in_name, Class<?> in_class, Object in_obj,
			CollectionType in_collection_type,
			LinkedList<ValueWatcher> in_attrs,
			LinkedList<ValueWatcher> in_vector, LinkedList<DataPair> in_map) {
		DefaultMutableTreeNode return_result = null;
		JTable cur_node_table = new JTable(
				create_table_body(in_name, in_class, in_obj),
				create_column_name());
		return_result = new DefaultMutableTreeNode(cur_node_table);
		Integer i = 0;
		if(in_collection_type == CollectionType.none){
			for(ValueWatcher cur_attr: in_attrs){
				return_result.add((DefaultMutableTreeNode) cur_attr.run_visitor(this));
			}
		}
		else if(in_collection_type == CollectionType.list ||
				in_collection_type == CollectionType.set){
			for(ValueWatcher cur_obj: in_vector){
				DefaultMutableTreeNode cur_index_node = get_index_node(i.toString());
				cur_index_node.add((DefaultMutableTreeNode) cur_obj.run_visitor(this));
				return_result.add(cur_index_node);
				i++;
			}
		}
		else if(in_collection_type == CollectionType.map){
			for(DataPair cur_pair: in_map){
				DefaultMutableTreeNode cur_pair_node = get_index_node(i.toString());
				cur_pair_node.add((DefaultMutableTreeNode) cur_pair.key.run_visitor(this));
				cur_pair_node.add((DefaultMutableTreeNode) cur_pair.value.run_visitor(this));
				return_result.add(cur_pair_node);
				i++;
			}
		}
		return return_result;
	}
	
	private DefaultMutableTreeNode get_index_node(String in_index){
		JTable cur_pair_table = new JTable(
				create_table_body("[" + in_index + "]", "", ""), 
				create_column_name());
		DefaultMutableTreeNode cur_index_node = new DefaultMutableTreeNode(cur_pair_table);
		return cur_index_node;
	}
	
	private Object[][] create_table_body(
			String in_name, 
			Class<?> in_class, 
			Object in_obj){
		return create_table_body(
				in_name, 
				in_class.getSimpleName(), 
				in_obj.toString());
	}
	
	private Object[][] create_table_body(
			String in_name, 
			String in_class, 
			String in_obj){
		Object[][] return_result = new Object[1][3];
		return_result[0][0] = in_name;
		return_result[0][1] = in_class;
		return_result[0][2] = in_obj;
		return return_result;
	}
	
	private Object[][] create_table_body(
			Integer in_index, 
			String in_name, 
			Class<?> in_class, 
			Object in_obj){
		return create_table_body(
				in_index.toString(), 
				in_name, 
				in_class.getSimpleName(), 
				in_obj.toString());
	}
	
	private Object[][] create_table_body(
			String in_index, 
			String in_name, 
			String in_class, 
			String in_obj){
		Object[][] return_result = new Object[1][4];
		return_result[0][0] = in_index;
		return_result[0][1] = in_name;
		return_result[0][2] = in_class;
		return_result[0][3] = in_obj;
		return return_result;
	} 
	
	private Object[] create_column_name(){
		Object[] return_result = new Object[3];
		return_result[0] = "name";
		return_result[1] = "type";
		return_result[2] = "value";
		return return_result;
	}

}
