package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc 模拟数据
 * @create 2018-06-28 22:46
 **/
@Getter
@Setter
public class MockData extends BusinessBasePo {

    private String dataName;
    private String dataType;
    private String dataValue;
    private String note;
    private Long createUserId; // 创建者的用户ID
    private Long updateUserId; // 修改者的用户ID

    private String dataTypeName; // 数据类型名称
    private String dataTypeDesc; // 数据类型描述
    private String getInfoApiUrl; // 获取数据值信息的接口URL地址
    private String createUserName; // 创建者的用户名称
    private String updateUserName; // 修改者的用户名称

    public void trim() {
        if (this.dataName != null)
            this.dataName = this.dataName.trim();

        if (this.dataType != null)
            this.dataType = this.dataType.trim();

        if (this.dataValue != null)
            this.dataValue = this.dataValue.trim();

        if (this.note != null)
            this.note = this.note.trim();
    }

}
