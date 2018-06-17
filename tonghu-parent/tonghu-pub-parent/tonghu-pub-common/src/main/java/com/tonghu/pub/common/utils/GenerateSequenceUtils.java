package com.tonghu.pub.common.utils;

import java.text.*;
import java.util.Calendar;

/**
 * @Description: 生成序列字符串
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午9:07:46
 */
public class GenerateSequenceUtils {
	
	private GenerateSequenceUtils(){};

	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

	private static final Format dateFormat = new SimpleDateFormat("MMddHHmmssS");

	private static final NumberFormat numberFormat = new DecimalFormat("0000");

	private static int seq = 0;

	private static final int MAX = 9999;

	/**
	 * 根据时间格式生成序列
	 * 
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {

		Calendar rightNow = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();

		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

		numberFormat.format(seq, sb, HELPER_POSITION);

		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}
		
		return sb.toString();
	}
	
}
