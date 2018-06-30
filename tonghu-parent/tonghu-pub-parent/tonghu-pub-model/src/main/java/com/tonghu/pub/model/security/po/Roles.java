package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统角色信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class Roles extends BusinessBasePo {

	private String roleName; // 角色名称
	private Integer editable; // 该系统角色是否可以编辑和删除
	private Long createUserId; // 创建者的用户ID
	private Long updateUserId; // 修改者的用户ID
	private String createUserName; // 创建者的用户名称
	private String updateUserName; // 修改者的用户名称
	private String note;// 备注

	public void trim() {
		if (this.roleName != null)
			this.roleName = this.roleName.trim();
		if (this.note != null)
			this.note = this.note.trim();
	}

}
