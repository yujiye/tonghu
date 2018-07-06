package com.tonghu.pub.common.constant;

import lombok.Getter;

/**
 * @author liangyongjian
 * @desc 资源文件类型
 * @create 2018-07-03 0:58
 **/
public enum EnumMaterialType {

    //图片
    IMAGE(1, "图片"),

    //视频
    VIDEO(2, "视频");

    @Getter
    private Integer type;
    @Getter
    private String typeDesc;

    private EnumMaterialType(Integer type, String typeDesc){
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static EnumMaterialType getByType(Integer type) {
        EnumMaterialType enumMaterialType = null;
        for (EnumMaterialType materialType : EnumMaterialType.values()) {
            if (type.intValue() == materialType.type.intValue()) {
                enumMaterialType = materialType;
                break;
            }
        }
        return enumMaterialType;
    }


}
