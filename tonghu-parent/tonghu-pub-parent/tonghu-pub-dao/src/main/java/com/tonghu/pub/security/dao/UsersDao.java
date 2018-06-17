package com.tonghu.pub.security.dao;

import com.tonghu.pub.model.security.po.Users;
import com.tonghu.pub.model.security.po.query.UsersQuery;

import java.util.List;

/**
 * @Description: 系统用户信息的DAO 接口类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午08:24:55
 */
public interface UsersDao {
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Users getUserInfoByUserId(Long userId);
	
	/**
	 * 根据用户名、用户id 精确查找符合条件的用户数量
	 * @param usersQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getUserCountByName(UsersQuery usersQuery);
	
	/**
	 * 根据邮箱地址、用户id 精确查找符合条件的用户数量
	 * @param usersQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getUserCountByEmail(UsersQuery usersQuery);
	
	/**
	 * 根据检索条件获取用户数量信息
	 * @param usersQuery
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer getUsersCountByQuery(UsersQuery usersQuery);
	
	/**
	 * 根据检索条件获取用户信息
	 * @param usersQuery
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<Users> getUsersInfoByQuery(UsersQuery usersQuery);
	
	/**
	 * 根据用户id集合获取用户信息
	 * @param userIdList
	 * @return List<Users>
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public List<Users> getUserInfoByUserIdList(List<Long> userIdList);
	
	/**
	 * 根据用户登录名称查询用户信息 用户登录名称是唯一的，所以返回信息为单条记录或null
	 * @param userName
	 * @return User
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Users getUserInfoByUserName(String userName);
	
	/**
	 * 根据用户电子邮箱查取用户信息
	 * @param userEmail
	 * @return Users
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Users getUserInfoByUserEmail(String userEmail);
	
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
	 * 根据用户ID修改用户信息
	 * @param user
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer updateUserInfoById(Users user);
	
	/**
	 * 根据用户ID修改用户密码信息
	 * @param user
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer updateUserPasswordById(Users user);
	
	/**
	 * 根据用户id删除用户信息
	 * @param userId
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer deleteUserById(Long userId);
	
	/**
	 * 检索符合给定条件的用户信息
	 * @param user
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer selectCountByUserInfo(Users user);
	
	/**
	 * 添加新用户
	 * @param user
	 * @return Integer
	 * @throws
	 * @author liangyongjian
	 * @date 2017-09-24 下午08:24:55
	 * @version V1.0
	 */
	public Integer addNewUser(Users user);

}
