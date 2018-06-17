package com.tonghu.pub.common.dao.impl;

import com.tonghu.pub.common.dao.AttachmentDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.common.po.Attachment;
import com.tonghu.pub.model.common.po.query.AttachmentQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统附件信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-04 下午08:39:23
 */
@Repository(value="attachmentDao")
public class AttachmentDaoImpl extends BaseDao implements AttachmentDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentDaoImpl.class);

	@Override
	public Attachment getAttachmentById(Long id) {
		LOGGER.debug("Dao层：根据附件id获取附件信息");
		return getReadSqlSession().selectOne("attachmentDao.selectAttachmentById", id);
	}

	@Override
	public List<Attachment> getAttachmentListByIdList(List<Long> idsList) {
		LOGGER.debug("Dao层：根据附件id集合获取附件信息集合");
		return getReadSqlSession().selectList("attachmentDao.selectAttachmentListByIdList", idsList);
	}
	
	@Override
	public List<Attachment> getAttachmentInfoByQuery(AttachmentQuery attachmentQuery) {
		LOGGER.debug("Dao层：根据检索条件获取附件的记录信息");
		return getReadSqlSession().selectList("attachmentDao.selectAttachmentInfoByQuery", attachmentQuery);
	}

	@Override
	public Integer addNewAttachment(Attachment attachment) {
		LOGGER.debug("Dao层：新增附件信息");
		return insert("attachmentDao.insertNewAttachment", attachment);
	}

	@Override
	public Integer updateAttachmentById(Attachment attachment) {
		LOGGER.debug("Dao层：根据记录ID更新附件信息");
		return update("attachmentDao.updateAttachmentById", attachment);
	}

	@Override
	public Integer deleteAttachmentById(Long attachmentId) {
		LOGGER.debug("Dao层：根据记录ID删除附件信息 物理删除");
		return delete("attachmentDao.deleteAttachmentById", attachmentId);
	}
	
}
