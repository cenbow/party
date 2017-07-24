package com.party.core.service.distributor.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.distributor.DistributorDetailReadDao;
import com.party.core.dao.write.distributor.DistributorDetailWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.service.distributor.IDistributorDetailService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 分销详情服务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:37
 */
@Service
public class DistributorDetailService implements IDistributorDetailService {

    @Autowired
    private DistributorDetailReadDao distributorDetailReadDao;

    @Autowired
    private DistributorDetailWriteDao distributorDetailWriteDao;

    /**
     * 插入分销详情
     * @param distributorDetail 分销详情
     * @return 编号
     */
    @Override
    public String insert(DistributorDetail distributorDetail) {
        BaseModel.preInsert(distributorDetail);
        boolean result = distributorDetailWriteDao.insert(distributorDetail);
        if (result){
            return distributorDetail.getId();
        }
        return null;
    }

    /**
     * 更新分销详情
     * @param distributorDetail 分销详情
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(DistributorDetail distributorDetail) {
        return distributorDetailWriteDao.update(distributorDetail);
    }

    /**
     * 逻辑删除分销详情
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorDetailWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除分销详情
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorDetailWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除分销详情
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorDetailWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除分销详情
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorDetailWriteDao.batchDelete(ids);
    }

    /**
     * 获取分销详情
     * @param id 主键
     * @return 分销详情
     */
    @Override
    public DistributorDetail get(String id) {
        return distributorDetailReadDao.get(id);
    }

    /**
     * 分页查询分销详情
     * @param distributorDetail 分销详情
     * @param page 分页信息
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> listPage(DistributorDetail distributorDetail, Page page) {
        return distributorDetailReadDao.listPage(distributorDetail, page);
    }

    /**
     * 查询所有分销详情
     * @param distributorDetail 分销详情
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> list(DistributorDetail distributorDetail) {
        return distributorDetailReadDao.listPage(distributorDetail, null);
    }

    /**
     * 报名列表
     * @param distributorRelationId 分销编号
     * @return 报名列表
     */
    @Override
    public List<DistributorDetail> applyList(String distributorRelationId) {
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorRelationId(distributorRelationId);
        return distributorDetailReadDao.applyList(distributorDetail);
    }

    /**
     * 下单列表
     * @param distributorRelationId 分销编号
     * @return 下单列表
     */
    @Override
    public List<DistributorDetail> buyList(String distributorRelationId) {
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorRelationId(distributorRelationId);
        return distributorDetailReadDao.buyList(distributorDetail);
    }

    /**
     * 分销详情
     * @param distributorRelationId 分销编号
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> corowdfundList(String distributorRelationId) {
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorRelationId(distributorRelationId);
        return distributorDetailReadDao.corowdfundList(distributorDetail);
    }

    /**
     * 支持详情
     * @param distributorRelationId 分销编号
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> supportList(String distributorRelationId) {
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorRelationId(distributorRelationId);
        return distributorDetailReadDao.supportList(distributorDetail);
    }

    /**
     * 批量查询分销详情
     * @param ids 主键集合
     * @param distributorDetail 分销详情
     * @param page 分页信息
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> batchList(@NotNull Set<String> ids, DistributorDetail distributorDetail, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return distributorDetailReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 根据分销关系查询分销详情
     * @param relationId 分销关系编号
     * @return 分销详情列表
     */
    @Override
    public List<DistributorDetail> findByRelationId(String relationId) {
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorRelationId(relationId);
        return this.listPage(distributorDetail, null);
    }

    /**
     * 根据分销详情查
     * @param targetId 目标编号
     * @return 分销详情
     */
    @Override
    public DistributorDetail findByTargetId(String targetId) {
        return distributorDetailReadDao.findByTargetId(targetId);
    }
}
