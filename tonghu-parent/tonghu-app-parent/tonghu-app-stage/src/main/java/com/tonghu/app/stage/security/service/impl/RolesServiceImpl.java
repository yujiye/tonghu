package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.security.service.RoleResourceService;
import com.tonghu.app.stage.security.service.RolesService;
import com.tonghu.app.stage.security.service.UserRoleService;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.constant.EnumEditable;
import com.tonghu.pub.common.constant.EnumIsSuccess;
import com.tonghu.pub.common.constant.EnumOperatorType;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.po.CheckErrorDto;
import com.tonghu.pub.model.po.TotalInfo;
import com.tonghu.pub.model.security.po.*;
import com.tonghu.pub.model.security.po.query.RolesQuery;
import com.tonghu.pub.security.dao.RolesDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 系统角色信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:23:50
 */
@Service("rolesService")
public class RolesServiceImpl implements RolesService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesServiceImpl.class);

	@Resource
	private RolesDao rolesDao;

	@Resource
	private InputConfig inputConfig;
	
	@Resource
	private SysLogContext sysLogContext;
	
	@Resource
	private SystemConstant systemConstant;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleResourceService roleResourceService;
	
	@Override
	public List<Roles> getAllRoles() {
		LOGGER.debug("Service层：获取所有的系统角色");
		return rolesDao.getAllRoles();
	}
	
	@Override
	public void getRolesInfoForInitPage(ModelMap model, String method, HttpServletRequest request) {
		LOGGER.debug("Service层：根据查询参数获取角色信息 用于角色管理页面");
		RolesQuery rolesQuery = new RolesQuery();
		//以id升序排序
		rolesQuery.setSortBy("id");
		rolesQuery.setSortType("1");
		if (method.equals("POST")) {
			String pageNum = request.getParameter("pageNumInput");
			if (! StringUtils.isBlank(pageNum)) {
				rolesQuery.setPage(Integer.parseInt(pageNum));
			}
			String roleName = request.getParameter("roleNameSearch");
			if (! StringUtils.isBlank(roleName)) {
				rolesQuery.setRoleName(roleName);
				model.addAttribute("roleName", roleName);
			}
		}
		Integer totalCount = rolesDao.getRolesCountByQuery(rolesQuery);
		TotalInfo totalInfo = new TotalInfo(totalCount, rolesQuery.getPageSize(),
				rolesQuery.getPage(), rolesQuery.getStartNum());
		model.addAttribute("totalInfo", totalInfo);
		List<Roles> rolesList = rolesDao.getRolesInfoByQuery(rolesQuery);
		model.addAttribute("rolesList", rolesList);
	}
	
	@Override
	public Roles getExtendRolesById(Long roleId) {
		LOGGER.debug("Service层：根据角色id获取角色扩展信息");
		Roles eRoles = rolesDao.getRoleInfoById(roleId);
		if (eRoles == null || eRoles.getId() == null) {
			LOGGER.debug("根据角色id获取角色扩展信息，没有查取到角色信息：roleId {}", roleId);
			return null;
		}

		//获取创建和修改角色信息的角色名称
		if (eRoles.getCreateUserId() != null || eRoles.getUpdateUserId() != null) {
			List<Long> userIdList = new ArrayList<Long>();
			if (eRoles.getCreateUserId() != null) {
				userIdList.add(eRoles.getCreateUserId());
			}
			if (eRoles.getUpdateUserId() != null) {
				userIdList.add(eRoles.getUpdateUserId());
			}
			List<Users> usersList = systemConstant.getUserInfoByUserIdList(userIdList);
			if (usersList != null && usersList.size() > 0) {
				for (Users user : usersList) {
					if (eRoles.getCreateUserId() != null && user.getId().compareTo(eRoles.getCreateUserId()) == 0) {
						eRoles.setCreateUserName(user.getTrueName());
					} 
					if (eRoles.getUpdateUserId() != null && user.getId().compareTo(eRoles.getCreateUserId()) == 0) {
						eRoles.setUpdateUserName(user.getTrueName());
					}
				}
			}
		}
		return eRoles;
	}
	
	@Override
	public boolean addNewRole(Roles role, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：新增角色信息");
		String sysLogCause = "";
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		boolean result = false;
		//过滤掉角色信息中各个属性值的前后空格
		role.trim();
		if (this.checkNewRole(role, modelMap)) {
			//通过校验，开始进行更新
			role.setCreateUserId(user.getId());
			Integer resultNum = rolesDao.addNewRole(role);
			if (resultNum.compareTo(new Integer(1)) == 0) {
				//将新的角色信息更新到内存中
				systemConstant.setRolesList();
				result = true;
			} else {
				sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
				modelMap.put("status", "failure");
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.ADD.getType(), "新增角色信息：" + role.getRoleName(),
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean updateRoleInfo(Roles role, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：根据角色id更新角色信息");
		boolean result = false;
		String sysLogCause = "";
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		//过滤掉角色信息中各个属性值的前后空格
		role.trim();
		if (this.checkUpdateRole(role, modelMap)) {
			//通过校验，开始进行更新
			role.setUpdateUserId(user.getId());
			Integer resultNum = rolesDao.updateRoleInfo(role);
			if (resultNum.compareTo(new Integer(1)) == 0) {
				//将新的角色信息更新到内存中
				systemConstant.setRolesList();
				result = true;
			} else {
				sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
				modelMap.put("status", "failure");
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), "更新角色信息：" + role.getRoleName(), 
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean removeRoleInfo(Long roleId, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：根据角色id删除角色信息 物理删除");
		boolean result = false;
		boolean isHave = false;
		String sysLogCause = "";
		Roles roles = null;
		List<Roles> rolesList = systemConstant.getRolesList();
		for (Roles r : rolesList) {
			if (r.getId().compareTo(roleId) == 0) {
				roles = r;
				isHave = true;
			}
		}
		if (!isHave) {
			modelMap.put("status", "failure");
			modelMap.put("data", "不存在该角色记录！");
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该角色记录";
		} else {
			if (this.checkRemoveRole(roleId, modelMap)) {
				//通过校验，开始进行删除
				Integer resultNum = rolesDao.deleteRoleById(roleId);
				if (resultNum.compareTo(new Integer(1)) == 0) {
					//将新的角色信息更新到内存中
					systemConstant.setRolesList();
					//内存中重新加载角色资源信息
					systemConstant.setRoleResourcesList();
					result = true;
				} else {
					modelMap.put("status", "failure");
				}
			} else {
				sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——" + modelMap.get("data");
			}
		}
		
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.DELETE.getType(), 
				"删除角色信息" + (result ? "：" + roles.getRoleName() : ""), 
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	@Transactional(value = "xinyuanTxManager", isolation = Isolation.REPEATABLE_READ,
			propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean assignRoleAuth(Long roleId, HttpServletRequest request, String resourceIds, PubModelMap modelMap){
		LOGGER.debug("Service层：根据角色id更新角色权限信息");
		boolean result = false;
		String sysLogCause = "";
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		boolean isHave = false;
		Roles roles = null;
		List<Roles> rolesList = systemConstant.getRolesList();
		for (Roles r : rolesList) {
			if (r.getId().compareTo(roleId) == 0) {
				roles = r;
				isHave = true;
			}
		}
		if (!isHave) {
			modelMap.put("status", "failure");
			modelMap.put("data", "不存在该角色记录！");
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该角色记录";
		} else {
			List<Long> resourceIdList = new ArrayList<Long>();
			if (checkAssignRoleAuth(roleId, resourceIds, modelMap, resourceIdList)) {
				//校验通过，可以更新角色的权限信息
				//先删除角色资源表中该角色现有的记录，然后再将新的资源记录插入 因此需要使用事务
				roleResourceService.deleteRoleResourceByRoleId(roleId);
				if (resourceIdList != null && resourceIdList.size() > 0) {
					RoleResource roleResource = null;
					for (Long resourceId : resourceIdList) {
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(resourceId);
						roleResource.setCreateUserId(user.getId());
						roleResourceService.addNewRoleResource(roleResource);
					}
				}
				result = true;
				//内存中重新加载角色资源信息
			} else {
				sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——" + modelMap.get("data");
			}
		}
		
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), 
				"为角色分配权限" + (result ? "：" + roles.getRoleName() : ""), 
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	/**
	 * 校验为角色分配权限的信息
	 * @param roleId
	 * @param resourceIds
	 * @param modelMap
	 * @param resourceIdList
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午04:44:55
	 * @version V1.0
	 */
	private boolean checkAssignRoleAuth(Long roleId, String resourceIds,
										PubModelMap modelMap, List<Long> resourceIdList) {
		//校验当前角色是否可编辑
		if (!checkRoleIsEditable(roleId)) {
			LOGGER.error("角色是系统默认角色，不可修改权限信息: roleId={}", roleId);
			modelMap.put("status", "failure");
			modelMap.put("data", "该角色是系统默认角色，不可修改权限信息！");
			modelMap.put("fresh", "false");
			return false;
		}
		
		//校验所传递来的资源id信息
		if (!resourceIds.equals("")) {
			String[] idArray = resourceIds.split(",");
			if (idArray != null && idArray.length > 0) {
				for (String id : idArray) {
					try {
						resourceIdList.add(new Long(id));
					} catch(Exception e) {
						modelMap.put("status", "error");
						modelMap.put("data", "资源参数有误，分配资源失败！");
						LOGGER.error("为角色分配资源的操作失败，参数资源id有误：{}", resourceIds);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 校验所给的角色是否可以被删除
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:47:12
	 * @version V1.0
	 */
	private boolean checkRemoveRole(Long roleId, PubModelMap modelMap){
		//校验角色是否为可编辑
		if (!checkRoleIsEditable(roleId)) {
			LOGGER.error("当前角色是系统默认角色，不可删除: roleId={}", roleId);
			modelMap.put("status", "failure");
			modelMap.put("data", "该角色是系统默认角色，不可删除！");
			modelMap.put("fresh", "false");
			return false;
		}
		//校验角色是否被分配给了具体的用户
		Integer resultNum = userRoleService.getUserRoleCountByRoleId(roleId);
		if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
			LOGGER.error("该角色已分配给用户，不可删除: roleId={}", roleId);
			modelMap.put("status", "failure");
			modelMap.put("data", "该角色已分配给用户，不可删除！");
			modelMap.put("fresh", "false");
			return false;
		}
		return true;
	}
	
	/**
	 * 校验新的角色信息
	 * @param role
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午05:19:29
	 * @version V1.0
	 */
	private boolean checkNewRole(Roles role, PubModelMap modelMap) {
		LOGGER.debug("Service层：校验角色名称的长度，备注的长度，校验新角色的名称是否已存在！");
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		
		this.checkRoleBaseInfo(role, errorInfoList);
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		LOGGER.debug("Service层：校验新的角色的名称是否已存在！");
		RolesQuery rolesQuery = new RolesQuery();
		rolesQuery.setRoleName(role.getRoleName());
		
		Integer resultNum = rolesDao.getRoleCountByName(rolesQuery);
		if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
			LOGGER.info("角色名称已经存在");
			errorInfoList.add(new CheckErrorDto("role_name", "角色名称已经存在"));
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		return true;
	}
	
	/**
	 * 校验更新后的角色信息
	 * @param role
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午05:18:17
	 * @version V1.0
	 */
	private boolean checkUpdateRole (Roles role, PubModelMap modelMap) {
		if (!checkRoleIsEditable(role.getId())) {
			LOGGER.error("该角色是系统默认角色，不可编辑: roleId={}", role.getId());
			modelMap.put("status", "failure");
			modelMap.put("data", "该角色是系统默认角色，不可编辑！");
			modelMap.put("fresh", "false");
			return false;
		}
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		this.checkRoleBaseInfo(role, errorInfoList);
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		LOGGER.debug("Service层：校验更新后角色的名称是否已存在！");
		RolesQuery rolesQuery = new RolesQuery();
		rolesQuery.setRoleName(role.getRoleName());
		rolesQuery.setId(role.getId());
		
		Integer resultNum = rolesDao.getRoleCountByName(rolesQuery);
		if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
			LOGGER.info("角色名称已经存在");
			errorInfoList.add(new CheckErrorDto("change_role_name", "角色名称已经存在"));
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		return true;
	}
	
	/**
	 * 校验角色的基本信息
	 * @param role
	 * @param errorInfoList
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午05:44:13
	 * @version V1.0
	 */
	private void checkRoleBaseInfo(Roles role, List<CheckErrorDto> errorInfoList) {
		Integer length = null;
		String prefix = "";
		if (role.getId() != null)
			prefix = "change_";
		//角色名称
		if (StringUtils.isBlank(role.getRoleName())) {
			LOGGER.info("请输入角色名");
			errorInfoList.add(new CheckErrorDto(prefix + "role_name", "请输入角色名"));
		} else {
			length = new Integer(role.getRoleName().length());
			if (length.compareTo(inputConfig.getRoleNameLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "role_name", "角色名称长度不能大于"
						+ inputConfig.getRoleNameLength() + "位"));
			}
		}
		
		//角色备注信息
		if (!StringUtils.isBlank(role.getNote())) {
			length = new Integer(role.getNote().length());
			if (length.compareTo(inputConfig.getNoteLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "role_note", "备注长度不能大于" 
						+ inputConfig.getNoteLength() + "位"));
			}
		}
	}
	
	@Override
	public List<ResourceModule> getAllResourcesByRole(Long roleId) {
		//首先，获取当前角色能够访问的资源信息
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(roleId);
		List<RoleResource> roleResourceList = roleResourceService.getRoleResourceInfoByRoleIdList(roleIdList);
		
		//获取到资源模块与模块下资源的集合
		List<ResourceModule> moduleList = systemConstant.getResourceModuleList();
		if (moduleList == null || moduleList.size() == 0) {
			LOGGER.error("获取菜单资源信息出现错误！");
			return null;
		} 
		//在系统所有资源的集合中，为每个元素打上标记 表明当前角色是否有权可以访问该资源
		if (roleResourceList != null && roleResourceList.size() > 0) {
			for (ResourceModule module : moduleList) {
				if (module.getResourceList() != null && module.getResourceList().size() > 0) {
					for (Resources resources : module.getResourceList()) {
						for (RoleResource roleRes : roleResourceList) {
							if (roleRes.getResourceId().compareTo(resources.getId()) == 0) {
								resources.setHaveAuth(true);
								break;
							}
						}
					}
				}
			}
		}
		return moduleList;
	}
	
	/**
	 * 校验所给角色是否能够被编辑
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:35:51
	 * @version V1.0
	 */
	private boolean checkRoleIsEditable(Long roleId){
		boolean isEditable = false;
		List<Roles> rolesList = systemConstant.getRolesList();
		if (rolesList != null && rolesList.size() > 0) {
			for (Roles role : rolesList) {
				if(role.getId().compareTo(roleId) == 0){
					if (role.getEditable().compareTo(EnumEditable.EDITABLE_YES.getStatus()) == 0)
						isEditable = true;
					break;
				}
			}
		}
		return isEditable;
	}
}
