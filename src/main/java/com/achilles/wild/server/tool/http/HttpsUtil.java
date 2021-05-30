package com.achilles.wild.server.tool.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * https请求工具类
 */
public class HttpsUtil {

	private SSLSocketFactory _sslSocketFactory = null;
	private NullHostNameVerifier _hostNameVerifier = null;
	private static HttpsUtil instance = new HttpsUtil();

	// 构造函数
	private HttpsUtil() {
		try {
			_hostNameVerifier = new NullHostNameVerifier();
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new NullX509TrustManager() }, new SecureRandom());

			_sslSocketFactory = context.getSocketFactory();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
//		String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=yukuym%20&oq=yukuym";
		String url = "https://crm.9f.cn/interface/queryCouponList";
//		String url = "https://cn.bing.com/search?q=%E7%A6%8F%E5%85%8B%E6%96%AF&qs=AS&pq=fu%5d&sc=8-3&cvid=C98404C6C5614895A6E4F3BEF8479220&FORM=QBLH&sp=1";
		String requestStr="{\"requestEntity\":{\"userUuid\":\"4A58989759D844EDB91C02B86D4BB02E\",\"status\":\"1\",\"memberId\":\"1001918267\",\"prePayAmount\":4568},\"type\":\"synchTradSettlement\"}";
		System.out.println(request(url, requestStr,"POST"));
//		System.out.println(request(url, "老虎","GET"));
	}
	
	/**
	 * 发送报文，返回结果，返回""代表异常或请求失败，查看log
	 * @param url
	 * @param xmlText
	 * @param type
	 * @return
	 */
	public static String request(String url, String postData,String requestType) {
		HttpsURLConnection conn = null;
		DataOutputStream dos = null;
		BufferedReader br = null;
		try {
			byte[] data = postData.getBytes("UTF-8");
			trustAllHosts();
			// 创建一个HttpsURLConnection连接
			conn = (HttpsURLConnection) (new URL(url)).openConnection();
			// 设置连接超时时间
			conn.setConnectTimeout(8000);
			// post请求必须设置允许输出
			conn.setDoOutput(true);
			// post请求不能使用缓存
			conn.setUseCaches(false);
			// 设置post方式请求
			conn.setRequestMethod(requestType);
			conn.setInstanceFollowRedirects(true);
			// 配置请求Content-Type
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			// 开始连接,注意以上的所有设置必须要在connect之前完成
			conn.connect();
			// 发送请求参数,将OutputStream封装成DataOutputStream
			dos = new DataOutputStream(conn.getOutputStream());
			dos.write(data);
			dos.flush();

			// 请求成功
			int returnCode = conn.getResponseCode();
			if (returnCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				StringBuffer strBuf = new StringBuffer();
				String line = null;
				while ((line = br.readLine())!=null) {
					strBuf.append(line);
					System.out.println(line);
				}
//				System.out.println(strBuf.toString());
			} else {
				System.out.println("return code: " + returnCode + ", postData: " + postData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (dos != null) {
				try {
					dos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/*
	 * 信任所有主机
	 */
	private static void trustAllHosts() {
		HttpsURLConnection.setDefaultHostnameVerifier(instance._hostNameVerifier);
		HttpsURLConnection.setDefaultSSLSocketFactory(instance._sslSocketFactory);
	}

}

class NullHostNameVerifier implements HostnameVerifier {
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}

class NullX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
	}

	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
