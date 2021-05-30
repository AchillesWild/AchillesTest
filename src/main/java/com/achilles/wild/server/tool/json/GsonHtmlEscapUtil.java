package com.achilles.wild.server.tool.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class GsonHtmlEscapUtil {
	static final Gson GsonHtmlEscap;
	
	static {
		GsonHtmlEscap = new GsonBuilder().disableHtmlEscaping().create();
	}	
	/**
	 * 
	 * 序列化json字符串 
	 * @param src   数据源
	 */
	public static String toJson(Object src) {
		return  GsonHtmlEscap.toJson(src);
	}	
	/**
	 * 
	 * 序列化json字符串（根据自定义类型）
	 * @param src  数据源
	 * @param t   自定义类型 
	 */
	public static String toJson(Object src,Type  t) {
		return GsonHtmlEscap.toJson(src, t);
		
	}
	/**
	 * 
	 * 反序列化json字符串成为对象 
	 * @param json        json格式的字符串
	 * @param classOfT    反序列的指定类型
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return  (T)GsonHtmlEscap.fromJson(json, classOfT);
	}	
	/**
	 * 
	 * 反序列化json字符串成为对象 
	 * @param json        json格式的字符串
	 * @param typeOfT    反序列的指定类型
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		return  (T)GsonHtmlEscap.fromJson(json, typeOfT);
	}	
	
}
