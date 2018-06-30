package com.tonghu.app.api.business.service;

import org.springframework.ui.ModelMap;

/**
 * @author liangyongjian
 * @desc 产品信息服务器类 接口
 * @create 2018-06-20 17:48
 **/
public interface ProductService {

    /**
     * 获取所有产品信息
     * @return
     */
    String getAllProductsInfo();

    /**
     * 获取单个产品信息
     * @param productId
     * @return
     */
    String getProductInfoByProductId(String productId);

    /**
     * 获取产品下的设备信息列表
     * @param productId
     * @return
     */
    String getDevicesListByProductId(String productId);

    /**
     * 获取设备信息
     * @param productId
     * @param deviceId
     * @return
     */
    String getDeviceInfoByProductIdAndDeviceId(String productId, String deviceId);

}
