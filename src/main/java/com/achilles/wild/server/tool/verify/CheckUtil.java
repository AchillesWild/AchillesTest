package com.achilles.wild.server.tool.verify;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验
 * @author Pang
 *
 */
public class CheckUtil {

	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String orginal="56i5";
		System.out.println(containNumber(orginal));
		System.out.println(containLetter(orginal));
	}

	public static boolean containNumber(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches()) {
			flag = true;
		}
		return flag;
	}

	public static boolean containLetter(String content) {
		String regex=".*[a-zA-Z]+.*";
		Matcher m=Pattern.compile(regex).matcher(content);
		return m.matches();
	}


	/**
	 * 是否0，  包含小数
	 * @param orginal
	 * @return
	 */
	public static boolean isZero(String orginal) {
		return isMatch(orginal,"^[0]+[.]?[0]+$")||isMatch(orginal, "^[0]+$");
	}
	
	/**
	 * 验证邮箱地址是否正确
	 */
	public static boolean isEmail(String email) {
		return isMatch(email, "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}
	
	/**
	 * 是否是 数字
	 * <p>
	 * 03214850108098978
	 * </p>
	 * @param orginal
	 * @return
	 */
	public static boolean isNumber(String orginal) {
		return isMatch(orginal,"\\d+$");
	}
	
	/**
	 * 是否含有汉字，中文
	 * @param orginal
	 * @return
	 */
	public static boolean isContainChinese(String orginal) {
		return exist(orginal,"[\\u4e00-\\u9fa5]");
	}
	
	/**
	 * 非负整数（正整数 和 0）
	 * @param orginal
	 * @return
	 */
	public static boolean isNotNegativeInteger(String orginal) {
		return isMatch(orginal,"^\\d+$" );
	}
	
	/**
	 * 最多可以有两位小数的正数
	 * @param orginal
	 * @return
	 */
	public static boolean isPositiveNum2(String orginal) {
		return isMatch(orginal,"^[+]?\\d*([.]\\d{0,2})?$");
	}
	

	/**
	 * 正整数，（不含0）或 正浮点数(必须带点)，（不含0.0）
	 * @param orginal
	 * @return
	 */
	public static boolean isPositiveNum(String orginal) {
		return isPositiveInteger(orginal)||isPositiveDecimal(orginal);
	}
	
	/**
	 * 正整数，（不含0）
	 */
	public static boolean isPositiveInteger(String orginal) {
		return isMatch(orginal,"^\\+{0,1}[1-9]\\d*");
	}

	/**
	 * 负整数，（不含0）
	 */
	public static boolean isNegativeInteger(String orginal) {
		return isMatch(orginal,"^-[1-9]\\d*");
	}

	/**
	 * 整数（0，正整数，负整数）
	 */
	public static boolean isWholeNumber(String orginal) {
		return isMatch( orginal,"[+-]{0,1}0") || isPositiveInteger(orginal) || isNegativeInteger(orginal);
	}
	

	/**
	 * 正浮点数(必须带点)，（不含0.0）
	 */
	public static boolean isPositiveDecimal(String orginal) {
		return isMatch( orginal,"\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*");
	}

	/**
	 * 负浮点数(必须带点)，（不含0.0）
	 */
	public static boolean isNegativeDecimal(String orginal) {
		return isMatch( orginal,"^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*");
	}
	
	/**
	 * 浮点数(必须带点)
	 */
	public static boolean isDecimal(String orginal) {
		return isMatch(orginal,"[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+");
	}

	/**
	 * 整数，浮点数，0 , 0.0
	 */
	public static boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}

	/**
	 * @param url
	 * @return
	 */
	public static boolean isURL(String url) {
		String regex="^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
		return isMatch(url,regex, Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * 是否匹配
	 * @param regex  正则
	 * @param orginal 字符串
	 * @param orginal 其他标志
	 * @return
	 */
	public static boolean isMatch(String orginal,String regex, int mark) {
		if (orginal == null || "".equals(orginal.trim())) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex,mark);
		Matcher matcher = pattern.matcher(orginal);
		return matcher.matches();
	}
	
	/**
	 * 是否匹配
	 * @param regex  正则
	 * @param orginal 字符串
	 * @return
	 */
	public static boolean isMatch(String orginal,String regex) {
		if (orginal == null || "".equals(orginal.trim())) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(orginal);
		return matcher.matches();
	}
	
	/**
	 * 是否包含xx
	 * @param regex 正则
	 * @param orginal 字符型
	 * @return
	 */
	public static boolean exist(String orginal,String regex) {
		if (orginal == null || "".equals(orginal.trim())) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(orginal);
        if (m.find()) {
            return true;
        }
        return false;
	}



}