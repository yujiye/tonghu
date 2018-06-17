package com.tonghu.app.stage.common.service.impl;

import com.tonghu.app.stage.common.service.SysConfigService;
import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.config.WebConfig;
import com.tonghu.app.stage.security.service.impl.SysLogContext;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.dao.SysConfigDao;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.constant.EnumIsSuccess;
import com.tonghu.pub.common.constant.EnumOperatorType;
import com.tonghu.pub.common.utils.MyStringUtils;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.common.po.SysConfig;
import com.tonghu.pub.model.po.CheckErrorDto;
import com.tonghu.pub.model.security.po.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liangyongjian
 * @desc 系统配置项信息 服务类实现类
 * @create 2017-11-19 23:20
 **/
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigServiceImpl.class);

    @Resource
    private WebConfig webConfig;

    @Resource
    private InputConfig inputConfig;

    @Resource
    private SysConfigDao sysConfigDao;

    @Resource
    private SysLogContext sysLogContext;

    @Resource
    private SystemConstant systemConstant;


    @Override
    public List<SysConfig> getAllSysConfig() {
        LOGGER.debug("Service层：获取所有的系统配置项信息");
        return sysConfigDao.getAllSysConfig();
    }

    @Override
    public void getSysConfigInfoForInitPage(ModelMap model, String method, HttpServletRequest request) {
        LOGGER.debug("Service层：查询系统配置项信息 用于配置项管理页面");
        model.addAttribute("fileSuffix", webConfig.getFileSuffix());
        model.addAttribute("maxFileSize", webConfig.getMaxFileSize());
        List<SysConfig> sysConfigList = systemConstant.getSysConfigList();
        model.addAttribute("sysConfigList", sysConfigList);
    }


    @Override
    public SysConfig getSysConfigByConfigItem(String configItem) {
        LOGGER.debug("Service层：根据配置项名称获取配置项信息");
        Map<String, SysConfig> sysConfigMapWithKeyName = systemConstant.getSysConfigMapWithKeyItem();
        if (!CollectionUtils.isEmpty(sysConfigMapWithKeyName)) {
            if (sysConfigMapWithKeyName.containsKey(configItem)) {
                return sysConfigMapWithKeyName.get(configItem);
            }
        }
        LOGGER.error("系统中不存在名称为 {} 的配置项", configItem);
        return null;
    }

    @Override
    public SysConfig getExtendSysConfigInfoById(Long sysConfigId) {
        LOGGER.debug("Service层：根据配置项名称获取配置项信息");
        Map<Long, SysConfig> sysConfigMapWithKeyId = systemConstant.getSysConfigMapWithKeyId();
        if (!CollectionUtils.isEmpty(sysConfigMapWithKeyId)) {
            if (sysConfigMapWithKeyId.containsKey(sysConfigId)) {

                SysConfig sysConfig = sysConfigMapWithKeyId.get(sysConfigId);


                //获取创建和修改项目展示密码信息的用户名称
                if (sysConfig.getCreateUserId() != null || sysConfig.getUpdateUserId() != null) {
                    List<Long> userIdList = new ArrayList<Long>();
                    if (sysConfig.getCreateUserId() != null) {
                        userIdList.add(sysConfig.getCreateUserId());
                    }
                    if (sysConfig.getUpdateUserId() != null) {
                        userIdList.add(sysConfig.getUpdateUserId());
                    }
                    List<Users> usersList = systemConstant.getUserInfoByUserIdList(userIdList);
                    if (usersList != null && usersList.size() > 0) {
                        for (Users user : usersList) {
                            if (sysConfig.getCreateUserId() != null
                                    && user.getId().compareTo(sysConfig.getCreateUserId()) == 0) {
                                sysConfig.setCreateUserName(user.getTrueName());
                            }
                            if (sysConfig.getUpdateUserId() != null
                                    && user.getId().compareTo(sysConfig.getUpdateUserId()) == 0) {
                                sysConfig.setUpdateUserName(user.getTrueName());
                            }
                        }
                    }
                }

                return sysConfig;
            }
        }
        LOGGER.error("系统中不存在id为 {} 的配置项", sysConfigId);
        return null;
    }

    @Override
    public boolean updateSysConfig(SysConfig sysConfig, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：更新配置项的值");

        String sysLogCause = "";
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        boolean result = false;
        // 去除回车、换行、制表符等信息
//        sysConfig.setConfigValue(MyStringUtils.replaceBlank(sysConfig.getConfigValue()));
        if (this.checkUpdateSysConfig(sysConfig, modelMap)) {
            //通过校验，开始进行更新
            sysConfig.setUpdateUserId(user.getId());
            Integer resultNum = sysConfigDao.updateSysConfig(sysConfig);
            if (resultNum.compareTo(new Integer(1)) == 0) {
                //更新存放到内存中的项目展示密码常量信息
                systemConstant.setSysConfig();

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
                "更新系统配置项的值：" + sysConfig.getConfigItem(),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    private boolean checkUpdateSysConfig(SysConfig sysConfig, PubModelMap modelMap) {
        LOGGER.debug("Service层：校验系统配置项是否已存在！");

        List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();

        SysConfig sc = null;
        if (StringUtils.isNotBlank(sysConfig.getConfigItem())) {
            sc = this.getSysConfigByConfigItem(sysConfig.getConfigItem());
        }
        if (sc == null) {
            LOGGER.info("系统配置项不存在");
            errorInfoList.add(new CheckErrorDto("change_config_value", "系统配置项不存在"));
            modelMap.put("status", "error");
            modelMap.put("data", errorInfoList);
            return false;
        }

        sysConfig.setValueType(sc.getValueType());
        this.checkSysConfigBaseInfo(sysConfig, errorInfoList);
        if (errorInfoList.size() > 0) {
            modelMap.put("status", "error");
            modelMap.put("data", errorInfoList);
            return false;
        }

        return true;
    }

    private void checkSysConfigBaseInfo(SysConfig sysConfig, List<CheckErrorDto> errorInfoList) {
        LOGGER.debug("Service层：校验系统配置项值的长度！");
        Integer length = null;
        String prefix = "";
        if (sysConfig.getId() != null)
            prefix = "change_";
        if (StringUtils.isBlank(sysConfig.getConfigValue())) {
            LOGGER.info("请输入系统配置值");
            errorInfoList.add(new CheckErrorDto(prefix + "config_value", "请输入系统配置值"));
        } else {
            length = new Integer(sysConfig.getConfigValue().length());
            if (length.compareTo(inputConfig.getConfigValueLength()) > 0) {
                errorInfoList.add(new CheckErrorDto(prefix + "config_value", "系统配置值的长度不能大于"
                        + inputConfig.getConfigValueLength() + "位"));
            } else {
                if (sysConfig.getValueType().equals("integer")) {
                    if (!MyStringUtils.isValidLongNumberForTongHu(sysConfig.getConfigValue())) {
                        errorInfoList.add(new CheckErrorDto(prefix + "config_value", "请输入数字且为正整数"));
                    }
                }
            }
        }
    }

}
