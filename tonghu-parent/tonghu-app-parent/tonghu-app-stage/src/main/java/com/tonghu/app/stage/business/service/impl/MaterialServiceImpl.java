package com.tonghu.app.stage.business.service.impl;

import com.tonghu.app.stage.business.service.MaterialService;
import com.tonghu.app.stage.common.service.AttachmentService;
import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.config.WebConfig;
import com.tonghu.app.stage.security.service.impl.SysLogContext;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.business.dao.MaterialDao;
import com.tonghu.pub.common.constant.*;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.Material;
import com.tonghu.pub.model.common.po.Attachment;
import com.tonghu.pub.model.po.CheckErrorDto;
import com.tonghu.pub.model.security.po.Users;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 素材数据 服务层实现类
 * @create 2018-06-28 23:51
 **/
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Resource
    private WebConfig webConfig;

    @Resource
    private InputConfig inputConfig;

    @Resource
    private MaterialDao materialDao;

    @Resource
    private SysLogContext sysLogContext;

    @Resource
    private SystemConstant systemConstant;

    @Resource
    private AttachmentService attachmentService;

    @Override
    public void getMaterialForInitPage(ModelMap model, HttpServletRequest request) {
        List<Material> materialList = materialDao.getAllMaterial();
        if (!CollectionUtils.isEmpty(materialList)) {
            Map<Long, List<Material>> allMaterialMapWithKeyId = new HashMap<>();
            for (Material material : materialList) {
                if (!StringUtils.isEmpty(material.getMaterialType())) {
                    EnumMaterialType enumMaterialType =
                            EnumMaterialType.getByType(material.getMaterialType());
                    if (enumMaterialType != null) {
                        material.setMaterialTypeDesc(enumMaterialType.getTypeDesc());
                    }
                }

                if (!allMaterialMapWithKeyId.containsKey(material.getAttachId())) {
                    allMaterialMapWithKeyId.put(material.getAttachId(),
                            new ArrayList<Material>());
                }
                allMaterialMapWithKeyId.get(material.getAttachId()).add(material);
            }



            if (MapUtils.isNotEmpty(allMaterialMapWithKeyId)) {
                List<Attachment> attachmentList = attachmentService.getAttachmentListByIdList(
                        new ArrayList<Long>(allMaterialMapWithKeyId.keySet()));
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    for (Attachment at : attachmentList) {
                        if (allMaterialMapWithKeyId.containsKey(at.getId())) {
                            // at.getFilePath() 的前面有斜线，不能要此斜线
                            for (Material material : allMaterialMapWithKeyId.get(at.getId())) {
                                if (!StringUtils.isEmpty(webConfig.getFileServerName())) {
                                    material.setAttachFileUrl("/" + webConfig.getFileServerName() + "/"
                                            + at.getFilePath().substring(
                                            1, at.getFilePath().length()) + at.getFileName());
                                } else {
                                    material.setAttachFileUrl(at.getFilePath().substring(
                                            1, at.getFilePath().length()) + at.getFileName());
                                }
                                material.setImgMeasure(at.getImgMeasure());

                                material.setAttachFileUrl(systemConstant.getTonghuStageHostName()
                                        + material.getAttachFileUrl());
                            }
                        }
                    }
                }
            }

        }
        model.addAttribute("materialList", materialList);
    }

    @Override
    public Material getExtendMaterialById(Long id, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id获取素材资源扩展信息");
        Material material = materialDao.getMaterialById(id);
        if (material == null || material.getId() == null) {
            LOGGER.debug("根据id获取素材资源扩展信息，没有查取到素材资源信息：id {}", id);
            return null;
        }

        if (!StringUtils.isEmpty(material.getMaterialType())) {
            EnumMaterialType enumMaterialType =
                    EnumMaterialType.getByType(material.getMaterialType());
            if (enumMaterialType != null) {
                material.setMaterialTypeDesc(enumMaterialType.getTypeDesc());
            }
        }
        // 获取附件信息
        Attachment at = attachmentService.getAttachmentById(material.getAttachId());
        if (at != null) {
            if (!StringUtils.isEmpty(webConfig.getFileServerName())) {
                material.setAttachFileUrl("/" + webConfig.getFileServerName() + "/"
                        + at.getFilePath().substring(
                        1, at.getFilePath().length()) + at.getFileName());
            } else {
                material.setAttachFileUrl(at.getFilePath().substring(
                        1, at.getFilePath().length()) + at.getFileName());
            }
            material.setImgMeasure(at.getImgMeasure());
            material.setAttachFileUrl(systemConstant.getTonghuStageHostName()
                    + material.getAttachFileUrl());
        }

        //获取创建和修改项目状态信息的用户名称
        if (material.getCreateUserId() != null || material.getUpdateUserId() != null) {
            List<Long> userIdList = new ArrayList<Long>();
            if (material.getCreateUserId() != null) {
                userIdList.add(material.getCreateUserId());
            }
            if (material.getUpdateUserId() != null) {
                userIdList.add(material.getUpdateUserId());
            }
            List<Users> usersList = systemConstant.getUserInfoByUserIdList(userIdList);
            if (usersList != null && usersList.size() > 0) {
                for (Users user : usersList) {
                    if (material.getCreateUserId() != null
                            && user.getId().compareTo(material.getCreateUserId()) == 0) {
                        material.setCreateUserName(user.getTrueName());
                    }
                    if (material.getUpdateUserId() != null
                            && user.getId().compareTo(material.getUpdateUserId()) == 0) {
                        material.setUpdateUserName(user.getTrueName());
                    }
                }
            }
        }
        return material;
    }

    @Override
    public boolean addNewMaterial(Material material, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：保存新的素材数据");
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        String sysLogCause = "";
        boolean result = false;
        //过滤掉信息中各个属性值的前后空格
        material.trim();
        if (this.checkMaterialForAddOrUpdate(material, modelMap, true)) {
            //通过校验，开始进行更新
            material.setCreateUserId(user.getId());
            Integer resultNum = materialDao.insertNewMaterial(material);
            if (resultNum.intValue() == 1) {
                result = true;
            } else {
                modelMap.put("status", "failure");
            }
        } else {
            sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
        }

        //记录访问日志
        sysLogContext.saveSysLog(request, EnumOperatorType.ADD.getType(),
                "新增素材资源信息：" + material.getMaterialName(),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    @Override
    public boolean updateMaterial(Material material, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id更新素材资源信息");
        String sysLogCause = "";
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        boolean result = false;
        //过滤掉各个属性值的前后空格
        material.trim();
        if (this.checkMaterialForAddOrUpdate(material, modelMap, false)) {
            //通过校验，开始进行更新
            material.setUpdateUserId(user.getId());
            Integer resultNum = materialDao.updateMaterialById(material);
            if (resultNum.intValue() == 1) {
                result = true;
            } else {
                sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
                modelMap.put("status", "failure");
            }
        } else {
            sysLogCause = Constant.COMMON_ERROR.ERROR_MSG;
        }
        //记录访问日志
        sysLogContext.saveSysLog(request, EnumOperatorType.MODIFY.getType(),
                "更新素材资源信息：" + material.getMaterialName(),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    @Override
    public boolean removeMaterial(Long id, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id删除素材资源信息 物理删除");
        boolean result = false;
        String sysLogCause = "";
        Material material = materialDao.getMaterialById(id);
        if (material == null) {
            modelMap.put("status", "error");
            modelMap.put("data", "不存在该素材资源记录！");
            sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该素材资源记录";
        } else {
            //通过校验，开始进行删除
            Integer resultNum = materialDao.deleteMaterialById(id);
            if (resultNum.intValue() == 1) {
                result = true;
            } else {
                sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
                modelMap.put("status", "failure");
            }
        }
        //记录访问日志
        sysLogContext.saveSysLog(request, EnumOperatorType.DELETE.getType(),
                "删除素材资源记录" + (result ? "：" + material.getMaterialName() : ""),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    /**
     * 校验素材数据信息
     * @param material
     * @param modelMap
     * @return
     */
    private boolean checkMaterialForAddOrUpdate(Material material, PubModelMap modelMap, boolean isAdd) {
        LOGGER.debug("Service层：校验素材数据信息的名称、数据值、描述的长度，校验新素材数据名称是否已存在！");
        List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();

        this.checkMaterialBaseInfo(material, errorInfoList);

        if (errorInfoList.size() > 0) {
            modelMap.put("status", "error");
            modelMap.put("data", errorInfoList);
            return false;
        }

        LOGGER.debug("Service层：校验新素材资源名称是否已存在！");
        Material md = materialDao.getMaterialByName(material.getMaterialName());
        if (md != null) {
            if (isAdd) {
                LOGGER.info("资源名称已经存在");
                errorInfoList.add(new CheckErrorDto("material_name", "资源名称已经存在"));
            } else {
                // 这是在更新用户
                if (md.getId().compareTo(material.getId()) != 0) {
                    LOGGER.info("资源名称已经存在");
                    errorInfoList.add(new CheckErrorDto("change_material_name", "资源名称已经存在"));
                }
            }
        }

        if (errorInfoList.size() > 0) {
            modelMap.put("status", "error");
            modelMap.put("data", errorInfoList);
            return false;
        }
        return true;
    }

    /**
     * 校验素材数据的基本信息
     * @param material
     * @param errorInfoList
     */
    private void checkMaterialBaseInfo(Material material, List<CheckErrorDto> errorInfoList) {
        LOGGER.debug("Service层：校验素材数据的基本信息！");
        Integer length = null;
        String prefix = "";
        if (material.getId() != null)
            prefix = "change_";

        if (StringUtils.isEmpty(material.getMaterialName())) {
            LOGGER.info("请输入资源名称");
            errorInfoList.add(new CheckErrorDto(prefix + "data_name", "请输入资源名称"));
        } else {
            length = new Integer(material.getMaterialName().length());
            if (length.intValue() > inputConfig.getMaterialNameLength()) {
                errorInfoList.add(new CheckErrorDto(prefix + "data_name", "资源名称不能大于"
                        + inputConfig.getMaterialNameLength() + "位"));
            }
        }

        if (material.getAttachId() == null) {
            LOGGER.info("请上传资源");
            errorInfoList.add(new CheckErrorDto(prefix + "attach_id", "请上传资源"));
        }

        if (StringUtils.isEmpty(material.getMaterialType())) {
            LOGGER.info("请选择资源类型");
            errorInfoList.add(new CheckErrorDto(prefix + "material_type", "请选择资源类型"));
        } else {
            EnumMaterialType enumMaterialType = EnumMaterialType.getByType(material.getMaterialType());
            if (enumMaterialType == null) {
                errorInfoList.add(new CheckErrorDto(prefix + "material_type", "资源类型选择有误"));
            }
        }

        if (!StringUtils.isEmpty(material.getMaterialDesc())) {
            length = new Integer(material.getMaterialDesc().length());
            if (length.compareTo(inputConfig.getNoteLength()) > 0) {
                errorInfoList.add(new CheckErrorDto(prefix + "material_desc", "资源描述长度不能大于"
                        + inputConfig.getNoteLength() + "位"));
            }
        }
    }

}
