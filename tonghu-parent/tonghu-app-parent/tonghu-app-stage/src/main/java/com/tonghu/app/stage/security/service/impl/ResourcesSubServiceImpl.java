package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.security.service.ResourcesSubService;
import com.tonghu.pub.model.security.po.ResourcesSub;
import com.tonghu.pub.security.dao.ResourcesSubDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统子资源信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午06:13:59
 */
@Service("resourcesSubService")
public class ResourcesSubServiceImpl implements ResourcesSubService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesSubServiceImpl.class);
	
	@Resource
	private ResourcesSubDao resourcesSubDao;
	
	public List<ResourcesSub> getSubResourcesByRIdList(List<Long> resourceIdList) {
		LOGGER.debug("Service层：根据资源id集合 获取子资源信息集合");
		return resourcesSubDao.getSubResourcesByRIdList(resourceIdList);
	}

	public List<ResourcesSub> getAllSubResources() {
		LOGGER.debug("Service层：获取所有子资源信息集合");
		return resourcesSubDao.getAllSubResources();
	}

}
