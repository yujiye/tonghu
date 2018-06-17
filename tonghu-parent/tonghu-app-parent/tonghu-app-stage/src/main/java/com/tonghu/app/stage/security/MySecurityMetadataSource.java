package com.tonghu.app.stage.security;


import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.spring.SpringContextUtils;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.model.security.po.ResourcesSub;
import com.tonghu.pub.model.security.po.RoleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * @Description: 资源源数据定义，即定义某一资源可以被哪些角色访问
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MySecurityMetadataSource.class);
	
	private AntPathMatcher urlMatcher = new AntPathMatcher();

	// 系统资源常量
	private SystemConstant systemConstant;
	

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	//构造函数 由spring调用
	public MySecurityMetadataSource() {
		ApplicationContext ctx = SpringContextUtils.getCurrentContext();

		if (ctx != null && ctx.getBean("systemConstant") != null && systemConstant == null)
			this.systemConstant = (SystemConstant) ctx.getBean("systemConstant");

		loadResourceDefine(false);
	}
	
	/**
	 * 加载角色资源列表数据 当系统初始化或者角色资源发生变化的时候调用
	 * @param isChange
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:43:54
	 * @version V1.0
	 */
	public void loadResourceDefine(boolean isChange) {
		if(resourceMap == null || isChange) {
			LOGGER.debug("MySecurityMetadataSource.loadResourcesDefine() 开始加载角色资源列表数据");

			// 获取所有的角色资源信息集合
			List<RoleResource> roleResourceList = systemConstant.getRoleResourcesList();
			if (roleResourceList == null || roleResourceList.size() == 0) {
				LOGGER.error("系统角色与资源关系为空，系统不能正常启动，请检查数据库！");
				throw new RuntimeException("系统角色与资源关系为空，系统不能正常启动，请检查数据库！");
			}
			//资源id set集合
			Set<Long> resourceIdSet = new HashSet<Long>();
			//角色id set集合
			Set<Long> roleIdSet = new HashSet<Long>();
			for(RoleResource roleResource : roleResourceList){
				//将资源id放入到set集合中，接下来方便查取资源的URL信息
				resourceIdSet.add(roleResource.getResourceId());
				//将角色id放入到set集合中，接下来方便配置角色默认的资源信息
				roleIdSet.add(roleResource.getRoleId());
			}
			List<Long> resourceIdList = new ArrayList<Long>(resourceIdSet);
			
			// 根据资源id获取资源集合
			List<Resources> resourcesList = systemConstant.getResourcesListByResourcesId(resourceIdList);
			
			if (resourcesList == null || resourcesList.size() == 0) {
				LOGGER.error("系统角色所对应的资源不存在，系统不能正常启动，请检查数据库！");
				throw new RuntimeException("系统角色所对应的资源不存在，系统不能正常启动，请检查数据库！");
			}
			
			ConfigAttribute configAttribute = null;
			Collection<ConfigAttribute> configAttributes = null;
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			
			//键值对： 键url 值roleId集合
			for (Resources resource : resourcesList) {
				configAttributes = new ArrayList<ConfigAttribute>();
				for (RoleResource roleResource : roleResourceList) {
					if (resource.getId().compareTo(roleResource.getResourceId()) == 0) {
						configAttribute = new SecurityConfig(roleResource.getRoleId().toString());
						configAttributes.add(configAttribute);
					}
				}
				resourceMap.put(resource.getResourceUrl(), configAttributes);
			}
			
			//根据资源id 获取资源下的子资源信息  拥有资源的访问权，则拥有资源下子资源的访问权
			List<ResourcesSub> subResourcesList = systemConstant.getResourcesSubListByResourcesId(resourceIdList);
			
			if (subResourcesList != null && subResourcesList.size() > 0) {
				for (ResourcesSub subResource : subResourcesList) {
					//子资源中可能会存在url一致的情况
					if (resourceMap.get(subResource.getSubResourceUrl()) != null) {
						configAttributes = resourceMap.get(subResource.getSubResourceUrl());
					} else {
						configAttributes = new ArrayList<ConfigAttribute>();
					}
					for (RoleResource roleResource : roleResourceList) {
						if (subResource.getResourceId().compareTo(roleResource.getResourceId()) == 0) {
							configAttribute = new SecurityConfig(roleResource.getRoleId().toString());
							if (!configAttributes.contains(configAttribute)) {
								configAttributes.add(configAttribute);
							}
						}
					}
					resourceMap.put(subResource.getSubResourceUrl(), configAttributes);
				}
			}
			
			//获取用户登录后默认可以访问的资源，不受角色限制
			List<Resources> nonRestrictedResourcesList = systemConstant.getNonRestrictedResources();
			
			if (nonRestrictedResourcesList != null && nonRestrictedResourcesList.size() > 0) {
				for (Resources resource : nonRestrictedResourcesList) {
					configAttributes = new ArrayList<ConfigAttribute>();
					for (Long roleId : roleIdSet) {
						configAttribute = new SecurityConfig(roleId.toString());
						configAttributes.add(configAttribute);
					}
					resourceMap.put(resource.getResourceUrl(), configAttributes);
				}
			}
		}
	}
	
	/**
	 * 返回所请求资源所需要的权限
	 * @param url
	 * @return Collection<ConfigAttribute>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午07:13:43
	 * @version V1.0
	 */
	public Collection<ConfigAttribute> getConfigAttributesByUrl(String url){
		LOGGER.debug("MySecurityMetadataSource:getAttributes() 请求地址为：{}", url);
		Iterator<String> it = resourceMap.keySet().iterator();
		while(it.hasNext()){
			String _url = it.next();
			if(_url.indexOf("?") != -1){
				_url = _url.substring(0, _url.indexOf("?"));
			}
			if(urlMatcher.match(url, _url))
				return resourceMap.get(_url);
		}
		return null;
	}

	//返回所请求资源所需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求的url地址
		String url = ((FilterInvocation)object).getRequestUrl();
		LOGGER.debug("MySecurityMetadataSource:getAttributes() 请求地址为：{}", url);
		Iterator<String> it = resourceMap.keySet().iterator();
		while(it.hasNext()){
			String _url = it.next();
			if(_url.indexOf("?") != -1){
				_url = _url.substring(0, _url.indexOf("?"));
			}
			if(urlMatcher.match(url, _url))
				return resourceMap.get(_url);
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
