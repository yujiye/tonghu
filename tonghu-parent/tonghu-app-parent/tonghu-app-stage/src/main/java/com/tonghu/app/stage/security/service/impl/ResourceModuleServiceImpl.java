package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.security.service.ResourceModuleService;
import com.tonghu.app.stage.security.service.ResourcesService;
import com.tonghu.pub.model.security.po.ResourceModule;
import com.tonghu.pub.model.security.po.Resources;
import com.tonghu.pub.security.dao.ResourceModuleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 系统资源模块信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 上午10:04:29
 */
@Service("resourceModuleService")
public class ResourceModuleServiceImpl implements ResourceModuleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceModuleServiceImpl.class);
	
	@Resource
	private ResourceModuleDao resourceModuleDao;
	
	@Resource
	private ResourcesService resourcesService;

	@Override
	public List<ResourceModule> getResourceModuleInfoByIdList(List<Long> idList) {
		LOGGER.debug("Service层：根据模块id集合获取资源模块信息");
		return resourceModuleDao.getResourceModuleInfoByIdList(idList);
	}
	
	@Override
	public List<ResourceModule> getResourceModuleInfoWithResources(){
		List<Resources> resourcesList = resourcesService.getAllMenuResources();
		if (resourcesList == null || resourcesList.size() == 0){
			LOGGER.error("获取菜单资源信息出现错误！");
			return null;
		}
		
		Set<Long> moudleIdSet = new HashSet<Long>();
		for (Resources resources : resourcesList) {
			moudleIdSet.add(resources.getModuleId());
		}
		if (moudleIdSet == null || moudleIdSet.size() == 0) {
			LOGGER.error("获取菜单资源信息出现错误！");
			return null;
		} 
		//获取到资源模块的集合
		List<ResourceModule> moduleList = this.getResourceModuleInfoByIdList(new ArrayList<Long>(moudleIdSet));
		if (moduleList == null || moduleList.size() == 0) {
			LOGGER.error("获取菜单资源信息出现错误！");
			return null;
		} 
		
		for (ResourceModule module : moduleList) {
			for (Resources resources : resourcesList) {
				if (module.getId().compareTo(resources.getModuleId()) == 0)
					module.addResource(resources);
			}
		}
		return moduleList;
	}

}
