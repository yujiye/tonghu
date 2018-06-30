package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统附件信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-03 下午07:36:30
 */
@Getter
@Setter
public class Attachment extends BusinessBasePo {

	private String fileName; // 文件名称
	private String originalFileName; // 文件原始名称
	private String fileType; // 文件类型
	private String filePath; // 文件相对路径
	private Long fileSize; // 文件大小
	private String imgMeasure; // 如果附件是图片，此值为图片的宽和高
	private String profile; // 文件摘要
	private Long createUserId; // 创建记录用户id
	private Long updateUserId; // 修改记录用户的id

}