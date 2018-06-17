package com.tonghu.pub.model.coordinate;

import com.tonghu.pub.model.po.BasePo;

/**
 * @author liangyongjian
 * @desc 地理坐标信息
 * @create 2018-06-17 17:34
 **/
public class DevicePosition extends BasePo {

    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
