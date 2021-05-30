package com.achilles.wild.server.tool.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.achilles.wild.server.tool.verify.ComVerifyUtil;


public class ClassUtil {

	public static void main(String[] args) {
//		getAllPropertiesOfClass(new Employee());
	}
	
	/**
	 * 获取一个bean的所有属性用","隔开
	 */
	@SuppressWarnings("rawtypes")
	public static Set<String> getAllPropertiesOfClass(Object obj){
		Class type = obj.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Set<String> proSet = new HashSet<String>();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			proSet.add(propertyName);
		}	
		proSet.remove("class");
//		System.out.println(proSet);
		return proSet;
	}	
	
	/**
	 * 获取一个bean里的属性为**的Set值集合
	 */
	@SuppressWarnings("rawtypes")
	public static Set<Object> getValsByBeanList(List beanList,String paramName){
		Set<Object> valSet = new HashSet<Object>();
		paramName = paramName.toUpperCase();
		for(Object bean:beanList){
			Class type = bean.getClass();
			BeanInfo beanInfo = null;
			try {
				beanInfo = Introspector.getBeanInfo(type);
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName().toUpperCase();
				if (propertyName.equals(paramName)) {
					Method readMethod = descriptor.getReadMethod();
					Object val = null;
					try {
						val = readMethod.invoke(bean, new Object[0]);
						if(ComVerifyUtil.isNotEmpty(val)){
							valSet.add(val);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}			
		}
		return valSet;
	}
	
	/**
	 * 20140427
	 * @param classFullName
	 * @return 根据类的全路径名称获取类  
	 */
	public static Class<?> getClassByClassFullName(String classFullName) {
		Class<?> clz =null;
		try {
			clz=Class.forName(classFullName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return  clz;
	}
}
