package com.tonghu.app.api.business.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 服务层基础类
 * @create 2018-06-29 0:30
 **/
public class BaseService {

    protected static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting()
            .disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    public static String getMessageJsonSrt(String errorMsg) {
        Map<String, String> errorMsgMap = new HashMap();
        errorMsgMap.put("errorMsg", errorMsg);
        return GSON.toJson(errorMsgMap);
    }

}
