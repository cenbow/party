package com.party.core.service.thirdParty.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.thirdParty.ThirdPartyReadDao;
import com.party.core.dao.write.thirdParty.ThirdPartyWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.thirdParty.IThirdPartyService;
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
 * 第三方公司管理数据服务接口
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */

@Service
public class ThirdPartyService implements IThirdPartyService{

    @Autowired
    ThirdPartyReadDao thirdPartyReadDao;

    @Autowired
    ThirdPartyWriteDao thirdPartyWriteDao;

    /**
     * 第三方数据插入
     * @param thirdParty 第三方公司管理数据
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(ThirdParty thirdParty) {
        BaseModel.preInsert(thirdParty);
        boolean result = thirdPartyWriteDao.insert(thirdParty);
        if (result){
            return thirdParty.getId();
        }
        return null;
    }

    /**
     * 第三方数据更新
     * @param thirdParty 第三方公司管理数据
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(ThirdParty thirdParty) {
        thirdParty.setUpdateDate(new Date());
        return thirdPartyWriteDao.update(thirdParty);
    }

    /**
     * 第三方数据逻辑删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return thirdPartyWriteDao.deleteLogic(id);
    }

    /**
     * 第三方数据物理删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return thirdPartyWriteDao.delete(id);
    }

    /**
     * 第三方数据批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return thirdPartyWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 第三方数据批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return thirdPartyWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取第三方数据
     * @param id 主键
     * @return 第三方公司管理数据
     */
    @Override
    public ThirdParty get(String id) {
        return thirdPartyReadDao.get(id);
    }

    /**
     * 分页查询第三方公司管理数据
     * @param thirdParty
     * @param page 分页信息
     * @return 第三方公司管理数据
     */
    @Override
    public List<ThirdParty> listPage(ThirdParty thirdParty, Page page) {
        return thirdPartyReadDao.listPage(thirdParty, page);
    }

    /**
     * 查询所有第三方公司管理数据
     * @param thirdParty 第三方公司管理数据
     * @return 第三方公司管理数据列表
     */
    @Override
    public List<ThirdParty> list(ThirdParty thirdParty) {
        return thirdPartyReadDao.listPage(thirdParty, null);
    }

    /**
     *
     * @param ids 主键集合
     * @param thirdParty
     * @param page 分页信息
     * @return
     */
    @Override
    public List<ThirdParty> batchList(@NotNull Set<String> ids, ThirdParty thirdParty, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return thirdPartyReadDao.batchList(ids, new HashedMap(), page);
    }
}
