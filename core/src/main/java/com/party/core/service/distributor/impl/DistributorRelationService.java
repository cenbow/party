package com.party.core.service.distributor.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.dao.read.distributor.DistributorCountReadDao;
import com.party.core.dao.read.distributor.DistributorDetailReadDao;
import com.party.core.dao.read.distributor.DistributorRelationReadDao;
import com.party.core.dao.write.distributor.DistributorCountWriteDao;
import com.party.core.dao.write.distributor.DistributorDetailWriteDao;
import com.party.core.dao.write.distributor.DistributorRelationWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.distributor.*;
import com.party.core.model.goods.GoodsType;
import com.party.core.service.distributor.IDistributorRelationService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 分销关系服务接口实现
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:33
 */

@Service
public class DistributorRelationService implements IDistributorRelationService {

    @Autowired
    private DistributorRelationReadDao distributorRelationReadDao;

    @Autowired
    private DistributorRelationWriteDao distributorRelationWriteDao;

    @Autowired
    private DistributorDetailReadDao distributorDetailReadDao;

    @Autowired
    private DistributorDetailWriteDao distributorDetailWriteDao;

    @Autowired
    private DistributorCountReadDao distributorCountReadDao;

    @Autowired
    private DistributorCountWriteDao distributorCountWriteDao;

    /**
     * 插入分销关系
     * @param distributorRelation 分销关系
     * @return 编号
     */
    @Override
    public String insert(DistributorRelation distributorRelation) {
        BaseModel.preInsert(distributorRelation);
        DistributorRelation dbDistriborartion
                = this.get(distributorRelation.getType(), distributorRelation.getTargetId(),
                distributorRelation.getDistributorId());
        if (null != dbDistriborartion){
            throw new BusinessException("该分销关系以及存在");
        }
        boolean result = distributorRelationWriteDao.insert(distributorRelation);
        if (result){
            return distributorRelation.getId();
        }
        return null;
    }

    /**
     * 更新分销关系
     * @param distributorRelation 分销关系
     * @return 编号
     */
    @Override
    public boolean update(DistributorRelation distributorRelation) {
        return distributorRelationWriteDao.update(distributorRelation);
    }


    /**
     * 逻辑删除分销关系
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorRelationWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除分销关系
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return distributorRelationWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除分销关系
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorRelationWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除分销关系
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return distributorRelationWriteDao.batchDelete(ids);
    }

    /**
     * 获取分销关系
     * @param id 主键
     * @return 分销关系
     */
    @Override
    public DistributorRelation get(String id) {
        return distributorRelationReadDao.get(id);
    }

    /**
     * 获取分销关系
     * @param type 类型
     * @param targetId 目标编号
     * @param distributorId 分销者编号
     * @return 分销关系
     */
    @Override
    public DistributorRelation get(Integer type, String targetId, String distributorId) {
        return distributorRelationReadDao.findByUniqueness(type, targetId,distributorId);
    }

    /**
     * 分页查询分销关系
     * @param distributorRelation 分页关系
     * @param page 分页信息
     * @return 分销关系列表
     */
    @Override
    public List<DistributorRelation> listPage(DistributorRelation distributorRelation, Page page) {
        return distributorRelationReadDao.listPage(distributorRelation, page);
    }

    /**
     * 查询所有分销关系
     * @param distributorRelation 分销关系
     * @return 分销关系列表
     */
    @Override
    public List<DistributorRelation> list(DistributorRelation distributorRelation) {
        return this.listPage(distributorRelation, null);
    }

    /**
     * 批量查询分销关系
     * @param ids 主键集合
     * @param distributorRelation 分销关系
     * @param page 分页信息
     * @return 分销关系列表
     */
    @Override
    public List<DistributorRelation> batchList(@NotNull Set<String> ids, DistributorRelation distributorRelation, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return distributorRelationReadDao.batchList(ids, new HashedMap(), page);
    }


    /**
     * 我代言的项目
     * @param memberId 会员编号
     * @param status 状态(0:进行中, 1:已经截止)
     * @return 项目列表
     */
    @Override
    public List<WithActivity> activityList(String memberId, Integer status, Page page) {
        HashMap<String, Object> pararm = Maps.newHashMap();
        pararm.put("distributorId", memberId);
        pararm.put("isCrowdfunded", Constant.IS_CROWDFUNDED);
        pararm.put("status", status);
        pararm.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
        pararm.put("type", GoodsType.GOODS_CROWD_FUND.getCode());
        return distributorRelationReadDao.activityList(pararm, page);
    }


    /**
     * 统计列表
     * @param param 参数
     * @param page 分页
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(HashMap<String, Object> param, Page page) {
        return distributorRelationReadDao.listWithCount(param, page);
    }

    /**
     * 统计列表
     * @param status 状态
     * @param type 类型
     * @param sort 排序
     * @param page 分页
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(Integer status, Integer type, Integer sort,String distributorId, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("distributorId", distributorId);
        param.put("status", status);
        param.put("type", type);
        param.put("sort", sort);
        param.put("excludeType", GoodsType.GOODS_CROWD_FUND.getCode());
        return this.listWithCount(param, page);
    }


    /**
     * 统计列表
     * @param targetId 目标编号
     * @param page 分页参数
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(String targetId, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("targetId", targetId);
        param.put("type", GoodsType.GOODS_CROWD_FUND.getCode());
        return this.listWithCount(param, page);
    }

    /**
     * 统计列表
     * @param withCount 统计实体
     * @param page 分页参数
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(WithCount withCount, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("targetId", withCount.getTargetId());
        param.put("type", GoodsType.GOODS_CROWD_FUND.getCode());
        param.put("distributorName", withCount.getAuthorName());
        param.put("authorMobile", withCount.getAuthorMobile());
        param.put("eventId", withCount.getEventId());
        return this.listWithCount(param, page);
    }

    /**
     * 统计列表
     * @param title 题目
     * @param type 类型
     * @param distributorId 分销者
     * @param page 分页参数
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(String title, Integer type, String distributorId, String startTime, String endTime, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("distributorId", distributorId);
        param.put("type", type);
        param.put("title", title);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("excludeType", GoodsType.GOODS_CROWD_FUND.getCode());
        return this.listWithCount(param, page);
    }


    /**
     * 统计列表
     * @param targetId 目标编号
     * @param title 题目
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(String targetId, String title, Integer type, String startTime, String endTime, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("targetId", targetId);
        param.put("type", type);
        param.put("title", title);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        return this.listWithCount(param, page);
    }


    /**
     * 统计列表
     * @param type 类型
     * @param title 题目
     * @param distributorName 分销名
     * @param page 分页
     * @return 统计列表
     */
    @Override
    public List<WithCount> listWithCount(Integer type, String title, String distributorName, String startTime, String endTime, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("type", type);
        param.put("title", title);
        param.put("distributorName", distributorName);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        return this.listWithCount(param, page);
    }

    /**
     * 根据父编号查询分销列表
     * @param parentId 父编号
     * @param targetId 目标编号
     * @param title 题目
     * @param distributorName 分销商名称
     * @param type 类型
     * @param page 分页参数
     * @return 分销列表
     */
    @Override
    public List<WithCount> findByParentId(String parentId, String targetId, String title, String distributorName,
                                          Integer type, String startTime, String endTime, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("parentId", parentId);
        param.put("targetId", targetId);
        param.put("type", type);
        param.put("title", title);
        param.put("distributorName", distributorName);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("excludeType", GoodsType.GOODS_CROWD_FUND.getCode());
        return distributorRelationReadDao.findByParentId(param, page);
    }

    /**
     * 根据父编号查询分销列表
     * @param parentId 父编号
     * @param targetId 目标编号
     * @return 分销列表
     */
    @Override
    public List<WithCount> findByParentId(String parentId, String targetId) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("parentId", parentId);
        param.put("targetId", targetId);
        param.put("excludeType", GoodsType.GOODS_CROWD_FUND.getCode());
        return distributorRelationReadDao.findByParentId(param, null);
    }

    /**
     * 递归查询是否分销过
     * @param parentId 上级编号
     * @param targetId 分销目标
     * @param distributorId 分销者
     * @return 是否（true/false）
     */
    @Override
    public boolean hasDistribution(String parentId, String targetId, String distributorId) {
        boolean result = false;
       /* DistributorRelation distributorRelation = this.findByParentId(parentId, targetId);
        if (null != distributorRelation){
            if (distributorRelation.getParentId().equals(distributorId)){
                result = true;
            }
            else {
                hasDistribution(distributorRelation.getParentId(), targetId, distributorId);
            }
        }*/
       return result;
    }

    /**
     * 根据事件编号查询
     * @param eventId 事件编号
     * @return 代言数
     */
    @Override
    public Integer countForEvent(String eventId) {
        return distributorRelationReadDao.countForEvent(eventId);
    }
}
