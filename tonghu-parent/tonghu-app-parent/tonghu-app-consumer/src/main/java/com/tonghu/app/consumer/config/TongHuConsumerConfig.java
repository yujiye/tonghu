package com.tonghu.app.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangyongjian
 * @desc 配置信息
 * @create 2018-08-07 23:13
 **/
@Getter
@Setter
@Configuration
public class TongHuConsumerConfig {

    @Value("#{tongHuConsumerConfigs['APP_ID']}")
    private String appId;

    @Value("#{tongHuConsumerConfigs['APP_SECRET']}")
    private String appSecret;

    @Value("#{tongHuConsumerConfigs['END_POINT']}")
    private String endPoint;

    @Value("#{tongHuConsumerConfigs['MQ_HOST']}")
    private String mqHost;

}
