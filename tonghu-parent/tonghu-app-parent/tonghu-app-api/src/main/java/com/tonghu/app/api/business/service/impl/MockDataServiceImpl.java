package com.tonghu.app.api.business.service.impl;

import com.tonghu.app.api.business.service.MockDataService;
import com.tonghu.pub.business.dao.MockDataDao;
import com.tonghu.pub.common.constant.EnumResourceDataType;
import com.tonghu.pub.common.web.ApiResponse;
import com.tonghu.pub.model.business.po.MockData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liangyongjian
 * @desc 模拟数据 服务层 实现类
 * @create 2018-06-29 0:27
 **/
@Service("mockDataService")
public class MockDataServiceImpl extends BaseService implements MockDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataServiceImpl.class);

    @Resource
    private MockDataDao mockDataDao;

    @Override
    public String getMockDataById(Long id) {
        MockData mockData = mockDataDao.getMockDataById(id);
        if (mockData == null) {
            return getMessageJsonSrt("no mock data info");
        } else if (StringUtils.isEmpty(mockData.getDataValue())) {
            return getMessageJsonSrt("mock data value is null");
        }
        if (mockData.getDataType().equals(EnumResourceDataType.JSON_STR.getDataTypeStr())) {
            return mockData.getDataValue();
        } else if (mockData.getDataType().equals(EnumResourceDataType.SIMPLE_STR.getDataTypeStr())) {
            return GSON.toJson(new ApiResponse<Object, Object,Object>(mockData.getDataValue(),"success",0));
        } else if (mockData.getDataType().equals(EnumResourceDataType.IMAGE_FILE.getDataTypeStr())) {
            // 这里只是一个id，需要转为URL
            return GSON.toJson(new ApiResponse<Object, Object,Object>(mockData.getDataValue(),"success",0));
        } else if (mockData.getDataType().equals(EnumResourceDataType.TXT_FILE.getDataTypeStr())) {
            // 这里只是一个 id，需要转为 URL
            return GSON.toJson(new ApiResponse<Object, Object,Object>(mockData.getDataValue(),"success",0));
        }
        return mockData.getDataValue();
    }

}
