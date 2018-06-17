package com.tonghu.pub.common.dao.impl;

import com.tonghu.pub.common.dao.SysConfigDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.common.po.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 系统配置项信息 Dao层实现类
 * @create 2017-11-19 23:04
 **/
@Repository("sysConfigDao")
public class SysConfigDaoImpl extends BaseDao implements SysConfigDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigDaoImpl.class);

    @Override
    public List<SysConfig> getAllSysConfig() {
        LOGGER.debug("Dao层：获取所有的系统配置项信息");
        return getReadSqlSession().selectList("sysConfigDao.selectAllSysConfigInfo");
    }

    @Override
    public SysConfig getSysConfigInfoById(Long sysConfigId) {
        LOGGER.debug("Dao层：获取所有的系统配置项信息");
        return getReadSqlSession().selectOne("sysConfigDao.selectSysConfigById", sysConfigId);
    }

    @Override
    public SysConfig getSysConfigByConfigItem(String configItem) {
        LOGGER.debug("Dao层：根据配置项名称获取配置项信息");
        return getReadSqlSession().selectOne("sysConfigDao.selectSysConfigByConfigItem", configItem);
    }

    @Override
    public Integer updateSysConfig(SysConfig sysConfig) {
        LOGGER.debug("Dao层：更新配置项的值");
        return update("sysConfigDao.updateSysConfigByConfigItem", sysConfig);
    }

}
