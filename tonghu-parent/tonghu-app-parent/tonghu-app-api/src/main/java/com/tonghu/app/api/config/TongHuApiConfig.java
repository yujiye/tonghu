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
public class TongHuApiConfig {

    @Value("#{tongHuApiConfigs['TOP_AREA_ID']}")
    private String topAreaId;

    @Value("#{tongHuApiConfigs['FIRST_OPEN_AREA_ID']}")
    private String firstOpenAreaId;

    @Value("#{tongHuApiConfigs['SHOW_HALL_AREA_ID']}")
    private String showHallAreaId;

    @Value("#{tongHuApiConfigs['HOTEL_AREA_ID']}")
    private String hotelAreaId;

    @Value("#{tongHuApiConfigs['ROOT_MODEL_ID']}")
    private String rootModelId;

}
