package com.tonghu.app.stage.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description: 一个自定义的filter
 * 必须包含authenticationManager,accessDecisionManager,securityMetadataSource三个属性，我们的所有控制将在这三个类中实现，解释详见具体配置
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
	
	
	//与spring-buick-activity-security.xml里的myFilter的属性securityMetadataSource对应，
	//其他的两个组件，已经在AbstractSecurityInterceptor定义
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		
		/**
		 * 核心的InterceptorStatusToken token = super.beforeInvocation(fi);
		 * 会调用我们定义的accessDecisionManager:decide(Object object)和securityMetadataSource:getAttributes(Object object)方法
		 * 
		 * super.beforeInvocation(fi) 执行的处理如下：
		 * 1.获取请求资源的权限 : MySecurityMetadataSource
		 * 	Collection<ConfigAttribute> attributes = securityMetadataSource.getAttributes(fi);
		 * 2.是否拥有权限  : MyAccessDecisionManager
		 * 	1) 获取安全主体，可以强制转换为UserDetails的实例
		 * 		Authentication authenticated = authenticateIfRequired();
		 * 		this.accessDecisionManager.decide(authenticated, fi, attributes);
		 * 	2) 用户拥有的权限GrantedAuthority
		 * 		Collection<GrantedAuthority> authenticated.getAuthorities()
		 */
		
		InterceptorStatusToken token = super.beforeInvocation(fi);
		
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}
	
	@Override
	public Class<? extends Object> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void destroy() {}

}
