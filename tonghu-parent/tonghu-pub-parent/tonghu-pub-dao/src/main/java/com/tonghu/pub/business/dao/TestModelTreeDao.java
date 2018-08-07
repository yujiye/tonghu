package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.TestAreaTree;
import com.tonghu.pub.model.business.po.TestModelTree;
import com.tonghu.pub.model.business.po.query.TestAreaTreeQuery;
import com.tonghu.pub.model.business.po.query.TestModelTreeQuery;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 模块信息测试表 Dao层接口
 * @create 2018-08-07 17:59
 **/
public interface TestModelTreeDao {

    /**
     * 根据查询对象查询数据
     * @param query
     * @return
     */
    List<TestModelTree> getTestModelTreeInfoByQuery(TestModelTreeQuery query);
    
}
