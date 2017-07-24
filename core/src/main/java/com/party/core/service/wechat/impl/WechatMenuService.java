package com.party.core.service.wechat.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.wechat.WechatMenuReadDao;
import com.party.core.dao.write.wechat.WechatMenuWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.wechat.WechatMenu;
import com.party.core.service.wechat.IWechatMenuService;
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
 * 微信菜单服务实现
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */

@Service
public class WechatMenuService implements IWechatMenuService{

    @Autowired
    WechatMenuReadDao wechatMenuReadDao;

    @Autowired
    WechatMenuWriteDao wechatMenuWriteDao;


    /**
     * 微信菜单插入
     * @param wechatMenu 微信菜单
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(WechatMenu wechatMenu) {
        BaseModel.preInsert(wechatMenu);
        boolean result = wechatMenuWriteDao.insert(wechatMenu);
        if (result){
            wechatMenu.getId();
        }
        return null;
    }

    /**
     * 微信菜单更新
     * @param wechatMenu 微信菜单信息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(WechatMenu wechatMenu) {
        wechatMenu.setUpdateDate(new Date());
        return wechatMenuWriteDao.update(wechatMenu);
    }


    /**
     * 微信菜单逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatMenuWriteDao.deleteLogic(id);
    }

    /**
     * 微信菜单物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatMenuWriteDao.delete(id);
    }

    /**
     * 微信菜单逻辑批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatMenuWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 微信菜单物理批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatMenuWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取微信菜单
     * @param id 主键
     * @return 微信菜单信息
     */
    @Override
    public WechatMenu get(String id) {
        return wechatMenuReadDao.get(id);
    }

    /**
     * 批量查询微信菜单
     * @param wechatMenu 微信菜单
     * @param page 分页信息
     * @return 微信菜单列表
     */
    @Override
    public List<WechatMenu> listPage(WechatMenu wechatMenu, Page page) {
        return wechatMenuReadDao.listPage(wechatMenu, page);
    }


    /**
     * 查询所有微信菜单信息
     * @param wechatMenu 微信菜单信息
     * @return 微信菜单列表
     */
    @Override
    public List<WechatMenu> list(WechatMenu wechatMenu) {
        return wechatMenuReadDao.listPage(wechatMenu, null);
    }


    /**
     * 批量查询微信菜单信息
     * @param ids 主键集合
     * @param wechatMenu 微信菜单信息
     * @param page 分页信息
     * @return 微信菜单列表
     */
    @Override
    public List<WechatMenu> batchList(@NotNull Set<String> ids, WechatMenu wechatMenu, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return wechatMenuReadDao.batchList(ids, new HashedMap(), page);
    }
}
