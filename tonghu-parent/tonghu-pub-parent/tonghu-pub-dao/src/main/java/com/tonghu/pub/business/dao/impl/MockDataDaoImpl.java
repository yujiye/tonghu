package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.MockDataDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.MockData;
import com.tonghu.pub.model.business.po.query.MockDataQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 模拟数据Dao层实现类
 * @create 2018-06-28 23:15
 **/
@Repository("mockDataDao")
public class MockDataDaoImpl extends BaseDao implements MockDataDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataDaoImpl.class);

    @Override
    public List<MockData> getMockDataByQuery(MockDataQuery query) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据查询条件获取 模拟数据");
        }
        return getReadSqlSession().selectList("mockDataDao.selectMockDataInfoByQuery", query);
    }

    @Override
    public Integer getMockDataCountByQuery(MockDataQuery query) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据查询条件获取 模拟数据记录条数");
        }
        return getReadSqlSession().selectOne("mockDataDao.selectMockDataCountByQuery", query);
    }

    @Override
    public List<MockData> getAllMockData() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：获取所有的模拟数据");
        }
        return getReadSqlSession().selectList("mockDataDao.selectAllMockDataInfo");
    }

    @Override
    public MockData getMockDataById(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据 id 获取模拟数据");
        }
        return getReadSqlSession().selectOne("mockDataDao.selectMockDataById", id);
    }

    @Override
    public MockData getMockDataByName(String dataName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据 数据项名称 精确查找 获取模拟数据");
        }
        return getReadSqlSession().selectOne("mockDataDao.selectMockDataByName", dataName);
    }

    @Override
    public Integer insertNewMockData(MockData mockData){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：新增模拟数据");
        }
        return insert("mockDataDao.insertNewMockData", mockData);
    }

    @Override
    public Integer updateMockDataById(MockData mockData) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：更新模拟数据");
        }
        return update("mockDataDao.updateMockDataById", mockData);
    }

    @Override
    public Integer deleteMockDataById(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：删除模拟数据");
        }
        return delete("mockDataDao.deleteMockDataById", id);
    }

}
