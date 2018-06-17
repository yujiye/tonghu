package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.PositionTest;

import java.util.List;

/**
 * @author liangyongjian
 * @desc
 * @create 2018-06-17 18:42
 **/
public interface PositionTestDao {

    Integer addOne(PositionTest positionTest);

    List<PositionTest> getAllPositionTestInfo();

}
