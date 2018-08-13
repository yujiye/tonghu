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

    /**
     * 根据类型获取点位信息
     * @param typeName
     * @return
     */
    String getTestPotsInfo(String typeName, String areaId, String modelId);

    /**
     * 获取模块的根结点信息
     * @return
     */
    String getRootModelInfo();

    String getSubModelInfo(String modelId);

    String getDeviceInfo(String areaId, String modelId);

    String getModelTreeInfo(String areaId);

    String noMappingTableMsg();

}
