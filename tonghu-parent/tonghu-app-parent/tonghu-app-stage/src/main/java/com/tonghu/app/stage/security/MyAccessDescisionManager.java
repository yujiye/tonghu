package com.tonghu.app.stage.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Description: 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-9-20 下午07:06:17
 */
public class MyAccessDescisionManager implements AccessDecisionManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccessDescisionManager.class);

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) 
			throws AccessDeniedException,InsufficientAuthenticationException {
		LOGGER.debug("MyAccessDescisionManager.decide() 验证用户是否具有一定的权限 ");
		if(configAttributes == null) 
			return;
		//所请求的资源（URL）拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext()){
			//访问所请求资源所需要的权限  
            String needPermission = iterator.next().getAttribute();
            LOGGER.debug("本次请求需要的权限包含： " + needPermission);
			for(GrantedAuthority ga : authentication.getAuthorities()){
				if(needPermission.equals(ga.getAuthority())){
					LOGGER.debug("当前用户拥有访问该资源的权限： " + ga.getAuthority());
					return;
				}
			}
		}
		LOGGER.error("MyAccessDescisionManager：decide 没有权限访问！");
		throw new AccessDeniedException("MyAccessDescisionManager：decide 没有权限访问！");
	}

	@Override
	public boolean supports(ConfigAttribute configAttribute) {
		LOGGER.debug("MyAccessDescisionManager.supports() 角色id：{}", configAttribute.getAttribute());
		return true;
	}

	/**
	 * 被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		LOGGER.debug("MyAccessDescisionManager.supports(Class<?> clazz)");
		return true;
	}

}
