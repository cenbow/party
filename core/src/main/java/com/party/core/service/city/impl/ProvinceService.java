package com.party.core.service.city.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.qcloud.cosapi.http.ResponseBodyKey;
import com.party.core.dao.read.city.ProvinceReadDao;
import com.party.core.dao.write.city.ProvinceWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.city.Province;
import com.party.core.service.city.IProvinceService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 省份服务实现
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */

@Service
public class ProvinceService implements IProvinceService {

    @Autowired
    ProvinceReadDao provinceReadDao;

    @Autowired
    ProvinceWriteDao provinceWriteDao;

    /**
     * 省份插入
     * @param province 省份
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Province province) {
        BaseModel.preInsert(province);
        boolean result = provinceWriteDao.insert(province);
        if (result){
            return province.getId();
        }
        return null;
    }

    /**
     * 省份更新
     * @param province 省份
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Province province) {
        province.setUpdateDate(new Date());
        return provinceWriteDao.update(province);
    }

    /**
     * 逻辑删除省份
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return provinceWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除省份
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return provinceWriteDao.delete(id);
    }

    /**
     * 逻辑删除省份
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return provinceWriteDao.batchDeleteLogic(ids);
    }


    /**
     * 物理批量删除省份
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return provinceWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取省份信息
     * @param id 主键
     * @return 省份
     */
    @Override
    public Province get(String id) {
        return provinceReadDao.get(id);
    }

    /**
     * 分页查询省份信息
     * @param province 省份
     * @param page 分页信息
     * @return 省份列表
     */
    @Override
    public List<Province> listPage(Province province, Page page) {
        return provinceReadDao.listPage(province, page);
    }

    /**
     * 查询所有省份信息
     * @param province 省份信息
     * @return 省份列表
     */
    @Override
    public List<Province> list(Province province) {
        return provinceReadDao.listPage(province, null);
    }

    /**
     * 批量删除省份信息
     * @param ids 主键集合
     * @param province 省份信息
     * @param page 分页信息
     * @return 省份列表
     */
    @Override
    public List<Province> batchList(@NotNull Set<String> ids, Province province, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return provinceReadDao.batchList(ids, new HashedMap(), page);
    }
}
