package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 设备信息
 * @create 2018-08-11 21:19
 **/
@Getter
@Setter
public class DeviceInfo extends BasePo {

    private String title;
    private String id;
    private String position;

}
