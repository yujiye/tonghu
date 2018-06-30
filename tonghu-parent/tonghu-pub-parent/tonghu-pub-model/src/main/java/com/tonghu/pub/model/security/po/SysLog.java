package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统日志 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:03:20
 */
@Getter
@Setter
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

}
