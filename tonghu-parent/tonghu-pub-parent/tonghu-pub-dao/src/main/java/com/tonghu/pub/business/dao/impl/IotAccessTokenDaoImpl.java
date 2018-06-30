package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.IotAccessTokenDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.IotAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author liangyongjian
 * @desc IOT接口调用凭证Dao层实现类
 * @create 2018-06-20 22:56
 **/
@Repository(value="iotAccessTokenDao")
public class IotAccessTokenDaoImpl extends BaseDao implements IotAccessTokenDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotAccessTokenDaoImpl.class);

    @Override
    public Integer update(IotAccessToken iotAccessToken) {
        LOGGER.debug("Dao层：更新IOT接口调用凭证信息");
        return update("iotAccessTokenDao.updateIotAccessToken", iotAccessToken);
    }

    @Override
    public IotAccessToken getRecentIotAccessToken() {
        LOGGER.debug("Dao层：获取最新的IOT接口调用凭证信息");
        return getReadSqlSession().selectOne(
                "iotAccessTokenDao.selectRecentIotAccessTokenInfo");
    }
}
