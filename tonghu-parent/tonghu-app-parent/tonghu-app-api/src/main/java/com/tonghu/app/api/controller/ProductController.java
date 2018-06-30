package com.tonghu.app.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tonghu.app.api.business.service.ProductService;
import com.tonghu.pub.common.utils.GsonWrapper;
import com.tonghu.pub.common.web.ApiResponse;
import com.tonghu.pub.model.coordinate.Position;
import com.tonghu.pub.proxy.base.BaseProxyService;
import com.tonghu.pub.proxy.config.OuterUrl;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangyongjian
 * @desc 厂商的产品信息控制层
 * @create 2018-06-20 0:55
 **/
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 获取所有的产品列表信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllProducts.do", method=RequestMethod.GET)
    public void getAllProducts(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LOGGER.debug("获取产品列表信息");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(productService.getAllProductsInfo());
    }

    /**
     * 获取单个的产品信息
     * @param request
     * @param response
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getProductInfo.do", method=RequestMethod.GET)
    public void getProductInfo(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="productId", required=true)String productId)
            throws Exception {
        LOGGER.debug("获取给定产品的详细信息 productId={}", productId);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(productService.getProductInfoByProductId(productId));
    }

    /**
     * 根据给定的产品id获取产品下的设备列表信息
     * @param request
     * @param response
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getDevicesList.do", method=RequestMethod.GET)
    public @ResponseBody String getDevicesList(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="productId", required=true)String productId)
            throws Exception {
        LOGGER.debug("根据给定的产品id获取产品下的设备列表信息 productId={}", productId);
        return productService.getDevicesListByProductId(productId);
    }

    /**
     * 获取设备信息
     * @param request
     * @param response
     * @param productId
     * @param deviceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getDeviceInfo.do", method=RequestMethod.GET)
    public void getDeviceInfo(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="productId", required=true)String productId,
            @RequestParam(value="deviceId", required=true)String deviceId)
            throws Exception {
        LOGGER.debug("根据给定的产品id 设备id 获取设备信息 productId={} deviceId={}",
                productId, deviceId);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(productService.getDeviceInfoByProductIdAndDeviceId(productId, deviceId));
    }

}
