package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;

/**
 * @Description: 系统附件信息 实体类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-03 下午07:36:30
 */
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

	public String getFileName() {
		return fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public String getImgMeasure() {
		return imgMeasure;
	}

	public String getProfile() {
		return profile;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public void setImgMeasure(String imgMeasure) {
		this.imgMeasure = imgMeasure;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

}