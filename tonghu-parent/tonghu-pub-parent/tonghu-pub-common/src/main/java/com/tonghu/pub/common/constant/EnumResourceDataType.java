package com.tonghu.pub.common.constant;

import lombok.Getter;

/**
 * @author liangyongjian
 * @desc 资源数据类型 枚举类型
 * @create 2018-06-29 16:21
 **/
public enum EnumResourceDataType {

    // 直接返回这个字符串，不包在结构体中
    JSON_STR("JSON_STR", new String[]{"JSON字符串", "访问接口可直接获取此JSON字符串"}),

    // 包在一个结构体中，以JSON字符串返回
    SIMPLE_STR("SIMPLE_STR", new String[]{"普通字符串", "将对字符串进行封装，访问接口可获取包装后的JSON字符串"}),

    // txt文件，作为附件上传，接口为直接下载文件的方式
    TXT_FILE("TXT_FILE", new String[]{"文本文件", "访问接口即可直接下载文件"}),

    // 图片，上传后需要发布一个图片URL，可供浏览器访问
    IMAGE_FILE("IMAGE_FILE", new String[]{"图片文件", "系统提供访问图片的URL地址"}),

    // 视频文件，上传后需要发布一个视频文件URL，可供浏览器访问
    VIDEO_FILE("VIDEO_FILE", new String[]{"视频文件", "系统提供访问视频文件的URL地址"});

    @Getter
    private String dataTypeStr; // 类型标识

    @Getter
    private String[] dataTypeDesc; // 类型描述

    private EnumResourceDataType(String dataTypeStr, String[] dataTypeDesc) {
        this.dataTypeStr = dataTypeStr;
        this.dataTypeDesc = dataTypeDesc;
    }

    /**
     * 根据 dataTypeStr 获取枚举类型
     * @param dataTypeStr
     * @return
     */
    public static EnumResourceDataType fromDataTypeStr(String dataTypeStr) {
        EnumResourceDataType enumMockDataType = null;
        for (EnumResourceDataType dataType : EnumResourceDataType.values()) {
            if (dataTypeStr.equals(dataType.dataTypeStr)) {
                enumMockDataType = dataType;
                break;
            }
        }
        return enumMockDataType;
    }

}
