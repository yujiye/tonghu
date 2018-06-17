package com.tonghu.pub.common.constant;

/**
 * @Description: 系统数据表中的记录是否可以被编辑 枚举类型
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public enum EnumEditable {
	
	// 不可编辑 0
	EDITABLE_NO(0),

	// 可编辑 1
	EDITABLE_YES(1);

	private Integer status;

	private EnumEditable(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

}
