package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.PositionTestDao;
import com.tonghu.pub.common.dao.AttachmentDao;
import com.tonghu.pub.common.dao.impl.AttachmentDaoImpl;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.PositionTest;
import com.tonghu.pub.model.common.po.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc
 * @create 2018-06-17 18:43
 **/
@Repository(value="positionTestDao")
public class PositionTestDaoImpl extends BaseDao implements PositionTestDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(PositionTestDaoImpl.class);

    @Override
    public Integer addOne(PositionTest PositionTest) {
        LOGGER.debug("Dao层：添加坐标信息");
        return insert("positionTestDao.addOne", PositionTest);
    }

    @Override
    public List<PositionTest> getAllPositionTestInfo() {
        LOGGER.debug("Dao层：获取所有的坐标信息");
        return getReadSqlSession().selectList("positionTestDao.selectAllPositionTestInfo");
    }

}
