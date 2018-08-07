package com.tonghu.app.api.config;

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
public class TongHuConfig {

    @Value("#{tongHuConfigs['TOP_AREA_ID']}")
    private String topAreaId;

    @Value("#{tongHuConfigs['FIRST_OPEN_AREA_ID']}")
    private String firstOpenAreaId;

    @Value("#{tongHuConfigs['SHOW_HALL_AREA_ID']}")
    private String showHallAreaId;

    @Value("#{tongHuConfigs['HOTEL_AREA_ID']}")
    private String hotelAreaId;

}
