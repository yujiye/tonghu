package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统资源信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class Resources extends BusinessBasePo implements Comparable<Resources> {

	private String resourceName; // 资源名称
	private String resourceUrl; // 访问资源的路径
	private Long moduleId; // 资源所属模块ID
	private Integer sortNum; // 展示顺序
	private Integer isRestricted; // 用户访问该页面是否受角色权限限制
	private Integer isMenu; // 是否能组装为页面中的菜单
	private Long createUserId; // 创建者的用户ID
	private Long updateUserId; // 修改者的用户ID
	private String note;// 备注
	private boolean isHaveAuth = false; //扩展字段，标识某角色是否有权访问,默认值为false

	@Override
	public int compareTo(Resources oterResources) {
		if (oterResources == null || oterResources.getSortNum() == null) {
			return 1;
		} 
		return this.sortNum.compareTo(oterResources.getSortNum());
	}

}
