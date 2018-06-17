package com.tonghu.pub.common.constant;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 静态资源
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午9:48:23
 */
public class Constant {public static final String INNER_SERIAL_THREAD_LOCAL = "innerSerialThreadLocal";

	public static final String INNER_SERIAL = "innerSerial";

	public static final String CLIENTIP = "operatorIp";

	public static final String RESULT_CODE = "resultCode";

	public static final String RESULT_MSG = "resultMsg";

	public static final String SEPARATOR = java.io.File.separator;

	public static final String WRAP = "\r\n";

	//  系统默认编码
	public static final class DEFAULT_ENCODE {

		public static final String STRING_GBK = "GBK";

		public static final String STRING_UTF8 = "UTF-8";

		public static final Charset CHARSET_GBK = Charset.forName(STRING_GBK);

		public static final Charset CHARSET_UTF8 = Charset.forName(STRING_UTF8);
	}

	public static final class DEFAULT_CONTENT_TYPE {

		public static final String CONTENT_TYPE_GBK = "text/html; charset=GBK";

		public static final String CONTENT_TYPE_UTF8 = "text/html; charset=UTF-8";
	}

	// 系统成功码
	public static final class COMMON_SUCCESS {

		public static final Integer SUCCESS_CODE = 10000;

		public static final String SUCCESS_MSG = "处理成功!";

	}

	// 处理校验错误编码
	public static final class COMMON_ERROR {

		public static final Integer ERROR_CODE = 10001;

		public static final String ERROR_MSG = "数据校验失败!";

	}

	// 系统失败码
	public static final class COMMON_FAIL {

		public static final Integer FAIL_CODE = 10002;

		public static final String FAIL_MSG = "处理失败!";

	}

	// 系统异常码
	public static final class COMMON_EXCEPTION {

		public static final Integer EXCEPTION_CODE = 10003;

		public static final String EXCEPTION_MSG = "系统内部异常!";

	}

	// 系统未处理
	public static final class COMMON_UNFOUND {

		public static final Integer UNFOUND_CODE = 10004;

		public static final String UNFOUND_MSG = "未发现处理信息!";

	}

	// 系统异常码
	public static final class PERSISTENCE_ERROR {

		public static final Integer ERROR_CODE = 10005;

		public static final String ERROR_MSG = "数据库保存失败!";

	}

	// 用户登录异常所对应的提示信息
	public static final class LONGIN_EXCEPTION {

		public static final Map<String, String> LONGIN_EXCEPTION_MAP = new HashMap<String, String>();

		static{
			LONGIN_EXCEPTION_MAP.put("USERNAME_IS_NOT_EXIST", "用户名不存在");
			LONGIN_EXCEPTION_MAP.put("BadCredentialsException", "密码错误");
			LONGIN_EXCEPTION_MAP.put("USERNAME_IS_NULL", "用户名为空");
			LONGIN_EXCEPTION_MAP.put("PASSWORD_IS_NULL", "密码为空");
			LONGIN_EXCEPTION_MAP.put("LockedException", "账户被锁，请与系统管理人员联系进行解锁");
			LONGIN_EXCEPTION_MAP.put("NOT_SUPPORTED_GET", "不支持GET方式调用");
		}

	}

	// 分页默认值
	public static final class PAGINATION_DEFAULT {
		// 默认页
		public static final Integer DEFAULT_CURRENT_PAGE = 1;

		// 默认页行数
		public static final Integer DEFAULT_PER_PAGE = 10;
	}

	// 默认类型值
	public static final class DEFAULT_TYPE_VALUE {
		// 默认所有值
		public static final Integer DEFAULT_ALL_TYPE = 9999;
	}

	// 特殊字符
	public static final class SPECIAL_CHARACTER {

		public static final String NEWLINE_CHARACTER = "\n";

		public static final String TABLE_CHARACTER = "\t";
		// ;
		public static final String SEMICOLON = ";";

		// :
		public static final String COLON = ":";

		// ,
		public static final String COMMA = ",";
		//'
		public static final String SINGLE_MARK = "'";

		//"
		public static final String DOUBLE_MARK = "\"";

		// ?
		public static final String QUESTION = "?";

		// >>
		public static final String DOUBLE_GREATER = ">>";

		// -
		public static final String SHORT_LINE = "-";

		// >>
		public static final String DOUBLEARROW = ">>";

		// _
		public static final String UNDER_LINE = "_";

		//
		public static final String BLANK = " ";

		// /
		public static final String SOLIDUS = "/";

		// 句点
		public static final String PERIOD = ".";

		// 竖线
		public static final String VERTICAL_LINE = "|";

		// 星号
		public static final String STAR = "*";

		public static final String PARAMETER_SEPARATOR = "&";

		// empty
		public static final String EMPTY = "empty";
	}
	
}
