package com.tonghu.pub.security.dao.impl;


import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.security.po.ResourcesSub;
import com.tonghu.pub.security.dao.ResourcesSubDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统子资源信息 Dao 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午06:15:57
 */
@Repository(value="resourcesSubDao")
public class ResourcesSubDaoImpl extends BaseDao implements ResourcesSubDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesSubDaoImpl.class);
	
	@Override
	public List<ResourcesSub> getSubResourcesByRIdList(List<Long> resourceIdList) {
		LOGGER.debug("Dao层：根据资源id集合 获取子资源信息集合");
		return getReadSqlSession().selectList("resourcesSubDao.selectSubResourcesByRIdList", resourceIdList);
	}

	@Override
	public List<ResourcesSub> getAllSubResources() {
		LOGGER.debug("Dao层：获取所有子资源信息集合");
		return getReadSqlSession().selectList("resourcesSubDao.selectAllSubResources");
	}
}
