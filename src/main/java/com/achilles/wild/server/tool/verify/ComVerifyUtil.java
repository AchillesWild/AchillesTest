package com.achilles.wild.server.tool.verify;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComVerifyUtil {
	
    public static boolean isNotEmpty(Set set) {
    	if(set!=null && set.size()!=0){
    		return true;
	    }
		return false;
    } 

    public static boolean isEmpty(Set set) {
    	if(set==null||set.size()==0){
    		return true;
	    }
		return false;
    } 
    
    public static boolean isNotEmpty(Map map) {
    	if(map!=null && map.size()!=0){
    		return true;
	    }
		return false;
    } 
    
    public static boolean isEmpty(Map map) {
    	if(map==null||map.size()==0){
    		return true;
	    }
		return false;
    } 

    public static boolean isEmpty(Object obj) {
    	if(obj==null || "".equals(obj)){
    		return true;
	    }
		return false;
    }
    
    public static boolean isNotEmpty(Object obj) {
    	if(obj!=null&&!"".equals(obj)){
    		return true;
	    }
		return false;
    }

    public static boolean isEmpty(BigDecimal obj) {
    	if(obj==null||obj==new BigDecimal(0)){
    		return true;
	    }
		return false;
    }
    
    
    public static boolean isNotEmpty(BigDecimal obj) {
    	if(obj!=null && obj!=new BigDecimal(0)){
    		return true;
	    }
		return false;
    }
    
    public static boolean isNotEmpty(String str) {
    	if (str!= null && !"".equals(str.trim())) {
    		if(str.trim().length()!=0){
				return true;
    		}
		}
    			return false;
    }     
    
    public static boolean isEmpty(List list) {
    	if(list==null||list.size()==0){
    		return true;
	    }
		return false;
    }       
    
    public static boolean isEmpty(String str) {
    	if (str== null || "".equals(str)) {
//    		if(str.trim().length()==0){
//				return true;
//    		}
    			return true;
		}
    			return false;
    }     
 
    public static boolean isNotEmpty(List list) {
    	if(list!=null&&list.size()!=0){
    		return true;
	    }
		return false;
    }

    public static boolean isNotEmpty(Integer integer) {
    	if(integer!= null||!"".equals(integer)){
    		return true;
	    }
		return false;
    }

    public static boolean isEmpty(Integer integer) {
    	if(integer== null){
    		return true;
	    }
		return false;
    }
}
