package com.tonghu.pub.model.po;

/**
 * @author liangyongjian
 * @desc Business基础PO
 * @create 2017-10-18 23:00
 **/
public class BusinessBasePo extends BasePo {

    private Long id;  //主键id
    private String createTime; // 状态被创建的时间
    private String updateTime; // 修改信息的时间

    public Long getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
