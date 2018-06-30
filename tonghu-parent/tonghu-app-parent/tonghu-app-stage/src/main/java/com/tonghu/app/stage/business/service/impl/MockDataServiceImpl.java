package com.tonghu.app.stage.business.service.impl;

import com.tonghu.app.stage.business.service.MockDataService;
import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.security.service.impl.SysLogContext;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.business.dao.MockDataDao;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.constant.EnumIsSuccess;
import com.tonghu.pub.common.constant.EnumOperatorType;
import com.tonghu.pub.common.constant.EnumResourceDataType;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.business.po.MockData;
import com.tonghu.pub.model.po.CheckErrorDto;
import com.tonghu.pub.model.security.po.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangyongjian
 * @desc 模拟数据 服务层实现类
 * @create 2018-06-28 23:51
 **/
@Service("mockDataService")
public class MockDataServiceImpl implements MockDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataServiceImpl.class);

    @Resource
    private InputConfig inputConfig;

    @Resource
    private MockDataDao mockDataDao;

    @Resource
    private SysLogContext sysLogContext;

    @Resource
    private SystemConstant systemConstant;

    @Override
    public void getMockDataForInitPage(ModelMap model, HttpServletRequest request) {
        List<MockData> mockDataList = mockDataDao.getAllMockData();
        if (!CollectionUtils.isEmpty(mockDataList)) {
            for (MockData mockData : mockDataList) {
                if (!StringUtils.isEmpty(mockData.getDataType())) {
                    EnumResourceDataType enumMockDataType =
                            EnumResourceDataType.fromDataTypeStr(mockData.getDataType());
                    if (enumMockDataType != null) {
                        mockData.setDataTypeName(enumMockDataType.getDataTypeDesc()[0]);
                        mockData.setDataTypeDesc(enumMockDataType.getDataTypeDesc()[1]);
                    }
                }

                mockData.setGetInfoApiUrl(systemConstant.getTongHuApiUrlPrefix()
                        + "/mock/getData?id=" + mockData.getId());

            }
        }
        model.addAttribute("mockDataList", mockDataList);
    }

    @Override
    public MockData getExtendMockDataById(Long id, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id获取模拟数据项扩展信息");
        MockData mockData = mockDataDao.getMockDataById(id);
        if (mockData == null || mockData.getId() == null) {
            LOGGER.debug("根据id获取模拟数据项扩展信息，没有查取到模拟数据项信息：id {}", id);
            return null;
        }

        if (!StringUtils.isEmpty(mockData.getDataType())) {
            EnumResourceDataType enumMockDataType =
                    EnumResourceDataType.fromDataTypeStr(mockData.getDataType());
            if (enumMockDataType != null) {
                mockData.setDataTypeName(enumMockDataType.getDataTypeDesc()[0]);
                mockData.setDataTypeDesc(enumMockDataType.getDataTypeDesc()[1]);
            }
        }

        mockData.setGetInfoApiUrl(systemConstant.getTongHuApiUrlPrefix()
                + "/mock/getData?id=" + mockData.getId());

        //获取创建和修改项目状态信息的用户名称
        if (mockData.getCreateUserId() != null || mockData.getUpdateUserId() != null) {
            List<Long> userIdList = new ArrayList<Long>();
            if (mockData.getCreateUserId() != null) {
                userIdList.add(mockData.getCreateUserId());
            }
            if (mockData.getUpdateUserId() != null) {
                userIdList.add(mockData.getUpdateUserId());
            }
            List<Users> usersList = systemConstant.getUserInfoByUserIdList(userIdList);
            if (usersList != null && usersList.size() > 0) {
                for (Users user : usersList) {
                    if (mockData.getCreateUserId() != null
                            && user.getId().compareTo(mockData.getCreateUserId()) == 0) {
                        mockData.setCreateUserName(user.getTrueName());
                    }
                    if (mockData.getUpdateUserId() != null
                            && user.getId().compareTo(mockData.getUpdateUserId()) == 0) {
                        mockData.setUpdateUserName(user.getTrueName());
                    }
                }
            }
        }
        return mockData;
    }

    @Override
    public boolean addNewMockData(MockData mockData, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：保存新的模拟数据");
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        String sysLogCause = "";
        boolean result = false;
        //过滤掉前端用户信息中各个属性值的前后空格
        mockData.trim();
        if (this.checkMockDataForAddOrUpdate(mockData, modelMap, true)) {
            //通过校验，开始进行更新
            mockData.setCreateUserId(user.getId());
            Integer resultNum = mockDataDao.insertNewMockData(mockData);
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
                "新增模拟数据项信息：" + mockData.getDataName(),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    @Override
    public boolean updateMockData(MockData mockData, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id更新模拟数据项信息");
        String sysLogCause = "";
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        boolean result = false;
        //过滤掉各个属性值的前后空格
        mockData.trim();
        if (this.checkMockDataForAddOrUpdate(mockData, modelMap, false)) {
            //通过校验，开始进行更新
            mockData.setUpdateUserId(user.getId());
            Integer resultNum = mockDataDao.updateMockDataById(mockData);
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
                "更新模拟数据项信息：" + mockData.getDataName(),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    @Override
    public boolean removeMockData(Long id, HttpServletRequest request, PubModelMap modelMap) {
        LOGGER.debug("Service层：根据id删除模拟数据项信息 物理删除");
        boolean result = false;
        String sysLogCause = "";
        MockData mockData = mockDataDao.getMockDataById(id);
        if (mockData == null) {
            modelMap.put("status", "error");
            modelMap.put("data", "不存在该模拟数据项记录！");
            sysLogCause = Constant.COMMON_ERROR.ERROR_MSG + "——不存在该模拟数据项记录";
        } else {
            //通过校验，开始进行删除
            Integer resultNum = mockDataDao.deleteMockDataById(id);
            if (resultNum.intValue() == 1) {
                result = true;
            } else {
                sysLogCause = Constant.PERSISTENCE_ERROR.ERROR_MSG;
                modelMap.put("status", "failure");
            }
        }
        //记录访问日志
        sysLogContext.saveSysLog(request, EnumOperatorType.DELETE.getType(),
                "删除模拟数据项记录" + (result ? "：" + mockData.getDataName() : ""),
                result ? EnumIsSuccess.SUCCESS.getStatus() : EnumIsSuccess.FAILURE.getStatus(), sysLogCause);
        return result;
    }

    /**
     * 校验模拟数据信息
     * @param mockData
     * @param modelMap
     * @return
     */
    private boolean checkMockDataForAddOrUpdate(MockData mockData, PubModelMap modelMap, boolean isAdd) {
        LOGGER.debug("Service层：校验模拟数据信息的名称、数据值、备注的长度，校验新模拟数据名称是否已存在！");
        List<CheckErrorDto> errorInfoList = new ArrayList<CheckErrorDto>();

        this.checkMockDataBaseInfo(mockData, errorInfoList);

        if (errorInfoList.size() > 0) {
            modelMap.put("status", "error");
            modelMap.put("data", errorInfoList);
            return false;
        }

        LOGGER.debug("Service层：校验新模拟数据项名称是否已存在！");
        MockData md = mockDataDao.getMockDataByName(mockData.getDataName());
        if (md != null) {
            if (isAdd) {
                LOGGER.info("模拟数据项名称已经存在");
                errorInfoList.add(new CheckErrorDto("data_name", "模拟数据项名称已经存在"));
            } else {
                // 这是在更新用户
                if (md.getId().compareTo(mockData.getId()) != 0) {
                    LOGGER.info("模拟数据项名称已经存在");
                    errorInfoList.add(new CheckErrorDto("change_data_name", "模拟数据名称已经存在"));
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
     * 校验模拟数据的基本信息
     * @param mockData
     * @param errorInfoList
     */
    private void checkMockDataBaseInfo(MockData mockData, List<CheckErrorDto> errorInfoList) {
        LOGGER.debug("Service层：校验模拟数据的基本信息！");
        Integer length = null;
        String prefix = "";
        if (mockData.getId() != null)
            prefix = "change_";

        if (StringUtils.isEmpty(mockData.getDataName())) {
            LOGGER.info("请输入数据项名称");
            errorInfoList.add(new CheckErrorDto(prefix + "data_name", "请输入数据项名称"));
        } else {
            length = new Integer(mockData.getDataName().length());
            if (length.intValue() > inputConfig.getDataNameLength()) {
                errorInfoList.add(new CheckErrorDto(prefix + "data_name", "数据项名称不能大于"
                        + inputConfig.getDataNameLength() + "位"));
            }
        }

        if (StringUtils.isEmpty(mockData.getDataValue())) {
            LOGGER.info("请输入数据值");
            errorInfoList.add(new CheckErrorDto(prefix + "data_value", "<BR/>请输入数据值"));
        } else {
            length = new Integer(mockData.getDataValue().length());
            if (length.intValue() > inputConfig.getDataValueLength()) {
                errorInfoList.add(new CheckErrorDto(prefix + "data_value", "<BR/>数据值不能大于"
                        + inputConfig.getDataValueLength() + "位"));
            }
        }

        if (StringUtils.isEmpty(mockData.getDataType())) {
            LOGGER.info("请选择数据类型");
            errorInfoList.add(new CheckErrorDto(prefix + "data_value", "请选择数据类型"));
        } else {
            EnumResourceDataType enumMockDataType = EnumResourceDataType.fromDataTypeStr(mockData.getDataType());
            if (enumMockDataType == null) {
                errorInfoList.add(new CheckErrorDto(prefix + "data_value", "数据类型选择有误"));
            }
        }

        if (!StringUtils.isEmpty(mockData.getNote())) {
            length = new Integer(mockData.getNote().length());
            if (length.compareTo(inputConfig.getNoteLength()) > 0) {
                errorInfoList.add(new CheckErrorDto(prefix + "data_note", "备注长度不能大于"
                        + inputConfig.getNoteLength() + "位"));
            }
        }
    }

}
