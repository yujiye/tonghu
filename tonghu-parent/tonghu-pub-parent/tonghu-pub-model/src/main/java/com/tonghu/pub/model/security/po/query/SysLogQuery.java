package com.tonghu.pub.model.security.po.query;


import com.tonghu.pub.model.po.BasePaginationQuery;

/**
 * @Description: 查询系统日志信息 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
public class SysLogQuery extends BasePaginationQuery {

	private Long id;// 记录主键
	private Long userId; // 当前操作用户的id
	private String userIp; //用户ip地址
	private Integer operatorType;// 操作类型
	private Integer isSuccess;// 当前操作是否成功
	private String createTimeStart; // 操作发生的时间
	private String createTimeEnd; // 操作发生的时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
