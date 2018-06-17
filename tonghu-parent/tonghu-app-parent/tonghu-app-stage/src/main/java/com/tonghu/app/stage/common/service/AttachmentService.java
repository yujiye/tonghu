package com.tonghu.app.stage.common.service;


import com.tonghu.pub.model.common.po.Attachment;

import java.util.List;

/**
 * @Description: 系统附件信息 服务层 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-14 下午08:36:34
 */
public interface AttachmentService {
	
	/**
	 * 根据附件id获取附件信息
	 * @param id
	 * @return Attachment
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-14 下午08:36:34
	 * @version V1.0
	 */
	public Attachment getAttachmentById(Long id);

	/**
	 * 根据附件id集合获取附件信息集合
	 * @param idsList
	 * @return Attachment
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-16 22:29:19
	 * @version V1.0
	 */
	public List<Attachment> getAttachmentListByIdList(List<Long> idsList);
	
	/**
	 * 新增附件信息
	 * @param attachment
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-14 下午08:36:34
	 * @version V1.0
	 */
	public Integer addNewAttachment(Attachment attachment);
	
	/**
	 * 根据附件id删除附件信息 物理删除
	 * @param attachmentId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-14 下午08:36:34
	 * @version V1.0
	 */
	public Integer deleteAttachmentByAttachmentId(Long attachmentId);
	
	/**
	 * 修改附件信息
	 * @param attachment
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-14 下午08:36:34
	 * @version V1.0
	 */
	public Integer updateAttachment(Attachment attachment);

}
