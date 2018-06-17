package com.tonghu.pub.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangyongjian
 * @desc AJAX请求过滤器 指引session过期的Ajax请求返回超时的提示
 * @create 2017-09-26 12:55
 **/
public class AjaxSessionTimeoutFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxSessionTimeoutFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 判断session里是否有用户信息
		LOGGER.info(req.getServletPath());
		if ("/getProjectInfo.do".equals(req.getServletPath())) {
			chain.doFilter(req, res);
		} else {
			if (req.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {
				LOGGER.info("此时用户的Session中没有信息，判定为没有登录或者session过期");
				// 如果是ajax请求响应头会有，x-requested-with；
				if (req.getHeader("x-requested-with") != null &&
						req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					LOGGER.info("此时访问类型为Ajax，直接返回session超时编码");
					res.setStatus(911);// 表示session timeout
				} else {
					chain.doFilter(req, res);
				}
			} else {
				chain.doFilter(req, res);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
