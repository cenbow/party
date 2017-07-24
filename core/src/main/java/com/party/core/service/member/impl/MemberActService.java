package com.party.core.service.member.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.dao.read.member.MemberActReadDao;
import com.party.core.dao.write.member.MemberActWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.MemberAct;
import com.party.core.model.member.WithBuyer;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.service.member.IMemberActService;
import com.sun.istack.NotNull;

/**
 * 活动报名实现
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Service
public class MemberActService implements IMemberActService {

    @Autowired
    MemberActWriteDao memberActWriteDao;

    @Autowired
    MemberActReadDao memberActReadDao;

    /**
     * 活动报名插入
     * @param memberAct 活动报名
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(MemberAct memberAct) {
        BaseModel.preInsert(memberAct);
        boolean result = memberActWriteDao.insert(memberAct);
        if (result){
            return memberAct.getId();
        }
        return null;
    }

    /**
     * 活动报名更新
     * @param memberAct 活动报名
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(MemberAct memberAct) {
        memberAct.setUpdateDate(new Date());
        return memberActWriteDao.update(memberAct);
    }

    /**
     * 逻辑删除活动报名
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return memberActWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除活动报名
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return memberActWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除活动报名
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return memberActWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除活动报名
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return memberActWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取活动报名
     * @param id 主键
     * @return 活动报名信息
     */
    @Override
    public MemberAct get(String id) {
        return memberActReadDao.get(id);
    }


    /**
     * 根据会员和活动编号获取报名记录
     * @param memberId 会员编号
     * @param activityId 活动编号
     * @return 报名记录
     */
    @Override
    public MemberAct findByMemberAct(String memberId, String activityId) {
        MemberAct memberAct = new MemberAct();
        memberAct.setMemberId(memberId);
        memberAct.setActId(activityId);

        List<MemberAct> memberActList = this.list(memberAct);
        if (CollectionUtils.isEmpty(memberActList)){
            return null;
        }
        return memberActList.get(0);
    }

    /**
     * 分页查询活动报名
     * @param memberAct 活动报名
     * @param page 分页信息
     * @return 活动报名信息列表
     */
    @Override
    public List<MemberAct> listPage(MemberAct memberAct, Page page) {
        return memberActReadDao.listPage(memberAct, page);
    }

    /**
     * 报名信息列表
     * @param withBuyer 报名信息
     * @param page 分页参数
     * @return 报名信息列表
     */
    @Override
    public List<WithBuyer> withBuyerList(WithBuyer withBuyer, Page page) {
        return memberActReadDao.withBuyerList(withBuyer, page);
    }

    /**
     * 报名信息列表
     * @param withBuyer 报名信息
     * @param page 分页参数
     * @return 报名信息列表
     */
    @Override
    public List<WithBuyer> withActivityList(WithBuyer withBuyer, Map<String, Object> params, Page page) {
        return memberActReadDao.withActivityList(withBuyer, params, page);
    }

    /**
     * 查询所有活动报名信息
     * @param memberAct 活动报名信息
     * @return 活动报名信息列表
     */
    @Override
    public List<MemberAct> list(MemberAct memberAct) {
        return memberActReadDao.listPage(memberAct, null);
    }

    /**
     * 根据商铺商品编号查询报名信息
     * @param id 商铺商品编号
     * @return 报名列表
     */
    @Override
    public List<MemberAct> listByStoreGoodsId(@NotNull String id, Page page) {
        if (!Strings.isNullOrEmpty(id)){
            MemberAct memberAct = new MemberAct();
            memberAct.setStoreGoodsId(id);
            memberAct.setJoinin(YesNoStatus.YES.getCode());//报名
            List<MemberAct> memberActList = memberActReadDao.listPage(memberAct, page);
            return memberActList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 批量查询活动报名
     * @param ids 主键集合
     * @param memberAct 活动报名信息
     * @param page 分页信息
     * @return 活动报名信息列表
     */
    @Override
    public List<MemberAct> batchList(@NotNull Set<String> ids, MemberAct memberAct, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return memberActReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 批量查询活动报名
     * @param ids 主键集合
     * @param applyWithActivity 查询参数
     * @param page 分页信息
     * @return 活动报名信息列表
     */
    @Override
    public List<ApplyWithActivity> batchWithActivityList(Set<String> ids, ApplyWithActivity applyWithActivity, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("title", applyWithActivity.getActivityTitle());
        param.put("checkStatus", applyWithActivity.getCheckStatus());
        return memberActReadDao.batchWithActivityList(ids, param, page);
    }

    @Override
	public List<MemberAct> activityMemberActs(MemberAct memberAct, Page page) {
		return memberActReadDao.activityListPage(memberAct, page);
	}

    /**
     * 查询报名数
     * @param activityId 活动编号
     * @return 报名列表
     */
    @Override
    public List<MemberAct> findMemberNum(String activityId) {
        MemberAct memberAct = new MemberAct();
        memberAct.setActId(activityId);
        List<MemberAct> memberActList = this.list(memberAct);
        if (!CollectionUtils.isEmpty(memberActList)){
            return memberActList;
        }
        return Collections.EMPTY_LIST;
    }
    
    @Override
	public List<MemberAct> listPage(MemberAct memberAct, Map<String, Object> params, Page page) {
		return memberActReadDao.webListPage(memberAct, params, page);
	}

	@Override
	public MemberAct findByOrderId(String orderId) {
		return memberActReadDao.findByOrderId(orderId);
	}

    /**
     * 取消报名
     * @param orderForm 订单信息
     */
    @Override
    public void cancel(OrderForm orderForm) {
        MemberAct memberAct = this.findByOrderId(orderForm.getId());
        if (null != memberAct){
            memberAct.setCheckStatus(ActStatus.ACT_STATUS_CANCEL.getCode());
            memberAct.setJoinin(YesNoStatus.NO.getCode()); // 取消
            this.update(memberAct);
        }
    }
    
	@Override
	public List<MemberAct> listPageTwo(MemberAct memberAct, Page page) {
		return memberActReadDao.listPageTwo(memberAct, page);
	}


    /**
     * 统计每天的报名数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计结果
     */
    @Override
    public List<HashMap<String, Integer>> countByDate(String startDate, String endDate) {
        HashMap<String, Object> parameter = Maps.newHashMap();
        parameter.put("startDate", startDate);
        parameter.put("endDate", endDate);
        return memberActReadDao.countByDate(parameter);
    }

    /**
     * 所有待处理订单
     * @return 统计数
     */
    @Override
    public Integer onHandCount() {
        HashMap<String, Object> parameter = Maps.newHashMap();
        parameter.put("status", OrderStatus.ORDER_STATUS_IN_REVIEW.getCode());
        return memberActReadDao.count(parameter);
    }

    /**
     * 所有报名
     * @return 统计数
     */
    @Override
    public Integer allCount() {
        HashMap<String, Object> parameter = Maps.newHashMap();
        return memberActReadDao.count(parameter);
    }

    @Override
	public List<MemberAct> getSuccessMemberAct(MemberAct memberAct, Set<Integer> status) {
		return memberActReadDao.getSuccessMemberAct(memberAct, status);
	}

	public Integer getSuccessMemberAct(Map<String, Object> params) {
		Set<Integer> status = new HashSet<Integer>();
		status.add(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode());
        status.add(ActStatus.ACT_STATUS_CANCEL.getCode());
        status.add(ActStatus.ACT_STATUS_NO_JOIN.getCode());
        params.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
        params.put("status", status);
		return memberActReadDao.getSuccessMemberAct2(params);
	}
}
