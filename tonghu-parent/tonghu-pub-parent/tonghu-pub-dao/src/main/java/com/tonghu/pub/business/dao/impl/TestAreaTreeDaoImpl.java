package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.TestAreaTreeDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.TestAreaTree;
import com.tonghu.pub.model.business.po.query.TestAreaTreeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 模块信息测试表 Dao层实现类
 * @create 2018-08-07 18:17
 **/
@Repository("testAreaTreeDao")
public class TestAreaTreeDaoImpl extends BaseDao implements TestAreaTreeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPotsDaoImpl.class);

    @Override
    public List<TestAreaTree> getTestAreaTreeInfoByQuery(TestAreaTreeQuery query) {
        LOGGER.debug("Dao层：获取地块信息数据");
        return getReadSqlSession().selectList("testAreaTreeDao.selectTestAreaTreeInfoByQuery", query);
    }

}
