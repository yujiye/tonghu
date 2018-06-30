package com.tonghu.pub.model.coordinate;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 坐标信息
 * @create 2018-06-17 17:57
 **/
@Getter
@Setter
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
}
