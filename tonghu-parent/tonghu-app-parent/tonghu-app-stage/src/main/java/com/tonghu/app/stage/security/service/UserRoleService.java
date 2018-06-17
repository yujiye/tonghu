package com.tonghu.app.stage.security.service;

import com.tonghu.pub.model.security.po.UserRole;

import java.util.List;

/**
 * @Description: 系统用户与角色关联信息 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:32:36
 */
public interface UserRoleService {
	
	/**
	 * 根据用户id获取用户的角色信息
	 * @param userId
	 * @return List<UserRole>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午10:23:14
	 * @version V1.0
	 */
	public List<UserRole> getRoleIdListByUserId(Long userId);
	
	/**
	 * 根据检索条件获取符合条件的用户角色关联信息的数量
	 * @param roleId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午03:25:50
	 * @version V1.0
	 */
	public Integer getUserRoleCountByRoleId(Long roleId);

	/**
	 * 删除给定用户的用户角色关联关系
	 * @param userId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:20:23
	 * @version V1.0
	 */
	public Integer deleteUserRoleByUserId(Long userId);
	
	/**
	 * 保存新的用户角色关系信息
	 * @param userRole
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:21:42
	 * @version V1.0
	 */
	public Integer addNewUserRole(UserRole userRole);
}
