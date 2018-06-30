package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.MockData;
import com.tonghu.pub.model.business.po.query.MockDataQuery;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 模拟数据Dao层 接口
 * @create 2018-06-28 22:51
 **/
public interface MockDataDao {

    /**
     * 根据查询条件获取 模拟数据
     * @param query
     * @return
     */
    List<MockData> getMockDataByQuery(MockDataQuery query);

    /**
     * 根据查询条件获取 模拟数据的记录条数
     * @param query
     * @return
     */
    Integer getMockDataCountByQuery(MockDataQuery query);

    /**
     * 获取所有的模拟数据
     * @return
     */
    List<MockData> getAllMockData();

    /**
     * 根据 id 获取模拟数据
     * @param id
     * @return
     */
    MockData getMockDataById(Long id);

    /**
     * 根据 数据项名称 精确查找 获取模拟数据
     * @param dataName
     * @return
     */
    MockData getMockDataByName(String dataName);

    /**
     * 新增模拟数据
     * @param mockData
     * @return
     */
    Integer insertNewMockData(MockData mockData);

    /**
     * 更新模拟数据
     * @param mockData
     * @return
     */
    Integer updateMockDataById(MockData mockData);

    /**
     * 删除模拟数据
     * @param id
     * @return
     */
    Integer deleteMockDataById(Long id);

}
