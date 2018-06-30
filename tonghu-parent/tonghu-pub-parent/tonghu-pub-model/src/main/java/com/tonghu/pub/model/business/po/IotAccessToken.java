package com.tonghu.pub.model.business.po;

import com.tonghu.pub.model.po.BusinessBasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangyongjian
 * @desc IOT接口调用凭证
 * @create 2018-06-20 21:56
 **/
@Getter
@Setter
public class IotAccessToken extends BusinessBasePo {

    private String keyId;
    private String keySecret;
    private String accessToken;

}
