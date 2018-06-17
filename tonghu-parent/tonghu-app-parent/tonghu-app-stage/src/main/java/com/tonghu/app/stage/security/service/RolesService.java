package com.tonghu.app.stage.security.service;

import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.security.po.ResourceModule;
import com.tonghu.pub.model.security.po.Roles;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 系统角色信息 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:19:55
 */
public interface RolesService {
	
	/**
	 * 获取所有的系统角色信息
	 * @return List<Roles>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午04:37:29
	 * @version V1.0
	 */
	public List<Roles> getAllRoles();
	
	/**
	 * 根据查询参数获取角色信息 用于角色管理页面
	 * @param model
	 * @param method
	 * @param request
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:41:46
	 * @version V1.0
	 */
	public void getRolesInfoForInitPage(ModelMap model, String method, HttpServletRequest request);
	
	/**
	 * 根据角色id获取角色信息 扩展信息
	 * @param roleId
	 * @return Roles
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午04:26:34
	 * @version V1.0
	 */
	public Roles getExtendRolesById(Long roleId);
	
	/**
	 * 添加新角色
	 * @param role
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午04:59:53
	 * @version V1.0
	 */
	public boolean addNewRole(Roles role, HttpServletRequest request, PubModelMap modelMap);

	/**
	 * 根据角色id修改角色信息
	 * @param role
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午05:01:45
	 * @version V1.0
	 */
	public boolean updateRoleInfo(Roles role, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 根据角色id删除角色信息
	 * @param roleId
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午06:02:47
	 * @version V1.0
	 */
	public boolean removeRoleInfo(Long roleId, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 根据角色id获取角色的权限信息
	 * @param roleId
	 * @return List<ResourceModule>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:06:15
	 * @version V1.0
	 */
	public List<ResourceModule> getAllResourcesByRole(Long roleId);
	
	/**
	 * 为角色分配权限
	 * @param roleId
	 * @param request
	 * @param resourceIds
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:33:54
	 * @version V1.0
	 */
	public boolean assignRoleAuth(Long roleId, HttpServletRequest request, String resourceIds, PubModelMap modelMap);
}
