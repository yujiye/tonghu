package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;

/**
 * @Description: 系统日志 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
public class SysLog extends BusinessBasePo {

	private String innerSerialId;// 内部流水号
	private Long userId; // 当前操作用户的id
	private String userIp;// 当前操作用户的ip地址
	private Integer operatorType;// 操作类型
	private String content;// 当前操作的内容
	private Integer isSuccess;// 当前操作是否成功
	private String cause;// 若操作失败，记录操作失败原因

	// 扩展信息 前台页面是使用
	private String userName; // 创建者的用户名称
	private String operatorTypeName; // 操作类型名称
	private String success; // 操作是否成功

	public SysLog(){}
	
	public SysLog(String innerSerialId, Long userId, String userIp,
			Integer operatorType, String content, Integer isSuccess,
			String cause) {
		super();
		this.innerSerialId = innerSerialId;
		this.userId = userId;
		this.userIp = userIp;
		this.operatorType = operatorType;
		this.content = content;
		this.isSuccess = isSuccess;
		this.cause = cause;
	}

	public String getInnerSerialId() {
		return innerSerialId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserIp() {
		return userIp;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public String getContent() {
		return content;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public String getCause() {
		return cause;
	}

	public String getUserName() {
		return userName;
	}

	public String getOperatorTypeName() {
		return operatorTypeName;
	}

	public String getSuccess() {
		return success;
	}

	public void setInnerSerialId(String innerSerialId) {
		this.innerSerialId = innerSerialId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOperatorTypeName(String operatorTypeName) {
		this.operatorTypeName = operatorTypeName;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
