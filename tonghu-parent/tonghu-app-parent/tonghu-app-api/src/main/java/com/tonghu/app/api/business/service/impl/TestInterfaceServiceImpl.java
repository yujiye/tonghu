package com.tonghu.app.api.business.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonghu.app.api.business.service.TestInterfaceService;
import com.tonghu.app.api.config.TongHuApiConfig;
import com.tonghu.pub.business.dao.TestAreaTreeDao;
import com.tonghu.pub.business.dao.TestInterfaceDao;
import com.tonghu.pub.business.dao.TestModelTreeDao;
import com.tonghu.pub.business.dao.TestPotsDao;
import com.tonghu.pub.model.business.po.*;
import com.tonghu.pub.model.business.po.query.TestAreaTreeQuery;
import com.tonghu.pub.model.business.po.query.TestModelTreeQuery;
import com.tonghu.pub.model.business.po.query.TestPotsQuery;
import com.tonghu.pub.proxy.config.OuterUrl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

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
    private OuterUrl outerUrl;

    @Autowired
    private TestPotsDao testPotsDao;

    @Autowired
    private TongHuApiConfig tongHuApiConfig;

    @Autowired
    private TestAreaTreeDao testAreaTreeDao;

    @Autowired
    private TestModelTreeDao testModelTreeDao;

    @Autowired
    private TestInterfaceDao testInterfaceDao;

    @Autowired
    private IotAccessTokenService iotAccessTokenService;

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
    public String getTestPotsInfoByQuery(String areaId, String modelId) {
        String errorMsg = "", result ="";
        TestPotsQuery query = new TestPotsQuery();
        query.setAreaIdForQuery(areaId);
        query.setModelId(modelId);
        List<String> iotProIdList = testPotsDao.getDistinctIotProIdByQuery(query);

        if (CollectionUtils.isEmpty(iotProIdList)) {
            errorMsg = "no record";
        } else {
            query.setPageSize(-1);
            query.setSortBy("iotid");
            query.setSortType("1");

            IotProPotsInfo iotProPotsInfo = new IotProPotsInfo();
            iotProPotsInfo.setAccessToken(iotAccessTokenService.getIotAccessTokenStr());

            iotProPotsInfo.setIntelligenceDeviceList(new ArrayList<IotProPotsInfo.DeviceInfo>());
            for (String iotProId : iotProIdList) {
                if (StringUtils.isNotEmpty(iotProId)) {
                    query.setIotProId(iotProId);
                    List<TestPots> testPotsList = testPotsDao.getTestPotsInfoByQuery(query);
                    if (!CollectionUtils.isEmpty(testPotsList)) {
                        IotProPotsInfo.DeviceInfo deviceInfo = new IotProPotsInfo.DeviceInfo();
                        deviceInfo.setDeviceName(testPotsList.get(0).getProName());
                        deviceInfo.setDeviceType(testPotsList.get(0).getDeviceType());

                        iotProPotsInfo.getIntelligenceDeviceList().add(deviceInfo);

                        deviceInfo.setDeviceList(new ArrayList<IotProPotsInfo.Device>());
                        for (TestPots testPots : testPotsList) {
                            IotProPotsInfo.Device device = new IotProPotsInfo.Device();
                            device.setId(testPots.getIotId());
                            device.setName(testPots.getDes());
                            device.setX(testPots.getX());
                            if (StringUtils.isNotEmpty(testPots.getY())) {
                                device.setY((new BigDecimal(1)).subtract(new BigDecimal(testPots.getY())).toPlainString());
                            } else {
                                device.setY("");
                            }
                            device.setPId(testPots.getIotProId());
                            device.setModelTypeId("0");
                            device.setDeviceFloorCount(testPots.getDeviceFloorCount());
                            if (StringUtils.isNotBlank(testPotsList.get(0).getDeviceType())) {
                                device.setDeviceType((new Integer(testPotsList.get(0).getDeviceType())).intValue());
                            }

                            String url = outerUrl.getGetVirtualDeviceInfoUrl().replaceAll(
                                    "\\{product_id\\}", testPots.getIotProId());
                            url = url.replaceAll("\\{device_id\\}", testPots.getIotId());

                            device.setApiUrl(url);

                            deviceInfo.getDeviceList().add(device);
                        }
                    }
                }
            }
            result = GSON.toJson(iotProPotsInfo);
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            result = getMessageJsonSrt(errorMsg);
        }
        return result;
    }

    @Override
    public String getTestPotsInfo(String typeName, String areaId, String modelId) {
        String errorMsg = "", result ="";

        if (StringUtils.isBlank(typeName) && StringUtils.isBlank(areaId) && StringUtils.isBlank(modelId)) {
            typeName = "all";
        }

        List<String> areaIdList = new ArrayList<>();
        String areaQueryId = null;
        // 根据类型查询
        if (StringUtils.isNotBlank(typeName)) {
            if (typeName.equals("all")) {
                areaQueryId = tongHuApiConfig.getTopAreaId();
            } else if (typeName.equals("firstOpen")) {
                areaQueryId = tongHuApiConfig.getFirstOpenAreaId();
            } else if (typeName.equals("showHall")) {
                areaQueryId = tongHuApiConfig.getShowHallAreaId();
            } else if (typeName.equals("hotel")) {
                areaQueryId = tongHuApiConfig.getHotelAreaId();
            }
        }

        if (StringUtils.isNotBlank(areaQueryId)) {
            areaIdList.add(areaQueryId);
            TestAreaTreeQuery areaTreeQuery = new TestAreaTreeQuery();
            areaTreeQuery.setFullParentId(areaQueryId);
            areaTreeQuery.setPageSize(-1);
            List<TestAreaTree> testAreaTreeList =
                    testAreaTreeDao.getTestAreaTreeInfoByQuery(areaTreeQuery);

            if (!CollectionUtils.isEmpty(testAreaTreeList)) {
                for (TestAreaTree testAreaTree : testAreaTreeList) {
                    if (StringUtils.isNotBlank(testAreaTree.getId())) {
                        areaIdList.add(testAreaTree.getId());
                    }
                }
            }
        }

        // 根据 areaId 参数查询
        if (StringUtils.isNotBlank(areaId)) {
            areaIdList.add(areaId);
            TestAreaTreeQuery areaTreeQuery = new TestAreaTreeQuery();
            areaTreeQuery.setFullParentId(areaId);
            areaTreeQuery.setPageSize(-1);
            List<TestAreaTree> testAreaTreeList =
                    testAreaTreeDao.getTestAreaTreeInfoByQuery(areaTreeQuery);

            if (!CollectionUtils.isEmpty(testAreaTreeList)) {
                for (TestAreaTree testAreaTree : testAreaTreeList) {
                    if (StringUtils.isNotBlank(testAreaTree.getId())) {
                        areaIdList.add(testAreaTree.getId());
                    }
                }
            }
        }

        // 根据 modelId 查询 test_modeltree 表
        List<String> modelIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(modelId)) {
            modelIdList.add(modelId);
            TestModelTreeQuery modelTreeQuery = new TestModelTreeQuery();
            modelTreeQuery.setFullParentId(modelId);
            modelTreeQuery.setPageSize(-1);
            List<TestModelTree> testModelTreeList =
                    testModelTreeDao.getTestModelTreeInfoByQuery(modelTreeQuery);

            if (!CollectionUtils.isEmpty(testModelTreeList)) {
                for (TestModelTree testModelTree : testModelTreeList) {
                    if (StringUtils.isNotBlank(testModelTree.getId())) {
                        modelIdList.add(testModelTree.getId());
                    }
                }
            }
        }


        List<String> iotProIdList = null;

        TestPotsQuery query = new TestPotsQuery();
        // 从 test_pots 表中根据 areaId、smodelId 查询出 iotProId
        if (!CollectionUtils.isEmpty(areaIdList) || !CollectionUtils.isEmpty(modelIdList)) {
            if (!CollectionUtils.isEmpty(areaIdList)){
                query.setAreaIdForQueryList(areaIdList);
            }
            if (!CollectionUtils.isEmpty(modelIdList)) {
                query.setModelIdList(modelIdList);
            }
            iotProIdList = testPotsDao.getDistinctIotProIdByQuery(query);
        }

        if (CollectionUtils.isEmpty(iotProIdList)) {
            errorMsg = "no record";
        } else {
            query.setPageSize(-1);
            query.setSortBy("iotid");
            query.setSortType("1");

            IotProPotsInfo iotProPotsInfo = new IotProPotsInfo();
            iotProPotsInfo.setAccessToken(iotAccessTokenService.getIotAccessTokenStr());

            iotProPotsInfo.setIntelligenceDeviceList(new ArrayList<IotProPotsInfo.DeviceInfo>());
            for (String iotProId : iotProIdList) {
                if (StringUtils.isNotEmpty(iotProId)) {
                    query.setIotProId(iotProId);
                    List<TestPots> testPotsList = testPotsDao.getTestPotsInfoByQuery(query);
                    if (!CollectionUtils.isEmpty(testPotsList)) {
                        IotProPotsInfo.DeviceInfo deviceInfo = new IotProPotsInfo.DeviceInfo();
                        deviceInfo.setDeviceName(testPotsList.get(0).getProName());
                        deviceInfo.setDeviceType(testPotsList.get(0).getDeviceType());

                        iotProPotsInfo.getIntelligenceDeviceList().add(deviceInfo);

                        deviceInfo.setDeviceList(new ArrayList<IotProPotsInfo.Device>());
                        for (TestPots testPots : testPotsList) {
                            IotProPotsInfo.Device device = new IotProPotsInfo.Device();
                            device.setId(testPots.getIotId());
                            device.setName(testPots.getDes());
                            device.setX(testPots.getX());
                            if (StringUtils.isNotEmpty(testPots.getY())) {
                                device.setY((new BigDecimal(1)).subtract(new BigDecimal(testPots.getY())).toPlainString());
                            } else {
                                device.setY("");
                            }
                            device.setPId(testPots.getIotProId());
                            device.setModelTypeId("0");
                            device.setDeviceFloorCount(testPots.getDeviceFloorCount());
                            if (StringUtils.isNotBlank(testPotsList.get(0).getDeviceType())) {
                                device.setDeviceType((new Integer(testPotsList.get(0).getDeviceType())).intValue());
                            }

                            String url = outerUrl.getGetVirtualDeviceInfoUrl().replaceAll(
                                    "\\{product_id\\}", testPots.getIotProId());
                            url = url.replaceAll("\\{device_id\\}", testPots.getIotId());

                            device.setApiUrl(url);

                            deviceInfo.getDeviceList().add(device);
                        }
                    }
                }
            }
            result = GSON.toJson(iotProPotsInfo);
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

    @Override
    public String getRootModelInfo() {
        String result ="";
        TestModelTreeQuery query = new TestModelTreeQuery();
        query.setPageSize(-1);
        query.setParentId(tongHuApiConfig.getRootModelId());
        List<TestModelTree> testModelTreeList =
                testModelTreeDao.getTestModelTreeInfoByQuery(query);
        if (!CollectionUtils.isEmpty(testModelTreeList)) {
            List<ModelInfo> rootModelInfoList = new ArrayList<>();
            for (TestModelTree treeNode : testModelTreeList) {
                ModelInfo rootModelInfo = new ModelInfo();
                rootModelInfoList.add(rootModelInfo);
                rootModelInfo.setName(treeNode.getName());
                rootModelInfo.setId("/test/getSubModelInfo?modelId=" + treeNode.getId());
                rootModelInfo.setIcon(treeNode.getName());
                rootModelInfo.setFireOSC(new ArrayList<ModelInfo.FireOSC>());
                rootModelInfo.setChildren(new ArrayList<ModelInfo>());
            }
            result = GSON.toJson(rootModelInfoList);
        } else {
            result = getMessageJsonSrt("no record");
        }
        return result;
    }

    @Override
    public String getSubModelInfo(String modelId) {
        String result ="", errorMsg = "";
        TestModelTreeQuery query = new TestModelTreeQuery();
        query.setPageSize(-1);
        query.setId(modelId);
        List<TestModelTree> testModelTreeList =
                testModelTreeDao.getTestModelTreeInfoByQuery(query);
        if (CollectionUtils.isEmpty(testModelTreeList)) {
            errorMsg = "no such model: id=" + modelId;
        } else {
            ModelInfo rootModelInfo = new ModelInfo();
            rootModelInfo.setName(testModelTreeList.get(0).getName());
            rootModelInfo.setId("/test/getSubModelInfo?modelId=" + testModelTreeList.get(0).getId());
            rootModelInfo.setIcon(testModelTreeList.get(0).getName());
            rootModelInfo.setFireOSC(new ArrayList<ModelInfo.FireOSC>());

            query.setId(null);
            query.setParentId(modelId);
            List<TestModelTree> subTestModelTreeList =
                    testModelTreeDao.getTestModelTreeInfoByQuery(query);
            if (!CollectionUtils.isEmpty(subTestModelTreeList)) {
                List<ModelInfo> subModelInfoList = new ArrayList<>();
                for (TestModelTree treeNode : subTestModelTreeList) {
                    ModelInfo subModelInfo = new ModelInfo();
                    subModelInfoList.add(subModelInfo);
                    subModelInfo.setName(treeNode.getName());
                    subModelInfo.setId("/test/getDeviceInfo?modelId="+treeNode.getId());
                    subModelInfo.setIcon(treeNode.getName());
                    ModelInfo.FireOSC fireOSC = new ModelInfo.FireOSC("");
                    subModelInfo.setFireOSC(new ArrayList<ModelInfo.FireOSC>());
                    subModelInfo.getFireOSC().add(fireOSC);
                }
                rootModelInfo.setChildren(subModelInfoList);
            }
            result = GSON.toJson(rootModelInfo);
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            result = getMessageJsonSrt(errorMsg);
        }
        return result;
    }

    @Override
    public String getDeviceInfo(String areaId, String modelId) {
        String result = "";

        List<String> areaIdList = new ArrayList<>();
        // 根据 areaId 参数查询
        if (StringUtils.isNotBlank(areaId)) {
            areaIdList.add(areaId);
            TestAreaTreeQuery areaTreeQuery = new TestAreaTreeQuery();
            areaTreeQuery.setFullParentId(areaId);
            areaTreeQuery.setPageSize(-1);
            List<TestAreaTree> testAreaTreeList =
                    testAreaTreeDao.getTestAreaTreeInfoByQuery(areaTreeQuery);

            if (!CollectionUtils.isEmpty(testAreaTreeList)) {
                for (TestAreaTree testAreaTree : testAreaTreeList) {
                    if (StringUtils.isNotBlank(testAreaTree.getId())) {
                        areaIdList.add(testAreaTree.getId());
                    }
                }
            }
        }

        // 根据 modelId 查询 test_modeltree 表
        List<String> modelIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(modelId)) {
            modelIdList.add(modelId);
            TestModelTreeQuery modelTreeQuery = new TestModelTreeQuery();
            modelTreeQuery.setFullParentId(modelId);
            modelTreeQuery.setPageSize(-1);
            List<TestModelTree> testModelTreeList =
                    testModelTreeDao.getTestModelTreeInfoByQuery(modelTreeQuery);

            if (!CollectionUtils.isEmpty(testModelTreeList)) {
                for (TestModelTree testModelTree : testModelTreeList) {
                    if (StringUtils.isNotBlank(testModelTree.getId())) {
                        modelIdList.add(testModelTree.getId());
                    }
                }
            }
        }

        TestPotsQuery testPotsQuery = new TestPotsQuery();
        testPotsQuery.setPageSize(-1);
        if (!CollectionUtils.isEmpty(areaIdList)) {
            testPotsQuery.setAreaIdForQueryList(areaIdList);
        }
        if (!CollectionUtils.isEmpty(modelIdList)) {
            testPotsQuery.setModelIdList(modelIdList);
        }
        List<TestPots> testPotsList = testPotsDao.getTestPotsInfoByQuery(testPotsQuery);
        List<DeviceInfo> deviceInfoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(testPotsList)) {
            for (TestPots testPots : testPotsList) {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfoList.add(deviceInfo);
                deviceInfo.setTitle(testPots.getDes());
                deviceInfo.setId(testPots.getIotId());
                deviceInfo.setPosition(testPots.getAreaId());
            }
        }
        result = GSON.toJson(deviceInfoList);
        return result;
    }

    public String getModelTreeInfo(String areaId) {
        String result = "";
        TestModelTreeQuery query = new TestModelTreeQuery();
        query.setPageSize(-1);
        query.setParentId(tongHuApiConfig.getRootModelId());
        List<TestModelTree> testModelTreeList =
                testModelTreeDao.getTestModelTreeInfoByQuery(query);
        if (!CollectionUtils.isEmpty(testModelTreeList)) {
            List<ModelInfo> rootModelInfoList = new ArrayList<>();
            for (TestModelTree treeNode : testModelTreeList) {
                ModelInfo rootModelInfo = new ModelInfo();
                rootModelInfoList.add(rootModelInfo);
                rootModelInfo.setName(treeNode.getName());
                rootModelInfo.setId("/test/getDeviceInfo?areaId=" + areaId + "&modelId=" + treeNode.getId());
                rootModelInfo.setIcon(treeNode.getName());


                query.setId(null);
                query.setParentId(treeNode.getId());
                List<TestModelTree> subTestModelTreeList =
                        testModelTreeDao.getTestModelTreeInfoByQuery(query);
                if (!CollectionUtils.isEmpty(subTestModelTreeList)) {
                    List<ModelInfo> subModelInfoList = new ArrayList<>();
                    for (TestModelTree subTreeNode : subTestModelTreeList) {
                        ModelInfo subModelInfo = new ModelInfo();
                        subModelInfoList.add(subModelInfo);
                        subModelInfo.setName(subTreeNode.getName());
                        subModelInfo.setId("/test/getDeviceInfo?areaId=" + areaId + "&modelId=" + subTreeNode.getId());
                        subModelInfo.setIcon(subTreeNode.getName());
                        ModelInfo.FireOSC fireOSC = new ModelInfo.FireOSC("");
                        subModelInfo.setFireOSC(new ArrayList<ModelInfo.FireOSC>());
                        subModelInfo.getFireOSC().add(fireOSC);
                    }
                    rootModelInfo.setChildren(subModelInfoList);
                }

            }
            result = GSON.toJson(rootModelInfoList);
        } else {
            result = getMessageJsonSrt("no record");
        }

        return result;
    }

}
