package com.tonghu.app.api.controller;

import com.tonghu.app.api.business.service.MockDataService;
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
 * @desc 模拟数据 控制层
 * @create 2018-06-29 0:25
 **/
@Controller
@RequestMapping("/mock")
public class MockDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);

    @Autowired
    private MockDataService mockDataService;

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public void getAllRecordList(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="id", required=true)Long id)
            throws Exception {
        LOGGER.debug("获取模拟数据信息");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(mockDataService.getMockDataById(id));
    }

}
