package org.umbrella.MapWatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.TreeMap;

public class ValueWatcher {
	private static TreeMap<String, LinkedList<Field>> class_attr_map = new TreeMap<>();
	
	private Object local_instance = null;
	private Class<?> local_class = null;
	private Type local_type = null;
	private String local_name = null;
	private LinkedList<ValueWatcher> attrs = new LinkedList<>();
	
	public ValueWatcher(){
		
	}
	
	public ValueWatcher(Object in_instance, Class<?> in_class, Type in_type, String in_name){
		local_instance = in_instance;
		local_type = in_type;
		local_class = in_class;
		local_name = in_name;
		
		if(is_can_watch_in()){
			LinkedList<Field> cur_fields = get_all_field(in_class);
			for(Field cur_field: cur_fields){
				try {
					cur_field.setAccessible(true);
					Object cur_instance = cur_field.get(local_instance);
					Type cur_type = cur_field.getType();
					Class<?> cur_class = null;
					if(!TypeDealer.is_primitive_type(cur_type) && cur_instance != null){
						cur_class = get_obj_class(cur_instance);
						if(cur_class.isEnum()){
							cur_class = null;
						}
					}
					attrs.add(new ValueWatcher(cur_instance, cur_class, cur_type, cur_field.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
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
	
	private Class<?> get_obj_class(Object in_obj){
		Class<?> return_result = null;
		try {
			return_result = Class.forName(in_obj.getClass().getName());
		} catch (ClassNotFoundException e) {
			
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
		return local_name + " " + local_type + " " + get_instance_str();
	}
	
	public boolean is_can_watch_in(){
		return local_class != null;
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
			for(ValueWatcher cur_attr: attrs){
				cur_attr.std_out_print();
			}
			dec_indent();
		}
	}
}
