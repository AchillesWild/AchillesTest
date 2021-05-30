package com.achilles.wild.server.tool.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


public class HttpPostUtil {
	private final static Logger log = LoggerFactory.getLogger(HttpPostUtil.class);

	
	public static String postJson(String url,Map<String,Object> params) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(url);
		String param = JSON.toJSONString(params);
//		log.info("请求URL:" + url);
//		log.info("入参:"+params);
		StringRequestEntity entity = null;
		try {
			entity = new StringRequestEntity(param,"application/json","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		postMethod.setRequestEntity(entity);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000); 
		int statusCode = 0;
		try {
			statusCode =httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream input = null;
		try {
			input = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		log.info("返回请求Code: " + statusCode);
		BufferedReader br = null;
		 try {
			 br = new BufferedReader(new InputStreamReader(input,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 String readLine = "";
		 StringBuffer sb = new StringBuffer();
		 try {
			while ((readLine = br.readLine()) != null){
				 sb.append(readLine);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 postMethod.releaseConnection();
		return sb.toString();
	}
	
	/**
	 * post请求,入参转为json串
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postJson1(String url,Map<String,Object> params) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(url);
		String param = JSON.toJSONString(params);
		log.info("请求URL:" + url);
		log.info("入参:"+params);
		StringRequestEntity entity = null;
		try {
			entity = new StringRequestEntity(param,"application/json","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		postMethod.setRequestEntity(entity);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000); 
		int statusCode = 0;
		try {
			statusCode =httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = null;
		try {
			str = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		log.info("返回请求Code: " + statusCode);
		return str;
	}
	
	public static String post(String url,Map<String,String> params) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		for (String key : params.keySet()) {
			postMethod.addParameter(key, params.get(key));
		}
		log.info("请求URL:" + url);
		log.info("入参:"+params);
//		postMethod.addRequestHeader("Content-Type", "application/json; application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
//        postMethod.setRequestHeader("Content-Type", "UTF-8");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,500000); 
//		postMethod.set
		int statusCode = 0;
		try {
			statusCode =httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = null;
		try {
			str = postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		log.info("返回请求Code:" + statusCode);
		return str;
	}

}
