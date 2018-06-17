package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.security.service.ResourcesService;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.model.security.po.query.ResourcesQuery;
import com.tonghu.pub.security.dao.ResourcesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统资源信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:22:31
 */
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Resource
	private ResourcesDao resourcesDao;
	
	@Override
	public List<Resources> getAllResources() {
		LOGGER.debug("Service层：获取系统所有资源信息");
		return resourcesDao.getAllResources();
	}
	
	@Override
	public List<Resources> getResourceInfoByIdList(List<Long> idList) {
		if (idList == null) {
			LOGGER.error("Service层：根据资源id集合获取系统资源信息，资源id集合参数为空");
			return null;
		}
		LOGGER.debug("Service层：根据资源id集合获取系统资源信息");
		return resourcesDao.getResourceInfoByIdList(idList);
	}
	
	@Override
	public List<Resources> getNonRestrictedResources() {
		LOGGER.debug("Service层：获取用户登录后默认可以访问的资源，不受角色限制");
		return resourcesDao.getNonRestrictedResources();
	}
	
	@Override
	public List<Resources> getAllMenuResources(){
		ResourcesQuery resourcesQuery = new ResourcesQuery();
		resourcesQuery.setIsMenu(new Integer(1)); //资源是菜单
//		resourcesQuery.setIsRestricted(new Integer(1)); //资源的访问需要用户拥有权限
		resourcesQuery.setSortBy("module_id, sort_num");
		resourcesQuery.setSortType("1");
		resourcesQuery.setPageSize(-1); //将分页设为-1 ，即意味不想要分页
		return resourcesDao.getResourcesByQuery(resourcesQuery);
	}
	
}
