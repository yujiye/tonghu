package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.config.WebConfig;
import com.tonghu.app.stage.security.service.SysLogService;
import com.tonghu.app.stage.security.service.UsersService;
import com.tonghu.pub.common.constant.EnumIsSuccess;
import com.tonghu.pub.common.constant.EnumOperatorType;
import com.tonghu.pub.common.utils.DateTimeUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.po.TotalInfo;
import com.tonghu.pub.model.security.po.SysLog;
import com.tonghu.pub.model.security.po.Users;
import com.tonghu.pub.model.security.po.query.SysLogQuery;
import com.tonghu.pub.security.dao.SysLogDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Description: 系统日志 服务层 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午11:37:29
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysLogServiceImpl.class);

	@Resource
	private WebConfig webConfig;
	
	@Resource
	private SysLogDao sysLogDao;
	
	@Resource
	private UsersService usersService;

	@Override
	public void getSysLogInfoForInitPage(ModelMap model, String method, HttpServletRequest request) {
		LOGGER.debug("Service层：根据查询参数获取系统日志信息");

		//从内存中获取项目类型
		List<Users> userList = usersService.getAllUsersInfo();
		// 增加作业带来的日志 此用户id为0
		Users sysUser = new Users();
		sysUser.setId(0L);
		sysUser.setTrueName("系统自动同步");
		userList.add(sysUser);
		model.addAttribute("userList", userList);
		
		
		SysLogQuery sysLogQuery = new SysLogQuery();
		//以id降序排序
		sysLogQuery.setSortBy("id");
		sysLogQuery.setSortType("2");
		
		//每页显示15条数据
		sysLogQuery.setPageSize(webConfig.getPageSize());
		
		if (method.equals("POST")) {
			//系统用户名称
			String userId = request.getParameter("userSearch");
			if (! StringUtils.isBlank(userId) && !userId.equals("-1")) {
				sysLogQuery.setUserId(new Long(userId));
				model.addAttribute("userId", userId);
			}
			
			//检索框 操作结果
			String operResult = request.getParameter("operResultSearch");
			if (! StringUtils.isBlank(operResult) && !operResult.equals("-1")) {
				sysLogQuery.setIsSuccess(new Integer(operResult));
				model.addAttribute("operResult", operResult);
			}
			
			// 操作时间范围
			String operateDateStart = request.getParameter("operateDateStartSearch");
			String operateDateEnd = request.getParameter("operateDateEndSearch");
			try {
				if (! StringUtils.isBlank(operateDateStart)) {
					operateDateStart = DateTimeUtils.formatDateStr(operateDateStart, DateTimeUtils.YMD);
				}
				if (! StringUtils.isBlank(operateDateEnd)) {
					operateDateEnd = DateTimeUtils.formatDateStr(operateDateEnd, DateTimeUtils.YMD);
				}
				if(! StringUtils.isBlank(operateDateStart) && StringUtils.isBlank(operateDateEnd)) {
					operateDateEnd = DateTimeUtils.date2str(new Date(), DateTimeUtils.YMD);
				}
				if (! StringUtils.isBlank(operateDateEnd) && StringUtils.isBlank(operateDateEnd)) {
					operateDateStart = DateTimeUtils.formatDateStr(webConfig.getStartDateSearch(), DateTimeUtils.YMD);
				}
			} catch(Exception e) {
				e.printStackTrace();
				LOGGER.error("处理操作时间范围时出现异常：{}", e);
			}
			
			if (! StringUtils.isBlank(operateDateStart) && ! StringUtils.isBlank(operateDateEnd)) {
				sysLogQuery.setCreateTimeStart(operateDateStart + " 00:00:00");
				sysLogQuery.setCreateTimeEnd(operateDateEnd + " 23:59:59");
				model.addAttribute("operateDateStart", operateDateStart);
				model.addAttribute("operateDateEnd", operateDateEnd);
			}
			
			//当前页码
			String pageNum = request.getParameter("pageNumInput");
			if (! StringUtils.isBlank(pageNum)) {
				sysLogQuery.setPage(Integer.parseInt(pageNum));
			}
			
		} else {
			//GET方式访问，将查询条件初始化
			model.addAttribute("userSearch", "-1");
			model.addAttribute("operResultSearch", "-1");
			model.addAttribute("operateDateStartSearch", "");
			model.addAttribute("operateDateStartEnd", "");
		}
		Integer totalCount = sysLogDao.getSysLogCountByQuery(sysLogQuery);
		TotalInfo totalInfo = new TotalInfo(totalCount, sysLogQuery.getPageSize(),
				sysLogQuery.getPage(), sysLogQuery.getStartNum());
		model.addAttribute("totalInfo", totalInfo);

		if (totalCount == 0) {
			return;
		}

		List<SysLog> sysLogList = sysLogDao.getSysLogInfoByQuery(sysLogQuery);
		
		if (!CollectionUtils.isEmpty(sysLogList)) {
			for (SysLog sysLog : sysLogList) {
				
				if (sysLog.getUserId() != null) {
					if (sysLog.getUserId() == 0) {
						sysLog.setUserName("系统自动同步");
					} else {
						for (Users user :userList) {
							if (user.getId().compareTo(sysLog.getUserId()) == 0) {
								sysLog.setUserName(user.getTrueName());
								break;
							}
						}
					}
				}
				
				if (sysLog.getIsSuccess() != null) {
					sysLog.setSuccess(EnumIsSuccess.getSuccess(sysLog.getIsSuccess()));
				}
				
				if (sysLog.getOperatorType() != null) {
					sysLog.setOperatorTypeName(EnumOperatorType.getOperTypeName(sysLog.getOperatorType()));
				}
				
			}
		}
		
		model.addAttribute("sysLogList", sysLogList);
	}

	@Override
	public void addNewSysLogInfo(SysLog sysLog) {
		sysLogDao.addNewSysLog(sysLog);
	}

	@Override
	public void removeSysLog(Long sysLogId, Users user,
			PubModelMap modelMap) {
		// TODO Auto-generated method stub
		
	}
	


}
