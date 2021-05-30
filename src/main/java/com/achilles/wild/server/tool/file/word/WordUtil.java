package com.achilles.wild.server.tool.file.word;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.*;

/**
 * 操作word文档
 * @author Achil
 *
 */
public class WordUtil {
	
	
	public static String read(String path) {
		if (path.lastIndexOf(".docx")!=-1) {
			return readDocx(path);
		}
		return readDoc(path);
	}
	
	/**
	 * 读docx  2007
	 * @param path
	 * @return
	 */
	public static String readDocx(String path) {
		OPCPackage opcPackage = null;
		try {
			opcPackage = POIXMLDocument.openPackage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		POIXMLTextExtractor extractor = null;
		try {
			extractor = new XWPFWordExtractor(opcPackage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		}
		return extractor.getText();
	}
	
	/**
	 * 读doc  2003
	 * @param path
	 * @return
	 */	
	public static String readDoc(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		WordExtractor ex = null;
		try {
			ex = new WordExtractor(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ex.getText();
	}
	

}
