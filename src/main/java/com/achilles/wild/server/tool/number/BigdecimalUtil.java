package com.achilles.wild.server.tool.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigdecimalUtil {

	public static void main(String[] args) {
		System.out.println(getComparePercent(new BigDecimal("2"),new BigDecimal("2")));
	}
	
	public static String getComparePercent(BigDecimal base,BigDecimal compare){
		if (base.compareTo(compare)==0) {
			return "0%";
		}
		DecimalFormat df = new DecimalFormat("0.00%");
		BigDecimal margin = compare.subtract(base);
		String percent=df.format(margin.divide(base));
		return percent;
	}
}
