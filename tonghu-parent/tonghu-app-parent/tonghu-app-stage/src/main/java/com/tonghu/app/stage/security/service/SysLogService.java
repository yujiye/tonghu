package com.tonghu.app.stage.security.service;

import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.security.po.SysLog;
import com.tonghu.pub.model.security.po.Users;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 系统日志 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午11:33:29
 */
public interface SysLogService {
	
	/**
	 * 根据查询参数获取系统日志信息 用于系统日志管理页面
	 * @param model
	 * @param method
	 * @param request
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午11:33:29
	 * @version V1.0
	 */
	public void getSysLogInfoForInitPage(ModelMap model, String method, HttpServletRequest request);
	
	/**
	 * 保存新的系统日志信息 包含正确性和重复性的校验
	 * @param sysLog
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午11:33:29
	 * @version V1.0
	 */
	public void addNewSysLogInfo(SysLog sysLog);
	
	/**
	 * 根据系统日志id删除系统日志信息 物理删除
	 * @param sysLogId
	 * @param user
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午11:33:29
	 * @version V1.0
	 */
	public void removeSysLog(Long sysLogId, Users user, PubModelMap modelMap);

}
