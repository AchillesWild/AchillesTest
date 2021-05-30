package com.achilles.wild.server.tool.file.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

public class ExcelReadUtil2 {
	/**
	 * 读取一个excel文件的内容
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// extractor();
		 String url = "C://Users//Administrator//Desktop//a.xlsx";
		readExcel(url,2,2);
	}

	/**
	 * 获取excel某一列的值
	 * @param url 文件路径
	 * @param startLineNum 起始行，直接用行号
	 * @param columnNum A列为1，
	 */
	public static void readExcel(String url,int startLineNum,int columnNum){
		Workbook book = createWorkbook(url);
		 if (book instanceof XSSFWorkbook) {
			 XSSFWorkbook books= (XSSFWorkbook) book;
			 readXSS(books,startLineNum,columnNum);
		 }else if(book instanceof HSSFWorkbook){
			 HSSFWorkbook books= (HSSFWorkbook) book;
			 readHSS(books,startLineNum,columnNum);
		 }
	}

	private static void readHSS(HSSFWorkbook book,int startLineNum,int columnNum) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			System.out.println("行号-----------------------------------------------" + lineNum);
			HSSFRow row = (HSSFRow) sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (columnNum==j+1) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					System.out.println(str);				
				}
			}
		}
	}
	
	private static void readXSS(XSSFWorkbook book,int startLineNum,int columnNum) {
		Sheet sheet = book.getSheetAt(0);// 第一个页签
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			int lineNum = i+1;
			if (lineNum<startLineNum) {
				continue;
			}
			System.out.println("行号*********************************************" + lineNum);
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (columnNum==j+1) {
					String str = row.cellIterator().next().getRow().getCell(j).toString().trim();
					if ("".equals(str)) {
						str=null;
					}
					System.out.println(str);				
				}
			}
		}
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

	// 直接抽取excel中的数据
	public static void extractor(String url) throws Exception {
		InputStream ips = new FileInputStream(url);
		HSSFWorkbook wb = new HSSFWorkbook(ips);
		ExcelExtractor ex = new ExcelExtractor(wb);
		ex.setFormulasNotResults(true);
		ex.setIncludeSheetNames(false);
		String text = ex.getText();
		System.out.println(text);
	}

}
