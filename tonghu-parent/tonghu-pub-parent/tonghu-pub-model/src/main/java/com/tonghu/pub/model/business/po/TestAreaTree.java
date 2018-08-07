package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 地块信息实体类
 * @create 2018-08-07 17:50
 **/
@Getter
@Setter
public class TestAreaTree extends BasePo {

    private String id;
    private String parentId;
    private String fullParentId;
    private String owner;
    private String name;
    private String type;
    private String nickName;

}
