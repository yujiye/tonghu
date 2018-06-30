package com.tonghu.pub.model.coordinate;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 地理坐标信息
 * @create 2018-06-17 17:34
 **/
@Getter
@Setter
public class DevicePosition extends BasePo {

    private Position position;

}
