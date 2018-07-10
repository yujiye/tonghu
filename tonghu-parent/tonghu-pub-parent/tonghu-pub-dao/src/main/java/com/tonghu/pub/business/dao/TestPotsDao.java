package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.TestPots;
import com.tonghu.pub.model.business.po.query.TestPotsQuery;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 点位测试表 Dao层接口
 * @create 2018-07-10 17:59
 **/
public interface TestPotsDao {

    /**
     * 根据查询对象查询数据
     * @param query
     * @return
     */
    List<TestPots> getTestPotsInfoByQuery(TestPotsQuery query);
}
