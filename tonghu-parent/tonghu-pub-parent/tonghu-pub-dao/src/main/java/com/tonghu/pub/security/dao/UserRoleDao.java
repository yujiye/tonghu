package com.tonghu.pub.security.dao;

import com.tonghu.pub.model.security.po.UserRole;
import com.tonghu.pub.model.security.po.query.UserRoleQuery;

import java.util.List;

/**
 * @Description: 系统用户与系统角色的关联信息 Dao 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public interface UserRoleDao {
	
	/**
	 * 根据检索条件获取符合条件的用户资源信息
	 * @param userRoleQuery
	 * @return List<UserRole>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<UserRole> getUserRoleByQuery(UserRoleQuery userRoleQuery);
	
	/**
	 * 根据检索条件获取符合条件的用户角色关联信息的数量
	 * @param userRoleQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getUserRoleCountByQuery(UserRoleQuery userRoleQuery);
	
	/**
	 * 删除给定用户的用户角色关联关系
	 * @param userId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer deleteUserRoleByUserId(Long userId);
	
	/**
	 * 保存新的用户角色关系信息
	 * @param userRole
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer addNewUserRole(UserRole userRole);
	
}
