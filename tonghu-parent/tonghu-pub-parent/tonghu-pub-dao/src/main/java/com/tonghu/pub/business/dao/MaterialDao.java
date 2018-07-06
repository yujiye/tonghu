package com.tonghu.pub.business.dao;

import com.tonghu.pub.model.business.po.Material;
import com.tonghu.pub.model.business.po.query.MaterialQuery;

import java.util.List;

/**
 * @author liangyongjian
 * @desc 素材数据Dao层 接口
 * @create 2018-06-28 22:51
 **/
public interface MaterialDao {

    /**
     * 根据查询条件获取 素材数据
     * @param query
     * @return
     */
    List<Material> getMaterialByQuery(MaterialQuery query);

    /**
     * 根据查询条件获取 素材数据的记录条数
     * @param query
     * @return
     */
    Integer getMaterialCountByQuery(MaterialQuery query);

    /**
     * 获取所有的素材数据
     * @return
     */
    List<Material> getAllMaterial();

    /**
     * 根据 id 获取素材数据
     * @param id
     * @return
     */
    Material getMaterialById(Long id);

    /**
     * 根据 资源名称 精确查找 获取素材数据
     * @param materialName
     * @return
     */
    Material getMaterialByName(String materialName);

    /**
     * 新增素材数据
     * @param Material
     * @return
     */
    Integer insertNewMaterial(Material material);

    /**
     * 更新素材数据
     * @param Material
     * @return
     */
    Integer updateMaterialById(Material material);

    /**
     * 删除素材数据
     * @param id
     * @return
     */
    Integer deleteMaterialById(Long id);

}
