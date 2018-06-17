package com.tonghu.pub.common.constant;

import com.tonghu.pub.common.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 将定义错误编码的配置文件读入，并转换为Map类型对象，放到内存中
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public class MappingErrorCode {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MappingErrorCode.class);

	private static Map<Integer, String> map = new HashMap<Integer, String>();
	
	private static final String propertiesName = "configs/tonghu-error-code.properties";
	
	static{
		LOGGER.debug("读取定义错误编码的配置文件：{}", propertiesName);
		try {
			Map<String, String> rstMap = PropertiesUtils.prop2Map(propertiesName);
			Set<String> set = rstMap.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
				String key = it.next();
				map.put(new Integer(key), rstMap.get(key));
			}
		} catch(Exception e) {
			LOGGER.error("读取定义错误编码的配置文件：{}，出现异常: {}", propertiesName, e);
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private MappingErrorCode(){}
	
	public static String getValue(Integer key){
		return map.get(key);
	}

}
