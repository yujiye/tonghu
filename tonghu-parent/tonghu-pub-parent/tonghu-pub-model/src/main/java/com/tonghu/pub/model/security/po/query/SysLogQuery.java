package com.tonghu.pub.model.security.po.query;


import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 查询系统日志信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
@Getter
@Setter
public class SysLogQuery extends BasePaginationQuery {

	private Long id;// 记录主键
	private Long userId; // 当前操作用户的id
	private String userIp; //用户ip地址
	private Integer operatorType;// 操作类型
	private Integer isSuccess;// 当前操作是否成功
	private String createTimeStart; // 操作发生的时间
	private String createTimeEnd; // 操作发生的时间

}
