package com.party.core.service.wechat.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.wechat.WechatAccountReadDao;
import com.party.core.dao.write.wechat.WechatAccountWriteDao;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.wechat.IWechatAccountService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 微信账户服务实现
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */
@Service
public class WechatAccountService implements IWechatAccountService {

    @Autowired
    WechatAccountReadDao wechatAccountReadDao;

    @Autowired
    WechatAccountWriteDao wechatAccountWriteDao;


    /**
     * 微信账户插入
     * @param wechatAccount 微信账户信息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(WechatAccount wechatAccount) {
        String uuid = UUIDUtils.generateRandomUUID();
        wechatAccount.setId(uuid);
        boolean result = wechatAccountWriteDao.insert(wechatAccount);
        if (result){
            return wechatAccount.getId();
        }
        return null;
    }


    /**
     * 微信账户更新
     * @param wechatAccount 微信账户信息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(WechatAccount wechatAccount) {
        return wechatAccountWriteDao.update(wechatAccount);
    }

    /**
     * 微信账户逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatAccountWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除微信账户信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){return false;}
        return wechatAccountWriteDao.delete(id);
    }


    /**
     * 微信账户逻辑批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatAccountWriteDao.batchDeleteLogic(ids);
    }


    /**
     * 微信账户物理批量删除
     * @param ids 主键集合
     * @return 删除结果
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatAccountWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键查询微信账户信息
     * @param id 主键
     * @return 微信账户信息
     */
    public WechatAccount get(String id) {
        return wechatAccountReadDao.get(id);
    }

    /**
     * 获取微信账户
     * @return 微信账户
     */
    @Override
    public WechatAccount getSystem() {
        return wechatAccountReadDao.getSystem();
    }

    /**
     * 获取合作商配置
     * @param memberId 会员编号
     * @return 微信账户
     */
    @Override
    public WechatAccount getPartner(String memberId) {
        return wechatAccountReadDao.findByMemberId(memberId);
    }

    /**
     * 根据会员编号查询
     * @param memberId 会员编号
     * @return 微信账户
     */
    @Override
    public WechatAccount findByMemberId(String memberId) {
        return wechatAccountReadDao.findByMemberId(memberId);
    }

    /**
     * 分页查询微信账户信息
     * @param wechatAccount 微信账户信息
     * @param page 分页信息
     * @return 微信账户信息列表
     */
    @Override
    public List<WechatAccount> listPage(WechatAccount wechatAccount, Page page) {
        return wechatAccountReadDao.listPage(wechatAccount, page);
    }


    /**
     * 查询所有微信账户信息
     * @param wechatAccount 微信账户信息
     * @return 微信账户信息列表
     */
    @Override
    public List<WechatAccount> list(WechatAccount wechatAccount) {
        return wechatAccountReadDao.listPage(wechatAccount, null);
    }


    /**
     * 批量查询微信账户信息
     * @param ids 主键集合
     * @param wechatAccount 微信账户信息
     * @param page 分页信息
     * @return 微信账户列表
     */
    @Override
    public List<WechatAccount> batchList(@NotNull Set<String> ids, WechatAccount wechatAccount, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return wechatAccountReadDao.batchList(ids, new HashedMap(), page);
    }
}
