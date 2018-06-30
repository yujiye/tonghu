package com.tonghu.app.stage.service;

import com.tonghu.app.stage.common.service.SysConfigService;
import com.tonghu.app.stage.security.service.*;
import com.tonghu.pub.common.utils.DateTimeUtils;
import com.tonghu.pub.model.common.po.SysConfig;
import com.tonghu.pub.model.security.po.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 系统常量管理 从数据库中取出的常用数据 如区域信息 车型信息
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
@Repository(value="systemConstant")
public class SystemConstant {

	@Resource
	private UsersService usersService;
	
	@Resource 
	private RolesService rolesService;

	@Resource
	private SysConfigService sysConfigService;

	@Resource
	private ResourcesService resourcesService;

	@Resource
	private ResourcesSubService resourcesSubService;

	@Resource
	private RoleResourceService roleResourceService;

	@Resource
	private ResourceModuleService resourceModuleService;


	/**
	 * @Fields ALL_RESOURCES_SUB_LIST : 所有的子资源集合
	 */
	private static List<ResourcesSub> ALL_RESOURCES_SUB_LIST = null;


	/**
	 * @Fields ALL_RESOURCES_LIST : 可以用作菜单的资源常量
	 */
	private static List<Resources> ALL_RESOURCES_LIST = null;
	
	/**
	 * @Fields ROLES_LIST : 角色常量
	 */
	private static List<Roles> ROLES_LIST = null;

	/**
	 * @Fields USERS_LIST : 用户信息常量LIST
	 */
	private static List<Users> USERS_LIST = null;

	/**
	 * @Fields USERS_MAP_WITH_KEY_ID : 用户信息常量MAP key 为 id
	 */
	private static Map<Long, Users> USERS_MAP_WITH_KEY_ID = null;
	
	/**
	 * @Fields ROLE_RESOURCES_LIST : 角色资源常量
	 */
	private static List<RoleResource> ROLE_RESOURCES_LIST = null;
	
	/**
	 * @Fields RESOURCE_MODULE_LIST : 系统资源模块与模块下资源的信息
	 */
	private static List<ResourceModule> RESOURCE_MODULE_LIST = null;

	/**
	 * @Fields MENU_BY_ROLE_IDS_MAP : 某些角色的菜单缓存 key为角色Id的字符串 如 id1_id2_id3
	 */
	private static Map<String, List<ResourceModule>> MENU_BY_ROLE_IDS_MAP = new HashMap<String, List<ResourceModule>>();

	/**
	 * @Fields SYS_CONFIG_LIST : 系统配置项常量集合
	 */
	private static List<SysConfig> SYS_CONFIG_LIST = null;

	/**
	 * @Fields SYS_CONFIG_MAP_WITH_KEY_ITEM : 系统配置项常量 MAP configItem 为key
	 */
	private static Map<String, SysConfig> SYS_CONFIG_MAP_WITH_KEY_ITEM = null;

	/**
	 * @Fields SYS_CONFIG_MAP_WITH_KEY_ID : 系统配置项常量 MAP id 为key
	 */
	private static Map<Long, SysConfig> SYS_CONFIG_MAP_WITH_KEY_ID = null;



//	/**
//	 * 获取 上传附件的最大限制
//	 * @return Integer
//	 * @throws
//	 * @author liangyongjian
//	 * @date 2017-09-24 上午12:35:48
//	 * @version V1.0
//	 */
	public Integer getMaxImportFileSize() {
//		if (MAX_IMPORT_FILE_SIZE == null)
//			this.setMaxImportFileSize();
		return 10;
	}

	public Integer getMaxUploadImageSize() {
		if(!CollectionUtils.isEmpty(getSysConfigMapWithKeyItem())) {
			if (getSysConfigMapWithKeyItem().containsKey("image_file_max_size")) {
				return Integer.parseInt(
						getSysConfigMapWithKeyItem().get("image_file_max_size").getConfigValue());
			}
		}
		return 10;
	}

	/**
	 * 获取潼湖API应用的地址前缀
	 * @return
	 */
	public String getTongHuApiUrlPrefix() {
		if(!CollectionUtils.isEmpty(getSysConfigMapWithKeyItem())) {
			if (getSysConfigMapWithKeyItem().containsKey("tonghu_api_url_prefix")) {
				return getSysConfigMapWithKeyItem().get("tonghu_api_url_prefix").getConfigValue();
			}
		}
		return "";
	}

	/**
	 * 获取角色信息集合
	 * @return List<Roles>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:46:53
	 * @version V1.0
	 */
	public List<Roles> getRolesList() {
		if (CollectionUtils.isEmpty(ROLES_LIST))
			this.setRolesList();
		return ROLES_LIST;
	}

	/**
	 * 生成角色信息集合
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:44:37
	 * @version V1.0
	 */
	public void setRolesList() {
		ROLES_LIST = rolesService.getAllRoles();
	}

	/**
	 * 获取用户信息集合
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:46:53
	 * @version V1.0
	 */
	public List<Users> getUsersList() {
		if (CollectionUtils.isEmpty(USERS_LIST))
			this.setUsersSysCons();
		return USERS_LIST;
	}

	/**
	 * 获取用户信息集合
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:46:53
	 * @version V1.0
	 */
	public Map<Long, Users> getUsersMapWithKeyId() {
		if (CollectionUtils.isEmpty(USERS_MAP_WITH_KEY_ID))
			this.setUsersSysCons();
		return USERS_MAP_WITH_KEY_ID;
	}

	/**
	 * 根据用户id获取用户信息
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:46:53
	 * @version V1.0
	 */
	public Users getUsersById(Long id) {
		if (!CollectionUtils.isEmpty(getUsersMapWithKeyId())) {
			return getUsersMapWithKeyId().get(id);
		}
		return null;
	}

	/**
	 * 根据用户id获取用户信息
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:46:53
	 * @version V1.0
	 */
	public List<Users> getUserInfoByUserIdList(List<Long> userIdList) {
		if (!CollectionUtils.isEmpty(getUsersMapWithKeyId())) {
			List<Users> usersList = new ArrayList<Users>();
			for (Long userId : userIdList) {
				if (getUsersMapWithKeyId().containsKey(userId)) {
					usersList.add(getUsersMapWithKeyId().get(userId));
				}
			}
			return usersList;
		}
		return null;
	}

	/**
	 * 生成用户信息集合
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:44:37
	 * @version V1.0
	 */
	public void setUsersSysCons() {
		USERS_LIST = usersService.getAllUsersInfo();
		if (!CollectionUtils.isEmpty(USERS_LIST)) {
			USERS_MAP_WITH_KEY_ID = new HashMap<Long, Users>();
			for (Users users : USERS_LIST) {


				USERS_MAP_WITH_KEY_ID.put(users.getId(), users);
			}
		}
	}

	/**
	 * 获取所有的资源信息
	 * @return
	 */
	public List<Resources> getAllResources() {
		if (CollectionUtils.isEmpty(ALL_RESOURCES_LIST)) {
			this.setAllResources();
		}
		return ALL_RESOURCES_LIST;
	}

	/**
	 * 设置所有的资源信息到常量中
	 */
	public void setAllResources() {
		ALL_RESOURCES_LIST = resourcesService.getAllResources();
	}


	/**
	 * 获取所有的子资源信息
	 * @return
	 */
	public List<ResourcesSub> getAllResourcesSub() {
		if (CollectionUtils.isEmpty(ALL_RESOURCES_SUB_LIST)) {
			this.setAllResourcesSub();
		}
		return ALL_RESOURCES_SUB_LIST;
	}

	/**
	 * 获取所有的子资源集合
	 */
	public void setAllResourcesSub() {
		ALL_RESOURCES_SUB_LIST = resourcesSubService.getAllSubResources();
	}

	/**
	 * 根据给定的资源id 获取其子资源的集合
	 * @param resourcesIdList
	 * @return
	 */
	public List<ResourcesSub> getResourcesSubListByResourcesId(List<Long> resourcesIdList) {
		List<ResourcesSub> allResourcesSubList = this.getAllResourcesSub();
		if (CollectionUtils.isEmpty(allResourcesSubList)){
			return null;
		}

		List<ResourcesSub> resourcesSubListByResourcesId = new ArrayList<ResourcesSub>();
		for (ResourcesSub sub : allResourcesSubList) {
			if (resourcesIdList.contains(sub.getResourceId())){
				resourcesSubListByResourcesId.add(sub);
			}
		}

		return resourcesSubListByResourcesId;
	}


	/**
	 * 获取到不需要权限就能访问的资源集合
	 * @return
	 */
	public List<Resources> getIsMenuAndNotRestrictResources(){
		List<Resources> allResourcesList = this.getAllResources();
		if (CollectionUtils.isEmpty(allResourcesList)) {
			return null;
		}

		List<Resources> isMenuAndNotRestrictResourcesList = new ArrayList<Resources>();
		for (Resources r : allResourcesList) {
			// 获取所有的不需要权限的菜单项
			if (r.getIsMenu() == 1 && r.getIsRestricted() == 0) {
				isMenuAndNotRestrictResourcesList.add(r);
			}
		}
		return isMenuAndNotRestrictResourcesList;
	}

	/**
	 * 根据资源id获取资源集合
	 * @param resourcesIdList
	 * @return
	 */
	public List<Resources> getResourcesListByResourcesId(List<Long> resourcesIdList) {
		List<Resources> allResourcesList = this.getAllResources();
		if (CollectionUtils.isEmpty(allResourcesList)) {
			return null;
		}

		List<Resources> resourcesList = new ArrayList<Resources>();
		for (Resources r : allResourcesList) {
			if (resourcesIdList.contains(r.getId())) {
				resourcesList.add(r);
			}
		}
		return resourcesList;

	}

	/**
	 * 获取所有的不需要权限就能访问的资源
	 * @return
	 */
	public List<Resources> getNonRestrictedResources() {
		List<Resources> allResourcesList = this.getAllResources();
		if (CollectionUtils.isEmpty(allResourcesList)) {
			return null;
		}

		List<Resources> nonRestrictedResourcesList = new ArrayList<Resources>();
		for (Resources r : allResourcesList) {
			// 获取所有的不需要权限的资源
			if (r.getIsRestricted() == 0) {
				nonRestrictedResourcesList.add(r);
			}
		}
		return nonRestrictedResourcesList;
	}
	
	/**
	 * 获取所有的角色资源信息集合
	 * @return List<RoleResource>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:02:01
	 * @version V1.0
	 */
	public List<RoleResource> getRoleResourcesList() {
		if (ROLE_RESOURCES_LIST == null || ROLE_RESOURCES_LIST.size() == 0)
			this.setRoleResourcesList();
		return ROLE_RESOURCES_LIST;
	}
	
	/**
	 * 生成角色资源信息集合
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24  下午02:01:37
	 * @version V1.0
	 */
	public void setRoleResourcesList() {
		List<RoleResource> roleResourceList = roleResourceService.getAllRoleResource();
		ROLE_RESOURCES_LIST = roleResourceList;
	}

	/**
	 * 给定角色id，找到角色所分配的权限的集合
	 * @param roleIdList
	 * @return
	 */
	public List<RoleResource> getRoleResourceListByRoleId(List<Long> roleIdList) {
		if (CollectionUtils.isEmpty(roleIdList)) {
			return null;
		}

		List<RoleResource> roleResourceList = getRoleResourcesList();
		if (CollectionUtils.isEmpty(roleResourceList)) {
			return null;
		} else {
			List<RoleResource> roleResourceListByRoleId = new ArrayList<RoleResource>();
			for (RoleResource rr : roleResourceList) {
				if (roleIdList.contains(rr.getRoleId())) {
					roleResourceListByRoleId.add(rr);
				}

			}
			return roleResourceListByRoleId;
		}
	}
	
	/**
	 * 获取系统资源模块与模块下资源的信息
	 * @return List<ResourceModule>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午11:13:08
	 * @version V1.0
	 */
	public List<ResourceModule> getResourceModuleList() {
		if (RESOURCE_MODULE_LIST == null || RESOURCE_MODULE_LIST.size() == 0)
			this.setResourceModuleList();
		if (RESOURCE_MODULE_LIST != null && RESOURCE_MODULE_LIST.size() > 0) {
			for (ResourceModule module : RESOURCE_MODULE_LIST) {
				if (module.getResourceList() != null && module.getResourceList().size() > 0) {
					for (Resources resources : module.getResourceList()) {
						resources.setHaveAuth(false);
					}
				}
			}
		}
		return RESOURCE_MODULE_LIST;
	}
	
	/**
	 * 生成 系统资源模块与模块下资源的信息
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午11:13:18
	 * @version V1.0
	 */
	public void setResourceModuleList() {
		List<ResourceModule> resourceModuleList = resourceModuleService.getResourceModuleInfoWithResources();
		RESOURCE_MODULE_LIST = resourceModuleList;
	}
	
	/**
	 * 获取系统资源信息
	 * @return List<Resources>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24  下午02:17:18
	 * @version V1.0
	 */
	public List<Resources> getResourcesList() {
		List<Resources> resourcesList = new ArrayList<Resources>();
		List<ResourceModule> resourceModuleList = this.getResourceModuleList();
		for (ResourceModule resourceModule : resourceModuleList) {
			resourcesList.addAll(resourceModule.getResourceList());
		}
		return resourcesList;
	}

	/**
	 * 设置某个角色的菜单集合
	 * @param roleIds
	 * @param resourceModuleList
	 */
	public void setMenuByRoleId(String roleIds, List<ResourceModule> resourceModuleList) {
		MENU_BY_ROLE_IDS_MAP.put(roleIds, resourceModuleList);
	}

	/**
	 * 获取某个角色的菜单集合
	 * @param roleIds
	 * @return
	 */
	public List<ResourceModule> getMenuByRoleId(String roleIds) {
		return MENU_BY_ROLE_IDS_MAP.get(roleIds);
	}

	public List<SysConfig> getSysConfigList() {
		if (CollectionUtils.isEmpty(SYS_CONFIG_LIST)) {
			this.setSysConfig();
		}
		return SYS_CONFIG_LIST;
	}

	public void setSysConfig() {
		SYS_CONFIG_LIST = sysConfigService.getAllSysConfig();
		if (!CollectionUtils.isEmpty(SYS_CONFIG_LIST)) {
			SYS_CONFIG_MAP_WITH_KEY_ITEM = new HashMap<String, SysConfig>();
			SYS_CONFIG_MAP_WITH_KEY_ID = new HashMap<Long, SysConfig>();
			for (SysConfig sc : SYS_CONFIG_LIST) {
				SYS_CONFIG_MAP_WITH_KEY_ITEM.put(sc.getConfigItem(), sc);
				SYS_CONFIG_MAP_WITH_KEY_ID.put(sc.getId(), sc);
			}
		}
	}

	public Map<String, SysConfig> getSysConfigMapWithKeyItem() {
		if (CollectionUtils.isEmpty(SYS_CONFIG_LIST)) {
			this.setSysConfig();
		}
		return SYS_CONFIG_MAP_WITH_KEY_ITEM;
	}

	public Map<Long, SysConfig> getSysConfigMapWithKeyId() {
		if (CollectionUtils.isEmpty(SYS_CONFIG_LIST)) {
			this.setSysConfig();
		}
		return SYS_CONFIG_MAP_WITH_KEY_ID;
	}


}
