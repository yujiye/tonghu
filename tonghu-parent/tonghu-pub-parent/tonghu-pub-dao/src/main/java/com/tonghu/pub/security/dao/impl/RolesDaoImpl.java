package com.tonghu.pub.security.dao.impl;


import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.security.po.Roles;
import com.tonghu.pub.model.security.po.query.RolesQuery;
import com.tonghu.pub.security.dao.RolesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统角色信息的DAO 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:31:14
 */
@Repository(value="rolesDao")
public class RolesDaoImpl extends BaseDao implements RolesDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesDaoImpl.class);
	
	@Override
	public List<Roles> getAllRoles() {
		LOGGER.debug("DAO层：获取所有的系统角色");
		return getReadSqlSession().selectList("rolesDao.selectAllRoles");
	}
	
	@Override
	public Integer getRolesCountByQuery(RolesQuery rolesQuery) {
		LOGGER.debug("Dao层：根据检索条件获取角色数量");
		return (Integer)getReadSqlSession().selectOne("rolesDao.selectRolesCountByQuery", rolesQuery);
	}
	
	@Override
	public List<Roles> getRolesInfoByQuery(RolesQuery rolesQuery){
		LOGGER.debug("Dao层：根据检索条件获取角色的记录信息");
		return getReadSqlSession().selectList("rolesDao.selectRolesInfoByQuery", rolesQuery);
	}
	
	@Override
	public Roles getRoleInfoById(Long roleId) {
		if (roleId == null) {
			LOGGER.error("Dao层：根据角色id查取用户信息，角色id参数为空：{}", roleId);
			return null;
		}
		LOGGER.debug("Dao层：根据角色id查取角色信息：{}", roleId);
		return (Roles)select("rolesDao.selectRoleInfoById", roleId);
	}
	
	@Override
	public Integer addNewRole(Roles role) {
		LOGGER.debug("Dao层：添加新的角色");
		return insert("rolesDao.insertNewRole", role);
	}
	
	@Override
	public Integer updateRoleInfo(Roles role) {
		LOGGER.debug("Dao层：根据角色id更新用户信息");
		return update("rolesDao.updateRoleInfoById", role);
	}
	
	@Override
	public Integer getRoleCountByName(RolesQuery rolesQuery) {
		LOGGER.debug("Dao层：根据角色名称、角色id，精确查找符合条件的角色数量");
		return (Integer)getReadSqlSession().selectOne("rolesDao.selectRoleCountByQueryForCheck", rolesQuery);
	}
	
	@Override
	public Integer deleteRoleById(Long roleId) {
		LOGGER.debug("Dao层：根据角色id删除角色信息");
		return (Integer)delete("rolesDao.deleteRoleById", roleId);
	}

}
