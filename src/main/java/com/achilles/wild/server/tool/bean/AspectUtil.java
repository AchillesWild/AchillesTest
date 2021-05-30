package com.achilles.wild.server.tool.bean;

import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AspectUtil {

    /**
     * get params
     *
     * @param joinPoint
     * @return
     */
    public static Map<String,Object> getParamsMap(JoinPoint joinPoint){

        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        if(paramNames.length==0){
            return new HashMap<>();
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IgnoreParams ignoreParams = method.getAnnotation(IgnoreParams.class);
//        String[] ignoreFields = null;
//        Set<String> ignoreFieldSet = new HashSet<>();
        if(ignoreParams !=null){
            return new HashMap<>();
//            ignoreFields = ignoreField.value();
//            if (ignoreFields.length!=0){
//                ignoreFieldSet = new HashSet<>(Arrays.asList(ignoreFields));
//            }
        }

        Object[] paramValues = joinPoint.getArgs();

        Map<String,Object> paramsMap = new HashMap<>();
        for(int i=0;i<paramNames.length;i++){
            String key = paramNames[i];
            Object value = paramValues[i];
            paramsMap.put(key,value);
//            if(value==null){
//                continue;
//            }
//            Object val = value;
//            boolean isSynthetic = value.getClass().isSynthetic();
//            if(isSynthetic){
//                val = JsonUtil.toJsonString(value);
//                String jsonVal = val.toString();
//                if (!jsonVal.contains(CommonConstant.PASSWORD)){
//                    Map<String,Object> newParamsMap = JsonUtil.fromJson(jsonVal,HashMap.class);
//                    newParamsMap.remove(CommonConstant.PASSWORD);
//                    val = JsonUtil.toJsonString(newParamsMap);
//                }
//            }
//            paramsMap.put(key,val);
        }

        return paramsMap;
    }
}
