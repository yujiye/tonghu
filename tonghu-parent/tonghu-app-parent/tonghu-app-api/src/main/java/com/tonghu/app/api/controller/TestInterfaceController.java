package com.tonghu.app.api.controller;

import com.tonghu.app.api.business.service.ProductService;
import com.tonghu.app.api.business.service.TestInterfaceService;
import com.tonghu.app.api.websocket.TongHuWebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangyongjian
 * @desc 接口测试 Controller
 * @create 2018-06-27 20:50
 **/
@Controller
@RequestMapping("/test")
public class TestInterfaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private TestInterfaceService testInterfaceService;

    @RequestMapping(value = "/{tableName}", method = RequestMethod.GET)
    public void getAllRecordList(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String tableName,
            @RequestParam(value = "areaId", required = false)String areaId,
            @RequestParam(value = "modelId", required = false)String modelId)
            throws Exception {
        LOGGER.debug("获取接口测试表信息");
        response.setContentType("application/json; charset=UTF-8");
        if (tableName.equals("test_interface")) {
            response.getWriter().print(testInterfaceService.getAllTestInterfaceRecord());
        } else if (tableName.equals("test_pots")) {
            response.getWriter().print(testInterfaceService.getTestPotsInfo(null, areaId, modelId));
        } else {
            response.getWriter().print(testInterfaceService.noMappingTableMsg());
        }
    }

    /**
     * 提供三种类型的数据
     * firstOpen 首开
     * showHall 展厅
     * hotel 酒店
     * all 首开+展厅+酒店
     * @param request
     * @param response
     * @param typeName
     * @throws Exception
     */
    @RequestMapping(value = "/test_pots/{typeName}", method = RequestMethod.GET)
    public void getRecordListByType(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable String typeName)
            throws Exception {
        LOGGER.debug("获取接口测试表信息");
        response.setContentType("application/json; charset=UTF-8");

        if (StringUtils.isNotEmpty(typeName) &&
                (typeName.equals("firstOpen")
                || typeName.equals("showHall")
                || typeName.equals("hotel")
                || typeName.equals("all"))) {
            TongHuWebSocketServer.broadCastMessage(typeName);
            response.getWriter().print(testInterfaceService.getTestPotsInfo(typeName, null, null));
        } else {
            response.getWriter().print(testInterfaceService.noMappingTableMsg());
        }
    }

    /**
     * 获取模块信息的根结点
     */
    @RequestMapping(value = "/getRootModelInfo", method = RequestMethod.GET)
    public void getRootModelInfo(
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.debug("获取模块信息的根结点");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(testInterfaceService.getRootModelInfo());
    }

    /**
     * 根据模块id获取其子节点
     * @param request
     * @param response
     * @param modelId
     * @throws Exception
     */
    @RequestMapping(value = "/getSubModelInfo", method = RequestMethod.GET)
    public void getSubModelInfo(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "modelId", required = true)String modelId)
            throws Exception {
        LOGGER.debug("根据模块id获取模块信息的子结点");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(testInterfaceService.getSubModelInfo(modelId));
    }

    @RequestMapping(value = "/getDeviceInfo", method = RequestMethod.GET)
    public void getDeviceInfo(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "areaId", required = false)String areaId,
            @RequestParam(value = "modelId", required = false)String modelId)
            throws Exception {
        LOGGER.debug("根据模块id获取设备");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(testInterfaceService.getDeviceInfo(areaId, modelId));
    }

    /**
     * 获取模块信息的根结点与子节点
     */
    @RequestMapping(value = "/getModelTreeInfo", method = RequestMethod.GET)
    public void getModelTreeInfo(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "areaId", required = true)String areaId) throws Exception {
        LOGGER.debug("获取模块信息的根结点与子节点");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(testInterfaceService.getModelTreeInfo(areaId));
    }

}
