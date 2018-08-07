package com.tonghu.pub.model.business.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 模块信息查询类
 * @create 2018-08-07 18:06
 **/
@Getter
@Setter
public class TestModelTreeQuery extends BasePaginationQuery {

    private String id;
    private String parentId;
    private String fullParentId;

}
