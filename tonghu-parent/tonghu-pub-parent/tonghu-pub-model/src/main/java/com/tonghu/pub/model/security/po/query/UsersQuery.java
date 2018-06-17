package com.tonghu.pub.model.security.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;

/**
 * @Description: 查询用户信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
public class UsersQuery extends BasePaginationQuery {

	private Long id;// 用户ID
	private String userName;// 用户登录名
	private String userEmail;// 用户电子邮箱地址
	private String trueName;// 用户真实姓名
	private Long orgId;// 用户所属机构id
	private String userMobile;// 用户手机号码
	private String isLock;// 当前用户的状态，是否被锁定

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

}
