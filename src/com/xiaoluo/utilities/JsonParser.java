package com.xiaoluo.utilities;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonParser {
	public static Gson gson = new Gson();
	
	// 关于泛型的问题：对泛型的理解不深,此处不知如何处理 java.lang.NullPointerException
	// OK,上面的问题解决了
	public static <T> T deserializeByJson(String data, Type type){
		if(TextUtil.isValidate(data)){
			return gson.fromJson(data, type);
		}
		
		return null;
	}
	
	public static <T> T deserializeByJson(String data, Class<T> clz){
		if(TextUtil.isValidate(data)){
			return gson.fromJson(data, clz);
		}
		
		return null;
	}
	
	public static <T> String serializeToJson(T t){
		if(t == null){
			return "";
		}
		
		return gson.toJson(t);
	}
}
