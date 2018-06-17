package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.security.service.UserRoleService;
import com.tonghu.pub.model.security.po.UserRole;
import com.tonghu.pub.model.security.po.query.UserRoleQuery;
import com.tonghu.pub.security.dao.UserRoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统用户与角色关联信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:33:52
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@Override
	public List<UserRole> getRoleIdListByUserId(Long userId) {
		if(userId == null) {
			LOGGER.error("Service层：根据用户id获取用户的角色信息，用户ID为空：{}", userId);
			return null;
		}
		LOGGER.debug("Service层：根据用户id获取用户的角色信息");
		UserRoleQuery userRoleQuery = new UserRoleQuery();
		userRoleQuery.setUserId(userId);
		userRoleQuery.setPageSize(-1);
		return userRoleDao.getUserRoleByQuery(userRoleQuery);
	}
	
	@Override
	public Integer getUserRoleCountByRoleId(Long roleId) {
		if(roleId == null) {
			LOGGER.error("Service层：根据检索条件获取符合条件的用户角色关联信息的数量，角色ID为空：{}", roleId);
			return null;
		}
		LOGGER.debug("Service层：根据检索条件获取符合条件的用户角色关联信息的数量");
		UserRoleQuery userRoleQuery = new UserRoleQuery();
		userRoleQuery.setUserId(roleId);
		return userRoleDao.getUserRoleCountByQuery(userRoleQuery);
	}
	
	@Override
	public Integer deleteUserRoleByUserId(Long userId) {
		LOGGER.debug("Service层：删除给定用户的用户角色关联关系: userId={}", userId);
		return userRoleDao.deleteUserRoleByUserId(userId);
	}
	
	@Override
	public Integer addNewUserRole(UserRole userRole) {
		LOGGER.debug("Service层：保存新的用户角色关系信息");
		return userRoleDao.addNewUserRole(userRole);
	}
}
