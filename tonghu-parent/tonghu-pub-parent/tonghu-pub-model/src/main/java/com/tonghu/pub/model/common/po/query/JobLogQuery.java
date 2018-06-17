package com.tonghu.pub.model.common.po.query;

import com.tonghu.pub.model.po.BasePaginationQuery;

/**
 * @author liangyongjian
 * @desc 同步日志信息
 * @create 2018-01-21 21:41
 **/
public class JobLogQuery extends BasePaginationQuery {

    private Long id; // 主键id
    private Integer status; // 主键id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
