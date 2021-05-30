package com.achilles.wild.server.tool.http;

import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HttpPostUtilTest {

	@Test
	public void pageSearchClientTest(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("invitationCode","CRM17317900756");
		params.put("page","1");
		params.put("size","100");
//		params.put("id","1001974116");
		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/pageSearchClient", params);
		System.out.println(retStr);
//		JSONObject jsonObj = JSON.parseObject(retStr);
//		if (jsonObj.getBooleanValue("success")) {
//			System.out.println(jsonObj.getJSONArray("content"));
//		}
	}
	
//	public List<Map<String,Object>> getAllClientByInviteCode(int page,String inviteCode,List<Map<String,Object>> allClientList){
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("invitationCode",inviteCode);
//		params.put("page",page);
//		params.put("size","100");
//		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/pageSearchClient", params);
//		System.out.println(retStr);
//		JSONObject jsonObj = JSON.parseObject(retStr);
//		if (!jsonObj.getBooleanValue("success")) {
//			return allClientList;;
//		}
//		JSONArray clientList = jsonObj.getJSONArray("content").toJavaList(clazz);
//		if(clientList==null || clientList.size()==0){
//			return allClientList;
//		}	
//		allClientList.addAll(clientList);
//		if (page<lastPage) {
//			getAllClientByInviteCode(page+1,inviteCode,allClientList);
//		}
//		return allClientList;
//	}
	
	@Test
	public void queryOrderInfoTest(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("memberId","1002113200");
		params.put("orderNo","APP201902220740484102492224");
//		params.put("startTime","2018-10-13 00:00:00");
//		params.put("endTime","2018-12-13 23:59:59");
//		params.put("orderStatus","0");
		params.put("page","1");
		params.put("size","10000");
		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/queryOrderInfo", params);
		System.out.println(retStr);
	}

	@Test
	public void detailPageTest(){
		Map<String,String> params=new HashMap<String,String>();
		params.put("memberId","1001414318");
		String retStr = HttpPostUtil.post("https://crm.9f.cn/interface/detailPage", params);
		System.out.println(retStr);
	}

	@Test
	public void myPageTest(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("invitationCode","JW113810215554");
//		params.put("id","106088");
		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/myPage", params);
		System.out.println(retStr);
	}
	
	@Test
	public void myMemberUOrderTest(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("invitationCode","JW113810215554");
		params.put("orderStatus","0");
		params.put("page","1");
		params.put("size","1000");
		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/myMemberUOrder", params);
		System.out.println(retStr);
	}
	
	@Test
	public void queryCouponListTest(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","4A58989759D844EDB91C02B86D4BB02E");
		params.put("status","1");
		params.put("memberId","1001918267");
		params.put("token","3A63E11235C747DCB6D64D847160654F");
		params.put("sign", MD5Util.get("9F|1001918267|1|4A58989759D844EDB91C02B86D4BB02E|LCS"));//注意顺序TreeMap
		System.out.println(HttpPostUtil.post("http://jflcs.nbeebank.com/customer/getCoupon.do", params));
		
//		Map<String, String> params = new HashMap<>();
//		params.put("memberId", "1001918267");
//		params.put("page", "1");
//		params.put("size", "1000");
//		System.out.println(HttpPostUtil.post("https://crm.9f.cn/interface/queryCouponList", params));
	}
	
	@Test
	public void joinActivity(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","F0276AE0CE0E45A2B67606347E4473E7");
		params.put("token","7F78793BB78B458C8E4194CD83AC412E3");
		System.out.println(HttpPostUtil.post("http://localhost/licaishi/user/register/join/activity.do", params));
//		System.out.println(HttpPostUtil.post("http://192.168.90.210/licaishi/user/register/join/activity.do", params));
		
//		Map<String, String> params = new HashMap<>();
//		params.put("memberId", "1001918267");
//		params.put("page", "1");
//		params.put("size", "1000");
//		System.out.println(HttpPostUtil.post("https://crm.9f.cn/interface/queryCouponList", params));
	}
	
	@Test
	public void ifActivityStart(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","5A8EADC2CC43486E94C83BD7EF2877B6");
		params.put("token","474D24DB7C544B9DB7584D844F7A67F3");
//		System.out.println(HttpPostUtil.post("http://192.168.90.210/licaishi/reward/ifActivityStart.do", params));
		System.out.println(HttpPostUtil.post("http://192.168.90.210/licaishi/user/register/join/activity.do", params));
		
//		Map<String, String> params = new HashMap<>();
//		params.put("memberId", "1001918267");
//		params.put("page", "1");
//		params.put("size", "1000");
//		System.out.println(HttpPostUtil.post("https://crm.9f.cn/interface/queryCouponList", params));
	}	
	
	@Test
	public void postTest(){
		Map<String,Object> params=new HashMap<String,Object>();
//		String retStr = HttpPostUtil.postJson("http://lcsadmin.nbeebank.com/", params);
		String retStr = HttpPostUtil.postJson("https://www.baidu.com/", params);
		System.out.println(retStr);
	}
	
	@Test
	public void searchApplyRecordTest(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("invitationCode","CRM18701514648");
//		params.put("orderStatus","0");
//		params.put("page","1");
//		params.put("size","1000");
		params.put("channel","jflcs");
//		String retStr = HttpPostUtil.postJson("http://60.205.115.2/crm-web/interface/searchApplyRecord", params);
		String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/searchApplyRecord", params);
		System.out.println(retStr);
	}
	
	@Test
	public void getTopRank(){//CRM18600000000
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","549BE8FCC601433784662B1D746AEDC8");
		params.put("token","BC057CD933C54BF4A8FC4A6C0078BB56");
//		params.put("sign",MD5Utils.encodeByMd5AndSalt("9F|1|9|549BE8FCC601433784662B1D746AEDC8|LCS"));//注意顺序TreeMap
//		String retStr = HttpPostUtil.post("http://localhost/licaishi/reward/ifSeeRank.do", params);
//		String retStr = HttpPostUtil.post("http://10.15.21.249/licaishi/reward/getOwnRank.do", params);
		String retStr = HttpPostUtil.post("http://10.15.21.249/licaishi/reward/getTopRank.do", params);
		System.out.println(retStr);
	}
	
	@Test
	public void monthCommListTest(){//CRM18600000000
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","4CD1C6124AC54F248155AD29CC5DD09C");
		String retStr = HttpPostUtil.post("http://localhost/licaishi/reward/monthCommList.do", params);
		System.out.println(retStr);
	}
	
	@Test
	public void monthCommList(){//CRM18600000000
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","A7E7F11995D643B699C1BC6EE7E28C7B");
		params.put("token","82752863C3F840A98CC7EB0BF24CB320");
		params.put("pageNo","1");
		params.put("pageSize","15");
		String retStr = HttpPostUtil.post("http://localhost/licaishi/reward/monthCommList.do", params);
		System.out.println(retStr);
	}
	
	@Test
	public void monthCommList2(){//CRM18600000000
		Map<String,String> params=new TreeMap<String,String>();
		params.put("userUuid","60E27BDF65F042CC93BE594B77C9D6FE");
		params.put("token","740D01328B59409CB3A94CBE8215CEA1");
		params.put("pageNo","1");
		params.put("pageSize","15");
		String retStr = HttpPostUtil.post("http://jflcs.nbeebank.com/reward/monthCommList.do", params);
		System.out.println(retStr);
	}
	
	@Test
	public void posterListTest(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("pageNo","1");
		params.put("pageSize","100");
		params.put("userUuid","60E27BDF65F042CC93BE594B77C9D6FE");
		params.put("token","862600B09C1E4B71A4869271C870BDF2");
//		System.out.println(HttpPostUtil.post("http://10.15.21.249/licaishi/lcsPoster/posterList.do", params));
		System.out.println(HttpPostUtil.post("http://localhost/licaishi/lcsPoster/posterList.do", params));
	}
	
	@Test
	public void typePosterListTest(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("pageNo","1");
		params.put("pageSize","100");
		params.put("type","1");
		params.put("userUuid","60E27BDF65F042CC93BE594B77C9D6FE");
		params.put("token","862600B09C1E4B71A4869271C870BDF2");
//		System.out.println(HttpPostUtil.post("http://10.15.21.249/licaishi/lcsPoster/posterList.do", params));
		System.out.println(HttpPostUtil.post("http://10.11.1.57/licaishi/lcsPoster/typePosterList.do", params));
	}
	
	@Test
	public void detailDataTest(){
		Map<String,String> params=new TreeMap<String,String>();
		params.put("memberId","366");
		params.put("userUuid","4CD1C6124AC54F248155AD29CC5DD09C");
		params.put("token","41A6274D5CB84C7EB9BA3467DA75126A");
		params.put("sign",MD5Util.get("9F|366|4CD1C6124AC54F248155AD29CC5DD09C|LCS"));//注意顺序TreeMap
		System.out.println(HttpPostUtil.post("http://10.15.21.249/licaishi/partner/detailData.do", params));
	}
}


