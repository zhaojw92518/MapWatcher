package org.umbrella.MapWatcher;

import java.lang.reflect.Type;
import java.util.HashSet;

public class TypeDealer {
	private static HashSet<Type> primitive_types = new HashSet<Type>();
	public static boolean init_types(){
		primitive_types.add(Integer.class);
        primitive_types.add(Character.class);
        primitive_types.add(Byte.class);
        primitive_types.add(Short.class);
        primitive_types.add(Long.class);
        primitive_types.add(Float.class);
        primitive_types.add(Double.class);
        primitive_types.add(Void.class);
        primitive_types.add(Boolean.class);
        primitive_types.add(String.class);
        
        primitive_types.add(int.class);
        primitive_types.add(char.class);
        primitive_types.add(byte.class);
        primitive_types.add(short.class);
        primitive_types.add(long.class);
        primitive_types.add(float.class);
        primitive_types.add(double.class);
        primitive_types.add(void.class);
        primitive_types.add(boolean.class);
        
        return true;
	}
	
	public static boolean is_primitive_type(Type in_type){
		return primitive_types.contains(in_type);
	}
}
