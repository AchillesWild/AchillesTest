package com.achilles.wild.server.tool.date;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {

	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";
	public static final String FORMAT_YYYY_MM_DD_HHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static String yyyyMM = "yyyyMM";
	public static String yyyy_MM_dd_CN = "yyyy年MM月dd日";
	public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static String yyyy_MM_dd_HH_mm_ss_CN = "yyyy年MM月dd日 HH:mm:ss";
	public static String MM_dd_CN = "MM月dd日";

    public static void main(String[] args) {
    	String dateStr = "2020-07-30 17:00:44";
		Date date = getDateFormat(YYYY_MM_DD_HH_MM_SS_SSS,"20210210213119322");
		System.out.println(date);

		System.out.println(getGapSeconds(date));
//    	Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
//    	   int day=aCalendar.getActualMaximum(Calendar.DATE);
//    	  System.out.println(getDateFormat(FORMAT_YYYY_MM_DD_HHMMSS, date));
//    	  Calendar cal = Calendar.getInstance();
//    	  System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//    	  System.out.println(cal.getTime());
//    	  exampleCalendar();

		//System.out.println(getStrDateFormat(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS,ZonedDateTime.now()));
		//System.out.println(getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS));
		//System.out.println(DateUtil.getCurrentDate());
		//System.out.println(inOneMinute(date.getTime()));

		//System.out.println(compareDateIfInScope(date.getTime(),60*1000L));

		//System.out.println(compareDateIfInScope(date.getTime(),60*60*1000L));


    }

	/**
	 * 根据传入毫秒数，返回当前时间减去该时间后的提示
	 *
	 * @param earlierTime
	 * @return
	 */
	public static String getPrompt(long earlierTime){

		long now = System.currentTimeMillis();
		long range = now - earlierTime;

		if(range<0){
			return null;
		}

		//一分钟内
		if(range <= 61*1000L){
			return "刚刚";
		}

		//60分钟内
		if(range >= 61*1000L && range<61*60*1000L){
			long num = range/1000/60;
			return num+"分钟前";
		}

		//24小时内
		if(range >= 61*60*1000L && range<25*60*60*1000L){
			long num = range/1000/60/60;
			return num+"小时前";
		}

		//7天内
		if(range >= 25*60*60*1000L && range<8*24*60*60*1000L){
			long num = range/1000/60/60/24;
			return num+"天前";
		}

		//今年内
		if(range >= 8*24*60*60*1000L && getTheYear(new Date())==getTheYear(new Date(earlierTime))){
			Date date = new Date(earlierTime);
			return getTheMonth(date)+"-"+getTheDate(date);
		}

		//今年之前
		int thisYear = getTheYear(new Date());
		int theYear = getTheYear(new Date(earlierTime));
		if(thisYear != theYear){
			int num = thisYear-theYear;
			return num+"年前";
		}

		return null;
	}

	/**
	 * 获取指定时间的零点时间
	 *
	 * @param specifiedDate
	 * @return
	 */
	public static Date truncateBegin(Date specifiedDate) {
		return DateUtils.truncate(specifiedDate, Calendar.DATE);
	}

	/**
	 * 根据指定日期获取某月月末时间；如传入 2017-12-09 12:12:12 ， 获取  2018-01-01 23:59:59
	 * @param dateTime  时间
	 * @param move   左右浮动，正数加月，负数减月
	 * @return
	 */
	public static Date getTheMonthLastDateByDate(Date dateTime,int move) {
	       SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
	       Calendar ca = Calendar.getInstance();
	       ca.setTime(dateTime);
	       ca.add(Calendar.MONTH, move);
	       ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	       String last = sdf.format(ca.getTime());
	 	  sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
	 	  Date date = null;
	       try {
	     	  date = sdf.parse(last+" 23:59:59");
	 	  } catch (ParseException e) {
	 		e.printStackTrace();
	 	  }
	 	return date;
	    }   
    
	/**
	 * 根据指定日期获取某月月初时间；如传入 2017-12-09 12:12:12 ， 获取  2018-01-01 00:00:00
	 * @param dateTime  时间
	 * @param move 左右浮动，正数加月，负数减月
	 * @return
	 */
	public static Date getTheMonthFirstDateByDate(Date dateTime,int move) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.add(Calendar.MONTH, move);
		cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		String first = sdf.format(cal.getTime());
		sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
		Date date = null;
		try {
    	    date = sdf.parse(first+" 00:00:00");
		} catch (ParseException e) {
		    e.printStackTrace();
	    }
	  return date;
	}
    
    /**
     * 根据指定时间获取某一周开始时间
     * @param move 为0获取当前周的起始时间，如果获取前一周起始时间：-7，后一周7，0本周
     * @return
     */
	public static Date getTheWeekFirstTime(Date date,int move) {
		Calendar cal = Calendar.getInstance();
		if (date!=null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DATE, move);
		int dayOrder = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 0-dayOrder+2);
		if (dayOrder==1) {//周日
			cal.add(Calendar.DATE, -7);
		}		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		String first = sdf.format(cal.getTime());
		sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
		try {
    	    date = sdf.parse(first+" 00:00:00");
		} catch (ParseException e) {
		    e.printStackTrace();
	    }
	  return date;
	}
    
    /**
     * 获取某一周开始时间
     * @param move 为0获取当前周的起始时间，如果获取前一周起始时间：-7，后一周7，0本周
     * @return
     */
	public static Date getTheWeekFirstTime(int move) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0-cal.get(Calendar.DAY_OF_WEEK)+2+move);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		String first = sdf.format(cal.getTime());
		sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
		Date date = null;
		try {
    	    date = sdf.parse(first+" 00:00:00");
		} catch (ParseException e) {
		    e.printStackTrace();
	    }
	  return date;
	}
    
    /**
     * Calendar方法
     */
    public static void exampleCalendar(){
    	Calendar calendar = Calendar.getInstance();
    	int day = calendar.get(Calendar.DAY_OF_WEEK);
    	String dayStr = null;
	    if(Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)){
	    	   dayStr =  "星期一";
		   }
		   if(Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期二";
		   }
		   if(Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期三";
		   }
		   if(Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期四";
		   }
		   if(Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期五";
		   }
		   if(Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期六";
		   }
		   if(Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)){
			   dayStr =  "星期日";
		}
    	System.out.println("年份	  ：" + calendar.get(Calendar.YEAR));
    	System.out.println("月份	  ：" + calendar.get(Calendar.MONTH)+1);
    	System.out.println("日期	  ：" + calendar.get(Calendar.DATE));
    	System.out.println("小时	  ：" + calendar.get(Calendar.HOUR));
    	System.out.println("24小时         ：" + calendar.get(Calendar.HOUR_OF_DAY));
    	System.out.println("分钟	  ：" + calendar.get(Calendar.MINUTE));
    	System.out.println("秒	  ：" + calendar.get(Calendar.SECOND));
    	System.out.println("星期	  ：" + day);
    	System.out.println("星期	  ：" + dayStr);
    	System.out.println("------------------时间增加/减少指定值后的时间-------------------------");
    	calendar.add(Calendar.DATE, 2);
    	int year1 = calendar.get(Calendar.YEAR);
    	int month1 = calendar.get(Calendar.MONTH) + 1;
    	int date1 = calendar.get(Calendar.DATE);
    	System.out.println("当前时间加两天后："+year1 + "年" + month1 + "月" + date1 + "日");
    	calendar = Calendar.getInstance();
      	calendar.add(Calendar.DATE, -2);
    	year1 = calendar.get(Calendar.YEAR);
    	month1 = calendar.get(Calendar.MONTH) + 1;
    	date1 = calendar.get(Calendar.DATE);
    	System.out.println("当前时间减两天后："+year1 + "年" + month1 + "月" + date1 + "日");
    	System.out.println("------------------after  比较-------------------------");
    	Calendar c4 = Calendar.getInstance();
    	c4.set(2009, 10 - 1, 10);//10-1  10月
    	Calendar c5 = Calendar.getInstance();
    	c5.set(2010, 10 - 1, 10);
    	System.out.println(c5.after(c4));
    	System.out.println("------------------加减指定毫秒后的时间-------------------------");
    	calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis()+3600*1000);
    	System.out.println("-----------"+calendar.get(Calendar.HOUR_OF_DAY));
    	
    	System.out.println("-----------"+getTheDate(new int[]{1,1,1,1,1,1,1}));
    }
    
    /**
     * 获取指定时间的年，默认当前
     * @param date
     * @return
     */
	public static int getTheYear(Date date){
		Calendar cal = Calendar.getInstance();
		if (date!=null) {
			cal.setTime(date);
		}
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 获取指定时间的月，默认当前
	 * @return
	 */
	public static int getTheMonth(Date date){
		Calendar cal = Calendar.getInstance();
		if (date!=null) {
			cal.setTime(date);
		}
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取指定时间的天，默认当前
	 *
	 * @return
	 */
	public static int getTheDate(Date date){
		Calendar cal = Calendar.getInstance();
		if (date!=null) {
			cal.setTime(date);
		}

		int day = cal.get(Calendar.DATE);
		return day;
	}
    
    /**
     * 获取只读日期一个月的天数，默认当前
     * @param date
     * @return
     */
    public static int getMonthDayNum(Date date){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (date!=null) {
        	calendar.setTime(date);
		}
		return calendar.getActualMaximum(Calendar.DATE);
    }
    
	/**
	 * 当前时间+/-指定毫秒数后获取的时间
	 * @param milliseconds(常量计算毫秒数加l)
	 * @return
	 */
	public static Date getDateByAddMill(long milliseconds){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(calendar.getTimeInMillis()+milliseconds); 
		return calendar.getTime();
	}
    
	/**
	 * 当前时间+/-指定毫秒数后获取的时间
	 * @param milliseconds(常量计算毫秒数加l)
	 * @return
	 */
	public static Date getDateByAddMilli(long milliseconds){
		Date date=new Date(); 
		date.setTime(date.getTime()+milliseconds);  
		return date;
	}
  
	
	public static Date getNowDate(){
		return Calendar.getInstance().getTime();
	}
	
    /**
     * 获取指定时间
     * @param params  从下标开始依次在年月日，累加/减后获取日期，如果获取当前，输入null或相应位置取0;如new int[]{1,1,1,1,1,1,1})
     * @return
     */
    public static Date getTheDate(int[] params){
    	Calendar calendar = Calendar.getInstance();
    	if (params!=null && params.length!=0) {
    		calendar.add(Calendar.YEAR, params[0]);
    		calendar.add(Calendar.MONTH, params[1]);
    		calendar.add(Calendar.DATE, params[2]);
    		calendar.add(Calendar.HOUR_OF_DAY, params[3]);
    		calendar.add(Calendar.MINUTE, params[4]);
    		calendar.add(Calendar.SECOND, params[5]);
		}
		return calendar.getTime();
    }

	/**
	 * 判断当前时间是否超过传入时间一定范围内
	 *
	 * @param earlierTime 早时间
	 * @param scopeTime 时间差，毫秒
	 * @return true 在时间差范围内
	 */
	public static boolean compareDateIfInScope(long earlierTime,long scopeTime){
		long now = System.currentTimeMillis();

		if(now < earlierTime){
			return false;
		}
		if(now-earlierTime <= scopeTime){
			return true;
		}
		return false;
	}


	/**
	  * 判断两个时间差是否在一定范围内  
	  * @param laterDate  晚时间
	  * @param earlierDate 早时间
	  * @param scopeTime 时间差，毫秒
	  * @return true 在时间差范围内
	  */
	public static boolean compareDateIfInScope(Date laterDate,Date earlierDate,long scopeTime){
		if(laterDate.getTime()-earlierDate.getTime() <= scopeTime){
			return true;
		}
		return false;
	}

	/**
	 * 判断当前时间是否超过传入时间多少
	 * @param baseTime  传入时间
	 * @param scopeTime  超出的范围    毫秒
	 * @return
	 */
	public static boolean isEarlierThanNow(Timestamp baseTime,long scopeTime){
		if(System.currentTimeMillis()-baseTime.getTime()>scopeTime){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取系统时间戳
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 当前时间
	 * @return Date类型的时间
	 */
	public static Date getSysDate() {
		Calendar cl = Calendar.getInstance();
		return cl.getTime();
	}
	
	/**
	 * 当前时间
	 * @return Date类型的时间
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 格式转换
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getStrDateFormat(String format, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取int日期
	 *
	 * @param format
	 * @param date
	 * @return
	 */
	public static Integer getIntDateFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return Integer.parseInt(sdf.format(date));
	}

	/**
	 * 格式转换
	 * @param format
	 * @param
	 * @return
	 */
	public static String getStrDateFormat(String format,ZonedDateTime zonedDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.format(zonedDateTime);
	}
	
	/**
	 * 格式转换
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date getDateFormat(String format, String date) {
		 SimpleDateFormat sdf = new SimpleDateFormat(format);  
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 格式转换
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getStrDateFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/*
	 * 日期格式转换
	 */
//	public static Date getDateFormat(String format,Date date) {
//		Date dateForm = null;
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		try {
//			dateForm = sdf.parse(date.toString());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return dateForm;
//	}

	/*
	 * 日期格式转换
	 */
//	public static Date getDateFormat(String format,String date) {
//		Date dateForm = null;
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat(format);
//			dateForm = sdf.parse(date);
//		} catch (Exception e) {
//			e.getMessage();
//		}
//		return dateForm;
//	}

	/**
	 * 获取当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static Date getSysDate(String format) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String sysDate = sdf.format(new Date());
		try {
			date = sdf.parse(sysDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取今天之前或之后的日期
	 * @param format 格式
	 * @param back  以当前为基准，向前或先后推几天，向前为正数，向后为负数
	 * @return
	 */
	public static String getTheDayBefOrAftStr(String format, int back) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, back);
		Date date = cal.getTime();
		String before = getStrDateFormat(format, date);
		return before;
	}

	/**
	 * 获取今天之前或之后的日期
	 * @param back  以当前为基准，向前或先后推几天，向前为正数，向后为负数
	 * @return
	 */	
	public static Date getTheDayBefOrAftDate(int back) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, back);
		Date date = cal.getTime();
		return date;
	}	
	
	/**
	 * 转换时间为毫秒数
	 * @param format  传入时间的格式
	 * @param time
	 * @return
	 */
	public static long getTimeMillis(String format,String time) {
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		long timeStart = 0;
		try {
			timeStart = sdf.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStart;
	}
	
	/**
	 * 获取一个月最后一天
	 * @param term 201709
	 * @return
	 */
	public static String getMonthLastDay(String term) {
		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));

		String getLastDay = "";

		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0) {
				getLastDay = "29";
			} else {
				getLastDay = "28";
			}
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11) {
			getLastDay = "30";
		} else {
			getLastDay = "31";
		}
		return getLastDay;
	}	
	
	/**
	 * 根据输入的年,月获取最后一天
	 * Discription:
	 * @param year
	 * @param month
	 * @author Achilles
	 * @since 2017年11月8日
	 */
	public static int getLastDayOfMonth(int year, int month) {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month-1);//月从0开始  
        return cal.getActualMaximum(Calendar.DATE);  
    } 

	/**
	 * Discription:根据输入的年,月获取最第一天的最早时间
	 * @param year
	 * @param month
	 * @return String
	 * @author Achilles
	 * @since 2017年11月9日
	 */
   public static String getFirstTimeOfSpecialMonth(String year, String month){
        if (month.length()==1) {
            month = "0"+month; 
        }
        StringBuffer sb = new StringBuffer();
        sb.append(year).append("-").append(month)
        .append("-01 00:00:00");
        return sb.toString();
    }
	
	/**
	 * Discription:获取指定年月最后一天的最后时间
	 * @param year
	 * @param month
	 * @return String
	 * @author Achilles
	 * @since 2017年11月8日
	 */
	public static String getLastTimeOfTheMonth(String year, String month){
	    int lastDay = getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
	    if (month.length()==1) {
	        month = "0"+month; 
        }
	    StringBuffer sb = new StringBuffer();
	    sb.append(year).append("-").append(month).append("-").append(lastDay)
	    .append(" 23:59:59");
        return sb.toString();
	}

   /**
    * 获取某月的最早时间
    * @param move 负数，向前减几个月，正数后加，0为当前
    * @return
    */
   public static Date getTheMonthFirstTime(int move){
       SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
       Calendar c = Calendar.getInstance();    
       c.add(Calendar.MONTH, move);
       c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
       String first = sdf.format(c.getTime());
 	  sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
 	  Date date = null;
       try {
     	  date = sdf.parse(first+" 00:00:00");
 	  } catch (ParseException e) {
 		e.printStackTrace();
 	  }
 	 return date;
   }    
   
   /**
    * 获取某月的最晚时间
    * @param move move 负数，向前减几个月，正数后加，0为当前
    * @return
    */
   public static Date getTheMonthLastTime(int move){
       SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
       Calendar ca = Calendar.getInstance();   
       ca.add(Calendar.MONTH, move);
       ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
       String last = sdf.format(ca.getTime());
 	  sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
 	  Date date = null;
       try {
     	  date = sdf.parse(last+" 23:59:59");
 	  } catch (ParseException e) {
 		e.printStackTrace();
 	  }
 	return date;
    }
  
	/**
	 * 201709 获取 2017-09-01 00:00:00
	 * @param str
	 * @return
	 */
	public static String getMinByYYYYMM(String str){
		StringBuffer sb =new StringBuffer();
		sb.append(str.substring(0,4)).append("-").append(str.substring(4,6)).append("-01 00:00:00");
		return sb.toString();
	}
	
	/**
	 * 201709 获取 2017-09-30 23:59:59
	 * @param str
	 * @return
	 */
	public static String getMaxByYYYYMM(String str){
		StringBuffer sb =new StringBuffer();
		sb.append(str.substring(0,4)).append("-").append(str.substring(4,6))
		.append("-").append(getMonthLastDay(str)).append(" 23:59:59");
		return sb.toString();
	}
	
	/**
	 * 获取某天开始时间
	 * @param move
	 * @return
	 */
	public static Date getTheDayFirstTime(int move) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, move);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		String first = sdf.format(cal.getTime());
		sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
		Date date = null;
		try {
    	    date = sdf.parse(first+" 00:00:00");
		} catch (ParseException e) {
		    e.printStackTrace();
	    }
	  return date;
	}
	
	/**
	 * 毫秒转换为时间
	 * @param milliseconds
	 * @return
	 */
	public static Date milliSecToDATE(long milliseconds){
		Date date = new Date();
		date.setTime(milliseconds);
		return date;
	}
	
	/**
	 * 根据给定日期获取之前或之后的日期
	 * @param date 给定日期
	 * @param back date为基准，向前或先后推几天，向前为正数，向后为负数
	 * @return
	 */
	public static Date getTheDayByDate(Date date,int back) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, back);
		return cal.getTime();
	}
	
	/**
	 * 根据出生年月日时间获取年龄
	 * @param birthTimeString
	 */
    public static int getAgeFromBirthTime(String birthTimeString) {  
        // 先截取到字符串中的年、月、日  
        String strs[] = birthTimeString.trim().split("-");  
        int selectYear = Integer.parseInt(strs[0]);  
        int selectMonth = Integer.parseInt(strs[1]);  
        int selectDay = Integer.parseInt(strs[2]);  
        // 得到当前时间的年、月、日  
        Calendar cal = Calendar.getInstance();  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH) + 1;  
        int dayNow = cal.get(Calendar.DATE);  
  
        // 用当前年月日减去生日年月日  
        int yearMinus = yearNow - selectYear;  
        int monthMinus = monthNow - selectMonth;  
        int dayMinus = dayNow - selectDay;  
  
        int age = yearMinus;// 先大致赋值  
        if (yearMinus < 0) {// 选了未来的年份  
            age = 0;  
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0  
            if (monthMinus < 0) {// 选了未来的月份  
                age = 0;  
            } else if (monthMinus == 0) {// 同月份的  
                if (dayMinus < 0) {// 选了未来的日期  
                    age = 0;  
                } else if (dayMinus >= 0) {  
                    age = 1;  
                }  
            } else if (monthMinus > 0) {  
                age = 1;  
            }  
        } else if (yearMinus > 0) {  
            if (monthMinus < 0) {// 当前月>生日月  
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄  
                if (dayMinus < 0) {  
                } else if (dayMinus >= 0) {  
                    age = age + 1;  
                }  
            } else if (monthMinus > 0) {  
                age = age + 1;  
            }  
        }  
        return age;  
    }

	/**
	 * getGapSeconds
	 *
	 * @param beginTime
	 * @return
	 */
	public static int getGapSeconds(Date beginTime){
		long begin = beginTime.getTime();
		long end = System.currentTimeMillis();
		long seconds = (end-begin)/(1000);
		return (int)seconds;
	}

	/**
	 * 计算天数
	 * @param beginTime	开始时间 
	 * @param endTime	结束时间
	 * @return	days	剩余天数
	 */
	public static long getDaysRemaining(Date beginTime,Date endTime){
		long beginMs=beginTime.getTime();
		long endMs=endTime.getTime();
		long days= (endMs-beginMs)/(1000*60*60*24);
		return days;
	}
	/**
	 * 计算天数
	 * @param beginTime	开始时间 
	 * @param nowtime	结束时间
	 * @return	days	之间天数
	 */
	public static long getBetweenDays(Date beginTime,Date nowtime){
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long begin = cal.getTimeInMillis();
		cal.setTime(nowtime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long now = cal.getTimeInMillis();
		long days= (now-begin)/(1000*60*60*24);
		return days+1;
	}
	
	 /**
     * 
     * 功能: 判断是否是月末
     * @param date
     * @return true月末,false不是月末
     */
    public static boolean isLastDady(Date date){
    	
        Calendar ca=Calendar.getInstance();
        ca.setTime(date);
        if(ca.get(Calendar.DATE)==ca.getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        else
            return false;
    }
    
	/**
	 * 根据日期取出是星期几,中文
	 * 
	 * @param date
	 * @return int 返回1-7
	 */
	public static String getWeekTextOfDate(Date date){
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int t=getWeekOfDate(date);
		if(t==7) t=0;
		return dayNames[t];
	}
	/**
	 * 获取周几 中文
	 *
	 * @param date
	 * @return
	 */
	public static String getWeekTextByDate(Date date){
		String[] dayNames = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayNames[w];
	}

	/**
	 * 根据日期取出是星期几,数字
	 * 
	 * @param date
	 * @return int 返回1-7
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 是否为闰年
	 * @param year
	 * @return
	 */
	public static  boolean isLeapYear( int year ) {
		if ( ( year % 400 ) == 0 )
			return true;
		else if ( ( year % 4 ) == 0 ){
			if ( ( year % 100 ) == 0 )
				return false;
			else return true;
		}
		else return false;
	}
	
	/**
	 * 是否为当天
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date){
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		if(today.get(Calendar.YEAR)== day.get(Calendar.YEAR)
				&& today.get(Calendar.MONTH)== day.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH)== day.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}
	
	public static String getNowStr(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date).replaceAll("-", "").replaceAll(":", "").replace(".", "").replace(" ", "");
	}

	/**
	 * 获取当前时间字符串
	 * @param format
	 * @return
	 */
	public static String getCurrentStr(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
}
