package com.party.core.service.version.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.version.VersionManagerReadDao;
import com.party.core.dao.write.version.VersionManagerWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.version.VersionManager;
import com.party.core.service.version.IVersionManagerService;
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
 * 第三方登录开关设置服务实现
 * party
 * Created by Wesley
 * on 2016/11/23.
 */

@Service
public class VersionManagerService implements IVersionManagerService{

    @Autowired
    private VersionManagerReadDao versionManagerReadDao;

    @Autowired
    private VersionManagerWriteDao versionManagerWriteDao;

    /**
     * 第三方登录开关设置插入
     * @param entity 第三方登录开关设置信息
     * @return 插入结果（true/false）
     */
    public String insert(VersionManager entity) {
        BaseModel.preInsert(entity);
        boolean result = versionManagerWriteDao.insert(entity);
        if (result){
            return entity.getId();
        }
        return null;
    }


    /**
     * 第三方登录开关设置更新
     * @param entity 第三方登录开关设置信息
     * @return 更新结果（true/false）
     */
    public boolean update(VersionManager entity) {
        entity.setUpdateDate(new Date());
        return versionManagerWriteDao.update(entity);
    }

    /**
     * 逻辑删除第三方登录开关设置
     * @param id 第三方登录开关设置主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return versionManagerWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除第三方登录开关设置
     * @param id 第三方登录开关设置主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return versionManagerWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除第三方登录开关设置
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return versionManagerWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除第三方登录开关设置
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return versionManagerWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取第三方登录开关设置信息
     * @param id 主键
     * @return 第三方登录开关设置信息
     */
    public VersionManager get(String id) {
        return versionManagerReadDao.get(id);
    }

    /**
     * 分页查询第三方登录开关设置
     * @param entity 第三方登录开关设置信息
     * @param page 分页信息
     * @return 第三方登录开关设置列表
     */
    public List<VersionManager> listPage(VersionManager entity, Page page) {
        return versionManagerReadDao.listPage(entity, page);
    }

    /**
     * 查询所有第三方登录开关设置信息
     * @param entity 第三方登录开关设置信息
     * @return 第三方登录开关设置列表
     */
    public List<VersionManager> list(VersionManager entity) {
        return versionManagerReadDao.listPage(entity, null);
    }



    /**
     * 批量查询第三方登录开关设置信息
     * @param ids 主键集合
     * @param entity 第三方登录开关设置信息
     * @param page 分页信息
     * @return 第三方登录开关设置列表
     */
    public List<VersionManager> batchList(@NotNull Set<String> ids, VersionManager entity, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return versionManagerReadDao.batchList(ids, new HashedMap(), page);
    }
}
