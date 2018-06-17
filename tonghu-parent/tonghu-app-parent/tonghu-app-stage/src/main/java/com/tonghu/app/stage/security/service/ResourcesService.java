package com.tonghu.app.stage.security.service;

import com.tonghu.pub.model.security.po.Resources;

import java.util.List;


/**
 * @Description: 系统资源信息 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:21:48
 */
public interface ResourcesService {
	
	/**
	 * 获取系统所有资源信息
	 * @param 
	 * @return List<Resources>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:49:18
	 * @version V1.0
	 */
	public List<Resources> getAllResources();
	
	/**
	 * 根据资源id集合获取系统资源信息
	 * @param idList
	 * @return Resources
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午05:02:00
	 * @version V1.0
	 */
	public List<Resources> getResourceInfoByIdList(List<Long> idList);
	
	/**
	 * 获取用户登录后默认可以访问的资源，不受角色限制
	 * @return List<Resources>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午04:46:54
	 * @version V1.0
	 */
	public List<Resources> getNonRestrictedResources();
	
	/**
	 * 获取所有用于生成菜单的资源的信息
	 * @return List<Resources>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:53:31
	 * @version V1.0
	 */
	public List<Resources> getAllMenuResources();

}
