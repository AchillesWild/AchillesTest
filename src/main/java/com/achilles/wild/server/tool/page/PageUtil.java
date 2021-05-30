package com.achilles.wild.server.tool.page;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class PageUtil {
	
	public static void main(String[] args) {
		List list = new ArrayList();
		int total =31;
		for (int i = 1; i <= total; i++) {
			list.add(i);
		}
//		int pageSize = 20;
//		int totalPage=getTotalPage(pageSize, 31);
//		System.out.println(totalPage);
//		for (int m=1;m<=totalPage;m++){
//			System.out.println(getSubList(m, pageSize, list));
//		}
	List<List> listList =	Lists.partition(list, 5);
	for (List subList : listList) {
		System.out.println(subList);
	   }
	}

	/**
	 * 获取分页后的每页数据集合
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static List<List> getPageDataList(List list, int pageSize){
		return Lists.partition(list, pageSize);
	}
	
	
	/**
	 * 获取总页码数
	 * @param pageSize
	 * @param total
	 * @return
	 */
	public static int getTotalPage(int pageSize, int total){
		int totalPage = total/pageSize;
		if ( total%pageSize!=0){
			totalPage+=1;
		}
		return totalPage;
	}

	/**
	 * 集合分页
	 * @param pageNo
	 * @param pageSize
	 * @param list
	 * @return
	 */
	public static List getSubList(int pageNo, int pageSize, List list) {
		if (list.size()==0) {
			return list;
		}
		int size = list.size();
		int pageCount=size/pageSize;
		int fromIndex = pageSize * (pageNo - 1);
		int toIndex = fromIndex + pageSize;
		if (toIndex >= size) {
			toIndex = size;
		}
		if(pageNo>pageCount+1){
			fromIndex=0;
			toIndex=0;
		}
		return list.subList(fromIndex,toIndex);
	}




	public static List getPageData(Integer pageNo, Integer pageSize, List list) {
		if (list.size()==0) {
			return list;
		}
		if ((pageNo-1)*pageSize>list.size()) {//第一页到倒数第二页的总数据大于总数据list
			return new ArrayList();
		}
		if (list.size()-(pageNo-1)*pageSize<pageSize) {//剩余数据小于一页的数据
			list = list.subList((pageNo-1)*pageSize, list.size());
		}else if(list.size()-(pageNo-1)*pageSize>pageSize || list.size()-(pageNo-1)*pageSize==pageSize){//剩余数据大于一页的数据||剩余数据等于一页的数据
			list = list.subList((pageNo-1)*pageSize, pageNo*pageSize);
		}
		return list;
	}



	
	
	/**
	 * 获取分页数据
	 * @param pageNo
	 * @param pageSize
	 * @param list
	 * @return
	 */
	public static List getPageData2(Integer pageNo, Integer pageSize, List list) {
		if (list.size()==0) {
			return list;
		}
		if (list.size()<=pageSize && pageNo==1) {
			list = list.subList(0, list.size());
		}else if (list.size()<=pageSize && pageNo>1) {
			list =  new ArrayList();
		}else if (list.size()>pageSize&&pageNo==1) {
			list = list.subList(0, pageSize);
		}
		int index = (pageNo-1) * pageSize;
	    if(list.size()-index>0 && list.size()-pageNo*pageSize<=0) {
	    	list = list.subList(index, list.size());
		}else if (list.size()-index>0 && list.size()-pageNo*pageSize>0) {
			list = list.subList(index, index+pageSize);
		}else if (list.size()-index<=0) {
			list = new ArrayList();
		}
		return list;
	}
}
