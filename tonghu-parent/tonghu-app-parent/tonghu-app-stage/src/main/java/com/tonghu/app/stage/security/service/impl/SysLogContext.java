package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.security.service.SysLogService;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.model.security.po.SysLog;
import com.tonghu.pub.model.security.po.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @Description: 多线程方式存储系统日志
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午11:53:46
 */
@Service("sysLogContext")
public class SysLogContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysLogContext.class);
	
	@Resource(name="taskExecutor")
	public ThreadPoolTaskExecutor poolTaskExecutor;
	
	@Resource
	public SysLogService sysLogService;
	private String innerSerialId;

	public void saveSysLog(HttpServletRequest request, Integer opType, String content,
						   Integer success, String cause) {
		String hostIp = "127.0.0.1";
		try {
			InetAddress address = InetAddress.getLocalHost();
			hostIp = address.getHostAddress();
		} catch (Exception e) {
		}

		LOGGER.debug("多线程方式保存系统日志");
		String innerSerialId = request.getParameter(Constant.INNER_SERIAL);
		if (StringUtils.isBlank(innerSerialId)) {
			innerSerialId = "";
		}
		Long userId = null;
		if (request.getSession().getAttribute("CURRENT_USER_INFO") != null)
			userId = ((Users)request.getSession().getAttribute("CURRENT_USER_INFO")).getId();

		if (!StringUtils.isEmpty(cause) && cause.length() > 500) {
			cause = cause.substring(0, 500);
		}

		poolTaskExecutor.execute(new SysLogTask(sysLogService, 
				new SysLog(innerSerialId, userId, hostIp, opType, content, success, cause)));
	}

	// 专用于记录作业对项目数据的修改日志
	public void saveSysLog(Integer opType, String content, Integer success, String cause) {
		LOGGER.debug("多线程方式保存系统日志");
		String innerSerialId = "";
		Long userId = 0L;
		poolTaskExecutor.execute(new SysLogTask(sysLogService,
				new SysLog(innerSerialId, userId, "127.0.0.1", opType, content, success, cause)));
	}
	
    /**
     * @Description: 保存系统日志 多线程方式
     * @author liangyongjian
     * @Version V1.0
     * @date 2017-09-24 上午12:03:05
     */
    public class SysLogTask implements Runnable{
		
		private SysLog log;
		private SysLogService sysLogService;
		
		public SysLogTask(SysLogService service, SysLog sysLog){
			sysLogService = service;
			log = sysLog;
		}

		/**
		 * 创建日志
		 */
		@Override
		public void run() {
			sysLogService.addNewSysLogInfo(log);
		}

	}

}
