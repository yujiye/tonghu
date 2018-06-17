package com.tonghu.app.stage.security.controller;

import com.tonghu.app.stage.security.service.UsersService;
import com.tonghu.pub.common.utils.WebUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.security.po.UserPassword;
import com.tonghu.pub.model.security.po.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 用户信息管理 控制器
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午02:58:23
 */
@Controller
public class UsersController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
	
	@Resource
	private UsersService usersService;

	/**
	 * 用户管理页面
	 * @param request
	 * @param model
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:55:55
	 * @version V1.0
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/userManage.do")
	public String usersManage(HttpServletRequest request, ModelMap model) {
		String method = request.getMethod();
		LOGGER.debug("以 {} 方式访问用户管理页面", method);
		model.addAttribute("pageHanName", "用户"); //页面名称
		WebUtils.WrapperModle(request, model);
		model.put("mulitselect", "mulitselect");
		usersService.getUsersInfoForInitPage(model, method, request);
		return "manage/" + model.get("pageName");
	}
	
	/**
	 * 根据指定的用户id 获取用户信息
	 * @param userId
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午12:35:17
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/showUser.do")
	public @ResponseBody Map<String, Object> getUserInfo(
			@RequestParam(value = "userId", required = true)Long userId, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("获取给定id的用户信息，userId={}", userId);
		PubModelMap modelMap = new PubModelMap(request);
		try {
			if (userId == null) {
				LOGGER.error("获取给定id的用户信息失败，用户id为空");
				modelMap.put("status", "error");
				modelMap.put("data", "该用户不存在，请刷新页面重新请求!");
			} else {
				Users users = usersService.getExtendUsersById(userId);
				if (users != null) {
					modelMap.put("status", "success");
					modelMap.put("data", users);
				} else {
					modelMap.put("status", "failure");
					modelMap.put("data", "该用户不存在，请刷新页面重新请求!");
				}
			}
		} catch(Exception e) {
			LOGGER.error("获取给定id的用户信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 保存新的用户信息
	 * @param user
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午02:14:37
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveNewUser.do")
	public @ResponseBody Map<String, Object> addNewUser(Users user,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("保存新的用户信息！");
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (currentUser == null || user == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.addNewUser(user, request, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "保存新的用户信息成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("保存新的用户信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午05:19:11
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/changeUser.do")
	public @ResponseBody Map<String, Object> updateUser(Users user,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("更新用户信息！");
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || currentUser == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.updateUserInfo(user, request, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "更新用户信息成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("更新用户信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/changeUserPass.do")
	public @ResponseBody Map<String, Object> changeUserPass(Users user,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("更新用户信息！");
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || currentUser == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.updateUserPass(user, request, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "更新用户信息成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("更新用户信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 获取当前用户的具体信息
	 * @param request
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午07:54:46
	 * @version V1.0
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/currentUserInfo.do")
	public @ResponseBody Map<String, Object> getCurrentUserInfo(
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("获取当前用户的具体信息！");
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		PubModelMap modelMap = new PubModelMap(request);
		modelMap.put("total", 1);
		modelMap.put("data", user);
		modelMap.put("status", "success");
		return modelMap.getModelMap();
	}
	
	/**
	 * 删除用户信息
	 * @param userId
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:16:27
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/removeUser.do")
	public @ResponseBody Map<String, Object> removeUserInfo(
			@RequestParam(value = "userId", required = true)Long userId, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("删除用户信息！");
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || userId == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.removeUser(userId, request, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "删除用户信息成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("删除用户信息的操作出现异常：{}", e);
			modelMap.put("status", "error");
			modelMap.put("data", "系统中存在该用户的操作数据，如上报活动等！<BR/>无法删除！");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 修改当前用户的基本信息
	 * @param newUser
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午08:57:18
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/setCurrentUserInfo.do")
	public @ResponseBody Map<String, Object> setCurrentUserInfo(Users newUser, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("修改当前用户的基本信息");
		LOGGER.debug("接收到的用户信息：{}" , newUser.toString());
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || newUser == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.updateCurrentUserBaseInfo(request, newUser, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "用户信息更新成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("修改当前用户的基本信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 设置当前用户的密码信息
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:26:36
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/setCurrentUserPassInfo.do")
	public @ResponseBody Map<String, Object> setCurrentUserPassInfo(UserPassword password,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("修改当前用户的密码信息");
		LOGGER.debug("接收到的密码信息 :{}" , password.toString());
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || password == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.updateCurrentUserPassInfo(request, password, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "密码信息更新成功！");
				}
			}
		} catch(Exception e) {
			LOGGER.error("修改当前用户的密码信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 根据指定的用户id 获取用户角色信息
	 * @param userId
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午07:30:17
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getUserRole.do")
	public @ResponseBody Map<String, Object> getUserRoleInfo(
			@RequestParam(value = "userId", required = true)Long userId, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("获取给定用户id的用户角色信息，userId={}", userId);
		PubModelMap modelMap = new PubModelMap(request);
		try {
			if (userId == null) {
				LOGGER.error("获取给定用户id的用户角色信息失败，用户id为空");
				modelMap.put("status", "error");
				modelMap.put("data", "该用户不存在，请刷新页面重新请求!");
			} else {
				usersService.getUserRoleInfoByUserId(userId, modelMap);
			}
		} catch(Exception e) {
			LOGGER.error("获取给定用户id的用户角色信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}
	
	/**
	 * 更新用户的角色信息
	 * @param userId
	 * @param roleId
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午05:19:11
	 * @version V1.0
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/changeUserRole.do")
	public @ResponseBody Map<String, Object> setUserRole(
			@RequestParam(value = "userId", required = true)Long userId, 
			@RequestParam(value = "roleId", required = true)Long roleId, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("更新用户角色信息！");
		PubModelMap modelMap = new PubModelMap(request);
		try {
			Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
			if (user == null || userId == null || roleId == null) {
				modelMap.put("status", "exception");
			} else {
				if (usersService.updateUserRole(userId, roleId, request, modelMap)) {
					modelMap.put("status", "success");
					modelMap.put("data", "更新用户角色信息成功！");
				} else {
					
				}
			}
		} catch(Exception e) {
			LOGGER.error("更新用户角色信息的操作出现异常：{}", e);
			modelMap.put("status", "exception");
		}
		return modelMap.getModelMap();
	}

}