package com.tonghu.pub.model.business.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 点位测试表
 * @create 2018-07-10 17:39
 **/
@Getter
@Setter
public class TestPots extends BasePo {

    @JsonProperty(value = "iotid")
    private String iotId;

    @JsonProperty(value = "iotproid")
    private String iotProId;

    private String mac;

    private String x;

    private String y;

    @JsonProperty(value = "areaid")
    private String areaId;

    @JsonProperty(value = "modelid")
    private String modelId;

    private String des;

    @JsonProperty(value = "cadid")
    private String cadId;

    private String other;

    @JsonProperty(value = "proname")
    private String proName;


}
