package com.achilles.wild.server.tool.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class DoubleUtil {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	/**#.## */
	public static final String REG_1 = "#.##";
	/** #,###.## */
	public static final String REG_2 = "#,###.##";

	/**
	 * 去掉科学记数法
	 */
	public static String toStr(double v1) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		return b1.toPlainString();
	}
	
	/**
	 * 主要用于格式化金额
	 * @param v1
	 * @return
	 */
	public static String format(double v1) {
		NumberFormat numberFormat = new DecimalFormat(REG_2);
		String str = numberFormat.format(v1);
		return str;
	}
	
	/**
	 * 主要用于格式化小数点
	 * @param v1
	 * @param reg
	 * @return
	 */
	public static String format(double v1,String reg) {
		NumberFormat numberFormat = new DecimalFormat(reg);
		String str = numberFormat.format(v1);
		return str;
	}
	
	
	/**
	 * 加法
	 */
	public static double add(double v1, double ...v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		for (double vv : v2){
			BigDecimal b2 = new BigDecimal(Double.toString(vv));
			b1=b1.add(b2);
		}
		return b1.doubleValue();
	}

	/**
	 * 减法
	 */
	public static double sub(double v1, double ...v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		for (double vv : v2){
			BigDecimal b2 = new BigDecimal(Double.toString(vv));
			b1=b1.subtract(b2);
        }
		
		return b1.doubleValue();
	}

	
	/**
	 * 乘法运算。
	 */
	public static double mul(double v1,double ...v2) {
		BigDecimal b1=new BigDecimal(Double.toString(v1));
		for (double vv : v2){
			BigDecimal b2 = new BigDecimal(Double.toString(vv));
			b1=b1.multiply(b2);
        }
		return b1.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。
	 */

	public static double div(double v1, double ...v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		
		for (double vv : v2){
			BigDecimal b2 = new BigDecimal(Double.toString(vv));
			b1=b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        }
		return b1.doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	/**
	 * 两个数比较，获取比较值相对于基准值的大小百分比
	 * @param base 基准值
	 * @param compare 比较值
	 * @return
	 */
	public static String getComparePercent(int base,int compare){
		if (base==compare) {
			return "0%";
		}
		if (compare==0) {
			return "-"+base*100+"%";
		}
		if (base==0) {
			return compare*100+"%";
		}
        NumberFormat numberFormat = NumberFormat.getInstance();  
        numberFormat.setMaximumFractionDigits(2); // 设置精确到小数点后2位  
        int margin = compare-base;
        String result = numberFormat.format((float) margin / (float) base * 100);  
		return result + "%";
	}	
	
};
