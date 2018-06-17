package com.tonghu.pub.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangyongjian
 * @desc 使浏览器不缓存页面的过滤器
 * @create 2017-09-26 12:55
 **/
public class ForceNoCacheFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		((HttpServletResponse) servletresponse).setHeader("Cache-Control", "no-cache");
	    ((HttpServletResponse) servletresponse).setHeader("Pragma", "no-cache");
	    ((HttpServletResponse) servletresponse).setDateHeader ("Expires", -1);
	    filterchain.doFilter(servletrequest, servletresponse);
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
