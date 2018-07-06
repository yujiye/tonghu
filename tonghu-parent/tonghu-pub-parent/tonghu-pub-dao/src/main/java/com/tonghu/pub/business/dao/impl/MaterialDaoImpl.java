package com.tonghu.pub.business.dao.impl;

import com.tonghu.pub.business.dao.MaterialDao;
import com.tonghu.pub.dao.BaseDao;
import com.tonghu.pub.model.business.po.Material;
import com.tonghu.pub.model.business.po.query.MaterialQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 素材数据Dao层实现类
 * @create 2018-06-28 23:15
 **/
@Repository("materialDao")
public class MaterialDaoImpl extends BaseDao implements MaterialDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialDaoImpl.class);

    @Override
    public List<Material> getMaterialByQuery(MaterialQuery query) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据查询条件获取 素材数据");
        }
        return getReadSqlSession().selectList("materialDao.selectMaterialInfoByQuery", query);
    }

    @Override
    public Integer getMaterialCountByQuery(MaterialQuery query) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据查询条件获取 素材数据记录条数");
        }
        return getReadSqlSession().selectOne("materialDao.selectMaterialCountByQuery", query);
    }

    @Override
    public List<Material> getAllMaterial() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：获取所有的素材数据");
        }
        return getReadSqlSession().selectList("materialDao.selectAllMaterialInfo");
    }

    @Override
    public Material getMaterialById(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据 id 获取素材数据");
        }
        return getReadSqlSession().selectOne("materialDao.selectMaterialById", id);
    }

    @Override
    public Material getMaterialByName(String dataName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：根据 素材名称 精确查找 获取素材数据");
        }
        return getReadSqlSession().selectOne("materialDao.selectMaterialByName", dataName);
    }

    @Override
    public Integer insertNewMaterial(Material material){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：新增素材数据");
        }
        return insert("materialDao.insertNewMaterial", material);
    }

    @Override
    public Integer updateMaterialById(Material material) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：更新素材数据");
        }
        return update("materialDao.updateMaterialById", material);
    }

    @Override
    public Integer deleteMaterialById(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dao层：删除素材数据");
        }
        return delete("materialDao.deleteMaterialById", id);
    }

}
