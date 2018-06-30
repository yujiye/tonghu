package com.tonghu.app.stage.business.service;

import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.MockData;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liangyongjian
 * @desc 模拟数据 服务层接口
 * @create 2018-06-28 23:37
 **/
public interface MockDataService {

    /**
     * 初始化管理页面
     * @param model
     * @param request
     */
    void getMockDataForInitPage(ModelMap model, HttpServletRequest request);

    /**
     * 获取模拟数据信息
     * @param id
     * @param request
     * @param modelMap
     * @return
     */
    MockData getExtendMockDataById(Long id, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 新增模拟数据
     * @param mockData
     * @param request
     * @param modelMap
     * @return
     */
    boolean addNewMockData(MockData mockData, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 更新模拟数据
     * @param mockData
     * @param request
     * @param modelMap
     * @return
     */
    boolean updateMockData(MockData mockData, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 删除模拟数据
     * @param id
     * @param request
     * @param modelMap
     * @return
     */
    boolean removeMockData(Long id, HttpServletRequest request, PubModelMap modelMap);

}
