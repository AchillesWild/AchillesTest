package com.achilles.wild.server.tool.file.txt;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Txt文档操作
 * 
 * @author Pang
 *
 */
public class TxtUtils {



	private static String getEncode(int flag1, int flag2, int flag3) {
		String encode = "";
		// txt文件的开头会多出几个字节，分别是FF、FE（Unicode）,
		// FE、FF（Unicode big endian）,EF、BB、BF（UTF-8）
		if (flag1 == 255 && flag2 == 254) {
			encode = "Unicode";
		} else if (flag1 == 254 && flag2 == 255) {
			encode = "UTF-16";
		} else if (flag1 == 239 && flag2 == 187 && flag3 == 191) {
			encode = "UTF8";
		} else {
			encode = "asci";// ASCII码
		}
		return encode;
	}
}
