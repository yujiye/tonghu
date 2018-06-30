package com.tonghu.pub.model.business.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 模拟数据查询类
 * @create 2018-06-28 22:54
 **/
@Getter
@Setter
public class MockDataQuery extends BasePaginationQuery {

    private Long id; // 主键id
    private String dataName; // 数据项名称
    private String dataType; // 数据类型

}
