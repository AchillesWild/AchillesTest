//package com.achilles.wild.server.tool.http;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
//
//import com.model.response.BaseResult;
//import com.achilles.wild.server.tool.json.FastJsonTools;
//
//
//public class HttpClientAppUtil {
//
//	static Logger log = Logger.getLogger(HttpClientAppUtil.class);
//
//	private static int REQUEST_TIMEOUT = 60 * 1000;
//
//	private HttpClientAppUtil() {
//
//	}
//
//	public static String sendPostRequest(String reqURL, String data) throws Exception {
//		HttpPost httpPost = new HttpPost(reqURL);
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpResponse response = null;
//		String result = null;
//		try {
//			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
//
//			StringEntity stringEntity = new StringEntity(data, "UTF-8");
//			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
//			stringEntity.setContentType("application/json");
//
//			httpPost.setConfig(doRequestConfig());
//
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//			HttpEntity httpEntity = response.getEntity();
//			int statusCode = response.getStatusLine().getStatusCode();
//			
//			if (statusCode== HttpStatus.SC_OK &&  httpEntity != null) {
//				result = EntityUtils.toString(httpEntity, "UTF-8");
//				log.info("url ="+reqURL+",data="+data+",result"+result);
//				EntityUtils.consume(httpEntity);
//			} else {
//				log.error("??????????????????. reqURL=" + reqURL+" ,statusCode="+statusCode + " ,data=" + data);
//			}
//		} catch (Exception e) {
//			log.error("reqURL=" + reqURL + " ,data=" + data, e);
//			e.printStackTrace();
//		} finally {
//			closeHttp(response, httpClient);
//		}
//		return result;
//	}
//
//	public static BaseResult sendPostRequestByAjax(String reqURL, String data) throws Exception {
//		HttpPost httpPost = new HttpPost(reqURL);
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpResponse response = null;
//		BaseResult respModel = null;
//		try {
//			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
//
//			StringEntity stringEntity = new StringEntity(data, "UTF-8");
//			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
//			stringEntity.setContentType("application/json");
//
//			httpPost.setConfig(doRequestConfig());
//			
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//
//			HttpEntity httpEntity = response.getEntity();
//			int statusCode = response.getStatusLine().getStatusCode();
//				
//			if (statusCode== HttpStatus.SC_OK &&  httpEntity != null) {
//				String result = EntityUtils.toString(httpEntity, "UTF-8");
//				log.info("url ="+reqURL+",data="+data+",result"+result);
//				respModel = FastJsonTools.getJson(result, BaseResult.class);
//				EntityUtils.consume(httpEntity);
//			} else {
//				log.error("??????????????????. reqURL=" + reqURL+" ,statusCode="+statusCode + " ,data=" + data);
//			}
//		} catch (Exception e) {
//			log.error("reqURL=" + reqURL + " ,data=" + data, e);
//			throw e;
//		} finally {
//			closeHttp(response, httpClient);
//		}
//		return respModel;
//	}
//
//	private static void closeHttp(CloseableHttpResponse response, CloseableHttpClient httpClient) {
//		try {
//			if (response != null) {
//				response.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (httpClient != null) {
//				httpClient.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * ??????HTTP_POST??????
//	 * 
//	 * @param reqURL
//	 *            ????????????
//	 * @param paramsMap
//	 *            ????????????
//	 * @return ????????????????????????
//	 * @throws Exception
//	 */
//	public static BaseResult sendPostRequestByAjax(String reqURL, Map<String, String> paramsMap) throws Exception {
//		log.debug("?????????????????????" + reqURL);
//		CloseableHttpClient httpClient = null;
//		CloseableHttpResponse response = null;
//		BaseResult respModel = null;
//		try {
//			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // ??????????????????
//			if (paramsMap != null && paramsMap.size() > 0) {
//				for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
//					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//				}
//			}
//			HttpPost httpPost = new HttpPost(reqURL);
//			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
//			httpPost.setConfig(doRequestConfig());
//
//			httpClient = HttpClients.createDefault();
//			response = httpClient.execute(httpPost);
//			HttpEntity httpEntity = response.getEntity();
//			 int statusCode = response.getStatusLine().getStatusCode();
//			
//			if (statusCode== HttpStatus.SC_OK &&  httpEntity != null) {
//				String result = EntityUtils.toString(httpEntity, "UTF-8");
//				log.info("url ="+reqURL+",data="+paramsMap+",result="+result);
//				respModel = FastJsonTools.getJson(result, BaseResult.class);
//				EntityUtils.consume(httpEntity);
//			} else {
//				log.error("??????????????????. reqURL=" + reqURL+" ,statusCode="+statusCode + " ,paramsMap=" );
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("reqURL=" + reqURL + " ,paramsMap=" , e);
//			throw e;
//		} finally {
//			closeHttp(response, httpClient);
//		}
//		return respModel;
//	}
//
//	
//	/**
//	 * ?????????????????????
//	 * 
//	 * @return
//	 */
//	private static RequestConfig doRequestConfig() {
//		RequestConfig requestConfig = RequestConfig.custom()//
//				.setConnectTimeout(REQUEST_TIMEOUT)// ????????????????????????
//				.setConnectionRequestTimeout(REQUEST_TIMEOUT)//
//				.setSocketTimeout(REQUEST_TIMEOUT)// ???????????????????????????????????????????????????
//													// ?????????????????????????????????????????????????????????????????????????????????????????????
//				.build();//
//		return requestConfig;
//	}
//
//}