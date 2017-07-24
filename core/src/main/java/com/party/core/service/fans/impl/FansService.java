package com.party.core.service.fans.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.fans.FansReadDao;
import com.party.core.dao.write.fans.FansWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.fans.Fans;
import com.party.core.service.fans.IFansService;
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
 * 粉丝服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Service
public class FansService implements IFansService{

    @Autowired
    FansReadDao fansReadDao;

    @Autowired
    FansWriteDao fansWriteDao;

    /**
     * 粉丝插入
     * @param fans 粉丝
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Fans fans) {
        BaseModel.preInsert(fans);
        boolean result = fansWriteDao.insert(fans);
        if (result){
            return fans.getId();
        }
        return null;
    }

    /**
     * 粉丝更新
     * @param fans 粉丝
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Fans fans) {
        fans.setUpdateDate(new Date());
        return fansWriteDao.update(fans);
    }

    /**
     * 逻辑删除粉丝
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return fansWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除粉丝
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return fansWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除粉丝
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return fansWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除粉丝
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return fansWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取粉丝信息
     * @param id 主键
     * @return 粉丝
     */
    @Override
    public Fans get(String id) {
        return fansReadDao.get(id);
    }

    /**
     * 分页查询粉丝信息
     * @param fans 粉丝信息
     * @param page 分页信息
     * @return 粉丝列表
     */
    @Override
    public List<Fans> listPage(Fans fans, Page page) {
        return fansReadDao.listPage(fans, page);
    }


    /**
     * 查询所有粉丝
     * @param fans 粉丝信息
     * @return 粉丝列表
     */
    @Override
    public List<Fans> list(Fans fans) {
        return fansReadDao.listPage(fans, null);
    }


    /**
     * 批量查询粉丝
     * @param ids 主键集合
     * @param fans 粉丝信息
     * @param page 分页信息
     * @return 粉丝列表
     */
    @Override
    public List<Fans> batchList(@NotNull Set<String> ids, Fans fans, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return fansReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public Integer countFans(Fans entity) {
        return fansReadDao.countFans(entity);
    }

    @Override
    public Integer countFocus(Fans entity) {
        return fansReadDao.countFocus(entity);
    }
}
