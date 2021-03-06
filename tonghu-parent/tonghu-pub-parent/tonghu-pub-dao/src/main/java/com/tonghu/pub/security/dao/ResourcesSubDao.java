package com.tonghu.pub.security.dao;


import com.tonghu.pub.model.security.po.ResourcesSub;

import java.util.List;

/**
 * @Description: 系统子资源信息 Dao 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public interface ResourcesSubDao {
	
	/**
	 * 根据资源id集合 获取子资源信息集合
	 * @param resourceIdList
	 * @return List<ResourcesSub>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<ResourcesSub> getSubResourcesByRIdList(List<Long> resourceIdList);


	/**
	 * 获取所有的子资源信息集合
	 * @return
	 */
	public List<ResourcesSub> getAllSubResources();

}
