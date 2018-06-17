package com.tonghu.pub.common.constant;

/**
 * @Description: 系统用户是否被锁定 枚举类型
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public enum EnumIsLock {

	// 未锁定 0
	IS_LOCK_NO(0),

	// 已锁定 1
	IS_LOCK_YES(1);

	private Integer status;

	private EnumIsLock(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

}
