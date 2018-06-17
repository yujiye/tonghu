package com.tonghu.pub.security.dao.impl;


import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.security.po.Users;
import com.tonghu.pub.model.security.po.query.UsersQuery;
import com.tonghu.pub.security.dao.UsersDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 处理用户信息的DAO 实现类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:15:48
 */
@Repository(value = "usersDao")
public class UsersDaoImpl extends BaseDao implements UsersDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersDaoImpl.class);
	
	@Override
	public Users getUserInfoByUserId(Long userId) {
		if (userId == null) {
			LOGGER.error("Dao层：根据用户id查取用户信息，用户id参数为空：{}", userId);
			return null;
		}
		LOGGER.debug("Dao层：根据用户id查取用户信息：{}", userId);
		return (Users)select("usersDao.selectUserInfoById", userId);
	}
	
	@Override
	public Integer getUserCountByName(UsersQuery usersQuery){
		LOGGER.debug("Dao层：根据用户名称、用户id，精确查找符合条件的用户数量");
		return (Integer)getReadSqlSession().selectOne("usersDao.selectUserCountByQueryForCheck", usersQuery);
	}
	
	@Override
	public Integer getUserCountByEmail(UsersQuery usersQuery){
		LOGGER.debug("Dao层：根据电子邮箱、用户id，精确查找符合条件的用户数量");
		return (Integer)getReadSqlSession().selectOne("usersDao.selectUserCountByQueryForCheck", usersQuery);
	}
	
	@Override
	public Integer getUsersCountByQuery(UsersQuery usersQuery) {
		LOGGER.debug("Dao层：根据检索条件获取用户数量");
		return (Integer)getReadSqlSession().selectOne("usersDao.selectUsersCountByQuery", usersQuery);
	}
	
	@Override
	public List<Users> getUsersInfoByQuery(UsersQuery usersQuery) {
		LOGGER.debug("Dao层：根据检索条件获取用户的记录信息");
		return getReadSqlSession().selectList("usersDao.selectUsersInfoByQuery", usersQuery);
	}
	
	@Override
	public List<Users> getUserInfoByUserIdList (List<Long> userIdList) {
		if (userIdList == null || userIdList.size() == 0) {
			LOGGER.error("Dao层：根据用户id集合查取用户信息，用户id集合参数为空：{}", userIdList);
			return null;
		}
		LOGGER.debug("Dao层：根据用户id查取用户信息：{}", userIdList);
		return getReadSqlSession().selectList("usersDao.selectUserInfoByIdList", userIdList);
	}
	
	@Override
	public Users getUserInfoByUserName(String userName){
		if (StringUtils.isBlank(userName)) {
			LOGGER.error("Dao层：根据用户登录名查取用户信息，用户名参数为空：{}", userName);
			return null;
		}
		LOGGER.debug("Dao层：根据用户登录名查取用户信息：{}", userName);
		return (Users)select("usersDao.selectUserInfoByUserName", userName);
	}
	
	@Override
	public Users getUserInfoByUserEmail (String userEmail) {
		if (StringUtils.isBlank(userEmail)) {
			LOGGER.error("Dao层：根据用户电子邮箱查取用户信息，用户名参数为空：{}", userEmail);
			return null;
		}
		LOGGER.debug("Dao层：根据用户电子邮箱查取用户信息：{}", userEmail);
		return (Users)select("usersDao.selectUserInfoByUserEmail", userEmail);
	}
	
	@Override
	public List<Users> getAllUsersInfo() {
		LOGGER.debug("Dao层：获取所有用户信息");
		return getReadSqlSession().selectList("usersDao.selectAllUser");
	}
	
	@Override
	public Integer updateUserInfoById (Users user) {
		LOGGER.debug("Dao层：根据用户id更新用户信息：{}", user.toString());
		return update("usersDao.updateUserInfoById",user);
	}
	
	@Override
	public Integer updateUserPasswordById (Users user) {
		LOGGER.debug("Dao层：根据用户id更新用户密码信息：{}", user.toString());
		return update("usersDao.updateUserPasswordById",user);
	}
	
	@Override
	public Integer deleteUserById (Long userId) {
		LOGGER.debug("Dao层：根据用户id删除用户信息：{}", userId);
		return update("usersDao.deleteUserById",userId);
	}
	
	@Override
	public Integer selectCountByUserInfo(Users user) {
		LOGGER.debug("Dao层：查取符合检索条件的用户数量，检索条件为: {}", user);
		return (Integer)select("usersDao.selectUsersCountByQuery",user);
	}
	
	@Override
	public Integer addNewUser(Users user) {
		LOGGER.debug("Dao层：增加信息的用户信息");
		return (Integer)insert("usersDao.insertNewUser",user);
	}

}
