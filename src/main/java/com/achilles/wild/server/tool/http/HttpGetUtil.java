package com.achilles.wild.server.tool.http;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class HttpGetUtil {

	private final static Logger log = LoggerFactory.getLogger(HttpGetUtil.class);

	public static String get(String url, Map<String, String> urlParams,Map<String, Object> headerParams) {

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		if (!CollectionUtils.sizeIsEmpty(urlParams)){
			NameValuePair[] nameValuePairs = new NameValuePair[urlParams.size()];
			int m = 0;
			for (String key : urlParams.keySet()) {
				nameValuePairs[m] = new NameValuePair(key, urlParams.get(key));
				m++;
			}
			getMethod.setQueryString(nameValuePairs);
		}
		if (!CollectionUtils.sizeIsEmpty(headerParams)){
			for (Map.Entry entry : headerParams.entrySet()) {
				Object key = entry.getKey();
				if (key==null){
					throw new IllegalArgumentException("key in header can not be null !");
				}
				Object value = entry.getValue();
				if (key==null){
					throw new IllegalArgumentException("value in header can not be null !");
				}
				getMethod.setRequestHeader(key.toString(), value.toString());
			}
		}

		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);// 超时为5秒
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		log.debug("请求URL=" + url + "  入参=" + urlParams);
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.debug("code : " + statusCode);

		InputStream inputStream = null;
		try {
			inputStream = getMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
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

		getMethod.releaseConnection();

		String str = sb.toString();
		log.debug("result : "+str);
		return str;
	}

}
