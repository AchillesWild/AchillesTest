package com.achilles.wild.server.tool.file.word;

import org.junit.Test;


public class WordUtilTest {

	/**
	 * docx file is ok
	 */
	@Test
	public void wordReadTest() {
//		String path = "C:\\Users\\Achilles\\Desktop\\resume.doc";
//		System.out.println(WordUtil.readDoc(path));
		String path = "C:\\Users\\Achilles\\Desktop\\resume1.docx";
		System.out.println(WordUtil.read(path));

	}

}