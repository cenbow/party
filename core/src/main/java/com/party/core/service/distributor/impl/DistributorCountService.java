package com.party.core.service.distributor.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.distributor.DistributorCountReadDao;
import com.party.core.dao.write.distributor.DistributorCountWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.service.distributor.IDistributorCountService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 分销统计服务接口实现
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:38
 */

@Service
public class DistributorCountService implements IDistributorCountService {

    @Autowired
    private DistributorCountReadDao distributorCountReadDao;

    @Autowired
    private DistributorCountWriteDao distributorCountWriteDao;

    /**
     * 插入分销统计
     * @param distributorCount 分销统计
     * @return 编号
     */
    @Override
    public String insert(DistributorCount distributorCount) {
        BaseModel.preInsert(distributorCount);
        DistributorCount dbCount = this.findByRelationId(distributorCount.getDistributorRalationId());
        if (null != dbCount){
            throw new BusinessException("该分销统计已经存在");
        }
        boolean result = distributorCountWriteDao.insert(distributorCount);
        if (result){
            return distributorCount.getId();
        }
        return null;
    }

    /**
     * 更新分销统计
     * @param distributorCount 分销统计
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(DistributorCount distributorCount) {
        return distributorCountWriteDao.update(distributorCount);
    }

    /**
     * 逻辑删除分销统计
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorCountWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除分销统计
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorCountWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除分销统计
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorCountWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除分销统计
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorCountWriteDao.batchDelete(ids);
    }

    /**
     * 获取分销统计
     * @param id 主键
     * @return 分销统计
     */
    @Override
    public DistributorCount get(String id) {
        return distributorCountReadDao.get(id);
    }

    /**
     * 根据分销关系查询分销统计
     * @param relationId 分销关系编号
     * @return 分销统计
     */
    @Override
    public DistributorCount findByRelationId(String relationId) {
        return distributorCountReadDao.findByRelationId(relationId);
    }

    /**
     * 分页查询分销统计
     * @param distributorCount 分销统计
     * @param page 分页信息
     * @return
     */
    @Override
    public List<DistributorCount> listPage(DistributorCount distributorCount, Page page) {
        return distributorCountReadDao.listPage(distributorCount, page);
    }

    /**
     * 查询所有分销统计
     * @param distributorCount 分销统计
     * @return 分销统计列表
     */
    @Override
    public List<DistributorCount> list(DistributorCount distributorCount) {
        return distributorCountReadDao.listPage(distributorCount, null);
    }

    /**
     * 批量查询分销统计
     * @param ids 主键集合
     * @param distributorCount 分销统计
     * @param page 分页信息
     * @return 分销统计列表
     */
    @Override
    public List<DistributorCount> batchList(@NotNull Set<String> ids, DistributorCount distributorCount, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return distributorCountReadDao.batchList(ids, new HashedMap(), page);
    }
}
