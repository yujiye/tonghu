package com.tonghu.pub.model.business.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 素材数据查询类
 * @create 2018-06-28 22:54
 **/
@Getter
@Setter
public class MaterialQuery extends BasePaginationQuery {

    private Long id; // 主键id
    private String materialName; // 资源名称
    private Integer materialType; // 资源类型

}
