package com.achilles.wild.server.tool.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.achilles.wild.server.tool.json.FastJsonUtil;
import net.sf.json.JSONException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


public class ToClientUtil {
	
	private final static Logger logger =LoggerFactory.getLogger(ToClientUtil.class);
	
	/**
	 * 发送正确返回信息
	 * */
	public static void jsonToClient(HttpServletResponse response,Object obj){
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8"); 
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		logger.info("JSON datas To Client: "+JSON.toJSONString(obj));
		try {
			response.getWriter().write(JSON.toJSONString(obj));
			response.flushBuffer();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Dec 14, 2013 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response, FastJsonUtil.toJSONString(object));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}

	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Dec 14, 2013 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

}
