package com.tonghu.app.stage.security.service;

import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.security.po.UserPassword;
import com.tonghu.pub.model.security.po.Users;
import com.tonghu.pub.model.security.po.query.UsersQuery;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 系统用户信息 服务层接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午09:09:58
 */
public interface UsersService {
	
	/**
	 * 根据查询参数获取用户信息 用于用户管理页面
	 * @param model
	 * @param method
	 * @param request
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:55:39
	 * @version V1.0
	 */
	public void getUsersInfoForInitPage(ModelMap model, String method, HttpServletRequest request);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午10:42:46
	 * @version V1.0
	 */
	public Users getUserInfoByUserId(Long userId);
	
	/**
	 * 根据用户id获取用户的扩展信息
	 * @param userId
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午12:38:42
	 * @version V1.0
	 */
	public Users getExtendUsersById(Long userId);
	
	/**
	 * 获取所有的用户信息
	 * @return List
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<Users> getAllUsersInfo();
	
	/**
	 * 保存新的用户信息
	 * @param user
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午02:17:23
	 * @version V1.0
	 */
	public boolean addNewUser(Users user, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午05:19:46
	 * @version V1.0
	 */
	public boolean updateUserInfo(Users user, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 更新用户密码信息
	 * @param user
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午06:27:06
	 * @version V1.0
	 */
	public boolean updateUserPass(Users user, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 删除用户信息记录
	 * @param userId
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午06:44:39
	 * @version V1.0
	 */
	public boolean removeUser(Long userId, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 根据用户id集合获取用户信息
	 * @param userIdList
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午10:52:51
	 * @version V1.0
	 */
	public List<Users> getUserInfoByUserIdList(List<Long> userIdList);
	
	/**
	 * 根据用户登录名查取用户信息
	 * @param userName
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:27:06
	 * @version V1.0
	 */
	public Users getUserInfoByUserName(String userName);
	
	/**
	 * 根据用户电子邮箱查取用户信息
	 * @param userEmail
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午09:35:16
	 * @version V1.0
	 */
	public Users getUserInfoByUserEmail(String userEmail);
	
	/**
	 * 修改当前用户的基本信息
	 * @param request
	 * @param newUser
	 * @param modelMap
	 * @return boolean
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 上午08:34:38
	 * @version V1.0
	 */
	public boolean updateCurrentUserBaseInfo(HttpServletRequest request, Users newUser, PubModelMap modelMap);
	
	/**
	 * 修改当前用户的密码信息
	 * @param request
	 * @param password
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:45:21
	 * @version V1.0
	 */
	public boolean updateCurrentUserPassInfo(HttpServletRequest request, UserPassword password, PubModelMap modelMap);
	
	/**
	 * 根据指定的用户id 获取用户角色信息
	 * @param userId
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午07:33:15
	 * @version V1.0
	 */
	public boolean getUserRoleInfoByUserId(Long userId, PubModelMap modelMap);
	
	/**
	 * 更新用户的角色信息
	 * @param userId
	 * @param roleId
	 * @param request
	 * @param modelMap
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午07:57:06
	 * @version V1.0
	 */
	public boolean updateUserRole(Long userId, Long roleId, HttpServletRequest request, PubModelMap modelMap);
	
	/**
	 * 根据检索条件获取用户信息
	 * @param usersQuery
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午09:32:46
	 * @version V1.0
	 */
	public List<Users> getUsersInfoByQuery(UsersQuery usersQuery);
}
