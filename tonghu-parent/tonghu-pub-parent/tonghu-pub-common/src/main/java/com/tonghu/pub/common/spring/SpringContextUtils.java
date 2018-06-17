package com.tonghu.pub.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: spring环境工具 实现SpringContextUtils中context参数的初始化，供该类提供springContext的静态get方法使用 
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午03:58:56
 */
public class SpringContextUtils implements ApplicationContextAware {
	
	private SpringContextUtils() {}
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	/**
	 * 获取spring上下文
	 * @param 
	 * @return ApplicationContext
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午03:58:56
	 * @version V1.0
	 */
	public static ApplicationContext getCurrentContext() {
		return context;
	}

	/**
	 * 根据web请求获取spring上下文
	 * @param request
	 * @return ApplicationContext
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-28 下午03:58:56
	 * @version V1.0
	 */
	public static ApplicationContext getApplicationContext(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		return WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}

}
