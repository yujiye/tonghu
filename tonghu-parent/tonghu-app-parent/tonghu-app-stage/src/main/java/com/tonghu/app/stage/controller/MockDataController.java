package com.tonghu.app.stage.controller;

import com.tonghu.app.stage.business.service.MockDataService;
import com.tonghu.pub.common.utils.WebUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.MockData;
import com.tonghu.pub.model.security.po.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 模拟数据 MVC控制层web入口
 * @create 2018-06-29 0:05
 **/
@Controller
public class MockDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataController.class);

    @Resource
    private MockDataService mockDataService;

    /**
     * 初始化模拟数据项页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/mockDataManage.do")
    public String mockDataManage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("pageHanName", "Mock数据"); //页面名称
        WebUtils.WrapperModle(request, model);
        mockDataService.getMockDataForInitPage(model, request);
        return "manage/" + model.get("pageName");
    }

    /**
     * 保存新的模拟数据项
     * @param mockData
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveNewMockData.do")
    public @ResponseBody Map<String, Object> saveNewMockData(MockData mockData,
           HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("保存新的模拟数据项！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || mockData == null) {
                modelMap.put("status", "exception");
            } else {
                if (mockDataService.addNewMockData(mockData, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "保存新的模拟数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("保存新的模拟数据项的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    /**
     * 获取给定id的模拟数据项信息
     * @param dataId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/showMockData.do")
    public @ResponseBody Map<String, Object> getMockDataInfo(
            @RequestParam(value = "dataId", required = true)Long dataId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("获取给定id的模拟数据项信息，statusId={}", dataId);
        PubModelMap modelMap = new PubModelMap(request);
        try {
            if (dataId == null) {
                LOGGER.error("获取给定id的模拟数据项信息失败，参数id为空");
                modelMap.put("status", "error");
                modelMap.put("data", "该项目模拟数据项不存在，请刷新页面重新请求!");
            } else {
                MockData mockData = mockDataService.getExtendMockDataById(dataId, request, modelMap);
                if (mockData != null) {
                    modelMap.put("status", "success");
                    modelMap.put("data", mockData);
                } else {
                    modelMap.put("total", 0);
                    modelMap.put("status", "failure");
                    modelMap.put("data", "该模拟数据项不存在，请刷新页面重新请求!");
                }
            }
        } catch(Exception e) {
            LOGGER.error("获取给定id的模拟数据项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeMockData.do")
    public @ResponseBody Map<String, Object> changeMockData(
            MockData mockData, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("保存编辑后的模拟数据项！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || mockData == null) {
                modelMap.put("status", "exception");
            } else {
                if (mockDataService.updateMockData(mockData, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "保存编辑后的模拟数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("保存编辑后的模拟数据项的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/removeMockData.do")
    public @ResponseBody Map<String, Object> removeMockData(
            @RequestParam(value = "dataId", required = true)Long dataId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("删除模拟数据项信息！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || dataId == null) {
                modelMap.put("status", "exception");
            } else {
                if (mockDataService.removeMockData(dataId, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "删除模拟数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("删除模拟数据项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

}
