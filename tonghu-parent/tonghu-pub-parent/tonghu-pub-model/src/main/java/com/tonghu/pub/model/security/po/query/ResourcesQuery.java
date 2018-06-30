package com.tonghu.pub.model.security.po.query;


import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 查询资源信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
@Getter
@Setter
public class ResourcesQuery extends BasePaginationQuery {

	private Long id;// 用户ID
	private String resourceName;// 资源名称
	private String resourceUrl;// 资源URL
	private Long moduleId; // 模块id
	private Integer isRestricted; // 用户访问是否需判断用户所属角色拥有访问此资源的权限
	private Integer isMenu; // 是否用于生成菜单

}
