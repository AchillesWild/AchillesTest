package com.achilles.wild.server.tool.file.excel;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelWriteUtilTest {

	@Test
	public void writeTest(){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 3; i++) {
//			int update = update("INSERT INTO wl_lcs.lcs_dict (dic_group, dic_key, dic_val, content, del_flag, dic_sort) VALUES "
//					+ "('red_packet', 'if_generate_red_packet_201804', 1, '是否开启红包201804；1开启，0关闭', 0, 0)");
			Map<String,Object> params = new HashMap<String,Object>();
/*			params.put("itemId", "1889"+i);
			params.put("publishDate", "2018-04-24 09:33:20");
			params.put("publishDateEnd", "2018-04-28 09:33:20");
			params.put("itemCount", i+3);
			params.put("maxUserCount", i+107);*/


			try {
				params.put(new String("商品id".getBytes(), "GBK"), "1880143889"+i);
				params.put("投放开始时间", "2018-04-24 00:00:00");
				params.put("投放结束时间", "2018-04-29 09:33:20");
				params.put("权益投放数量", i+3);
				params.put("最大参与人数", i+107);
				dataList.add(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		write("C://Users//Administrator//Desktop//JAVA生成EXCEL测试.xls",dataList);
		String file_name = null;
		try {
			file_name = new String("现货商品".getBytes("UTF-8"),"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(ExcelWriteUtil.write("/Users/achilleswild/Desktop/"+file_name+".xls",dataList));

		//String md5 = DigestUtils.md5Hex(file.getBytes());
	
	}
}
