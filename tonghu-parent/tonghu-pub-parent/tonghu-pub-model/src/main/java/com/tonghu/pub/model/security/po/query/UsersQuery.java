package com.tonghu.pub.model.security.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 查询用户信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
@Getter
@Setter
public class UsersQuery extends BasePaginationQuery {

	private Long id;// 用户ID
	private String userName;// 用户登录名
	private String userEmail;// 用户电子邮箱地址
	private String trueName;// 用户真实姓名
	private Long orgId;// 用户所属机构id
	private String userMobile;// 用户手机号码
	private String isLock;// 当前用户的状态，是否被锁定

}
