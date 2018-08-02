package com.tonghu.app.api.controller;

import com.tonghu.app.api.business.service.ProductService;
import com.tonghu.app.api.business.service.TestInterfaceService;
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
            response.getWriter().print(testInterfaceService.getTestPotsInfoByQuery(areaId, modelId));
        } else {
            response.getWriter().print(testInterfaceService.noMappingTableMsg());
        }

    }
}
