package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.dao.read.crowdfund.SupportReadDao;
import com.party.core.dao.write.crowdfund.SupportWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportCount;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.order.OrderStatus;
import com.party.core.service.crowdfund.ISupportService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 众筹支持服务接口实现
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 18:53
 */
@Service
public class SupportService implements ISupportService{

    @Autowired
    private SupportReadDao supportReadDao;

    @Autowired
    private SupportWriteDao supportWriteDao;

    /**
     * 插入众筹支持
     * @param support 支持信息
     * @return 支持编号
     */
    @Override
    public String insert(Support support) {
        BaseModel.preInsert(support);
        boolean result = supportWriteDao.insert(support);
        if (result){
            return support.getId();
        }
        return null;
    }

    /**
     * 更新众筹支持
     * @param support 支持信息
     * @return 更新结果(true/false)
     */
    @Override
    public boolean update(Support support) {
        return supportWriteDao.update(support);
    }

    /**
     * 逻辑删除众筹支持
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return supportWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除众筹支持
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return supportWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除众筹支持
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return supportWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除众筹支持
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return supportWriteDao.batchDelete(ids);
    }

    /**
     * 根据编号获取众筹支持
     * @param id 主键
     * @return 众筹支持信息
     */
    @Override
    public Support get(String id) {
        return supportReadDao.get(id);
    }

    /**
     * 分页查询众筹支持
     * @param support 众筹支持
     * @param page 分页信息
     * @return 众筹支持列表
     */
    @Override
    public List<Support> listPage(Support support, Page page) {
        return supportReadDao.listPage(support, page);
    }

    /**
     * 查询所有众筹支付
     * @param support 众筹支持
     * @return 众筹支持列表
     */
    @Override
    public List<Support> list(Support support) {
        return supportReadDao.listPage(support, null);
    }

    /**
     * 批量查询众筹支持
     * @param ids 主键集合
     * @param support 众筹支持
     * @param page 分页信息
     * @return 众筹支持列表
     */
    @Override
    public List<Support> batchList(@NotNull Set<String> ids, Support support, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return supportReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 支持列表（带支持者信息）
     * @param supportWithMember 支持信息
     * @param page 分页参数
     * @return 众筹支持列表
     */
    @Override
    public List<SupportWithMember> listWithMember(SupportWithMember supportWithMember, Page page) {
        return supportReadDao.listWithMember(supportWithMember, page);
    }

    /**
     * 根据订单号查询支持信息
     * @param orderId 订单编号
     * @return 支持信息
     */
    @Override
    public Support findByOrderId(String orderId) {
        return supportReadDao.findByOrderId(orderId);
    }

    /**
     * 根据项目编号查询支持列表
     * @param ids 项目编号集合
     * @return 支持列表
     */
    @Override
    public List<Support> findByProjectIds(Set<String> ids) {
        return supportReadDao.list(ids, Maps.newHashMap());
    }

    /**
     * 根据分销关系查询支持列表
     * @param relationId 分销关系
     * @return 支持列表
     */
    @Override
    public List<Support> findByRelationId(String relationId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("relationId", relationId);
        return supportReadDao.list(Sets.newHashSet(), param);
    }

    /**
     * 我支持的金额
     * @param favorerId 支持者编号
     * @param projectId 项目编号
     * @return 支持金额
     */
    @Override
    public Float myFavorerAmount(String favorerId, String projectId) {
        //我支持的金额
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setFavorerId(favorerId);
        supportWithMember.setProjectId(projectId);
        supportWithMember.setPayStatus(Constant.IS_SUCCESS);
        List<SupportWithMember> supportWithMemberList = this.listWithMember(supportWithMember, null);
        Float myFavorerAmount = 0f;
        for (SupportWithMember s : supportWithMemberList){
            myFavorerAmount = BigDecimalUtils.add(myFavorerAmount, s.getPayment());
            myFavorerAmount = BigDecimalUtils.round(myFavorerAmount, 2);
        }
        return myFavorerAmount;
    }

    /**
     * 根据项目编号查询支持
     * @param projectId 项目编号
     * @return 支持列表
     */
    @Override
    public List<SupportWithMember> findByProjectId(String projectId) {
        SupportWithMember supportWithMember = new SupportWithMember();

        supportWithMember.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        supportWithMember.setProjectId(projectId);
        return supportReadDao.listWithMember(supportWithMember, null);
    }

    /**
     * 支持列表
     * @param supportWithMember 支持信息
     * @param page 分页参数
     * @return 支持列表
     */
    @Override
    public List<SupportWithMember> distributorList(SupportWithMember supportWithMember, Page page) {
        return supportReadDao.distributorList(supportWithMember, page);
    }

    /**
     * 众筹支持是否退完
     * @param projectId 项目编号
     * @return 是否
     */
    @Override
    public boolean isRefundAll(String projectId) {
        List<SupportWithMember> supportList = this.findByProjectId(projectId);
        if (CollectionUtils.isEmpty(supportList)){
            return true;
        }
        return false;
    }

    /**
     * 众筹的支持金额
     * @param projectId 众筹编号
     * @return 众筹总额
     */
    @Override
    public Float sumByProjectId(String projectId) {
        Float num = supportReadDao.sumByProjectId(projectId);
        num = num == null ? 0 : num;
        return num;
    }

    /**
     * 所有支持
     * @param projectId 项目编号
     * @return 支持列表
     */
    @Override
    public List<Support> findSupportByProjectId(String projectId) {
        Support support = new Support();
        support.setProjectId(projectId);
        return this.list(support);
    }

    /**
     * 统计支持者数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param projectId 项目编号
     * @return 统计结果
     */
    @Override
    public List<SupportCount> count(String startTime, String endTime, String projectId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("projectId", projectId);
        return supportReadDao.count(param);
    }

    /**
     * 总数
     * @param endTime 结束时间
     * @param projectId 项目编号
     * @return 统计总数
     */
    @Override
    public Float countAll(String endTime, String projectId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("endTime", endTime);
        param.put("projectId", projectId);
        Float sum = supportReadDao.countAll(param);
        return null == sum ? 0 : sum;
    }
}
