package com.tonghu.pub.security.dao;

import com.tonghu.pub.model.security.po.Roles;
import com.tonghu.pub.model.security.po.query.RolesQuery;

import java.util.List;


/**
 * @Description: 系统角色信息的DAO 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public interface RolesDao {
	
	/**
	 * 从数据库中获取所有的系统角色信息
	 * @return List<Roles>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<Roles> getAllRoles();
	
	/**
	 * 根据检索条件获取符合条件的角色数量
	 * @param rolesQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getRolesCountByQuery(RolesQuery rolesQuery);
	
	/**
	 * 根据检索条件获取符合条件的角色信息
	 * @param rolesQuery
	 * @return List<Roles>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<Roles> getRolesInfoByQuery(RolesQuery rolesQuery);
	
	/**
	 * 根据角色id获取角色信息
	 * @param roleId
	 * @return Roles
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Roles getRoleInfoById(Long roleId);
	
	/**
	 * 添加新角色
	 * @param role
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer addNewRole(Roles role);

	/**
	 * 根据角色id修改角色信息
	 * @param role
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer updateRoleInfo(Roles role);
	
	/**
	 * 根据角色名称和id精确查找符合条件的角色数量
	 * @param rolesQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getRoleCountByName(RolesQuery rolesQuery);
	
	/**
	 * 根据角色id删除角色信息
	 * @param roleId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer deleteRoleById(Long roleId);

}
