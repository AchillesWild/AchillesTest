package com.achilles.wild.server.tool.file.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadUtil {

	
	/**读出excel指定列的值
	 * @param url 文件路径
	 * @param startLineNum 起始行，直接用行号
	 * @param columns 要读的列，A列为1
	 */
	public static List<Map<String,Object>> readExcelForList(String url,int startLineNum,Integer[] columns){
		Set<Integer> columnSet = new HashSet<Integer>();
		for (Integer column : columns) {
			columnSet.add(column);
		}
		List<Map<String,Object>> mapList = null;
		Workbook book = createWorkbook(url);
		 if (book instanceof XSSFWorkbook) {
			 XSSFWorkbook books= (XSSFWorkbook) book;
			 mapList = readXSSForList(books,startLineNum,columnSet);
		 }else if(book instanceof HSSFWorkbook){
			 HSSFWorkbook books= (HSSFWorkbook) book;
			 mapList = readHSSForList(books,startLineNum,columnSet);
		 }
		return mapList;
	}
	
	
	private static List<Map<String,Object>> readHSSForList(HSSFWorkbook book,int startLineNum,Set<Integer> columnSet) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			HSSFRow row = (HSSFRow) sheet.getRow(i);
			Map<String,Object> map = new HashMap<String,Object>();
			int column = 0;
			for (int j = 0; j < row.getLastCellNum(); j++) {
				column=j+1;
				if (!columnSet.contains(column)) {
					continue;
				}
				String val = row.cellIterator().next().getRow().getCell(j).toString().trim();
				map.put(column+"", val);
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	private static List<Map<String,Object>> readXSSForList(XSSFWorkbook book,int startLineNum,Set<Integer> columnSet) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			Map<String,Object> map = new HashMap<String,Object>();
			int column = 0;
			for (Integer j = 0; j < row.getLastCellNum(); j++) {
				column=j+1;
				if (!columnSet.contains(column)) {
					continue;
				}
				String val = row.cellIterator().next().getRow().getCell(j).toString().trim();
				map.put(column+"", val);
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 读取excel一列数据为set
	 * @param url 文件路径
	 * @param startLineNum 起始行，直接用行号
	 * @param column列号，A列为1
	 * @return
	 */
	public static Set<String> readExcelForSet(String url,int startLineNum,int column){
		Set<String> set = null;
		Workbook book = createWorkbook(url);
		 if (book instanceof XSSFWorkbook) {
			 XSSFWorkbook books= (XSSFWorkbook) book;
			 set = readOneXSSForSet(books,startLineNum,column);
		 }else if(book instanceof HSSFWorkbook){
			 HSSFWorkbook books= (HSSFWorkbook) book;
			 set = readOneHSSForSet(books,startLineNum,column);
		 }
		return set;
	}
	
	private static Set<String> readOneHSSForSet(HSSFWorkbook book,int startLineNum,int column) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		Set<String> set = new HashSet<String>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			HSSFRow row = (HSSFRow) sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (column==j+1) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					if (str==null) {
						continue;
					}else{
						str = str.replaceAll("\\s", "");
					}
					if ("".equals(str)) {
						continue;
					}
					set.add(str);
				}
			}
		}
		return set;
	}
	
	private static Set<String> readOneXSSForSet(XSSFWorkbook book,int startLineNum,int column) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		Set<String> set = new HashSet<String>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (column==j+1) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					if (str==null) {
						continue;
					}else{
						str = str.replaceAll("\\s", "");
					}
					if ("".equals(str)) {
						continue;
					}
					set.add(str);
				}
			}
		}
		return set;
	}
	
	/**读出excel某几列的值，生成对应数据库的键值对 [{mobile=CRM18021076721, remark=邱成艳}, {mobile=CRM13107757818, remark=陈小明}]
	 * 获取excel某一列的值
	 * @param url 文件路径
	 * @param startLineNum 起始行，直接用行号
	 * @param columnToFieldMap 键为列号，A列为1，值对应数据库的字段名称
	 */
	public static List<Map<String,Object>> readExcelForList(String url,int startLineNum,Map<Integer,String> columnToFieldMap){
		List<Map<String,Object>> mapList = null;
		Workbook book = createWorkbook(url);
		 if (book instanceof XSSFWorkbook) {
			 XSSFWorkbook books= (XSSFWorkbook) book;
			 mapList = readXSSForList(books,startLineNum,columnToFieldMap);
		 }else if(book instanceof HSSFWorkbook){
			 HSSFWorkbook books= (HSSFWorkbook) book;
			 mapList = readHSSForList(books,startLineNum,columnToFieldMap);
		 }
		return mapList;
	}

	private static List<Map<String,Object>> readHSSForList(HSSFWorkbook book,int startLineNum,Map<Integer,String> columnToFieldMap) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			HSSFRow row = (HSSFRow) sheet.getRow(i);
			Map<String,Object> map = new HashMap<String,Object>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String field = columnToFieldMap.get(j+1);
				if (field!=null) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					if ("".equals(str)) {
						str=null;
					}
					map.put(field, str);
				}
			}
			mapList.add(map);
		}
		return mapList;
	}
	
	private static List<Map<String,Object>> readXSSForList(XSSFWorkbook book,int startLineNum,Map<Integer,String> columnToFieldMap) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			Map<String,Object> map = new HashMap<String,Object>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String field = columnToFieldMap.get(j+1);
				if (field!=null) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					if ("".equals(str)) {
						str=null;
					}
					map.put(field, str);
				}
			}
			mapList.add(map);
		}
		return mapList;
	}

	public static Workbook createWorkbook(String filePath) {
		File file = new File(filePath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Workbook book = null;
		try {
			try {
				book = WorkbookFactory.create(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return book;
	}

	/**
	 * 读取整个excel数据
	 * @param url
	 * @return
	 */
	public static String readExcelAll(String url){
		InputStream ips = null;
		try {
			ips = new FileInputStream(url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(ips);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExcelExtractor ex = new ExcelExtractor(wb);
		ex.setFormulasNotResults(true);
		ex.setIncludeSheetNames(false);
	  return ex.getText();
	}

}
