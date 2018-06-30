package com.tonghu.app.api.business.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tonghu.app.api.business.service.ProductService;
import com.tonghu.app.api.controller.ProductController;
import com.tonghu.pub.business.dao.IotAccessTokenDao;
import com.tonghu.pub.model.business.po.IotAccessToken;
import com.tonghu.pub.proxy.base.BaseProxyService;
import com.tonghu.pub.proxy.config.OuterUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 产品信息服务类 实现类
 * @create 2018-06-20 17:48
 **/
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting()
            .disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    @Autowired
    private OuterUrl outerUrl;

    @Autowired
    private BaseProxyService baseProxyService;

    @Autowired
    private IotAccessTokenService iotAccessTokenService;

    @Override
    public String getAllProductsInfo() {
        String errorMsg = "", result ="";
        String accessToken = iotAccessTokenService.getIotAccessTokenStr();
        if (StringUtils.isNotEmpty(accessToken)) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HTTP.CONTENT_TYPE, "application/json");
            headerMap.put("Access-Token", accessToken);
            try {
                result = baseProxyService.fetchContentFromUrl(
                        outerUrl.getGetProductListUrl(), null, headerMap);
                if (StringUtils.isEmpty(result))
                    errorMsg = "iot return null";

            } catch(Exception e) {
                LOGGER.error("获取产品列表信息 调用IOT接口出现异常 e=[{}]", e);
                errorMsg = "invoke iot has exception";
            }
        } else {
            LOGGER.error("getAllProductsInfo accessToken 信息为空");
            errorMsg = "accessToken is null";
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            Map<String, String> errorMsgMap = new HashMap();
            errorMsgMap.put("errorMsg", errorMsg);
            result = GSON.toJson(errorMsgMap);
        }
        return result;
    }

    @Override
    public String getProductInfoByProductId(String productId) {
        String errorMsg = "", result ="";
        String accessToken = iotAccessTokenService.getIotAccessTokenStr();
        if (StringUtils.isNotEmpty(accessToken)) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HTTP.CONTENT_TYPE, "application/json");
            headerMap.put("Access-Token", accessToken);

            try {
                String url = outerUrl.getGetProductInfoUrl().replaceAll(
                        "\\{product_id\\}", productId);
                result = baseProxyService.fetchContentFromUrl(
                        url, null, headerMap);

                if (StringUtils.isEmpty(result))
                    errorMsg = "iot return null";

            } catch(Exception e) {
                LOGGER.error("获取产品详细信息 productId=[{}] 调用IOT接口出现异常 e=[{}]",
                        productId, e);
                errorMsg = "invoke iot has exception";
            }
        } else {
            LOGGER.error("getProductInfoByProductId accessToken 信息为空");
            errorMsg = "accessToken is null";
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            Map<String, String> errorMsgMap = new HashMap();
            errorMsgMap.put("errorMsg", errorMsg);
            result = GSON.toJson(errorMsgMap);
        }
        return result;
    }

    @Override
    public String getDevicesListByProductId(String productId) {
        String errorMsg = "", result ="";
        String accessToken = iotAccessTokenService.getIotAccessTokenStr();
        if (StringUtils.isNotEmpty(accessToken)) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HTTP.CONTENT_TYPE, "application/json");
            headerMap.put("Access-Token", accessToken);

            try {
                String url = outerUrl.getGetDeviceListUrl().replaceAll(
                        "\\{product_id\\}", productId);
                result = baseProxyService.fetchContentFromUrl(
                        url, null, headerMap);

                if (StringUtils.isEmpty(result))
                    errorMsg = "iot return null";

            } catch(Exception e) {
                LOGGER.error("获取指定产品下的设备列表信息 productId=[{}] 调用IOT接口出现异常 e=[{}]",
                        productId, e);
                errorMsg = "invoke iot has exception";
            }
        } else {
            LOGGER.error("getDevicesListByProductId accessToken 信息为空");
            errorMsg = "accessToken is null";
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            Map<String, String> errorMsgMap = new HashMap();
            errorMsgMap.put("errorMsg", errorMsg);
            result = GSON.toJson(errorMsgMap);
        }
        return result;
    }

    @Override
    public String getDeviceInfoByProductIdAndDeviceId(String productId, String deviceId) {
        String errorMsg = "", result ="";
        String accessToken = iotAccessTokenService.getIotAccessTokenStr();
        if (StringUtils.isNotEmpty(accessToken)) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HTTP.CONTENT_TYPE, "application/json");
            headerMap.put("Access-Token", accessToken);

            try {
                String url = outerUrl.getGetDeviceInfoUrl().replaceAll(
                        "\\{product_id\\}", productId);
                url = url.replaceAll("\\{device_id\\}", deviceId);
                result = baseProxyService.fetchContentFromUrl(
                        url, null, headerMap);

                if (StringUtils.isEmpty(result))
                    errorMsg = "iot return null";

            } catch(Exception e) {
                LOGGER.error("获取设备信息 productId=[{}] deviceId=[{}] 调用IOT接口出现异常 e=[{}]",
                        productId, deviceId, e);
                errorMsg = "invoke iot has exception";
            }
        } else {
            LOGGER.error("getDeviceInfoByProductIdAndDeviceId accessToken 信息为空");
            errorMsg = "accessToken is null";
        }

        if (StringUtils.isNotEmpty(errorMsg)) {
            Map<String, String> errorMsgMap = new HashMap();
            errorMsgMap.put("errorMsg", errorMsg);
            result = GSON.toJson(errorMsgMap);
        }
        return result;
    }

}
