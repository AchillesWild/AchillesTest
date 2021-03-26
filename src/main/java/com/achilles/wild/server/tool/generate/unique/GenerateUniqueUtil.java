package com.achilles.wild.server.tool.generate.unique;

import com.achilles.wild.server.tool.date.DateUtil;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 生成唯一数
 * @author Pang
 *
 */
public class GenerateUniqueUtil {

	public static void main(String[] args) {
		System.out.println(getRandomUUID());
	}


	public static String getTraceId(String mark){
		String traceId = DateUtil.getCurrentStr(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)+"_"
				+ mark+"_"
				+ GenerateUniqueUtil.getUuId();
		return traceId;
	}

	public static String getRandomUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * 获取UuId
	 * @return
	 */
	 public static String getUuId(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	 }
	
	/**
	 * Discription:生成唯一数    毫秒数+线程数+随机数  在一个循环中调用的时候使用
	 * @param numSet  生成的字符串临时存储
	 * @param randomMaxLength  随机数长度
	 * @param maxLength  生成字符串总长度
	 * @return String
	 * @author Achilles
	 * @since 2016年10月13日
	 */
   public static String getGenerateNum(Set<String> numSet,int randomMaxLength,int maxLength){
        String num = getGenerateNum(randomMaxLength,maxLength);
        if (!numSet.contains(num)) {
            if (num.length()>maxLength) {
                num=num.substring(0, maxLength); 
            }
            numSet.add(num);
        }else{
            getGenerateNum(numSet,randomMaxLength,maxLength);
        }
        return num;
    }
	

	
    /**
     * 生成唯一数
     * Discription:
     * @param randomMaxLength 随机数长度
     * @param maxLength  生成字符串最大长度
     * @return String
     * @author Achilles
     * @since 2016年10月13日
     */
    public static String getGenerateNum(int randomMaxLength,int maxLength){
        String str = ""+ System.currentTimeMillis()+Thread.currentThread().getId()+getRandomNum(randomMaxLength);
        if (str.length()>maxLength) {
            str=str.substring(0, maxLength); 
        }
        return str;
    }	
	
	
	/**
	 * 根据传入的数字获取这个值长度内的随机数
	 * @param maxLength
	 * @return
	 */
	public static int getRandomNum(int maxLength){
		int max = getMaxInt(maxLength);
		Random random = new Random();
		int randomNumber =  random.nextInt(max)%(max-1) + 1;
		return randomNumber;
	}
	
	/**
	 * 根据传入的位数获取这个位数最大的整数
	 * @param maxLength
	 * @return
	 */
	public static int getMaxInt(int maxLength){
		int max = 0;
		for (int i = maxLength; i>0; i--) {
			max += Math.pow(10, i-1)*9;
		}
		return max;
	}
	
	/**(不要再使用此方法，使用此类其他有maxLength 参数的方法)
	 * 生成20位以内唯一数(非循环中使用)    毫秒数+线程数+随机数
	 * @return
	 */
	public static String getGenerateId(){
		Random random = new Random();
		int randomNumber =  random.nextInt(99999)%(99999-1) + 1;
		String id = ""+ System.currentTimeMillis()+Thread.currentThread().getId()+randomNumber;
		return id;
	}
	
}