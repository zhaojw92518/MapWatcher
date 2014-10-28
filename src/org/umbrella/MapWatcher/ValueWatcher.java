package org.umbrella.MapWatcher;

import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ValueWatcher {
	private static TreeMap<String, LinkedList<Field>> class_attr_map = new TreeMap<>();
	
	private Object local_instance = null;
	private Class<?> local_class = null;
	private String local_name = null;
	private LinkedList<ValueWatcher> attrs = new LinkedList<>();
	
	private CollectionType local_collection_type = CollectionType.none;
	private LinkedList<ValueWatcher> vector_collect = null;
	private LinkedList<DataPair> map_collect = null;
	private int collection_size = -1;
	
	public ValueWatcher(){
		
	}
	
	public ValueWatcher(Object in_instance, String in_name){
		local_instance = in_instance;
		local_class = local_instance.getClass();
		local_name = in_name;
		
		//判断是否为容器
		local_collection_type = get_collection_type(local_instance.getClass());
		switch (local_collection_type) {
		case none:
			deal_not_collection();
			break;
		case list:
			deal_list();
			break;
		case set:
			deal_set();
			break;
		case map:
			deal_map();
			break;
		default:
			break;
		}
	}
	
	public boolean is_can_watch_in(){
		return !TypeDealer.is_primitive_type(local_class) && !local_class.isEnum() && local_instance != null;
	}
	
	private void deal_not_collection(){
		if(is_can_watch_in()){
			LinkedList<Field> cur_fields = get_all_field(local_class);
			for(Field cur_field: cur_fields){
				try {
					cur_field.setAccessible(true);
					Object cur_instance = cur_field.get(local_instance);
					if(cur_instance != null){
						attrs.add(new ValueWatcher(cur_instance, cur_field.getName()));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void deal_list(){
		vector_collect = new LinkedList<>();
		List cur_list = (List) local_instance;
		collection_size = cur_list.size();
		for(Integer i = 0; i < collection_size; i++){
			Object cur_obj = cur_list.get(i);
			vector_collect.add(new ValueWatcher(cur_obj, "[" + i.toString() + "]"));
		}
	}
	
	private void deal_set(){
		vector_collect = new LinkedList<>();
		Set cur_set = (Set) local_instance;
		collection_size = cur_set.size();
		Integer i = 0;
		for(Object cur_obj: cur_set){
			vector_collect.add(new ValueWatcher(cur_obj, "[" + i.toString() + "]"));
			i++;
		}
	}
	
	private void deal_map(){
		map_collect = new LinkedList<>();
		Map cur_map = (Map) local_instance;
		collection_size = cur_map.size();
		Set key_set = cur_map.keySet();
		for(Object cur_key: key_set){
			Object cur_value = cur_map.get(cur_key);
			map_collect.add(new DataPair(
					new ValueWatcher(cur_key, "key"),
					new ValueWatcher(cur_value, "value")
					));
		}
	}
	
	private LinkedList<Field> get_all_field(Class<?> in_class){
		String in_class_name = in_class.getName();
		LinkedList<Field> return_result = class_attr_map.get(in_class_name);
		if(return_result == null){
			Field[] cur_fields = in_class.getDeclaredFields();
			return_result = new LinkedList<>();
			for(int i = 0; i < cur_fields.length; i++){
				return_result.add(cur_fields[i]);
			}
			Class<?> super_class = in_class.getSuperclass();
			if(super_class != null){
				return_result.addAll(get_all_field(super_class));
			}
			class_attr_map.put(in_class_name, return_result);
		}
		return return_result;
	}
		
	private String get_instance_str(){
		String return_result = null;
		if(local_instance == null){
			return_result = "null";
		}
		else{
			return_result = local_instance.toString();
		}
		return return_result;
	}
	
	public String toString(){
		return local_name + " " + local_class.getSimpleName() + " " + get_instance_str();
	}
	
	private LinkedList<Class<?>> get_inherit_list(Class<?> in_class){
		LinkedList<Class<?>> return_result = new LinkedList<>();
		while(true){
			if(in_class != null){
				return_result.add(in_class);
				in_class = in_class.getSuperclass();
			}
			else{
				break;
			}
		}
		return return_result;
	}
	
	private CollectionType get_collection_type(Class<?> in_class){
		LinkedList<Class<?>> in_class_inherit_list = get_inherit_list(in_class);
		int list_size = in_class_inherit_list.size();
		CollectionType return_result = CollectionType.none;
		if(list_size > 3 && in_class_inherit_list.get(list_size - 2).equals(AbstractCollection.class)){
			Class<?> conllection_type = in_class_inherit_list.get(list_size - 3);
			if(conllection_type.equals(AbstractList.class)){
				return_result = CollectionType.list;
			}
			else if(conllection_type.equals(AbstractSet.class)){
				return_result = CollectionType.set;
			}
		}
		else if(in_class_inherit_list.get(list_size - 2).equals(AbstractMap.class)){
			return_result = CollectionType.map;
		}
		return return_result;
	}
	
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
	
	public void std_out_print(){
		indent_println(this.toString());
		if(is_can_watch_in()){
			inc_indent();
			switch (local_collection_type) {
			case none:
				for(ValueWatcher cur_attr: attrs){
					cur_attr.std_out_print();
				}
				break;
			case list:
				for(ValueWatcher cur_obj: vector_collect){
					cur_obj.std_out_print();
				}
				break;
			case set:
				for(ValueWatcher cur_obj: vector_collect){
					cur_obj.std_out_print();
				}
				break;
			case map:
				Integer i = 0;
				for(DataPair cur_pair: map_collect){
					indent_println("[" + i.toString() + "]");
					inc_indent();
					cur_pair.key.std_out_print();
					cur_pair.value.std_out_print();
					dec_indent();
					i++;
				}
			default:
				break;
			}
			
			dec_indent();
		}
	}
	
	private enum CollectionType {
		none,list,set,map;
	}
	
	private class DataPair{
		public ValueWatcher key = null, value = null;
		public DataPair(ValueWatcher in_key, ValueWatcher in_value){
			key = in_key;
			value = in_value;
		}
	}
}
