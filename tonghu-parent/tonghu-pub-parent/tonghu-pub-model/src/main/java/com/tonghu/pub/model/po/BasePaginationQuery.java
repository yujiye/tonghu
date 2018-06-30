package com.tonghu.pub.model.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @desc 查询时所用分页信息基类
 * @author liangyongjian
 * @create 2017-09-24 22:25
 **/
public class BasePaginationQuery extends BasePo {

    private Integer page = 1; // 当前页码 默认为1

    private Integer pageSize = 15; // 每页显示记录数 默认为15

    private Integer startNum; // 查询的起始记录序号

    @Getter
    @Setter
    private String sortBy; // 排序SQL

    @Getter
    @Setter
    private String sortType = "2"; // 1升序 2降序

    public Integer getPage() {
        if (page <= 0) {
            page = 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartNum() {
        startNum = 0;
        if (pageSize != null && page != null) {
            if (page <= 0) {
                page = 1;
            }
            startNum = (page - 1) * pageSize;
        }
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

}
