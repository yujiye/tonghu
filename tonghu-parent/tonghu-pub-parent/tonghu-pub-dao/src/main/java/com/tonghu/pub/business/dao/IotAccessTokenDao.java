package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.IotAccessToken;
import com.tonghu.pub.model.business.po.PositionTest;

/**
 * @author liangyongjian
 * @desc IOT接口调用凭证Dao层接口
 * @create 2018-06-20 22:04
 **/
public interface IotAccessTokenDao {


    /**
     * 更新IOT接口调用凭证
     * @param iotAccessToken
     * @return
     */
    Integer update(IotAccessToken iotAccessToken);


    /**
     * 获取最新的IOT接口调用凭证
     * @return
     */
    IotAccessToken getRecentIotAccessToken();

}
