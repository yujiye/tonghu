package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.TestAreaTree;
import com.tonghu.pub.model.business.po.TestPots;
import com.tonghu.pub.model.business.po.query.TestAreaTreeQuery;
import com.tonghu.pub.model.business.po.query.TestPotsQuery;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 地块信息测试表 Dao层接口
 * @create 2018-08-07 17:59
 **/
public interface TestAreaTreeDao {

    /**
     * 根据查询对象查询数据
     * @param query
     * @return
     */
    List<TestAreaTree> getTestAreaTreeInfoByQuery(TestAreaTreeQuery query);

}
