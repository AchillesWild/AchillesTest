package com.achilles.wild.server.tool.jdbc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.achilles.wild.server.tool.date.DateUtil;

/**
 * 生成sql
 * @author Pang
 *
 */
public class SqlUtil {
    
    public static void main(String[] args) {
        Set set = new HashSet<>();
        set.add("a");
        set.add("d");
        set.add("g");
        System.out.println(setStrToCon(set));
    }

	/**
	 * 获取生成sql
	 * @param tableName 数据库名，表名，如wl_lcs.lcs_dict
	 * @param params 字段和值的键值对
	 * @return
	 */
	public static String getInsertSql(String tableName,Map<String,Object> params){//
		StringBuffer columnSb = new StringBuffer();
		StringBuffer valueSb = new StringBuffer("'");
		int count = 0;
		 params.put("create_date", DateUtil.getSysTimestamp());
//		 params.put("create_date", new java.util.Date(new java.sql.Date().getTime()));
		 for (Map.Entry<String, Object> entry : params.entrySet()) {
			 count++;
			 if (count < params.size() && params.size()!=1) {
				 columnSb.append(entry.getKey()).append(",");
				 valueSb.append(entry.getValue()).append("','");
			}else if (count == params.size() || params.size()==1){
				 columnSb.append(entry.getKey());
				 valueSb.append(entry.getValue()).append("'");
			}
			 
		}
		String insertSql = "INSERT INTO "+tableName+" ("+columnSb.toString()+") VALUES "+"("+valueSb.toString()+")";
		return insertSql;
	}
    
	public static String setToInSql(Set set){
		String str = set.toString().replace("[", "('").replace("]", "')").replaceAll(",", "','").replaceAll(" ", "");
		return str;
	}
    
	/**
	 * set 转换为where后的条件,column为数据库字段名称，如果是多表操作就为如A.column 20141127 ppp
	 * @return
	 */
	public static String setToQuyCon(String column, Set set) {
		if (set!=null && set.size()!=0) {
			return " and " + column + " in " + set.toString().replace("[", "(").replace("]", ")");
		}
		return "";
	}

	/**
	 * set 转为In  后的条件
	 * @param set  除了字符串的值
	 * @since 2018年2月3日
	 */
    public static String setToCon(Set set) {
        String str = set.toString().replace("[", "('").replace("]", "')");
        return str;
    }	
	
    /**
     * set 转为In  后的条件
     * @param set  字符串的值
     * @since 2018年2月3日
     */
    public static String setStrToCon(Set set) {
        String str = set.toString().replace("[", "('").replace("]", "')").replaceAll(",", "','").replaceAll(" ", "");
        return str;
    }   
    
	/**
	 * list 转换为where后的条件
	 * @param paramName
	 * @param list
	 * @return
	 */
	public static String listToQuyCon(String paramName, List list) {
		if (list!=null && list.size()!=0) {
			return " and " + paramName + " in " + list.toString().replace("[", "(").replace("]", ")");
		}
		return "";
	}
	
	/**
	 * 20141125 ppp
	 * 
	 * @param alias
	 *            查询别名，如果是多表查询，传入参数为诸如"A.",如果是单表查询传入空字符串
	 * @param sourceMap
	 *            源数据
	 * @param aimParams
	 *            你要生成条件的数据，竖线左边的为参数名称，右边的值如果传入"String"表示是字符串，其他为非字符串（必传）；
	 * @return 根据源map数据转换为需要的查询条件
	 */
	public static String mapToQuyCon(String alias, Map<String, String> sourceMap, String[] aimParams) {
		StringBuffer sbCon = new StringBuffer();
		for (String params : aimParams) {
			String param = params.split("\\|")[0];
			String val = sourceMap.get(param);
			if (val != null && !"".equals(val.trim())) {
				String type = params.split("\\|")[1];
				if ("String".equals(type)) {
					sbCon.append(" and " + alias + param + "='" + val + "'");
				} else {
					sbCon.append(" and " + alias + param + "=" + val);
				}
			}
		}
		return sbCon.toString();
	}
	
}
