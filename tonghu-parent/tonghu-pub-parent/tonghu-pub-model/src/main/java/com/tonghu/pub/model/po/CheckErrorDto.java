package com.tonghu.pub.model.po;

/**
 * @Description: Ajax返回到前台的错误信息封装实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public class CheckErrorDto extends BasePo {
	
	private String key;
	private String value;
	
	public CheckErrorDto (String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
