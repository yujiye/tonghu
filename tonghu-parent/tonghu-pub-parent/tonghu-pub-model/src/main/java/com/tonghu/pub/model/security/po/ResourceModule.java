package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 系统资源模块信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:21
 */
@Getter
@Setter
public class ResourceModule extends BusinessBasePo {

	private String moduleName; // 模块名称
	private Integer sortNum; //展示顺序
	private Long pId; // 当前模块所属父模块的id
	private Long createUserId; // 创建者的用户ID
	private Long updateUserId; // 修改者的用户ID
	private String note;// 备注
	private String url;
	private List<Resources> resourceList = new ArrayList<Resources>(); // 模块下的资源信息

	public void addResource(Resources resources) {
		this.resourceList.add(resources);
	}

}
