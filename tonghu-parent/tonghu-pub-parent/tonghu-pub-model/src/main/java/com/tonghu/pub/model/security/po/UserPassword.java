package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: TODO
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class UserPassword extends BasePo {

	private String password; // 原密码
	private String newPassword; // 新密码
	private String confirmNewPassword; // 新密码确认


	/**
	 * 去掉首尾空格
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:15:10
	 * @version V1.0
	 */
	public void trim() {
		if (this.password != null)
			this.password = this.password.trim();
		
		if (this.newPassword != null)
			this.newPassword = this.newPassword.trim();
		
		if (this.confirmNewPassword != null)
			this.confirmNewPassword = this.confirmNewPassword.trim();
	}

}
