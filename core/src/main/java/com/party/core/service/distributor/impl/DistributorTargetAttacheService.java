package com.party.core.service.distributor.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.distributor.DistributorTargetAttacheReadDao;
import com.party.core.dao.write.distributor.DistributorTargetAttacheWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 分销附属表接口实现
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 18:55
 */

@Service
public class DistributorTargetAttacheService implements IDistributorTargetAttacheService {

    @Autowired
    private DistributorTargetAttacheReadDao distributorTargetAttacheReadDao;

    @Autowired
    private DistributorTargetAttacheWriteDao distributorTargetAttacheWriteDao;

    /**
     * 插入分销关系附属
     * @param distributorTargetAttache 分销关系附属
     * @return 分销关系附属
     */
    @Override
    public String insert(DistributorTargetAttache distributorTargetAttache) {
        BaseModel.preInsert(distributorTargetAttache);
        boolean result = distributorTargetAttacheWriteDao.insert(distributorTargetAttache);
        if (result){
            return distributorTargetAttache.getId();
        }
        return  null;
    }

    /**
     * 更新分销关系附属
     * @param distributorTargetAttache 分销关系附属
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(DistributorTargetAttache distributorTargetAttache) {
        return distributorTargetAttacheWriteDao.update(distributorTargetAttache);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorTargetAttacheWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorTargetAttacheWriteDao.delete(id);
    }

    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return false;
    }

    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return false;
    }

    /**
     * 根据编号获取
     * @param id 主键
     * @return 分销关系附属
     */
    @Override
    public DistributorTargetAttache get(String id) {
        return distributorTargetAttacheReadDao.get(id);
    }

    /**
     * 根据分销关系获取
     * @param relationId 关系编号
     * @return 分销关系附属
     */
    @Override
    public DistributorTargetAttache findByRelationId(String relationId) {
        return distributorTargetAttacheReadDao.findByRelationId(relationId);
    }

    @Override
    public List<DistributorTargetAttache> listPage(DistributorTargetAttache distributorTargetAttache, Page page) {
        return null;
    }

    @Override
    public List<DistributorTargetAttache> list(DistributorTargetAttache distributorTargetAttache) {
        return null;
    }

    @Override
    public List<DistributorTargetAttache> batchList(@NotNull Set<String> ids, DistributorTargetAttache distributorTargetAttache, Page page) {
        return null;
    }
}
