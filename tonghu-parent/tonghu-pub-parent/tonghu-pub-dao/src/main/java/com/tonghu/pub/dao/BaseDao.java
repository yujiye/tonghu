package com.tonghu.pub.dao;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: MyBatis Dao基类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-09-24 下午07:37:50
 */
public class BaseDao implements Dao {

	@Resource(name = "tonghuSqlSession")
	private SqlSession writeSqlSession;
	
	@Resource(name = "tonghuSqlSession")
	private SqlSession readSqlSession;
	
	public SqlSession getReadSqlSession() {
		return readSqlSession;
	}
	
	@Override
	public int insert(String myBatis, Object object) {
		return writeSqlSession.insert(myBatis, object);
	}

	@Override
	public Object select(String myBatis, Object object) {
		return readSqlSession.selectOne(myBatis, object);
	}

	@Override
	public int update(String myBatis, Object object) {
		return writeSqlSession.update(myBatis, object);
	}

	@Override
	public int delete(String myBatis, Object object) {
		return writeSqlSession.delete(myBatis, object);
	}

	@Override
	public int delete(String myBatis) {
		return writeSqlSession.delete(myBatis);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectList(String myBatis, Object object) {
		return readSqlSession.selectList(myBatis, object);
	}

}
