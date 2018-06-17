package com.tonghu.app.stage.security;

import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.model.security.po.RoleResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @Description: TODO
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Resource
	private SystemConstant systemConstant;

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//从内存中查出角色权限信息
		List<RoleResource> roleResourceList = systemConstant.getRoleResourcesList();
		//从内存中查出系统资源信息
		List<Resources> resourcesList = systemConstant.getResourcesList();
		boolean isFind = false;
		Resources findResource = null;
		if (userDetails.getAuthorities() != null && userDetails.getAuthorities().size() > 0) {
			for (Object userRole : userDetails.getAuthorities().toArray()) {
				String userRoleId = String.valueOf(userRole);
				if (userRoleId != null) {
					for (RoleResource roleResource : roleResourceList) {
						if (roleResource.getRoleId().compareTo(new Long(userRoleId)) == 0) {
							for (Resources resources : resourcesList) {
								if (resources.getId().compareTo(roleResource.getResourceId()) == 0) {
									findResource = resources;
									isFind = true; break;
								}
							}
							if (isFind) break;
						}
						if (isFind) break;
					}
				}
				if (isFind) break;
			}
		}

		// 需要考虑那些不需要权限的菜单是否应该首先展示
		for (Resources resources : resourcesList) {
			if (resources.getIsMenu() == 1 && resources.getIsRestricted() == 0) {
				if (isFind) {
					if (resources.getModuleId() <= findResource.getModuleId()
							&& resources.getSortNum() <= findResource.getSortNum()) {
						findResource = resources;
					}
				} else {
					findResource = resources;
				}
				break;
			}
		}

		
		if (findResource != null) {
			response.sendRedirect(request.getContextPath() + findResource.getResourceUrl());
		} else {
			response.sendRedirect(request.getContextPath() + "/login?msg=error");
		}
    }  

}
