package com.achilles.wild.server.tool.generate.unique;

import org.junit.Test;

public class GenerateUniqueUtilTest {

	@Test
	public void getUuIdTest(){
		for (int i = 0; i < 10; i++) {
			System.out.println(GenerateUniqueUtil.getUuId());
		}
		
	}
}
