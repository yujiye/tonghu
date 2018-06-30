package com.tonghu.app.api.business.service.impl;

import com.tonghu.pub.business.dao.IotAccessTokenDao;
import com.tonghu.pub.model.business.po.IotAccessToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liangyongjian
 * @desc IOT接口调用凭证服务类
 * @create 2018-06-20 23:15
 **/
@Service("iotAccessTokenService")
public class IotAccessTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotAccessTokenService.class);

    @Autowired
    private IotAccessTokenDao iotAccessTokenDao;

    public String getIotAccessTokenStr() {
        IotAccessToken accessToken = iotAccessTokenDao.getRecentIotAccessToken();
        if (accessToken == null || StringUtils.isEmpty(accessToken.getAccessToken())) {
            LOGGER.error("DB中不存在IotAccessToken表记录或者 accessToken 信息为空");
            return null;
        }

        return accessToken.getAccessToken();

    }

    /**
     * 调用IOT接口，重置IOT的 调用凭证 字符串
     */
    public void resetAccessToken() {
        IotAccessToken accessToken = iotAccessTokenDao.getRecentIotAccessToken();
        //TODO 等待完善
    }


}
