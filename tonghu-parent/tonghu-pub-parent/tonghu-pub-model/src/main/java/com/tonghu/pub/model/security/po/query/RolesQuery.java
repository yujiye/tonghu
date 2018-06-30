package com.tonghu.pub.model.security.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 查询角色信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
@Getter
@Setter
public class RolesQuery extends BasePaginationQuery {

	private Long id;// 用户ID
	private String roleName;// 角色名称

}
