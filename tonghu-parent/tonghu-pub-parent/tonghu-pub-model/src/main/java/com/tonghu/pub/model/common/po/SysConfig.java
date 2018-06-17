package com.tonghu.pub.model.common.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import org.apache.commons.lang.StringUtils;

/**
 * @author liangyongjian
 * @desc 系统全局配置信息
 * @create 2017-11-19 22:49
 **/
public class SysConfig extends BusinessBasePo {

    private String configItem; // 配置项
    private String configName; // 配置项名称
    private String configValue; // 配置值
    private String valueType; // 配置值的类型
    private String configNote;  // 配置项简介
    private Long createUserId; // 记录创建者
    private Long updateUserId; // 记录修改者

    // 扩展属性，页面使用
    private String createUserName; // 创建者的用户名称
    private String updateUserName; // 修改者的用户名称
    private String valueTypeStr; // 配置值的类型 汉字名称

    public String getConfigItem() {
        return configItem;
    }

    public String getConfigName() {
        return configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public String getValueType() {
        return valueType;
    }

    public String getConfigNote() {
        return configNote;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public String getValueTypeStr() {
        return valueTypeStr;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
        if (StringUtils.isNotEmpty(valueType)) {
            if (valueType.equals("integer")) {
                this.valueTypeStr = "整数";
            } else if (valueType.equals("file")) {
                this.valueTypeStr = "文件";
            }
        }
    }

    public void setConfigNote(String configNote) {
        this.configNote = configNote;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public void setValueTypeStr(String valueTypeStr) {
        this.valueTypeStr = valueTypeStr;
    }
}
