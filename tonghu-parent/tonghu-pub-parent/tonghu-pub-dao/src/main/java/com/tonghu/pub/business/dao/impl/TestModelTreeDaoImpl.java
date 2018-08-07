package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.TestModelTreeDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.TestModelTree;
import com.tonghu.pub.model.business.po.query.TestModelTreeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 模块信息测试表 Dao层实现类
 * @create 2018-08-07 18:17
 **/
@Repository("testModelTreeDao")
public class TestModelTreeDaoImpl extends BaseDao implements TestModelTreeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPotsDaoImpl.class);

    @Override
    public List<TestModelTree> getTestModelTreeInfoByQuery(TestModelTreeQuery query) {
        LOGGER.debug("Dao层：获取模块信息数据");
        return getReadSqlSession().selectList("testModelTreeDao.selectTestModelTreeInfoByQuery", query);
    }

}
