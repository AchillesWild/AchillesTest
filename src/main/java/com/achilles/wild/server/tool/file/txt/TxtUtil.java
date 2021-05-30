package com.achilles.wild.server.tool.file.txt;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Txt文档操作
 * @author Pang
 *
 */
public class TxtUtil {

	/**
	 * 读取文件
	 * @param path
	 * @return
	 */
	public static String read(String path){
        File filename = new File(path); 
        InputStreamReader reader = null;
		try {
			try {
				String code = getFileEncode(path);
			    if("asci".equals(code)){
			        code = "GBK";
			      }
				reader = new InputStreamReader(new FileInputStream(filename),code);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(reader); 
        String line = null;  
        StringBuffer sb = new StringBuffer();
        try {
			while ((line = br.readLine())!=null) {  
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/** 
	 * 按照规定行长度写文件
	 * @param path
	 * @param content 内容
	 * @param eachLength 每一行的长度
	 * @return
	 */
	public static String writeByLineLength(String path,String content,int eachLength){
	    File writename = new File(path);
        try {
			writename.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(writename));
		} catch (IOException e) {
			e.printStackTrace();
		} 
        try {
        	int maxLength = content.length();
        	int lineNum = maxLength/eachLength+1;
        	for (int i = 0; i < lineNum; i++) {
        		if (lineNum!=i+1) {
        			bw.write(content, i*eachLength, eachLength);
				}else{
					bw.write(content, i*eachLength, content.length()-eachLength*(lineNum-1));
				}
        		bw.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
        	bw.flush();
        	bw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return path;
	}
	
	/**
	 * 写入文件一行
	 * @param path
	 * @return
	 */
	public static String write(String path,String content){
	    File writename = new File(path); // 相对路径，如果没有则要建立一个新的output.txt文件  
        try {
			writename.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(writename));
		} catch (IOException e) {
			e.printStackTrace();
		}  
        try {
			out.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			out.flush();
			out.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return path;
	}
	
	/**
	 * 获取文件编码集
	 * @param path
	 * @return
	 */
	private static String getFileEncode(String path) {
		String charset = "asci";
		byte[] first3Bytes = new byte[3];
		BufferedInputStream bis = null;
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(path));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "Unicode";// UTF-16LE
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "Unicode";// UTF-16BE
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				while ((read = bis.read()) != -1) {
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)
							// 双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ex) {
				}
			}
		}
		return charset;
	}
	
}
