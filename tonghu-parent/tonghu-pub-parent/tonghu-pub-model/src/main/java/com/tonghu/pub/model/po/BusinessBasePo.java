package com.tonghu.pub.model.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc Business基础PO
 * @create 2017-10-18 23:00
 **/
@Getter
@Setter
public class BusinessBasePo extends BasePo {

    private Long id;  //主键id
    private String createTime; // 状态被创建的时间
    private String updateTime; // 修改信息的时间

}
