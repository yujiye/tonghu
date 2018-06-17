package com.tonghu.app.stage.common.service.impl;

import com.tonghu.app.stage.common.service.AttachmentService;
import com.tonghu.pub.common.dao.AttachmentDao;
import com.tonghu.pub.model.common.po.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统附件信息 服务层 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-14 下午08:37:09
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Resource
	private AttachmentDao attachmentDao;

	@Override
	public Attachment getAttachmentById(Long id) {
		LOGGER.debug("Service层：根据附件id获取附件信息");
		return attachmentDao.getAttachmentById(id);
	}

	@Override
	public List<Attachment> getAttachmentListByIdList(List<Long> idsList) {
		LOGGER.debug("Service层：根据附件id集合获取附件信息集合");
		return attachmentDao.getAttachmentListByIdList(idsList);
	}

	@Override
	public Integer addNewAttachment(Attachment attachment) {
		LOGGER.debug("Service层：新增附件信息");
		return attachmentDao.addNewAttachment(attachment);
	}

	@Override
	public Integer deleteAttachmentByAttachmentId(Long attachmentId) {
		LOGGER.debug("Service层：根据附件id删除附件信息 物理删除");
		return attachmentDao.deleteAttachmentById(attachmentId);
	}
	
	@Override
	public Integer updateAttachment(Attachment attachment) {
		LOGGER.debug("Service层：更新附件信息");
		return attachmentDao.updateAttachmentById(attachment);
	}

}
