package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.TestPotsDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.TestPots;
import com.tonghu.pub.model.business.po.query.TestPotsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 点位测试表 Dao层实现类
 * @create 2018-07-10 18:01
 **/
@Repository("testPotsDao")
public class TestPotsDaoImpl extends BaseDao implements TestPotsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPotsDaoImpl.class);

    @Override
    public List<TestPots> getTestPotsInfoByQuery(TestPotsQuery query) {
        LOGGER.debug("Dao层：获取所有的接口测试表的数据");
        return getReadSqlSession().selectList("testPotsDao.selectTestPotsInfoByQuery", query);
    }

}
