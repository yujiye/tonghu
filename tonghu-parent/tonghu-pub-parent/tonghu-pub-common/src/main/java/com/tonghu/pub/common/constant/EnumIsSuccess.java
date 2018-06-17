package com.tonghu.pub.common.constant;

/**
 * @Description: 操作是否成功 枚举类型
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public enum EnumIsSuccess {

	// 成功 1
	SUCCESS(1),

	// 失败 0
	FAILURE(0);

	private Integer status;

	private EnumIsSuccess(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
	
	public static String getSuccess(Integer success) {
		if (success.compareTo(new Integer(1)) == 0) {
			return "成功";
		} else if (success.compareTo(new Integer(0)) == 0) {
			return "失败 ";
		}
		return null;
	}

}
