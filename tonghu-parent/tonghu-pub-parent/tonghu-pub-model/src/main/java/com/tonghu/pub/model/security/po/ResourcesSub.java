package com.tonghu.pub.model.security.po;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统子资源信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:34:52
 */
@Getter
@Setter
public class ResourcesSub extends BasePo {

	private Long id; // 资源id
	private String subResourceName; // 子资源名称
	private String subResourceUrl; // 访问资源的路径
	private Long resourceId; // 资源所属模块ID
	private String createTime;// 用户被创建的时间
	private Long createUserId; // 创建者的用户ID
	private String updateTime; // 修改信息的时间
	private Long updateUserId; // 修改者的用户ID
	private String note;// 备注


}
