package com.tonghu.app.stage.security.service.impl;

import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.config.WebConfig;
import com.tonghu.app.stage.security.service.UserRoleService;
import com.tonghu.app.stage.security.service.UsersService;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.constant.*;
import com.tonghu.pub.common.utils.MyStringUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.po.CheckErrorDto;
import com.tonghu.pub.model.po.TotalInfo;
import com.tonghu.pub.model.security.po.Roles;
import com.tonghu.pub.model.security.po.UserPassword;
import com.tonghu.pub.model.security.po.UserRole;
import com.tonghu.pub.model.security.po.Users;
import com.tonghu.pub.model.security.po.query.UsersQuery;
import com.tonghu.pub.security.dao.UsersDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 系统用户信息 服务层实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:11:08
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {
	
	@Resource
	private UsersDao usersDao;

	@Resource
	private WebConfig webConfig;

	@Resource
	private InputConfig inputConfig;
	
	@Resource
	private SysLogContext sysLogContext;
	
	@Resource
	private SystemConstant systemConstant;
	
	@Resource
	private UserRoleService userRoleService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	@Override
	public void getUsersInfoForInitPage(ModelMap model, String method, HttpServletRequest request) {
		LOGGER.debug("Service层：根据查询参数获取用户信息 用于用户管理页面");

		UsersQuery usersQuery = new UsersQuery();
		//以id升序排序
		usersQuery.setSortBy("id");
		usersQuery.setSortType("1");
		usersQuery.setPageSize(webConfig.getPageSize());
		if (method.equals("POST")) {
			String pageNum = request.getParameter("pageNumInput");
			if (! StringUtils.isBlank(pageNum)) {
				usersQuery.setPage(Integer.parseInt(pageNum));
			}
			String userName = request.getParameter("userNameSearch");
			if (! StringUtils.isBlank(userName)) {
				usersQuery.setUserName(userName);
				model.addAttribute("userName", userName);
			}
			String userEmail = request.getParameter("userEmailSearch");
			if (! StringUtils.isBlank(userEmail)) {
				usersQuery.setUserEmail(userEmail);
				model.addAttribute("userEmail", userEmail);
			}
			String orgId = request.getParameter("orgSearch");
			if (! StringUtils.isBlank(orgId) && !orgId.equals("-1")) {
				usersQuery.setOrgId(new Long(orgId));
				model.addAttribute("orgId", orgId);
			}
		}
		Integer totalCount = usersDao.getUsersCountByQuery(usersQuery);
		TotalInfo totalInfo = new TotalInfo(totalCount, usersQuery.getPageSize(),
				usersQuery.getPage(), usersQuery.getStartNum());
		model.addAttribute("totalInfo", totalInfo);

		if (totalCount == 0) {
			return;
		}

		List<Users> usersList = this.getUsersInfoByQuery(usersQuery);
		
		//获取所有区域的信息，用于用户管理页面中的区域检索
		//获取角色信息
		List<Roles> rolesList = systemConstant.getRolesList();
		
		
		model.addAttribute("rolesList", rolesList);
		
		for (Users user : usersList) {

			if (user.getIsLock().compareTo(EnumIsLock.IS_LOCK_YES.getStatus()) == 0) {
				user.setLockStr("被锁定");
			} else if (user.getIsLock().compareTo(EnumIsLock.IS_LOCK_NO.getStatus()) == 0) {
				user.setLockStr("正常");
			}
			
			//获取用户角色名称
			user.setUserRoleList(userRoleService.getRoleIdListByUserId(user.getId()));
			if (user.getUserRoleList() != null && user.getUserRoleList().size() > 0) {
				String roleName = "";
				for (UserRole userRole : user.getUserRoleList()) {
					for (Roles roles : rolesList) {
						if(userRole.getRoleId().compareTo(roles.getId()) == 0) {
							roleName += ", " + roles.getRoleName();
							break;
						}
					}
				}
				if (roleName.length() > 0) 
					roleName = roleName.substring(2);
				user.setRoleName(roleName);
			}
			
		}
	
		model.addAttribute("usersList", usersList);
	}
	
	@Override
	public List<Users> getAllUsersInfo() {
		LOGGER.debug("Service层：获取所有用户的记录信息");
		List<Users> usersList = usersDao.getAllUsersInfo();
		if (CollectionUtils.isNotEmpty(usersList)) {
			for(Users users : usersList) {
				users.setUserRoleList(userRoleService.getRoleIdListByUserId(users.getId()));
			}
		}
		return usersList;
	}
	
	@Override
	public List<Users> getUsersInfoByQuery(UsersQuery usersQuery) {
		LOGGER.debug("Service层：根据检索条件获取用户的记录信息");
		return usersDao.getUsersInfoByQuery(usersQuery);
	}
	
	@Override
	public Users getExtendUsersById(final Long userId) {
		LOGGER.debug("Service层：根据用户id获取用户扩展信息");
		List<Users> usersList = systemConstant.getUserInfoByUserIdList(new ArrayList<Long>(){{add(userId);}});
		if (CollectionUtils.isEmpty(usersList) || usersList.size() == 0) {
			LOGGER.debug("根据用户id获取用户扩展信息，没有查取到用户信息：userId {}", userId);
			return null;
		}
		Users users = usersList.get(0);
		//获取用户角色名称
		if (CollectionUtils.isNotEmpty(users.getUserRoleList())) {
			users.setRoleId(users.getUserRoleList().get(0).getRoleId());
			String roleName = "";
			List<Roles> rolesList = systemConstant.getRolesList();
			for (UserRole userRole : users.getUserRoleList()) {
				for (Roles roles : rolesList) {
					if(userRole.getRoleId().compareTo(roles.getId()) == 0) {
						roleName += ", " + roles.getRoleName();
						break;
					}
				}
			}
			if (roleName.length() > 0)
				roleName = roleName.substring(2);
			users.setRoleName(roleName);
		}
		//获取创建和修改用户信息的用户名称
		if (users.getCreateUserId() != null || users.getUpdateUserId() != null) {
			List<Long> userIdList = new ArrayList<Long>();
			if (users.getCreateUserId() != null) {
				userIdList.add(users.getCreateUserId());
			}
			if (users.getUpdateUserId() != null) {
				userIdList.add(users.getUpdateUserId());
			}
			List<Users> uList = systemConstant.getUserInfoByUserIdList(userIdList);
			if (uList != null && uList.size() > 0) {
				for (Users user : uList) {
					if (users.getCreateUserId() != null && user.getId().compareTo(users.getCreateUserId()) == 0) {
						users.setCreateUserName(user.getTrueName());
					} 
					if (users.getUpdateUserId() != null && user.getId().compareTo(users.getUpdateUserId()) == 0) {
						users.setUpdateUserName(user.getTrueName());
					}
				}
			}
		}
		if (users.getIsLock().compareTo(EnumIsLock.IS_LOCK_YES.getStatus()) == 0) {
			users.setLockStr("被锁定");
		} else if (users.getIsLock().compareTo(EnumIsLock.IS_LOCK_NO.getStatus()) == 0) {
			users.setLockStr("正常");
		}
		return users;
	}
	
	@Override
	public boolean addNewUser(Users user, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：新增用户信息");
		boolean result = false;
		String sysLogCause = "";
		Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		//过滤掉用户信息中各个属性值的前后空格
		user.trim();
		if (this.checkNewUser(user, modelMap)) {
			//通过校验，开始进行更新
			user.setCreateUserId(currentUser.getId());
			user.setPassword(MyStringUtils.createMd5Str(user.getPassword(), null));
			Integer resultNum = usersDao.addNewUser(user);
			if (resultNum.compareTo(new Integer(1)) == 0) {

				if (user.getRoleId().compareTo(new Long(-1)) != 0) {
					UserRole userRole = new UserRole();
					userRole.setRoleId(user.getRoleId());
					userRole.setUserId(user.getId());
					userRole.setCreateUserId(currentUser.getId());
					resultNum = userRoleService.addNewUserRole(userRole);
					if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
						result = true;
						LOGGER.debug("创建用户成功！");
					} else {
						modelMap.put("status", "failure");
					}
				}
				systemConstant.setUsersSysCons();
			} else {
				sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
				modelMap.put("status", "failure");
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.ADD.getType(), "新增用户信息：" + user.getUserName(),
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean updateUserInfo (Users user, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：根据用户id更新用户信息");
		boolean result = false;
		String sysLogCause = "";
		Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		//过滤掉用户信息中各个属性值的前后空格
		user.trim();
		if (this.checkUpdateUser(user, modelMap)) {
			//通过校验，开始进行更新
			user.setUpdateUserId(currentUser.getId());
			Integer resultNum = usersDao.updateUserInfoById(user);
			if (resultNum.compareTo(new Integer(1)) == 0) {

				if (user.getRoleId().compareTo(new Long(-1)) != 0) {
					userRoleService.deleteUserRoleByUserId(user.getId());
					UserRole userRole = new UserRole();
					userRole.setRoleId(user.getRoleId());
					userRole.setUserId(user.getId());
					userRole.setCreateUserId(currentUser.getId());
					resultNum = userRoleService.addNewUserRole(userRole);
					if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
						result = true;
						LOGGER.debug("更新用户的角色信息成功！");
					} else {
						modelMap.put("status", "failure");
					}
				}
				systemConstant.setUsersSysCons();
			} else {
				sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
				modelMap.put("status", "failure");
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), "更新用户信息：" + user.getUserName(),
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean removeUser(final Long userId, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：根据用户id删除用户信息 物理删除");
		boolean result = false;
		String sysLogCause = "", userName = "";


		List<Users> usersList = systemConstant.getUserInfoByUserIdList(new ArrayList<Long>(){{add(userId);}});
		if (CollectionUtils.isEmpty(usersList) || usersList.size() == 0) {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该用户记录";
			userName = "[不存在该用户]";
		} else {
			Users user = usersList.get(0);
			userName = user.getUserName();
			if (checkRemoveUser(userId, modelMap)) {
				//通过了校验
				Integer resultNum = usersDao.deleteUserById(userId);
				if (resultNum.compareTo(new Integer(1)) == 0) {
					result = true;
					systemConstant.setUsersSysCons();
				} else {
					sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
					modelMap.put("status", "failure");
				}
			} else {
				sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——" + modelMap.get("data");
			}
		}

		
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.DELETE.getType(), 
				"删除用户信息" + userName,
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		
		return result;
	}
	
	/**
	 * 校验所给的用户是否可以被删除
	 * @param userId
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午02:47:12
	 * @version V1.0
	 */
	private boolean checkRemoveUser(Long userId, PubModelMap modelMap){
		//校验角色是否为可编辑
		if (!checkUserIsEditable(userId)) {
			LOGGER.error("该用户是系统默认用户，不可删除: userId={}", userId);
			modelMap.put("status", "failure");
			modelMap.put("data", "该用户是系统默认用户，不可删除！");
			modelMap.put("fresh", "false");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean updateUserPass(Users user, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("Service层：根据用户id更新用户密码信息");
		boolean result = false;
		String sysLogCause = "";
		Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		if (StringUtils.isBlank(user.getPassword())) {
			LOGGER.info("请输入密码");
			errorInfoList.add(new CheckErrorDto("users_change_user_pass", "请输入密码"));
		} else {
			Integer length = new Integer(user.getPassword().length());
			if (length.compareTo(inputConfig.getPasswordMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto("users_change_user_pass", "密码长度不能小于" 
						+ inputConfig.getPasswordMinLength() + "位"));
			} else if (length.compareTo(inputConfig.getPasswordMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("users_change_user_pass", "密码长度不能大于" +
						inputConfig.getPasswordMaxLength() + "位"));
			}
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		} else {
			//通过校验，开始进行更新
			user.setUpdateUserId(currentUser.getId());
			user.setPassword(MyStringUtils.createMd5Str(user.getPassword(), null));
			Integer resultNum = usersDao.updateUserPasswordById(user);
			if (resultNum.compareTo(new Integer(1)) == 0) {
				result = true;
				systemConstant.setUsersSysCons();
			} else {
				sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
				modelMap.put("status", "failure");
			}
		}
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), 
				"更新用户密码" + (result ? "：" + user.getUserName() : ""), 
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		
		return result;
	}
	
	/**
	 * 校验改后用户的信息
	 * @param user
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午05:22:14
	 * @version V1.0
	 */
	private boolean checkUpdateUser(Users user, PubModelMap modelMap){
		if (!checkUserIsEditable(user.getId())) {
			LOGGER.error("该用户是系统默认用户，不可编辑: userId={}", user.getId());
			modelMap.put("status", "failure");
			modelMap.put("data", "该用户是系统默认用户，不可编辑！");
			modelMap.put("fresh", "false");
			return false;
		}
		
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		this.checkUserBaseInfo(user, errorInfoList);
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		LOGGER.debug("Service层：校验更新后用户的名称是否已存在！");
		UsersQuery userQuery = new UsersQuery();
		userQuery.setUserName(user.getUserName());
		userQuery.setId(user.getId());
		
		Integer resultNum = usersDao.getUserCountByName(userQuery);
		if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
			LOGGER.info("用户名称已经存在");
			errorInfoList.add(new CheckErrorDto("users_change_user_name", "用户名称已经存在"));
		}

		if (StringUtils.isNotBlank(user.getUserEmail())) {
			UsersQuery userEmailQuery = new UsersQuery();
			userEmailQuery.setUserEmail(user.getUserEmail());
			userEmailQuery.setId(user.getId());

			resultNum = usersDao.getUserCountByEmail(userEmailQuery);
			if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
				LOGGER.info("用户电子邮箱已经存在");
				errorInfoList.add(new CheckErrorDto("users_change_user_email", "用户电子邮箱已经存在"));
			}
		}

		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		return true;
	}
	
	/**
	 * 校验新用户的信息
	 * @param user
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午02:21:30
	 * @version V1.0
	 */
	private boolean checkNewUser(Users user, PubModelMap modelMap) {
		LOGGER.debug("Service层：校验用户名称的长度，备注的长度，校验新用户的名称是否已存在！");
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		
		this.checkUserBaseInfo(user, errorInfoList);
		
		//校验登录名是否唯一
		if (this.checkUserNameIsExist(user.getUserName())) {
			LOGGER.info("用户登录名已经存在");
			errorInfoList.add(new CheckErrorDto("user_name", "用户登录名已经存在"));
		}
		
		//校验改后的电子邮箱是否唯一
		if (StringUtils.isNotBlank(user.getUserEmail()) && this.checkUserEmailIsExist(user.getUserEmail())) {
			LOGGER.info("电子邮箱已经存在");
			errorInfoList.add(new CheckErrorDto("user_email", "电子邮箱已经存在"));
		}

		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		return true;
	}
	
	@Override
	public Users getUserInfoByUserId(Long userId) {
		if (userId == null) {
			LOGGER.error("Service层：根据用户id获取用户信息：用户id参数为空 {}", userId);
			return null;
		}
		LOGGER.debug("Service层：根据用户id获取用户信息");
		return usersDao.getUserInfoByUserId(userId);
	}
	
	@Override
	public List<Users> getUserInfoByUserIdList(List<Long> userIdList) {
		if (userIdList == null) {
			LOGGER.error("Service层：根据用户id集合获取用户信息：用户id集合参数为空 {}", userIdList);
			return null;
		}
		LOGGER.debug("Service层：根据用户id集合获取用户信息");
		return usersDao.getUserInfoByUserIdList(userIdList);
	}
	
	@Override
	public Users getUserInfoByUserName (String userName) {
		if (StringUtils.isBlank(userName)) {
			LOGGER.error("Service层：根据用户登录名查取用户信息：用户名称参数为空 {}", userName);
			return null;
		}
		LOGGER.debug("Service层：根据用户登录名查取用户信息");
		return usersDao.getUserInfoByUserName(userName);
	}
	
	@Override
	public Users getUserInfoByUserEmail (String userEmail) {
		if (StringUtils.isBlank(userEmail)) {
			LOGGER.error("Service层：根据用户电子邮箱地址查取用户信息：电子邮箱参数为空 {}", userEmail);
			return null;
		}
		LOGGER.debug("Service层：根据用户电子邮箱地址查取用户信息");
		return usersDao.getUserInfoByUserEmail(userEmail);
	}
	
	@Override
	public boolean updateCurrentUserBaseInfo(HttpServletRequest request, Users newUser, PubModelMap modelMap) {
		boolean result = false;
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		String sysLogCause = "";
		//过滤掉用户信息中各个属性值的前后空格
		newUser.trim();
		if (this.checkUserChangeInfo(user, newUser, modelMap)) {
			if (modelMap.get("status") != null && modelMap.get("status").equals("same")) {
				LOGGER.debug("改前与改后的用户基本信息相同，无需更新数据库，直接返回更新成功");
				result = true;
			} else {
				//通过校验，开始进行更新
				newUser.setId(user.getId());
				newUser.setUpdateUserId(user.getId());
				Integer resultNum = usersDao.updateUserInfoById(newUser);
				if (resultNum.compareTo(new Integer(1)) == 0) {
					user.setUserName(newUser.getUserName());
					user.setUserEmail(newUser.getUserEmail());
					user.setTrueName(newUser.getTrueName());
					user.setUserPhone(newUser.getUserPhone());
					user.setUserMobile(newUser.getUserMobile());
					user.setNote(newUser.getNote());
					//此时更新成功，将改后的用户信息更新到Session中
					request.getSession().setAttribute("CURRENT_USER_INFO", user);
					result = true;
					systemConstant.setUsersSysCons();
				} else {
					sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
					modelMap.put("status", "failure");
				}
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), "当前用户更新自己的信息：" + newUser.getUserName(), 
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean updateCurrentUserPassInfo(HttpServletRequest request, UserPassword password, PubModelMap modelMap) {
		boolean result = false;
		Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		String sysLogCause = "";
		//过滤掉用户信息中各个属性值的前后空格
		password.trim();
		if (this.checkUserChangePassInfo(currentUser, password, modelMap)) {
			if (modelMap.get("status") != null && modelMap.get("status").equals("same")) {
				LOGGER.debug("改前与改后的密码信息相同，无需更新数据库，直接返回更新成功");
				result = true;
			} else {
				//通过校验，开始进行更新
				Users user = new Users();
				user.setId(currentUser.getId());
				user.setPassword(MyStringUtils.createMd5Str(password.getNewPassword(), null));
				user.setUpdateUserId(currentUser.getId());
				Integer resultNum = usersDao.updateUserPasswordById(user);
				if (resultNum.compareTo(new Integer(1)) == 0) {
					result = true;
					systemConstant.setUsersSysCons();
				} else {
					sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
					modelMap.put("status", "failure");
				}
			}
		} else {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
		}
		
		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), "当前用户更新自己的密码信息："
						+ currentUser.getUserName(),
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	@Override
	public boolean getUserRoleInfoByUserId(Long userId, PubModelMap modelMap) {
		LOGGER.debug("根据指定的用户id 获取用户角色信息: userId={}", userId);
		//获取用户的角色信息
		List<UserRole> userRoleId = userRoleService.getRoleIdListByUserId(userId);
		modelMap.put("status", "success");
		if (userRoleId != null && userRoleId.size() > 0) {
			modelMap.put("data", userRoleId.get(0).getRoleId());
		} else {
			modelMap.put("data", "-1");
		}
		return true;
	}
	
	@Override
	@Transactional(value = "tonghuTxManager", isolation = Isolation.REPEATABLE_READ,
			propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateUserRole(final Long userId, Long roleId, HttpServletRequest request, PubModelMap modelMap) {
		LOGGER.debug("更新用户的角色信息: userId={}, roleId={}", userId, roleId);
		boolean result = false;
		String sysLogCause = "";
		Users currentUser = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		String userName = "";
		List<Users> usersList = systemConstant.getUserInfoByUserIdList(new ArrayList<Long>(){{add(userId);}});
		if (CollectionUtils.isEmpty(usersList) || usersList.size() == 0) {
			sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该用户记录";
			userName = "[用户不存在]";
		} else {
			Users user = usersList.get(0);
			userName = user.getUserName();
			if (this.checkUpdateUserRole(userId, roleId, modelMap)) {
				//通过了校验
				//首先需要删除用户角色管理表中 用户与角色的关联记录
				//接着将新的用户角色关联信息插入到表中
				userRoleService.deleteUserRoleByUserId(userId);
				if (roleId.compareTo(new Long(-1)) != 0) {
					UserRole userRole = new UserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(userId);
					userRole.setCreateUserId(currentUser.getId());
					Integer resultNum = userRoleService.addNewUserRole(userRole);
					if (resultNum != null && resultNum.compareTo(new Integer(0)) > 0) {
						result = true;
						LOGGER.debug("更新用户的角色信息成功！");
						systemConstant.setUsersSysCons();
					} else {
						modelMap.put("status", "failure");
					}
				} else {
					result = true;
				}

			} else {
				sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——" + modelMap.get("data");
			}
		}

		//记录访问日志
		sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(), "更新用户的角色信息：" + userName,
				result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
		return result;
	}
	
	private boolean checkUpdateUserRole(Long userId, Long roleId, PubModelMap modelMap) {
		//校验当前用户是否可以被编辑
		if (! this.checkUserIsEditable(userId)) {
			LOGGER.error("该用户是系统默认用户，不可改变角色: roleId={}", userId);
			modelMap.put("status", "failure");
			modelMap.put("data", "该用户是系统默认用户，不可改变角色！");
			modelMap.put("fresh", "false");
			return false;
		}
		
		if (roleId.compareTo(new Long(-1)) != 0) {
			boolean isHaveRole = false;
			List<Roles> rolesList = systemConstant.getRolesList();
			if (rolesList != null && rolesList.size() > 0) {
				for (Roles role : rolesList) {
					if(role.getId().compareTo(roleId) == 0){
						isHaveRole = true;
						break;
					}
				}
			}
			
			if (!isHaveRole) {
				LOGGER.error("更新用户角色失败，所传角色id参数有误，系统中无此角色id: roleId={}", roleId);
				modelMap.put("status", "failure");
				modelMap.put("data", "更新用户角色失败，参数有误！");
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 校验用户自己所修改的密码信息
	 * 校验新密码是否与确认密码相同；校验原密码是否与信息密码相同；校验原密码是否与数据库中密码相同；
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:56:20
	 * @version V1.0
	 */
	private boolean checkUserChangePassInfo(Users user, UserPassword password, PubModelMap modelMap) {
		LOGGER.debug("Service层：校验用户自己所修改的密码信息：校验新密码是否与确认密码相同；校验原密码是否与信息密码相同；校验原密码是否与数据库中密码相同");
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		Integer length = null;
		
		if (StringUtils.isBlank(password.getPassword())) {
			LOGGER.info("请输入原密码");
			errorInfoList.add(new CheckErrorDto("old_password", "请输入原密码"));
		} else {
			length = new Integer(password.getPassword().length());
			if (length.compareTo(inputConfig.getPasswordMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto("old_password", "密码长度不能小于" 
						+ inputConfig.getPasswordMinLength() + "位"));
			} else if (length.compareTo(inputConfig.getPasswordMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("old_password", "密码长度不能大于" +
						inputConfig.getPasswordMaxLength() + "位"));
			}
		}
		
		if (StringUtils.isBlank(password.getNewPassword())) {
			LOGGER.info("请输入新密码");
			errorInfoList.add(new CheckErrorDto("new_password", "请输入新密码"));
		} else {
			length = new Integer(password.getPassword().length());
			if (length.compareTo(inputConfig.getPasswordMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto("new_password", "密码长度不能小于" 
						+ inputConfig.getPasswordMinLength() + "位"));
			} else if (length.compareTo(inputConfig.getPasswordMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("new_password", "密码长度不能大于" + 
						inputConfig.getPasswordMaxLength() + "位"));
			}
		}
		
		if (StringUtils.isBlank(password.getConfirmNewPassword())) {
			LOGGER.info("请输入确认密码");
			errorInfoList.add(new CheckErrorDto("confirm_new_password", "请输入确认密码"));
		} else {
			length = new Integer(password.getPassword().length());
			if (length.compareTo(inputConfig.getPasswordMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto("confirm_new_password", "密码长度不能小于" 
						+ inputConfig.getPasswordMaxLength() + "位"));
			} else if (length.compareTo(inputConfig.getPasswordMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("confirm_new_password", "密码长度不能大于" + 
						inputConfig.getPasswordMaxLength() + "位"));
			}
		}
		
		if (! password.getNewPassword().equals(password.getConfirmNewPassword())) {
			LOGGER.info("两次密码不一致，请确认");
			errorInfoList.add(new CheckErrorDto("confirm_new_password", "两次密码不一致,请确认"));
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}

		String encryptOldPass = MyStringUtils.createMd5Str(password.getPassword(), null);
		if (! this.checkPasswordIsCorrect(user.getId(), encryptOldPass)) {
			LOGGER.info("原密码错误，请重新输入");
			errorInfoList.add(new CheckErrorDto("old_password", "原密码错误,请重新输入"));
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		if (password.getPassword().equals(password.getNewPassword())) {
			modelMap.put("status", "same");
		}
		
		return true;
	}
	
	/**
	 * 校验用户自己所修改的用户信息
	 * 校验用户名、电子邮箱、真实姓名信息是否存在，其长度，校验改后登录名是否唯一，校验改后的电子邮箱是否唯一。如若登录名与电子邮箱没有发生变动，该校验自动认为成功
	 * @param newUser
	 * @param modelMap
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午08:51:01
	 * @version V1.0
	 */
	private boolean checkUserChangeInfo(Users user, Users newUser, PubModelMap modelMap) {
		LOGGER.debug("Service层：校验用户自己所修改的用户信息：校验用户名、电子邮箱、真实姓名等信息是否存在，其长度，校验改后登录名是否唯一，校验改后的电子邮箱是否唯一。" +
				"如若登录名与电子邮箱没有发生变动，该校验自动认为成功！");
		List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();
		Integer length = null;
		if (StringUtils.isBlank(newUser.getUserName())) {
			LOGGER.info("请输入用户名");
			errorInfoList.add(new CheckErrorDto("change_user_name", "请输入用户名"));
		} else {
			length = new Integer(newUser.getUserName().length());
			if (length.compareTo(inputConfig.getUserNameMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto("change_user_name", "用户名长度不能小于" 
						+ inputConfig.getUserNameMinLength() + "位"));
			} else if (length.compareTo(inputConfig.getUserNameMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_user_name", "用户名长度不能大于" + 
						inputConfig.getUserNameMaxLength() + "位"));
			}
		}
		
		if (!StringUtils.isBlank(newUser.getUserEmail())) {
			length = new Integer(newUser.getUserEmail().length());
			if (length.compareTo(inputConfig.getUserEmailLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_user_email", "电子邮箱地址长度不能大于"
						+ inputConfig.getUserEmailLength() + "位"));
			}
		}
		
		if (StringUtils.isBlank(newUser.getTrueName())) {
			LOGGER.info("请输入真实姓名");
			errorInfoList.add(new CheckErrorDto("change_true_name", "请输入真实姓名"));
		} else {
			length = new Integer(newUser.getTrueName().length());
			if (length.compareTo(inputConfig.getTrueNameLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_true_name", "真实姓名长度不能大于" 
						+ inputConfig.getTrueNameLength() + "位"));
			}
		}
		
		if (!StringUtils.isBlank(newUser.getUserMobile())) {
			length = new Integer(newUser.getUserMobile().length());
			if (length.compareTo(inputConfig.getUserMobileLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_user_mobile", "手机号码长度不能大于" 
						+ inputConfig.getUserMobileLength() + "位"));
			}
		}
		
		if (!StringUtils.isBlank(newUser.getUserPhone())) {
			length = new Integer(newUser.getUserPhone().length());
			if (length.compareTo(inputConfig.getUserPhoneLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_user_phone", "座机号码长度不能大于" 
						+ inputConfig.getUserPhoneLength() + "位"));
			}
		}
		
		if (!StringUtils.isBlank(newUser.getNote())) {
			length = new Integer(newUser.getNote().length());
			if (length.compareTo(inputConfig.getNoteLength()) > 0) {
				errorInfoList.add(new CheckErrorDto("change_note", "备注长度不能大于" 
						+ inputConfig.getNoteLength() + "位"));
			}
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
			
		
		//确认用户登录名已经发生了变化
		if(!(newUser.getUserName().equals(user.getUserName()))) {
			//校验改后登录名是否唯一
			if (this.checkUserNameIsExist(newUser.getUserName())) {
				LOGGER.info("用户登录名已经存在");
				errorInfoList.add(new CheckErrorDto("change_user_name", "用户登录名已经存在"));
			}
		}
		
		//确认电子邮箱已经发生了变化
		if(StringUtils.isNotBlank(user.getUserEmail()) && !(newUser.getUserEmail().equals(user.getUserEmail()))) {
			//校验改后的电子邮箱是否唯一
			if (this.checkUserEmailIsExist(newUser.getUserEmail())) {
				LOGGER.info("电子邮箱已经存在");
				errorInfoList.add(new CheckErrorDto("change_user_email", "电子邮箱已经存在"));
			}
		}
		
		if (errorInfoList.size() > 0) {
			modelMap.put("status", "error");
			modelMap.put("data", errorInfoList);
			return false;
		}
		
		if (newUser.getUserName().equals(user.getUserName()) && newUser.getUserEmail().equals(user.getUserEmail())
				&& newUser.getTrueName().equals(user.getTrueName()) && newUser.getUserMobile().equals(user.getUserMobile())
				&& newUser.getUserPhone().equals(user.getUserPhone()) && newUser.getNote().equals(user.getNote())) {
			modelMap.put("status", "allSame");
		}
		
		return true;
	}
	
	/**
	 * 校验登录名是否唯一
	 * @param newUserName
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午09:25:30
	 * @version V1.0
	 */
	private boolean checkUserNameIsExist(String newUserName) {
		Users user = this.getUserInfoByUserName(newUserName);
		if(user != null && user.getUserName().equals(newUserName)) {
			return true;
		} else 
			return false;
	}
	
	/**
	 * 校验用户电子邮箱是否唯一
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午09:30:13
	 * @version V1.0
	 */
	private boolean checkUserEmailIsExist(String newUserEmail) {
		Users user = this.getUserInfoByUserEmail(newUserEmail);
		if(user != null && user.getUserEmail().equals(newUserEmail)) {
			return true;
		} else 
			return false;
	}
	
	/**
	 * 根据用户id，查询该用户的密码是否与给定的字符串相同
	 * @param userId
	 * @param password
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:36:22
	 * @version V1.0
	 */
	private boolean checkPasswordIsCorrect(Long userId, String password) {
		Users user = new Users(userId, password);
		Integer resultNum = usersDao.selectCountByUserInfo(user);
		return resultNum.compareTo(new Integer(1)) == 0;
	}
	
	
	/**
	 * 校验用户的基本信息
	 * @param user
	 * @param errorInfoList
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午02:22:58
	 * @version V1.0
	 */
	private void checkUserBaseInfo(Users user, List<CheckErrorDto> errorInfoList) {
		Integer length = null;
		String prefix = "";
		if (user.getId() != null)
			prefix = "users_change_";
		//用户名
		if (StringUtils.isBlank(user.getUserName())) {
			LOGGER.info("请输入用户名");
			errorInfoList.add(new CheckErrorDto(prefix + "user_name", "请输入用户名"));
		} else {
			length = new Integer(user.getUserName().length());
			if (length.compareTo(inputConfig.getUserNameMinLength()) < 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_name", "用户名长度不能小于" 
						+ inputConfig.getUserNameMinLength() + "位"));
			} else if (length.compareTo(inputConfig.getUserNameMaxLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_name", "用户名长度不能大于" + 
						inputConfig.getUserNameMaxLength() + "位"));
			}
		}
		
		//登录密码
		if (user.getId() == null) {
			if (StringUtils.isBlank(user.getPassword())) {
				LOGGER.info("请输入密码");
				errorInfoList.add(new CheckErrorDto(prefix + "user_pass", "请输入密码"));
			} else {
				length = new Integer(user.getPassword().length());
				if (length.compareTo(inputConfig.getPasswordMinLength()) < 0) {
					errorInfoList.add(new CheckErrorDto(prefix + "user_pass", "密码长度不能小于" 
							+ inputConfig.getPasswordMaxLength() + "位"));
				} else if (length.compareTo(inputConfig.getPasswordMaxLength()) > 0) {
					errorInfoList.add(new CheckErrorDto(prefix + "user_pass", "密码长度不能大于" + 
							inputConfig.getPasswordMaxLength() + "位"));
				}
			}
		}
		
		
		//电子邮箱
		if (!StringUtils.isBlank(user.getUserEmail())) {
			length = new Integer(user.getUserEmail().length());
			if (length.compareTo(inputConfig.getUserEmailLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_email", "电子邮箱地址长度不能大于"
						+ inputConfig.getUserEmailLength() + "位"));
			}
		}
		
		//真实姓名
		if (StringUtils.isBlank(user.getTrueName())) {
			LOGGER.info("请输入真实姓名");
			errorInfoList.add(new CheckErrorDto(prefix + "true_name", "请输入真实姓名"));
		} else {
			length = new Integer(user.getTrueName().length());
			if (length.compareTo(inputConfig.getTrueNameLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "true_name", "真实姓名长度不能大于" 
						+ inputConfig.getTrueNameLength() + "位"));
			}
		}
		
		//用户职称
		if (StringUtils.isBlank(user.getTrueName())) {
			LOGGER.info("请输入用户职称");
			errorInfoList.add(new CheckErrorDto(prefix + "user_title", "请输入用户职称"));
		} else {
			length = new Integer(user.getTrueName().length());
			if (length.compareTo(inputConfig.getUserTitleLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_title", "用户职称长度不能大于" 
						+ inputConfig.getUserTitleLength() + "位"));
			}
		}
		
		//用户状态
		if (user.getIsLock() == null || user.getIsLock().compareTo(new Integer(-1)) == 0) {
			LOGGER.info("请选择用户状态");
			errorInfoList.add(new CheckErrorDto(prefix + "user_lock", "请选择用户状态"));
		} else {
			if (! (user.getIsLock().compareTo(EnumIsLock.IS_LOCK_NO.getStatus()) == 0 
					|| user.getIsLock().compareTo(EnumIsLock.IS_LOCK_YES.getStatus()) == 0)) {
				LOGGER.info("请选择用户状态");
				errorInfoList.add(new CheckErrorDto(prefix + "user_lock", "请选择用户状态"));
			}
		}
		
		//手机号
		if (!StringUtils.isBlank(user.getUserMobile())) {
			length = new Integer(user.getUserMobile().length());
			if (length.compareTo(inputConfig.getUserEmailLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_mobile", "手机号码长度不能大于" 
						+ inputConfig.getUserEmailLength() + "位"));
			}
		}
		
		//座机号码
		if (!StringUtils.isBlank(user.getUserPhone())) {
			length = new Integer(user.getUserPhone().length());
			if (length.compareTo(inputConfig.getUserPhoneLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_phone", "座机号码长度不能大于" 
						+ inputConfig.getUserPhoneLength() + "位"));
			}
		}
		
		//用户备注信息
		if (!StringUtils.isBlank(user.getNote())) {
			length = new Integer(user.getNote().length());
			if (length.compareTo(inputConfig.getNoteLength()) > 0) {
				errorInfoList.add(new CheckErrorDto(prefix + "user_note", "备注长度不能大于" 
						+ inputConfig.getNoteLength() + "位"));
			}
		}
	}
	
	/**
	 * 根据用户id获取用户是否可以被编辑
	 * @param userId
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:02:20
	 * @version V1.0
	 */
	private boolean checkUserIsEditable(final Long userId) {
		List<Users> usersList = systemConstant.getUserInfoByUserIdList(new ArrayList<Long>(){{add(userId);}});
		if (CollectionUtils.isEmpty(usersList) || usersList.size() == 0) {
			return false;
		}
		Users user = usersList.get(0);
		if (user.getEditable().compareTo(EnumEditable.EDITABLE_YES.getStatus()) == 0) {
			return true;
		}
		return false;
	}
	
}
