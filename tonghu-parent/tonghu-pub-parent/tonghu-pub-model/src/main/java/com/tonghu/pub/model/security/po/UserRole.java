package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统用户角色关联信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class UserRole extends BusinessBasePo {

	private Long userId; // 用户id
	private Long roleId; // 角色id
	private Long createUserId; // 创建者的用户ID
	private Long updateUserId; // 修改者的用户ID
	private String note;// 备注

}
