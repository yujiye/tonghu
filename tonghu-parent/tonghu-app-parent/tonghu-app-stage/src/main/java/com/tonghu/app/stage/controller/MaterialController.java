package com.tonghu.app.stage.controller;

import com.tonghu.app.stage.business.service.MaterialService;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.utils.WebUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.Material;
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
 * @desc 资源数据 MVC控制层web入口
 * @create 2018-06-29 0:05
 **/
@Controller
public class MaterialController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private SystemConstant systemConstant;

    @Resource
    private MaterialService materialService;

    /**
     * 初始化资源数据项页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/materialManage.do")
    public String materialManage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("pageHanName", "资源数据"); //页面名称
        WebUtils.WrapperModle(request, model);
        materialService.getMaterialForInitPage(model, request);
        model.addAttribute("imageSuffix", systemConstant.getImageFileSuffix());
        model.addAttribute("maxImageSize", systemConstant.getMaxUploadImageSize());

        model.addAttribute("videoSuffix", systemConstant.getVideoFileSuffix());
        model.addAttribute("maxVideoSize", systemConstant.getVideoFileMaxSize());

        model.addAttribute("hostName", systemConstant.getTonghuStageHostName());
        return "manage/" + model.get("pageName");
    }

    /**
     * 保存新的资源数据项
     * @param material
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveNewMaterial.do")
    public @ResponseBody Map<String, Object> saveNewMaterial(Material material,
           HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("保存新的资源数据项！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || material == null) {
                modelMap.put("status", "exception");
            } else {
                if (materialService.addNewMaterial(material, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "保存新的资源数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("保存新的资源数据项的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    /**
     * 获取给定id的资源数据项信息
     * @param dataId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/showMaterial.do")
    public @ResponseBody Map<String, Object> getMaterialInfo(
            @RequestParam(value = "dataId", required = true)Long dataId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("获取给定id的资源数据项信息，statusId={}", dataId);
        PubModelMap modelMap = new PubModelMap(request);
        try {
            if (dataId == null) {
                LOGGER.error("获取给定id的资源数据项信息失败，参数id为空");
                modelMap.put("status", "error");
                modelMap.put("data", "该项目资源数据项不存在，请刷新页面重新请求!");
            } else {
                Material material = materialService.getExtendMaterialById(dataId, request, modelMap);
                if (material != null) {
                    modelMap.put("status", "success");
                    modelMap.put("data", material);
                } else {
                    modelMap.put("total", 0);
                    modelMap.put("status", "failure");
                    modelMap.put("data", "该资源数据项不存在，请刷新页面重新请求!");
                }
            }
        } catch(Exception e) {
            LOGGER.error("获取给定id的资源数据项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeMaterial.do")
    public @ResponseBody Map<String, Object> changeMaterial(
            Material material, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("保存编辑后的资源数据项！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || material == null) {
                modelMap.put("status", "exception");
            } else {
                if (materialService.updateMaterial(material, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "保存编辑后的资源数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("保存编辑后的资源数据项的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/removeMaterial.do")
    public @ResponseBody Map<String, Object> removeMaterial(
            @RequestParam(value = "dataId", required = true)Long dataId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("删除资源数据项信息！");
        PubModelMap modelMap = new PubModelMap(request);
        try {
            Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
            if (user == null || dataId == null) {
                modelMap.put("status", "exception");
            } else {
                if (materialService.removeMaterial(dataId, request, modelMap)) {
                    modelMap.put("status", "success");
                    modelMap.put("data", "删除资源数据项成功！");
                }
            }
        } catch(Exception e) {
            LOGGER.error("删除资源数据项信息的操作出现异常：{}", e);
            modelMap.put("status", "exception");
        }
        return modelMap.getModelMap();
    }

}
