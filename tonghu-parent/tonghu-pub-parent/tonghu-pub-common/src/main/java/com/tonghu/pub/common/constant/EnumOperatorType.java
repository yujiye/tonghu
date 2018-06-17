package com.tonghu.pub.common.constant;

/**
 * @Description: 系统日志中的操作类型 枚举类型
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:17:55
 */
public enum EnumOperatorType {
	
	// 新增 1
	ADD(1),

	// 修改 2
	MODIFY(2),
	
	// 删除 3
	DELETE(3),
	
	// 查询 4
	SEARCH(4),
	
	// 查看 5
	SHOW(5),
	
	// 登录系统 6
	LOGIN(6),
	
	// 退出系统 7
	LOGOUT(7);

	private Integer type;

	private EnumOperatorType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}
	
	public static String getOperTypeName(Integer opertype) {
		if (opertype.compareTo(new Integer(1)) == 0) {
			return "新增";
		} else if (opertype.compareTo(new Integer(2)) == 0) {
			return "修改";
		} else if (opertype.compareTo(new Integer(3)) == 0) {
			return "删除";
		} else if (opertype.compareTo(new Integer(4)) == 0) {
			return "查询";
		} else if (opertype.compareTo(new Integer(5)) == 0) {
			return "查看";
		} else if (opertype.compareTo(new Integer(6)) == 0) {
			return "登录系统";
		} else if (opertype.compareTo(new Integer(7)) == 0) {
			return "退出系统";
		}
		return null;
	}

}
