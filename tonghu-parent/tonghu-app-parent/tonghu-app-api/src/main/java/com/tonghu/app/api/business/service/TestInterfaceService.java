package com.tonghu.app.api.business.service;

/**
 * @author liangyongjian
 * @desc 接口测试 服务类接口
 * @create 2018-06-27 21:14
 **/
public interface TestInterfaceService {

    /**
     * 获取所有接口测试表信息
     * @return
     */
    String getAllTestInterfaceRecord();

    /**
     * 获取点位测试表数据信息
     * @param areaId
     * @return
     */
    String getTestPotsInfoByQuery(String areaId, String modelId);

    String noMappingTableMsg();

}
