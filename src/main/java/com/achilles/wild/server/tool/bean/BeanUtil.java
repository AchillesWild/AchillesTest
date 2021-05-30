package com.achilles.wild.server.tool.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;


public class BeanUtil {
	
	/**
	 * 获取一个bean里的属性为**的值集合
	 */
	public static Set<Object> getValsOfByBeanList(List beanList, String paramName) {
		Set<Object> valSet = new HashSet<Object>();
		paramName = paramName.toUpperCase();
		for (Object bean : beanList) {
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
						if (val != null) {
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
	 * bean 转换为Map 20140806 15:00 PPP
	 */
	public static Map<String, Object> beanToMap(Object bean) {
		@SuppressWarnings("rawtypes")
		Class type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = null;
				try {
					result = readMethod.invoke(bean, new Object[0]);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * bean集合转换为Map集合 20140806 15:08 PPP
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> beanListToMapList(List list) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object o : list) {
			Map<String, Object> map = beanToMap(o);
			listMap.add(map);
		}
		return listMap;
	}
	
	/**
	 * 获取一个bean里的属性为**的值集合 20141209
	 */
	public static <T> Set getSetValsByBeanList(List<T> beanList, String paramName) {
		Set<T> valSet = new HashSet<T>();
		paramName = paramName.toUpperCase();
		for (T bean : beanList) {
			Class<?> type = bean.getClass();
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
					T val = null;
					try {
						val = (T) readMethod.invoke(bean, new Object[0]);
						if (val != null) {
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
	 * 获取Vo对象的字段和类型键值对
	 * 
	 * @param clz
	 * @return
	 */
	public static Map<String, String> getFieldToTypeMap(Class<?> clz) {
		Field[] fieldArray = clz.getSuperclass().getDeclaredFields();
		Map<String, String> fieldToTypeMap = new HashMap<>();
		for (int i = 0; i < fieldArray.length; i++) {
			// 属性类型
			Class<?> type = fieldArray[i].getType();
			fieldToTypeMap.put(fieldArray[i].getName(), type.getSimpleName());
		}
		return fieldToTypeMap;
	}
	
	/**
	 * 获取Vo对象的字段
	 * 
	 * @param clz
	 * @return
	 */
	public static String[] getFieldArray(Class<?> clz) {
		Field[] fieldArray = clz.getSuperclass().getDeclaredFields();
		String[] colArray = new String[fieldArray.length];
		for (int count = 0; count < fieldArray.length; count++) {
			colArray[count] = fieldArray[count].getName();
		}
		return colArray;
	}
	
	public static final <T> T convertMap2Bean(Class<T> clazz, Map<String, Object> map){
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		
		PropertyDescriptor[] targetPds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if(writeMethod != null && map.containsKey(targetPd.getName().toLowerCase())){
				try {
					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
						writeMethod.setAccessible(true);
					}
					writeMethod.invoke(t, map.get(targetPd.getName().toLowerCase()));
				} catch (Throwable ex) {
					throw new FatalBeanException("Could not write property '" + targetPd.getName() + "' from bean", ex);
				}
			}
		}
		
		return t;
	}
	
	/**
	 * 内省 遍历原bean 取出非null的
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T beanDelNullAndEmpty(T t){
		Object ret = null;
		try {
			ret =  t.getClass().newInstance();
			 BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] targetPds = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor targetPd : targetPds) {
				Method writeMethod = targetPd.getWriteMethod();
				Method readMethod = targetPd.getReadMethod();
				if(writeMethod != null && readMethod!=null){
					
					try {
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						Object invoke = readMethod.invoke(t, null);
						if(invoke!=null){
							writeMethod.invoke(ret, invoke);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not write property '" + targetPd.getName() + "' from bean", ex);
					}
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T)ret;
	}
	
	/**
	 * 获取传入的对象集合属性值
	 * @param list
	 * @param needPros 需要获取的字段,如果要获取所有传null
	 * @return 返回不同对象的值用"||"隔开
	 */
	public static String getBeanListVal(List list,String[] needPros) {
		if (list==null||list.size()==0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object obj : list) {
			sb.append(getBeanVal(obj,needPros)).append("||");
		}
		return sb.toString();
	}

	/**
     * 获取传入的对象属性值
     * 
     * @param bean
     * @param needPros 需要获取的字段,如果要获取所有传null
     * @return 返回的值用","隔开
     */
    public static String getBeanVal(Object bean, String[] needPros) {
        if (bean == null) {
            return "";
        }
        @SuppressWarnings("rawtypes")
        Class cls = bean.getClass();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(cls);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        // 获取字段的类型
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (propertyName.equals("class")) {
                continue;
            }
            if (needPros == null || (needPros != null && Arrays.asList(needPros).contains(propertyName))) {
                Method readMethod = descriptor.getReadMethod();
                Object result = null;
                try {
                    result = readMethod.invoke(bean, new Object[0]);
                    if (result instanceof Object[] && result != null) {
                        Object[] array = (Object[]) result;
                        result = Arrays.toString(array);
                    }
                    sb.append(propertyName + "=" + result + ",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        String result = sb.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 校验一个对象传入的若干字段值是否有为null，或""的（不适用于基本类型） ，如果有为null的直接返回这个字段，否则返回null;
     * @param list
     * @param needPros 传null校验所有
     * @return
     */
    public static String validateBeanListProEmpty(List list,String[] needPros) {
        String result = null;
        for (Object bean:list) {
            result = validateBeanProEmpty(bean, needPros);
            if (result!=null) {
                return result; 
            }
        }
        return null;
    }
    
    /**
     * 校验一个对象传入的若干字段值是否有为null，或""的（不适用于基本类型） ，如果有为null的直接返回这个字段，否则返回null;
     * @param bean
     * @param needPros  传null校验所有
     * @return
     */
    public static String validateBeanProEmpty(Object bean,String[] needPros) {
        @SuppressWarnings("rawtypes")
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
            String propertyName = descriptor.getName();
            if (propertyName.equals("class")) {
                continue;
            }
            if (needPros==null || (needPros!=null && Arrays.asList(needPros).contains(propertyName))) {
                Method readMethod = descriptor.getReadMethod();
                Object result = null;
                try {
                    result = readMethod.invoke(bean, new Object[0]);
                    if (result==null || "".equals(result)) {
                        return propertyName;
                    }
                    if (result instanceof Object[]) {
                        Object[] array = (Object[]) result;
                        if (array.length==0) {
                            return propertyName;
                        }
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
        return null;
    }

	/**
	 * 获取属性值为空(包含null或""或空集合)的字段集合
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		if(source==null){
			return new String[0];
		}

		BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor property : propertyDescriptors) {
			if("class".equals(property.getName())){
				continue;
			}

			Object srcValue = src.getPropertyValue(property.getName());
			if (srcValue == null || "".equals(srcValue)) {
				emptyNames.add(property.getName());
			}else{
				if(srcValue instanceof java.util.Collection && ((Collection)srcValue).size()==0){
					emptyNames.add(property.getName());
				}
			}
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

    
    public static void main(String[] args) {
//        com.server.beans.User user = new com.server.beans.User();
////        user.setAge(age);
//        user.setRootInstCd("M000001");
//        user.setUserId("");
//        user.setRoleCode(new String[]{"d","23","23we"});
//        user.setType(null);
//        user.setPassword("232");
//        System.out.println(getBeanVal(user,null));;
    }
    
}
