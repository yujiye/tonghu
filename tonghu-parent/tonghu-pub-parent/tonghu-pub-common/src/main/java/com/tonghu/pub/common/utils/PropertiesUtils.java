package com.tonghu.pub.common.utils;

import com.tonghu.pub.common.constant.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.Map.Entry;

/**
 * @ClassName: PropertiesUtils
 * @Description: 根据资源文件路径读取文件，转换为Map类型对象
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class PropertiesUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

	private PropertiesUtils() {}

	/**
	 * 根据资源文件路径读取
	 * @param path
	 * @return Map<String,String>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public static Map<String, String> prop2Map(String path) {
		LOGGER.debug("根据资源文件路径读取文件，转换为Map类型对象: {}", path);
		return prop2Map(path, Constant.DEFAULT_ENCODE.STRING_UTF8);
	}

	/**
	 * 根据资源文件路径读取
	 * @param path
	 * @param encode
	 * @return Map<String,String>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public static Map<String, String> prop2Map(String path, String encode) {
		Map<String, String> map = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream ins = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
		Reader reader = null;
		try {
			reader = new InputStreamReader(ins, StringUtils.isEmpty(encode) ? Constant.DEFAULT_ENCODE.STRING_UTF8 : encode);
			prop.load(reader);
			Set<Entry<Object, Object>> set = prop.entrySet();
			Iterator<Entry<Object, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				map.put((String) entry.getKey(), (String) entry.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("根据资源文件路径读取文件，转换为Map类型对象：{}，在读取的过程中出现异常: {}", path, e);
		} finally {
			try {
				ins.close();
				reader.close();
			} catch (IOException e) {
				LOGGER.error("根据资源文件路径读取文件，转换为Map类型对象：{}，在关闭文件流对象时出现异常: {}", path, e);
			}
		}
		return map;
	}
}
