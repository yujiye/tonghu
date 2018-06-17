package com.tonghu.pub.security.dao.impl;


import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.security.po.UserRole;
import com.tonghu.pub.model.security.po.query.UserRoleQuery;
import com.tonghu.pub.security.dao.UserRoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统用户与系统角色的关联信息 Dao 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:04:18
 */
@Repository(value="userRoleDao")
public class UserRoleDaoImpl extends BaseDao implements UserRoleDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleDaoImpl.class);
	
	@Override
	public List<UserRole> getUserRoleByQuery(UserRoleQuery userRoleQuery) {
		LOGGER.debug("DAO层：根据检索条件获取符合条件的用户资源信息");
		return getReadSqlSession().selectList("userRoleDao.selectUserRoleByQuery", userRoleQuery);
	}
	
	@Override
	public Integer getUserRoleCountByQuery(UserRoleQuery userRoleQuery) {
		LOGGER.debug("DAO层：根据检索条件获取符合条件的用户角色关联信息的数量");
		return (Integer)getReadSqlSession().selectOne("userRoleDao.selectUserRoleCountByQuery", userRoleQuery);
	}

	public Integer deleteUserRoleByUserId(Long userId) {
		LOGGER.debug("DAO层：删除给定用户的用户角色关联关系: userId={}", userId);
		return (Integer)delete("userRoleDao.deleteUserRoleByUserId", userId);
	}
	
	@Override
	public Integer addNewUserRole(UserRole userRole) {
		LOGGER.debug("DAO层：保存新的用户角色关系信息");
		return (Integer)insert("userRoleDao.inserNewUserRole", userRole);
	}
	
}
