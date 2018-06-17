package com.tonghu.pub.security.dao;

import com.tonghu.pub.model.security.po.RoleResource;

import java.util.List;


/**
 * @Description: 系统角色与系统资源关联信息 Dao 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public interface RoleResourceDao {
	
	/**
	 * 获取数据库中所有的角色与资源关联信息
	 * @return List<RoleResource>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<RoleResource> getAllRoleResource();
	
	/**
	 * 根据角色id集合获取系统角色资源信息
	 * @param roleIdList
	 * @return List<RoleResource>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<RoleResource> getRoleResourceInfoByRoleIdList(List<Long> roleIdList);
	
	/**
	 * 根据角色id删除该角色对应的角色资源关系记录
	 * @param roleId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer deleteRoleResourceByRoleId(Long roleId);
	
	/**
	 * 添加新的角色资源关系记录
	 * @param roleResource
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer addNewRoleResource(RoleResource roleResource);

}
