package com.achilles.wild.server.tool.file.txt;

import org.junit.Test;

public class TxtUtilTest {


	
	@Test
	public void reabByTest() {
		String path = "C://Users//Administrator//Desktop//项目.txt"; // 
//		String  path = "C://Users//Achil//Desktop//1.txt"; // 
		String writePath = "C://Users//Administrator//Desktop//生成.txt"; //
		String content = TxtUtil.read(path);
		System.out.println(TxtUtil.write(writePath,content));
//		System.out.println(TxtUtil.writeByLineLength(writePath, content, 50));
	}
	
	
	@Test
	public void writeByLengthTest() {
		String  path = "C://Users//Administrator//Desktop//1.txt";
		System.out.println(TxtUtil.writeByLineLength(path, "1,3", 3));
	}
}
