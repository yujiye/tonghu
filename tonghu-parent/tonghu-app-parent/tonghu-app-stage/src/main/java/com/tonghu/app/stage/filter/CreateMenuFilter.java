package com.tonghu.app.stage.filter;

import com.tonghu.app.stage.security.service.ResourceModuleService;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.spring.SpringContextUtils;
import com.tonghu.pub.model.security.po.ResourceModule;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.model.security.po.RoleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author liangyongjian
 * @desc 过滤器 用于生成用户操作界面中的左侧菜单
 * @create 2017-09-26 12:55
 **/
public class CreateMenuFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateMenuFilter.class);
	
	private ResourceModuleService resourceModuleService;

	private SystemConstant systemConstant;

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if ("/getProjectInfo.do".equals(request.getServletPath())) {
			filterChain.doFilter(request, response);
		} else {
			if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {
				response.sendRedirect(request.getContextPath() + "/login?msg=error");
			} else {
				if (request.getSession().getAttribute("USER_MENU") == null
						|| String.valueOf(request.getSession().getAttribute("USER_MENU")).equals("")) {
					this.createMenu(request);
				}

				if (request.getSession().getAttribute("USER_MENU") == null
						|| String.valueOf(request.getSession().getAttribute("USER_MENU")).equals("")) {
					LOGGER.error("已经过生成菜单的逻辑，但是菜单没有生成");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/login?msg=error");
					request.setAttribute("USER_MENU_ERROR", "error");
					dispatcher.forward(request, response);
					return;
				}
				filterChain.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	/**
	 * 生成用户操作界面中的左侧菜单, 并将菜单放置到request对象中
	 * @param request
	 * @return void
	 * @author liangyongjian
	 * @date 2017-09-26 12:55
	 */
	private void createMenu(HttpServletRequest request) {
		// 过滤用户对系统页面MVC资源的访问，生成当前用户操作界面中的左侧菜单，并放置到当前用户的request对象中，
		// 一次生成后，在session有效期，该菜单不会再重新生成
		LOGGER.debug("用户的菜单尚未生成，这里要进行生成！");
		
		ApplicationContext ctx = SpringContextUtils.getApplicationContext(request);

        if (resourceModuleService == null && ctx != null && ctx.getBean("resourceModuleService") != null)
        	resourceModuleService = (ResourceModuleService) ctx.getBean("resourceModuleService");

		if (systemConstant == null && ctx != null && ctx.getBean("systemConstant") != null)
			systemConstant = (SystemConstant) ctx.getBean("systemConstant");

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails.getAuthorities() != null && userDetails.getAuthorities().size() > 0) {
			String roleIds = userDetails.getAuthorities().toString();
			LOGGER.debug("当前用户所拥有的权限ID：{}", roleIds);
			//去掉首尾的"["和"]"
			roleIds = roleIds.substring(1);
			roleIds = roleIds.substring(0, roleIds.length() - 1);
			Set<Long> roleIdSet = new TreeSet<Long>();
			String[] roleIdArray = roleIds.split(",");

			for (int i = 0; i < roleIdArray.length; i++) {
				roleIdSet.add(new Long(roleIdArray[i]));
			}

			// 缓存中的key 形如： roleId1_roleId2_roleId3
			String roleIdsStr = "";
			for (Long roleId : roleIdSet) {
				roleIdsStr += roleId + "_";
			}
			roleIdsStr.substring(0, roleIdsStr.length() - 1);

			// 从缓存中获取 当前用户角色对应的菜单
			List<ResourceModule> menuList = systemConstant.getMenuByRoleId(roleIdsStr);

			if (!CollectionUtils.isEmpty(menuList)) {
				request.getSession().setAttribute("USER_MENU", menuList);
				return;
			}
			// 缓存中没有，自己生成

			// 可以展示给当前用户的菜单的id
			List<Long> resourceIdList = new ArrayList<Long>();


			// 找到那些可以作为菜单但又不需要权限的菜单
			List<Resources> isMenuAndNotRestrictResourcesList = systemConstant.getIsMenuAndNotRestrictResources();
			if (!CollectionUtils.isEmpty(isMenuAndNotRestrictResourcesList)) {
				for (Resources r : isMenuAndNotRestrictResourcesList) {
					resourceIdList.add(r.getId());
				}
			}

			// 给定角色id，找到角色所分配的权限的集合
			// 这些权限都可以作为菜单项 且需要权限才能访问，不需要权限就能访问的菜单不在这里
			List<RoleResource> roleResourceListByRoleId =
					systemConstant.getRoleResourceListByRoleId(new ArrayList<Long>(roleIdSet));
			if (!CollectionUtils.isEmpty(roleResourceListByRoleId)) {
				for(RoleResource rr : roleResourceListByRoleId){
					resourceIdList.add(rr.getResourceId());
				}
				List<Resources> resourcesList = systemConstant.getResourcesListByResourcesId(resourceIdList);
				if (!CollectionUtils.isEmpty(resourcesList)) {
					List<Long> resourceModuleIdList = null;
					Set<Long> resourceModuleIdSet = new TreeSet<Long>();
					for (Resources resources : resourcesList) {
						if (resources.getIsMenu().compareTo(new Integer(1)) == 0) {
							resourceModuleIdSet.add(resources.getModuleId());
						}
					}
					resourceModuleIdList = new ArrayList<Long>(resourceModuleIdSet);
					if (!CollectionUtils.isEmpty(resourceModuleIdList)) {
						List<ResourceModule> resourceModuleList =
							resourceModuleService.getResourceModuleInfoByIdList(resourceModuleIdList);
						for (ResourceModule module : resourceModuleList) {
							for (Resources resources : resourcesList) {
								if (module.getId().compareTo(resources.getModuleId()) == 0
										&& resources.getIsMenu().compareTo(new Integer(1)) == 0) {
									module.addResource(resources);
								}
							}
							
							//若模块下只有一个资源，且资源名称与模块名称相同，保留模块信息，舍去资源信息
							if (module.getResourceList() != null && module.getResourceList().size() == 1) {
								if (module.getModuleName().equals(module.getResourceList().get(0).getResourceName())) {
									module.setUrl(module.getResourceList().get(0).getResourceUrl());
									module.getResourceList().remove(0);
								}
							}
							
							if(module.getResourceList() != null && module.getResourceList().size() > 0) {
								//自动排个序 从大到小
								Collections.sort(module.getResourceList());
//								//将模块的URL置为模块下的第一个资源的URL
//								module.setUrl(module.getResourceList().get(0).getResourceUrl());
								//如果模块下资源的sortNum字段值为0，表示该资源菜单可以不展示出来，可由模块菜单代为展示
								if(module.getResourceList().get(0).getSortNum().compareTo(new Integer(0)) == 0) {
									module.getResourceList().remove(0);
								}
							}
						}
						//将菜单信息放入到request对象中
						request.getSession().setAttribute("USER_MENU", resourceModuleList);

						// 放入缓存中
						systemConstant.setMenuByRoleId(roleIdsStr, resourceModuleList);
					} else {
						LOGGER.error("生成菜单的过程中，用户所拥有的资源中没有可用于生成菜单的资源");
					}
					
				} else {
					LOGGER.error("生成菜单的过程中，没有获取到当前用户所拥有的资源信息");
				}
			} else {
				LOGGER.error("生成菜单的过程中，没有获取到当前用户所拥角色的资源id");
			}
		}
	}

}
