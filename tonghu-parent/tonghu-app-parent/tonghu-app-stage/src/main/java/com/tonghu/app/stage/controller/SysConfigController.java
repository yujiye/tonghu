package com.tonghu.app.stage.controller;

import com.tonghu.app.stage.common.service.SysConfigService;
import com.tonghu.pub.common.utils.WebUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.common.po.SysConfig;
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
 * @desc 系统配置项信息 MVC控制层web入口
 * @create 2017-11-19 23:09
 **/
@Controller
public class SysConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigController.class);

    @Resource
    private SysConfigService sysConfigService;


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/sysConfig.do")
    public String sysConfigManage(HttpServletRequest request, ModelMap model) {
        String method = request.getMethod();
        LOGGER.debug("以 {} 方式访问系统配置项管理页面", method);
        model.addAttribute("pageHanName", "系统配置"); //页面名称
        WebUtils.WrapperModle(request, model);
        sysConfigService.getSysConfigInfoForInitPage(model, method, request);
        return "manage/" + model.get("pageName");
    }


    @RequestMapping(method = RequestMethod.POST, value = "/showSysConfig.do")
    public @ResponseBody
    Map<String, Object> getSysConfigInfo(
            @RequestParam(value = "sysConfigId", required = true)Long sysConfigId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("获取给定id的配置项新息，sysConfigId={}", sysConfigId);
        PubModelMap modelMap = new PubModelMap(request);
        try {
            if (sysConfigId == null) {
                LOGGER.error("获取给定id的配置项信息失败，id为空");
                modelMap.put("status", "error");
                modelMap.put("data", "该配置项不存在，请刷新页面重新请求!");
            } else {
                SysConfig sysConfig = sysConfigService.getExtendSysConfigInfoById(sysConfigId);
                if (sysConfig != null) {
                    modelMap.put("total", 1);
                    modelMap.put("status", "success");
                    modelMap.put("data", sysConfig);
                } else {
                    modelMap.put("total", 0);
                    modelMap.put("status", "failure");
                    modelMap.put("data", "该配置项不存在，请刷新页面重新请求!");
                }
            }
        } catch(Exception e) {
            LOGGER.error("获取给定id的配置项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeSysConfig.do")
    public @ResponseBody Map<String, Object> updateSysConfig(SysConfig sysConfig,
                HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("更新配置项信息！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || sysConfig == null) {
                modelMap.put("status", "exception");
            } else {
                if (sysConfigService.updateSysConfig(sysConfig, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "更新配置项信息成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("更新配置项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }


}
