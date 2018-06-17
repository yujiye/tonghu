package com.tonghu.pub.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 处理字符串的工具类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-28下午08:24:55
 */
public class MyStringUtils extends StringUtils {
	
	private static final Md5PasswordEncoder MD5 = new Md5PasswordEncoder();
	
	/**
	 * 生成MD5加密后的字符串
	 * @param rawPass
	 * @param salt
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-28 下午09:32:52
	 * @version V1.0
	 */
	public static String createMd5Str(String rawPass, Object salt) {
		return MD5.encodePassword(rawPass, salt);
	}
	
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
	
	//
	public static boolean isNumer(String str){
		if (MyStringUtils.isBlank(str))  {
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
	public static boolean isPositiveNumber(String str){
		if (MyStringUtils.isBlank(str))  {
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
	
	/**
	 * 截取字符串的长度为所给的长度
	 * @param str
	 * @param maxLength
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-4 下午08:26:21
	 * @version V1.0
	 */
	public static String cutStr(String str, int maxLength) {
		if (str != null && str.length() > maxLength) {
			str = str.substring(0, maxLength);
		}
		return str;
	}
	
	/**
	 * 把字符串转换为集合
	 * @param str
	 * @param seprator
	 * @return List<Long>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-10 下午03:56:57
	 * @version V1.0
	 */
	public static List<Long> transStrToLongList(String str, String seprator){
		if (str != null && str.equals("")) {
			return null;
		}
		String[] array = str.split(seprator);
		List<Long> list = new ArrayList<Long>();
		for (String s : array) {
			list.add( (s == null || s.trim().equals("")) ? null : new Long(s.trim()));
		}
		return list;
	}
	
	public static List<Integer> transStrToIntegerList(String str, String seprator){
		if (str != null && str.equals("")) {
			return null;
		}
		String[] array = str.split(seprator);
		List<Integer> list = new ArrayList<Integer>();
		for (String s : array) {
			list.add( (s == null || s.trim().equals("")) ? null : new Integer(s.trim()));
		}
		return list;
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-10 下午08:15:50
	 * @version V1.0
	 */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++) 
			if (!Character.isDigit(str.charAt(i))) return false;
		return true;
	}
	
	/**
	 * 获取两个数值相除后的百分数 给定百分数的小数点后N位
	 * @param m
	 * @param n
	 * @param precision
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-21 下午11:14:14
	 * @version V1.0
	 */
	public static String getDecimal(BigDecimal m, BigDecimal n, int precision) {
		if (n.compareTo(new BigDecimal(0)) == 0) return "";
		if (m.compareTo(new BigDecimal(0)) == 0) return "0.00%";
		BigDecimal k = new BigDecimal(m.doubleValue() / n.doubleValue() * 100);
		String value = String.valueOf((k.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue()));
		if (value != null && !value.equals("") && value.indexOf(".") > 0) {
			if ((value.length() - value.indexOf(".")) > (precision - 1)) {
				String temp = "";
				for (int i = 0; i < (value.length() - value.indexOf(".")); i++) {
					temp = value + "0";
				}
				value = temp;
			}
			value += "%";
		}
		return value;
	}
	
	public static String getDecimalForInteger(Integer m, Integer n, int precision) {
		if (m != 0 && n == 0) return "--";
		if (m == 0) return "0.00%";
		BigDecimal k = new BigDecimal(m.doubleValue() / n.doubleValue() * 100);
		String value = String.valueOf((k.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue()));
		if (value != null && !value.equals("") && value.indexOf(".") > 0) {
			if ((value.length() - value.indexOf(".")) > (precision - 1)) {
				String temp = "";
				for (int i = 0; i < (value.length() - value.indexOf(".")); i++) {
					temp = value + "0";
				}
				value = temp;
			}
			if (value != null && value.indexOf(".") > 0 && value.substring(value.indexOf(".")).length() > precision) {
				value = value.substring(0, value.length() - (value.substring(value.indexOf(".")).length() - precision) + 1);
			}
			value += "%";
		}
		return value;
	}
	
	/**
	 * 如果字符串是null或"null"，则返回空值""
	 * @param str str : 需要替换的字符串
	 * @return String str ：空值或字符串str
	 * @Author liangyongjian
	 * @Version V1.0
	 * @Create at 2017-09-27 下午05:38:54
	 */
	public static String replaceNullValue (String str) {
		if (str == null) return "";
		return str;
	}
	
	/**
	 * 转义双引号为HTML的代码
	 * @param str
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-28 下午08:37:47
	 * @version V1.0
	 */
	public static String replaceQuotationValue(String str) {
		if (str == null) return "";
		str = str.replaceAll("\"", "&quot;");
			
		return str;
	}


	/**
	 * 判断是否为数字，且最多12位整数，8位小数
	 * @param str
	 * @return
	 */
	public static boolean checkDecimalForTongHu(String str){
		if (StringUtils.isBlank(str)) {
			return false;
		}

		Pattern pattern=Pattern.compile("^(-)?[0-9]{1,12}+(.[0-9]{1,8})?$");
		Matcher match=pattern.matcher(str);
		if(match.matches()==false){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 去掉小数点后无用的0 以及末尾是小数点，则去掉小数点
	 * @param str
	 * @return
	 */
	public static String subZeroAndDot(String str){
		if (StringUtils.isBlank(str)) {
			return str;
		}
		if(str.indexOf(".") > 0){
			str = str.replaceAll("0+?$", "");//去掉多余的0
			str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return str;
	}


	/**
	 * 判断当前字符串是否为有效的数字
	 * 1、若为小数，整数部分最多12位，小数部分最多8位
	 * 2、若为正整数或负整数，数字最多为12位
	 * @param str
	 * @return
	 */
	public static boolean isValidDecimalForTongHu(String str) {

		str = subZeroAndDot(str);

		if (StringUtils.isBlank(str)) {
			return false;
		}
		// 按照小数对待
		if (str.indexOf(".") > 0) {
			return checkDecimalForTongHu(str);
		} else {
			//按照整数对待，长度不可大于12
			if (str.length() > 12) {
				return false;
			}
			return isPositiveNumber(str);
		}
	}

	public static boolean isValidLongNumberForTongHu(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}

		if (!isPositiveNumber(str)) {
			return false;
		}

		try {
			Long.parseLong(str);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	public static String getFourLengthStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (str.indexOf(".") > -1 && str.length() > 5) {
			// 通过小数点的位置，得知精确到几位
			int indexOfDot = str.indexOf(".");
			BigDecimal num = new BigDecimal(str);
			if (indexOfDot > 4) {
				// 不要小数了
				str = MyStringUtils.subZeroAndDot(String.valueOf(
						num.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()));
			} else {
				str = MyStringUtils.subZeroAndDot(String.valueOf(
						num.setScale(4 - indexOfDot, BigDecimal.ROUND_HALF_UP).floatValue()));
			}
		}
		return str;
	}

	public static String getNLengthStr(String str, int length) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (length == 2) {
			// 需要的长度为2，则不能再带有小数，只能取整了
			BigDecimal num = new BigDecimal(str);
			str = MyStringUtils.subZeroAndDot(String.valueOf(
					num.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()));
		} else if (str.indexOf(".") > -1 && str.length() > length) {
			// 通过小数点的位置，得知精确到几位
			int indexOfDot = str.indexOf(".");
			BigDecimal num = new BigDecimal(str);
			if (indexOfDot > (length - 1)) {
				// 不要小数了
				str = MyStringUtils.subZeroAndDot(String.valueOf(
						num.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()));
			} else {
				str = MyStringUtils.subZeroAndDot(String.valueOf(
						num.setScale((length - 1) - indexOfDot, BigDecimal.ROUND_HALF_UP).floatValue()));
			}
		}
		return str;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static void main(String[] args) {
//		System.out.println(checkDecimalForTongHu("-123456789012.111111111"));
//		System.out.println(isValidDecimalForTongHu("0.00000000"));
//		System.out.println(new BigDecimal(2073384677.12000000).stripTrailingZeros().toPlainString());
//
//		String mainInfo = "<Scl1><Col>{0}</Col></Scl1><Scl1>共计项目</Scl1>{1}<Scl1>个</Scl1><Scl1>总面积</Scl1>" +
//				"{2}<Scl1>万平方米</Scl1><Scl1>总住户</Scl1>{3}<Scl1>万</Scl1>";
//		System.out.println(MessageFormat.format(mainInfo, "郑州", "12", "314", "12"));

		System.out.println(getFourLengthStr("12000000"));
	}

}
