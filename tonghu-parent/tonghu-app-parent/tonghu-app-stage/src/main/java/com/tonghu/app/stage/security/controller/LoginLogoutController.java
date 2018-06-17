package com.tonghu.app.stage.security.controller;

import com.tonghu.pub.common.constant.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户登录与退出系统 控制层
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-9-27 下午06:54:54
 */
@Controller
public class LoginLogoutController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginLogoutController.class);

	//session 注册器
	@Autowired
	SessionRegistry sessionRegistry;

	/**
	 * 用户登录时调用地址
	 * @param msg
	 * @param request
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-9-27 下午06:55:32
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String root(String msg, HttpServletRequest request, ModelMap model) {
		model.addAttribute("ctx", request.getContextPath()); //应用名称
		LOGGER.debug("进入到用户登录控制器处理逻辑！");
		if (!StringUtils.isBlank(msg)) {
			if (msg.equals("error")) {
				String errorInfo = "";
				if (request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {

					String exceptionInfo = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString();
					if (exceptionInfo != null) {
						for (Map.Entry<String, String> entry : Constant.LONGIN_EXCEPTION.LONGIN_EXCEPTION_MAP.entrySet()) {
							if (exceptionInfo.contains(entry.getKey()))
								errorInfo = entry.getValue() + " ";
						}
					}
					LOGGER.info("用户登录遇到错误：{}" , errorInfo);
					errorInfo += "登录失败，请重新登录！";
					model.addAttribute("message", errorInfo);
					if (request.getSession().getAttribute("LOGIN_USER_NAME") != null) {
						model.addAttribute("username", request.getSession().getAttribute("LOGIN_USER_NAME"));
					}
				} else if (request.getAttribute("USER_MENU_ERROR") != null) {
					errorInfo = "系统异常，加载菜单失败！";
					model.addAttribute("message", errorInfo);
				}
			}
			if (msg.equals("expired")) {
				LOGGER.info("认证已经失效，可能是账户在其他地方登录造成！");
				model.addAttribute("message", "认证已经失效，请重新登录！");
			}
		}
		return "login"; // 设置返回页面，这里对应 / 目录下的 login.ftl 文件
	}

	/**
	 * 用户登录控制器处理逻辑
	 * @param msg
	 * @param request
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-9-27 下午06:56:23
	 * @version V1.0
	 */
	@RequestMapping(method = {RequestMethod.GET}, value = "/login")
	public String login(String msg, HttpServletRequest request, ModelMap model) {
		model.addAttribute("ctx", request.getContextPath()); //应用名称
		LOGGER.debug("进入到用户登录控制器处理逻辑！");
		if (!StringUtils.isBlank(msg)) {
			if (msg.equals("error")) {
				String errorInfo = "";
				if (request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {

					String exceptionInfo = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString();
					if (exceptionInfo != null) {
						for (Map.Entry<String, String> entry : Constant.LONGIN_EXCEPTION.LONGIN_EXCEPTION_MAP.entrySet()) {
							if (exceptionInfo.contains(entry.getKey()))
								errorInfo = entry.getValue() + " ";
						}
					}
					LOGGER.info("用户登录遇到错误：{}" , errorInfo);
					errorInfo += "登录失败，请重新登录！";
					model.addAttribute("message", errorInfo);
					if (request.getSession().getAttribute("LOGIN_USER_NAME") != null) {
						model.addAttribute("username", request.getSession().getAttribute("LOGIN_USER_NAME"));
					}
				} else if (request.getAttribute("USER_MENU_ERROR") != null) {
					errorInfo = "系统异常，加载菜单失败！";
					model.addAttribute("message", errorInfo);
				} else if (request.getAttribute("SPRING_SECURITY_CONTEXT") == null) {
					errorInfo = "认证已失效，请重新登录！";
					model.addAttribute("message", errorInfo);
				}
			}
			if (msg.equals("expired")) {
				LOGGER.info("认证已失效，可能是账户在其他地方登录造成！");
				model.addAttribute("message", "认证已失效，请重新登录！");
			}
		}
		return "login"; // 设置返回页面，这里对应 / 目录下的 login.ftl 文件
	}

	/**
	 * 列出当前在线用户的信息
	 * SessionRegistry跟踪所有活跃用户session的信息。我们可以可以很容易地在一个页面中列出所有的用户活跃用户以及他们在站点中使用的名字。
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-9-27 下午06:57:21
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/onlineUserList.do")
	public String onlineUserList(ModelMap model, HttpServletRequest request) {
		//getAllPrincipals：返回拥有活跃session的Principal对象（典型情况下为UserDetails对象）所组成的List
		List<Object> slist = sessionRegistry.getAllPrincipals();
        int totalCount = slist.size();
        if(slist.size() == 0) {
        	model.addAttribute("message", "{totalCount:" + totalCount + ",data:[]}");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> pageList = slist;
        StringBuffer retVal = new StringBuffer("[");
        int k = 0;
        for(int i=0; i<pageList.size(); i++) {
        	// getAllSessions(principal, includeExpired)：得到指定Principal的SessionInformation组成的List，包含了每个session的信息。也能够包含过期的session
            List<SessionInformation> sessionList = sessionRegistry.getAllSessions(pageList.get(i), true);
            User user = (User)pageList.get(i);
            for(SessionInformation t : sessionList){
                if(k != 0){
                    retVal.append(",");
                }
                retVal.append("{\"id\":\""+k+"\",\"userName\":\""+user.getUsername()+"\",\"expired\":\""+t.isExpired()
                		+"\",\"sessionId\":\""+t.getSessionId()+"\",\"lastRequest\":\""+sdf.format(t.getLastRequest())+"\"}");
                k = k+1;
            }
        }
        retVal.append("]");
		model.addAttribute("message", retVal);
		return "activeUsers";
	}

	/**
	 * 控制用户下线
	 * @param msg
	 * @param request
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-9-27 下午06:57:45
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/shotOffOnlineUser.do")
	public String shotOffOnlineUser(String msg, HttpServletRequest request, ModelMap model) {
		//getAllPrincipals：返回拥有活跃session的Principal对象（典型情况下为UserDetails对象）所组成的List
		List<Object> slist = sessionRegistry.getAllPrincipals();
        for(int i=0; i < slist.size(); i++) {
        	// getAllSessions(principal, includeExpired)：得到指定Principal的SessionInformation组成的List，包含了每个session的信息。也能够包含过期的session
            List<SessionInformation> sessionList = sessionRegistry.getAllSessions(slist.get(i), true);
            User user = (User)slist.get(i);
            for(SessionInformation t : sessionList) {
            	//只踢掉传递来的用户
            	if (!StringUtils.isBlank(msg) && !StringUtils.isBlank(msg.trim())) {
            		if (user.getUsername().equals(msg.trim())) {
            			if (t.isExpired())
                    		t.expireNow();
            			break;
            		}
            	} else { //全部用户都踢掉
            		if (t.isExpired())
                		t.expireNow();
            	}
            }
        }
        model.addAttribute("message", msg + "已经退出");
        return "main";
     }

}
