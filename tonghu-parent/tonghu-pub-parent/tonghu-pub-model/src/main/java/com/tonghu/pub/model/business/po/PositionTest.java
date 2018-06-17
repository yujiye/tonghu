package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BusinessBasePo;

/**
 * @author liangyongjian
 * @desc 坐标位置
 * @create 2018-06-17 18:40
 **/
public class PositionTest extends BusinessBasePo {

    private Float x;
    private Float y;
    private Float z;

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }
}
