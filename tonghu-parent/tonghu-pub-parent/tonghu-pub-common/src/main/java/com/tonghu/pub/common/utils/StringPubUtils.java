package com.tonghu.pub.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: StringPubUtils
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午6:31:03
 */
public class StringPubUtils  extends StringUtils {

	/**
	 * 删除第一个字符
	 * @param str
	 * @return
	 */
	public static String removeFirst(String str) {
		return isEmpty(str) ? "" : str.substring(1, str.length());
	}

	/**
	 * 删除最后一个字符
	 * @param str
	 * @return
	 */
	public static String removeLast(String str) {
		return isEmpty(str) ? "" : str.substring(0, str.length() - 1);
	}

	/**
	 * 删除首尾的指定字符串
	 * @param str
	 * @return
	 */
	public static String trimStr(String str, String fix) {
		if(str == null || "".equals(str) || fix == null || "".equals(fix)){
			return str;
		}
		
		String itemStr = str;
		while(itemStr.startsWith(fix)){
			itemStr = itemStr.substring(fix.length()); 
		}
		
		while(itemStr.endsWith(fix)){
			itemStr = itemStr.substring(0, itemStr.length() - fix.length()); 
		}
		
		
		return isEmpty(str) ? "" : str.substring(0, str.length() - 1);
	}
	
	/**
	 * 将 str按照指定的 分隔符 生成一个 list，并过滤掉其中的空数据
	 * @param str
	 * @param split
	 * @return
	 */
	public static List<String> string2ListBySplit(String str,String split){
		if(isNotEmpty(str)){
			List<String> list = new ArrayList<String>();
			String[] sts = str.split(split);
			for(String s : sts){
				if(isNotEmpty(s)){
					list.add(s);
				}
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 对于 aab{1},{0},bb 的消息进行转换
	 * @param source
	 * @param objects
	 * @return
	 */
	public static String format(String source,Object... objects){
		 MessageFormat mf = new MessageFormat(source);
		 return mf.format(objects);
	}
	
	
	/**
	 * 对列表类转换成字符串，并增加序号
	 * @param Iterator
	 * @param separator
	 * @return
	 */
	public static String joinWithSeq(List<String> list, char separator){
		String totalError  = "";
		for (int i = 1; i < list.size()+1; i++) {
			totalError += i+"."+ list.get(i-1) +separator; 
		}
		return totalError;
	}
	
	/**
	 * 判断是否整数（包括正整数、负整数、0）
	 * @param str
	 * @return
	 */
	public static boolean  isNumer(String str){
		if (StringUtils.isBlank(str))  {
			return  false;
		}
		String matchStr = "^-?\\d+$";
        Pattern pattern = Pattern.compile(matchStr);
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
              return false;
        }
        return true;
	}
	
	/**
	 * 判断是否正整数
	 * @param str
	 * @return
	 */
	public static boolean  isPositiveNumber(String str){
		if (StringUtils.isBlank(str))  {
			return  false;
		}
		String matchStr = "^\\d+$";
		Pattern pattern = Pattern.compile(matchStr);
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() )
		{
			return false;
		}
		return true;
	}
	
	public static boolean isPositiveIntegerOrZero(String str) {
		try {
			int num = Integer.valueOf(str);
			return num >= 0;
		} catch (Exception e) {
			return  false;
		}
	}
	
	public static boolean isPositiveLongOrZero(String str) {
		try {
			long num = Long.valueOf(str);
			return num >= 0;
		} catch (Exception e) {
			return  false;
		}
	}
	
	public static String toString(Object[] objs) {
		if(objs == null || objs.length == 0) {
			return "";
		}
		
		List<String> strList = new ArrayList<String>(objs.length);
		for(Object obj : objs) {
			if(null != obj) {
				strList.add(obj.toString());
			} else {
				strList.add("null");
			}
		}
		return ListUtils.toString(strList, ", ");
	}
	
	public static String trimByLength(String str, int length){
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		
		if (str.length() <= length) {
			return str;
		}
		
		return str.substring(0, length);
	}
	
	/**
	 * @Description: 根据所给的字符串与编码规范,将字符串解码
	 * @param str
	 * @param codeFormat
	 * @return String
	 * @author liangyongjian
	 * @date 2017-09-24 上午11:51:01
	 * @version V1.0
	 */
	public static String decode(String str, String codeFormat) throws UnsupportedEncodingException {
		if (isBlank(str) || isBlank(codeFormat))
			return "";
		return URLDecoder.decode(str, codeFormat);
	}
	
	/**
	 * @Description: 如果字符串是null或"null"，则返回空值""
	 * @param str
	 * @return String
	 * @author liangyongjian
	 * @date 2017年9月28日 下午1:11:35
	 * @version V1.0
	 */
	public static String replaceNullValue (String str) {
		if (str == null) return "";
		return str;
	}
	
	/**
	 * @Description: 计算百分比，并设置百分数精确度
	 * @param a
	 * @param b
	 * @param bits
	 * @return String
	 * @author liangyongjian
	 * @date 2017年9月28日 下午8:43:01
	 * @version V1.0
	 */
	public static String formatPercent(double a, double b, int bits) {
		//计算百分比
		double percent = a / b;
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度 
		nt.setMinimumFractionDigits(bits);
		return nt.format(percent);
	}
	
	public static void main(String[] args) {
		String eventTypeArr = "12345";
		eventTypeArr = eventTypeArr.substring(0, 4);
		System.out.println(eventTypeArr);
	}
	
}
