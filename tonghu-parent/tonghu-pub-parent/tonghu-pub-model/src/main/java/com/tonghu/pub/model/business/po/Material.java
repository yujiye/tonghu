package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author liangyongjian
 * @desc 素材资源表
 * @create 2018-07-05 12:41
 **/
@Getter
@Setter
public class Material extends BusinessBasePo {

    private String materialName;
    private Integer materialType;
    private Long attachId;
    private String materialDesc;
    private Long createUserId; // 创建者的用户ID
    private Long updateUserId; // 修改者的用户ID

    private String materialTypeDesc;
    private String attachFileUrl; // 图片或文件附件的HTTP URL
    private String imgMeasure; //图片的规格 宽*高


    private String createUserName; // 创建者的用户名称
    private String updateUserName; // 修改者的用户名称


    public void trim() {
        if (this.materialName != null)
            this.materialName = this.materialName.trim();

        if (this.materialDesc != null)
            this.materialDesc = this.materialDesc.trim();
    }

}
