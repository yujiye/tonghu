package com.tonghu.pub.model.security.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;

/**
 * @Description: 查询用户角色关联信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
public class UserRoleQuery extends BasePaginationQuery {

	private Long id;
	private Long userId; // 用户id
	private Long roleId; // 角色id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
