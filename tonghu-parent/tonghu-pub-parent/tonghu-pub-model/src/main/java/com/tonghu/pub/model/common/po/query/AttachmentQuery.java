package com.tonghu.pub.model.common.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 系统附件 Query类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-04 下午02:48:58
 */
@Getter
@Setter
public class AttachmentQuery extends BasePaginationQuery {

	private Long id; // 附件id
	private String fileName; // 文件名称
	private String originalFileName; // 文件原始名称
	private String fileType; // 文件类型
	private String filePath; // 文件相对路径

}
