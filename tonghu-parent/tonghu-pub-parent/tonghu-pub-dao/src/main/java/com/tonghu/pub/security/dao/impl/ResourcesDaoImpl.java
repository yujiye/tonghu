package com.tonghu.pub.security.dao.impl;

import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.model.security.po.query.ResourcesQuery;
import com.tonghu.pub.security.dao.ResourcesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统资源信息的DAO 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:15:52
 */
@Repository(value="resourcesDao")
public class ResourcesDaoImpl extends BaseDao implements ResourcesDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesDaoImpl.class);
	
	@Override
	public List<Resources> getAllResources() {
		LOGGER.debug("Dao层：获取系统所有资源信息");
		return getReadSqlSession().selectList("resourcesDao.selectAllResources");
	}
	
	@Override
	public List<Resources> getResourceInfoByIdList(List<Long> idList) {
		if (idList == null) {
			LOGGER.error("Dao层：根据资源id集合获取系统资源信息，资源id集合参数为空");
			return null;
		}
		LOGGER.debug("Dao层：根据资源id集合获取系统资源信息");
		return getReadSqlSession().selectList("resourcesDao.selectResourceInfoByIdList", idList);
	}
	
	@Override
	public List<Resources> getNonRestrictedResources() {
		LOGGER.debug("Dao层：获取用户登录后默认可以访问的资源，不受角色限制");
		return getReadSqlSession().selectList("resourcesDao.selectNonRestrictedResources");
	}
	
	@Override
	public List<Resources> getResourcesByQuery(ResourcesQuery resourcesQuery) {
		LOGGER.debug("Dao层：获取所有符合条件的系统资源信息");
		return getReadSqlSession().selectList("resourcesDao.selectResourcesByQuery", resourcesQuery);
	}
	
}
