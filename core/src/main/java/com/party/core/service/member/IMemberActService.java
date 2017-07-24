package com.party.core.service.member;

import com.party.common.paging.Page;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.MemberAct;
import com.party.core.model.member.WithBuyer;
import com.party.core.model.order.OrderForm;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 活动报名服务接口
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public interface IMemberActService extends IBaseService<MemberAct> {

    /**
     * 根据会员和活动主键获取报名记录
     * @param memberId 会员编号
     * @param activityId 活动编号
     * @return 报名记录
     */
    MemberAct findByMemberAct(String memberId, String activityId);

    /**
     * 根据 商铺商品编号查询报名
     * @param id 商铺商品编号
     * @param page 分页参数
     * @return 报名列表
     */
    List<MemberAct> listByStoreGoodsId(@NotNull String id, Page page);
    
    /**
     * 查询当前用户发布的活动的报名数据
     * @param memberAct
     * @param page
     * @return
     */
    List<MemberAct> activityMemberActs(MemberAct memberAct, Page page);

    /**
     * 查询报名数
     * @param activityId 活动编号
     * @return 报名数
     */
    List<MemberAct> findMemberNum(String activityId);

    /**
     * 根据主键集合批量查询列表
     * @param ids 主键集合
     * @param applyWithActivity 查询参数
     * @param page 分页信息
     * @return 实体列表
     */
    List<ApplyWithActivity> batchWithActivityList(Set<String> ids, ApplyWithActivity applyWithActivity, Page page);
    
    /**
     * web端
     * @param memberAct
     * @param params
     * @param page
     * @return
     */
    List<MemberAct> listPage(MemberAct memberAct, Map<String, Object> params, Page page);

	MemberAct findByOrderId(String orderId);

    /**
     * 报名信息列表
     * @param withBuyer 报名信息
     * @param page 分页参数
     * @return 报名信息列表
     */
    List<WithBuyer> withBuyerList(WithBuyer withBuyer, Page page);

    /**
     * 取消报名
     * @param orderForm 订单信息
     */
    void cancel(OrderForm orderForm);
    
    List<MemberAct> listPageTwo(MemberAct memberAct, Page page);

    /**
     * 统计每天的报名量
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数
     */
    List<HashMap<String, Integer>> countByDate(String startDate, String endDate);

    /**
     * 所有待处理订单
     * @return 待处理订单
     */
    Integer onHandCount();

    /**
     * 所有报名
     * @return 统计数
     */
    Integer allCount();
    /**
     * 报名详情
     * @param withBuyer 报名信息
     * @param page 分页参数
     * @return 报名信息列表
     */
    List<WithBuyer> withActivityList(WithBuyer withBuyer,Map<String, Object> params, Page page);

    /**
     * 获取有效的报名信息
     * @param memberAct
     * @param status
     * @return
     */
	List<MemberAct> getSuccessMemberAct(MemberAct memberAct, Set<Integer> status);

	Integer getSuccessMemberAct(Map<String, Object> params);
}
