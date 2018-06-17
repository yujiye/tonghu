package com.tonghu.pub.model.common.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;

/**
 * @Description: 系统附件 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-04 下午02:48:58
 */
public class AttachmentQuery extends BasePaginationQuery {

	private Long id; // 附件id
	private String fileName; // 文件名称
	private String originalFileName; // 文件原始名称
	private String fileType; // 文件类型
	private String filePath; // 文件相对路径

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
