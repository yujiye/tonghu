package com.tonghu.app.api.business.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonghu.app.api.business.service.TestInterfaceService;
import com.tonghu.app.api.config.TongHuConfig;
import com.tonghu.pub.business.dao.TestAreaTreeDao;
import com.tonghu.pub.business.dao.TestInterfaceDao;
import com.tonghu.pub.business.dao.TestPotsDao;
import com.tonghu.pub.model.business.po.IotProPotsInfo;
import com.tonghu.pub.model.business.po.TestAreaTree;
import com.tonghu.pub.model.business.po.TestInterface;
import com.tonghu.pub.model.business.po.TestPots;
import com.tonghu.pub.model.business.po.query.TestAreaTreeQuery;
import com.tonghu.pub.model.business.po.query.TestPotsQuery;
import com.tonghu.pub.proxy.config.OuterUrl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private OuterUrl outerUrl;

    @Autowired
    private TestPotsDao testPotsDao;

    @Autowired
    private TongHuConfig tongHuConfig;

    @Autowired
    private TestAreaTreeDao testAreaTreeDao;

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
    public String getTestPotsInfoByType(String typeName) {
        String errorMsg = "", result ="";

        String areaId = "";
        if (typeName.equals("all")) {
            areaId = tongHuConfig.getTopAreaId();
        } else if (typeName.equals("firstOpen")) {
            areaId = tongHuConfig.getFirstOpenAreaId();
        } else if (typeName.equals("showHall")) {
            areaId = tongHuConfig.getShowHallAreaId();
        } else if (typeName.equals("hotel")) {
            areaId = tongHuConfig.getHotelAreaId();
        }

        List<String> areaIdList = new ArrayList<>();
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

        TestPotsQuery query = new TestPotsQuery();
        query.setAreaIdForQueryList(areaIdList);
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
    public String noMappingTableMsg() {
        Map<String, String> errorMsgMap = new HashMap();
        errorMsgMap.put("errorMsg", "no mapping table");
        return GSON.toJson(errorMsgMap);
    }
}
