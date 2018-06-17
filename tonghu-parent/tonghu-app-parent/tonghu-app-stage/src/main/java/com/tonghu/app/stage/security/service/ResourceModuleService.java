package com.tonghu.app.stage.security.service;

import com.tonghu.pub.model.security.po.ResourceModule;

import java.util.List;

/**
 * @Description: 系统资源模块信息 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 上午10:02:52
 */
public interface ResourceModuleService {
	
	/**
	 * 根据模块id集合获取资源模块信息
	 * @param idList
	 * @return List<ResourceModule>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午10:03:52
	 * @version V1.0
	 */
	public List<ResourceModule> getResourceModuleInfoByIdList(List<Long> idList);
	
	/**
	 * 系统资源模块与模块下资源的信息
	 * @return List<ResourceModule>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午11:07:53
	 * @version V1.0
	 */
	public List<ResourceModule> getResourceModuleInfoWithResources();

}
