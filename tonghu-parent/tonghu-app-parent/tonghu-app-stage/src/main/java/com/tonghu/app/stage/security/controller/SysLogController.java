package com.tonghu.app.stage.security.controller;

import com.tonghu.app.stage.security.service.SysLogService;
import com.tonghu.pub.common.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @Description: 系统日志  MVC控制层web入口
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-21 下午11:29:22
 */
@Controller
public class SysLogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysLogController.class);
	
	@Resource
	private SysLogService sysLogService;
	
	/**
	 * 系统日志管理页面
	 * @param request
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午11:29:22
	 * @version V1.0
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/sysLog.do")
	public String sysLogManage(HttpServletRequest request, ModelMap model) {
		String method = request.getMethod();
		LOGGER.debug("以 {} 方式访问系统日志管理页面", method);
		model.addAttribute("pageHanName", "系统日志"); //页面名称
		WebUtils.WrapperModle(request, model);
		sysLogService.getSysLogInfoForInitPage(model, method, request);
		return "manage/" + model.get("pageName");
	}
	
}
