package com.tonghu.pub.common.constant;

/**
 * @Description: 专家性别 枚举类型
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:59:45
 */
public enum EnumSex {

	// 男性 1
	MALE(1),

	// 女性 2
	FEMALE(2),
	
	// 未知
	UNKNOWN(3);

	private Integer sex;

	private EnumSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return this.sex;
	}
	
	public static String getSexName(Integer sex) {
		if (sex.compareTo(new Integer(1)) == 0) {
			return "男";
		} else if (sex.compareTo(new Integer(2)) == 0) {
			return "女";
		} else if (sex.compareTo(new Integer(3)) == 0) {
			return "未知";
		}
		return null;
	}

}
