package com.tonghu.pub.common.dao;

import com.tonghu.pub.model.common.po.Attachment;
import com.tonghu.pub.model.common.po.query.AttachmentQuery;

import java.util.List;


/**
 * @Description: 系统附件信息 Dao层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-04 下午08:38:02
 */
public interface AttachmentDao {
	
	/**
	 * 根据附件id获取附件信息
	 * @param attachmentId
	 * @return Attachment
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-04 下午08:38:02
	 * @version V1.0
	 */
	public Attachment getAttachmentById(Long attachmentId);

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
	 * 根据检索条件获取附件的记录信息
	 * @param attachmentQuery
	 * @return List<Attachment>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-04 下午08:38:02
	 * @version V1.0
	 */
	public List<Attachment> getAttachmentInfoByQuery(AttachmentQuery attachmentQuery);
	
	/**
	 * 新增附件信息
	 * @param attachment
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-04 下午08:38:02
	 * @version V1.0
	 */
	public Integer addNewAttachment(Attachment attachment);
	
	/**
	 * 根据附件id更新附件信息
	 * @param attachment
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-04 下午08:38:02
	 * @version V1.0
	 */
	public Integer updateAttachmentById(Attachment attachment);
	
	/**
	 * 根据附件id删除附件信息 物理删除
	 * @param attachmentId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-04 下午08:38:02
	 * @version V1.0
	 */
	public Integer deleteAttachmentById(Long attachmentId);
	
}
