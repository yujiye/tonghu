package com.tonghu.pub.common.utils;

/**
 * @Package com.warning.app.job.utils
 * @ClassName: JobUtils
 * @Description: 作业工具类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午10:47:55
 */
public class JobUtils {

	//获得当前类执行名称
	public static <T> String getJobClassName(Class<T> c){
		String name = c.getName();
		if(name.contains(".")){
			return name.substring(name.lastIndexOf(".")+1);
		}
		return name;
	}
}
