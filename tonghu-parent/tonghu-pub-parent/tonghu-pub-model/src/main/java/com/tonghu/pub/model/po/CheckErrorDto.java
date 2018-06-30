package com.tonghu.pub.model.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: Ajax返回到前台的错误信息封装实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
@Getter
@Setter
public class CheckErrorDto extends BasePo {
	
	private String key;
	private String value;
	
	public CheckErrorDto (String key, String value){
		this.key = key;
		this.value = value;
	}

}
