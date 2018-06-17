package com.tonghu.pub.model.coordinate;

import com.tonghu.pub.model.po.BasePo;

/**
 * @author liangyongjian
 * @desc 坐标信息
 * @create 2018-06-17 17:57
 **/
public class Position extends BasePo {

    private Float x;
    private Float y;
    private Float z;

    public Position() {

    }

    public Position(Float x, Float y, Float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

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
