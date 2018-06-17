package com.tonghu.pub.common.utils;

import com.tonghu.pub.common.constant.Constant;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 时间工具类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年7月25日 下午9:44:54
 */
public class DateTimeUtils {

	public static final String DEFAULT_START_DATE = "1900-1-1";

	public static final String YMD = "yyyy-MM-dd";

	public static final String _YMD = "yyyy-M-d";

	public static final String YM = "yyyy-MM";

	public static final String YMDHM = "yyyy-MM-dd HH:mm";

	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

	public static final String YMDHMSS = "yyyy-MM-dd HH:mm:ss:SS";

	public static final String HMS = "HH:mm:ss";

	public static final String YMDHMS_STR = "yyyyMMddHHmmss";

	public static final String Y = "yyyy";

	public static final String M = "MM";

	public static final String D = "dd";

	public static final String MD = "M-dd";

	public static final String MMDD = "M-dd";

	public static final String MD_ZH = "MM月dd日";

	/**
	 * 将java.util.Date 按指定格式转化为String
	 *
	 * @param date
	 * @param format
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-28下午08:24:55
	 * @version V1.0
	 */
	public static String date2str(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将string 按指定格式转化为java.util.Date
	 *
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date str2Date(String str, String format)
			throws ParseException {
		if (str == null || "".equals(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * 将时间字符串转换为标准格式时间字符串
	 * @param str
	 * @param format
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-28下午08:24:55
	 * @version V1.0
	 */
	public static String formatDateStr(String str, String format) throws ParseException {
		return date2str(str2Date(str, format), format);
	}

	public static String formatMDDateStrZh(String str) throws ParseException {
		String result = date2str(str2Date(str, _YMD), _YMD);
		result = result.substring(5);
		result = result.replaceAll("-", "月");
		result += "日";
		return result;
	}

	/**
	 * 返回当前java.sql.Timestamp类型时间
	 */
	public static Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 将string 按指定格式转化为java.sql.Timestamp
	 *
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp str2Timestamp(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return new Timestamp(sdf.parse(str).getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将Date 按照 format 进行格式化
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 验证字符串是否为合法日期格式 支持YYYY-MM-DD OR YYYY-MM-DD HH:mm:ss
	 *
	 * @param dateString
	 */
	public static boolean validateDateFormat(String dateString) {
		Boolean validate = Boolean.FALSE;
		String reg1 = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		String reg2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern p1 = Pattern.compile(reg1);
		Pattern p2 = Pattern.compile(reg2);
		Matcher m1 = p1.matcher(dateString);
		Matcher m2 = p2.matcher(dateString);
		if (m1.matches() || m2.matches()) {
			validate = Boolean.TRUE;
		}
		return validate;
	}

	/**
	 * 将制定日期向 向后推若干分钟
	 *
	 * @param startTime
	 *            日期
	 * @param compartTime
	 *            要推迟的分钟数 正数 向后 负数向前
	 * @return
	 */
	public static Date compartDate(Date startTime, int compartTime) {
		if (null == startTime) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		c.add(Calendar.MINUTE, compartTime);
		return c.getTime();
	}

	/**
	 * 获得对某个时间单位进行偏移之后时间 如getDate(new Date(), 1, Calendar.DATE)，表示取到当前时间一天之后的时间
	 *
	 * @param date
	 * @param offset
	 * @param unit
	 * @return
	 */
	public static Date getDate(Date date, int offset, int unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(unit, offset);
		return c.getTime();
	}

	/**
	 * 创建Y M D 格式的目录 比如 2017/01/04
	 *
	 * @return
	 */
	public static String createYMDDir() {
		Date date = new Date();
		String y = formatDate(date, Y);
		String m = formatDate(date, M);
		String d = formatDate(date, D);
		return y + Constant.SPECIAL_CHARACTER.SOLIDUS + m
				+ Constant.SPECIAL_CHARACTER.SOLIDUS + d;
	}

	/**
	 * 获取指定格式的时间字符串
	 * @param timeFormat : 指定的格式
	 * @return String
	 * @Exception
	 * @Author liangyongjian
	 * @Version V1.0
	 * @Create at 2017-09-24 下午02:47:56
	 */
	public static String getDateTime(String timeFormat) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(timeFormat);
		return df.format(calendar.getTime());
	}

	/**
	 * 比较两个日期
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-10 下午05:48:31
	 * @version V1.0
	 */
	public static boolean compareTwoDate(String startDate, String endDate, String format) throws Exception {
		Date sDate = new SimpleDateFormat(format).parse(startDate);
		Date eDate = new SimpleDateFormat(format).parse(endDate);
		return (eDate.getTime() - sDate.getTime() >= 0);
	}

	/**
	 * 两个日期相减
	 * @param date1
	 * @param date2
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-10 下午05:51:04
	 * @version V1.0
	 */
	public static long reduceTwoDate(String date1, String date2, String format) throws Exception {
		Date sDate = new SimpleDateFormat(format).parse(date1);
		Date eDate = null;
		if (date2 == null) {
			eDate = new SimpleDateFormat(format).parse(getDateTime(format));
		} else {
			eDate = new SimpleDateFormat(format).parse(date2);
		}
		return ((sDate.getTime() - eDate.getTime()) / (24*60*60*1000));
	}

	public static String getLastDayOfMonth(Integer year, Integer month, String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(lastDate, format);
	}

	public static String getFirstDayOfMonth(Integer year, Integer month, String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = cal.getTime();
		return formatDate(firstDate, format);
	}

	/**
	 *
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-13 上午10:56:56
	 * @version V1.0
	 */
	public static String dateAddDays(String dateStr, int offset, int unit, String format) throws Exception {
		Date date = new SimpleDateFormat(format).parse(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(getDate(date, offset, unit));
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = (Date) currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7+6);
		Date monday = (Date) currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获取当前年
	 * @return
	 */
	public static Integer getCurrentYear() {
		Integer currentYear = null;
		try {
			String currentYearStr = DateTimeUtils.getDateTime(DateTimeUtils.Y);
			currentYear = Integer.parseInt(currentYearStr);
		} catch(Exception e) {
			currentYear = 2018;
		}
		return currentYear;
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(getDateTime("yyyy"));
//		System.out.println(getDateTime("MM"));
//		System.out.println(reduceTwoDate("2017-10-12", null, YMD));
//		System.out.println(new Integer("001"));
//		System.out.println(dateAddDays("2017-10-12", -1, Calendar.DATE, YMD));
//		System.out.println(getLastDayOfMonth(2017, 2, "yyyy-MM-dd"));
//		System.out.println(getFirstDayOfMonth(2017, 2, "yyyy-MM-dd"));
//		System.out.println(formatMDDateStrZh("2017-02-02"));


//		String currentYearMonth = DateTimeUtils.getDateTime(DateTimeUtils.YMD);
//		System.out.println(new Integer(currentYearMonth.substring(0, 4)));
//		System.out.println(new Integer(currentYearMonth.substring(5, 7)));
//		System.out.println(new Integer(currentYearMonth.substring(8, 10)));
//
//		String dateStr = DateTimeUtils.dateAddDays(DateTimeUtils.getDateTime(DateTimeUtils.YMD),
//				7, Calendar.DATE, DateTimeUtils.YMD);
//		System.out.println(dateStr);

		System.out.println(validateDateFormat("2017-10-1"));
	}
	
}
