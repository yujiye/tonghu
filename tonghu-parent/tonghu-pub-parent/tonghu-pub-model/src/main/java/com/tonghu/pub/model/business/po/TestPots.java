package com.tonghu.pub.model.business.po;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("iotid")
    private String iotId;

    @SerializedName("iotproid")
    private String iotProId;

    private String mac;

    private String x;

    private String y;

    @SerializedName("areaid")
    private String areaId;

    @SerializedName("modelid")
    private String modelId;

    private String des;

    @SerializedName("cadid")
    private String cadId;

    private String other;

    @SerializedName("proname")
    private String proName;


}
