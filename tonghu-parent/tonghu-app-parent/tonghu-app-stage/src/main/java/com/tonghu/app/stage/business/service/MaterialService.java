package com.tonghu.app.stage.business.service;

import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.Material;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liangyongjian
 * @desc 素材数据 服务层接口
 * @create 2018-06-28 23:37
 **/
public interface MaterialService {

    /**
     * 初始化管理页面
     * @param model
     * @param request
     */
    void getMaterialForInitPage(ModelMap model, HttpServletRequest request);

    /**
     * 获取素材数据信息
     * @param id
     * @param request
     * @param modelMap
     * @return
     */
    Material getExtendMaterialById(Long id, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 新增素材数据
     * @param material
     * @param request
     * @param modelMap
     * @return
     */
    boolean addNewMaterial(Material material, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 更新素材数据
     * @param material
     * @param request
     * @param modelMap
     * @return
     */
    boolean updateMaterial(Material material, HttpServletRequest request, PubModelMap modelMap);

    /**
     * 删除素材数据
     * @param id
     * @param request
     * @param modelMap
     * @return
     */
    boolean removeMaterial(Long id, HttpServletRequest request, PubModelMap modelMap);

}
