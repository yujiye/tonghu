package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description: 系统用户信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class Users extends BusinessBasePo {
	
	public Users() {}
	
	public Users(Long id, String password) {
		this.setId(id);
		this.password = password;
	}

	private String userName;// 用户登录名
	private String password; // 用户登录密码
	private String userEmail;// 用户电子邮箱地址
	private String trueName;// 用户真实姓名
	private String userMobile;// 用户手机号码
	private String userPhone;// 用户座机号码
	private Integer isLock;// 当前用户的状态，是否被锁定
	private Integer editable; // 该系统用户是否可以编辑和删除
	private Long createUserId; // 创建者的用户ID
	private Long updateUserId; // 修改者的用户ID
	private String note;// 备注

	private String roleName; //角色名称
	private List<UserRole> userRoleList; //用户角色
	private Long roleId; //用户角色id
	private String lockStr; // 用户状态
	private String orgName;// 用户所属机构名称
	private String createUserName; // 创建者的用户名称
	private String updateUserName; // 修改者的用户名称

	@Override
	public String toString() {
		return "userName: " + userName + ",password: " + password
				+ ",userEmail: " + userEmail + ",trueName: " + trueName;
	}

	/**
	 * 过滤掉用户信息中各个属性值的前后空格
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午09:49:26
	 * @version V1.0
	 */
	public void trim() {
		if (this.userName != null)
			this.userName = this.userName.trim();
		if (this.userEmail != null)
			this.userEmail = this.userEmail.trim();
		if (this.trueName != null)
			this.trueName = this.trueName.trim();
			this.userMobile = this.userMobile.trim();
		if (this.userPhone != null)
			this.userPhone = this.userPhone.trim();
		if (this.getCreateTime() != null)
			this.setCreateTime(this.getCreateTime().trim());
		if (this.getUpdateTime() != null)
			this.setUpdateTime(this.getUpdateTime().trim());
		if (this.note != null)
			this.note = this.note.trim();

		if (this.lockStr != null)
			this.lockStr = this.lockStr.trim();
		if (this.orgName != null)
			this.orgName = this.orgName.trim();
		if (this.createUserName != null)
			this.createUserName = this.createUserName.trim();
		if (this.updateUserName != null)
			this.updateUserName = this.updateUserName.trim();
	}

}
