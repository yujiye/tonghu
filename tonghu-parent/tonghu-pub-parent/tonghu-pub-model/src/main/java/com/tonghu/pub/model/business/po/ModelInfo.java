package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BasePo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 功能模块信息
 * @create 2018-08-11 20:17
 **/
@Getter
@Setter
public class ModelInfo extends BasePo {

    private String name;
    private String id;
    private String icon;
    private List<FireOSC> fireOSC;
    private List<ModelInfo> children;

    @Getter
    @Setter
    public static class FireOSC {
        public FireOSC() {}
        public FireOSC(String allValue) {
            this.ip = "";
            this.port = null;
            this.addr = "";
            this.msg = "";
        }
        private String ip;
        private Integer port;
        private String addr;
        private String msg;
    }

}
