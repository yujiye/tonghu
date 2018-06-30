package com.tonghu.app.api.business.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonghu.app.api.business.service.TestInterfaceService;
import com.tonghu.pub.business.dao.TestInterfaceDao;
import com.tonghu.pub.model.business.po.TestInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 接口测试 服务类实现类
 * @create 2018-06-27 21:15
 **/
@Service("testInterfaceService")
public class TestInterfaceServiceImpl extends BaseService implements TestInterfaceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestInterfaceServiceImpl.class);

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting()
            .disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    @Autowired
    private TestInterfaceDao testInterfaceDao;

    @Override
    public String getAllTestInterfaceRecord() {
        String errorMsg = "", result ="";
        List<TestInterface> tiList = testInterfaceDao.getAllRecordInfo();
        if (CollectionUtils.isEmpty(tiList)) {
            errorMsg = "no record";
        } else {
            result = GSON.toJson(tiList);
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            result = getMessageJsonSrt(errorMsg);
        }

        return result;
    }

    @Override
    public String noMappingTableMsg() {
        Map<String, String> errorMsgMap = new HashMap();
        errorMsgMap.put("errorMsg", "no mapping table");
        return GSON.toJson(errorMsgMap);
    }
}
