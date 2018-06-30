package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 坐标位置
 * @create 2018-06-17 18:40
 **/
@Getter
@Setter
public class PositionTest extends BusinessBasePo {

    private Float x;
    private Float y;
    private Float z;

}
