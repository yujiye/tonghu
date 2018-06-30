package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.TestInterfaceDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.TestInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 接口测试表 Dao层实现类
 * @create 2018-06-27 20:48
 **/
@Repository("testInterfaceDao")
public class TestInterfaceDaoImpl extends BaseDao implements TestInterfaceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestInterfaceDaoImpl.class);

    @Override
    public List<TestInterface> getAllRecordInfo() {
        LOGGER.debug("Dao层：获取所有的接口测试表的数据");
        return getReadSqlSession().selectList("testInterfaceDao.selectAllRecordInfo");
    }

}
