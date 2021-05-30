package com.achilles.wild.server.tool.http;

import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;


public class HttpGetUtilTest {
	
	@Test
	public void getTest() {
		try {
			System.out.println(URLEncoder.encode("http://68377e0f.ngrok.io/house.do","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Map<String,String> params=new TreeMap<String,String>();
//		params.put("userUuid","4A58989759D844EDB91C02B86D4BB02E");
//		System.out.println(HttpGetUtil.get("https://www.csdn.net/", params));


	}

	@Test
	public void detailPageTest() {
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","dce1c5be-3ea0-01257037-25aa4a11");
		params.put("status","0");
//		params.put("memberId","102234094");
		params.put("memberId","1001529497");
		params.put("pageNum","1");
		params.put("token","CDA8EF1019014D56A6C3D668D4E1F901");
		params.put("sign", MD5Util.get("9F|1001529497|1|0|dce1c5be-3ea0-01257037-25aa4a11|LCS"));//注意顺序TreeMap
//		System.out.println(HttpGetUtil.get("http://jflcs.nbeebank.com/customer/detailPage.do", params));
		System.out.println(HttpPostUtil.post("http://jflcs.nbeebank.com/customer/detailPage.do", params));
	}
	
	@Test
	public void getCouponTest() {
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","4A58989759D844EDB91C02B86D4BB02E");
		params.put("status","1");
		params.put("memberId","1001918267");
		params.put("pageNum","1");
		params.put("token","3A63E11235C747DCB6D64D847160654F");
		params.put("sign",MD5Util.get("9F|1001918267|1|1|4A58989759D844EDB91C02B86D4BB02E|LCS"));//注意顺序TreeMap
		System.out.println(HttpGetUtil.get("http://jflcs.nbeebank.com/customer/getCoupon.do", params,null));
	}
	
	@Test
	public void posterListTest() {
		Map<String,String> params=new TreeMap<String,String>();
		params.put("pageNo","1");
		params.put("pageSize","9");
		params.put("userUuid","1");
		params.put("token","9");
//		System.out.println(HttpGetUtil.get("http://localhost:80/licaishi/lcsPoster/posterList.do", params));
		System.out.println(HttpGetUtil.get("http://192.168.90.210/licaishi/lcsPoster/posterList.do", params,null));
//		System.out.println(HttpGetUtil.get("http://192.168.90.210/licaishi/lcsPoster/posterList.do?pageNo=1&pageSize=9&userUuid=232&token=23", params));
	}
	
//	 public void httpGet() throws ClientProtocolException, IOException{   				
//		 HttpClient httpClient = new HttpClient();
////		 GetMethod getMethod = new GetMethod("http://localhost:8080/licaishi_admin/lcsCommissionRecord/fastWithdraw.do?userUuid=4CD1C6124AC54F248155AD29CC5DD09C&flowNo=11111614&status=1&pageNo=1"
////			 		+ "&pageSize=15");
////		 GetMethod getMethod = new GetMethod("http://localhost:8080/licaishi/user/recordAct.do?userUuid=4CD1C6124AC54F248155AD29CC5DD09C&funcType=2285739");
////		 GetMethod getMethod = new GetMethod("http://localhost:8080/licaishi/customer/getCoupon.do?userUuid=4CD1C6124AC54F248155AD29CC5DD09C&memberId=1000025520&status=0&pageNo=1"
////			 		+ "&pageSize=15");		 
//		 GetMethod getMethod = new GetMethod("http://www.foxnews.com/");
//		 getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
//		 httpClient.executeMethod(getMethod);
//		 byte[] responseBody = getMethod.getResponseBody();
//		 String  responseMsg = new String(responseBody);
//	    System.out.println(responseMsg);           
//	 }
	

}
