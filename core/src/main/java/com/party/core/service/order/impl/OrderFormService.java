package com.party.core.service.order.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.dao.read.activity.ActivityReadDao;
import com.party.core.dao.read.member.MemberActReadDao;
import com.party.core.dao.read.order.OrderFormReadDao;
import com.party.core.dao.write.order.OrderFormWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.order.IOrderFormService;
import com.sun.istack.NotNull;

/**
 * 商品订单服务实现
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Service
public class OrderFormService implements IOrderFormService {

    @Autowired
    OrderFormReadDao orderFormReadDao;

    @Autowired
    OrderFormWriteDao orderFormWriteDao;
    
    @Autowired
    ActivityReadDao activityReadDao;
    
    @Autowired
    MemberActReadDao memberActReadDao;


    /**
     * 商品订单插入
     * @param orderForm 商品订单
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(OrderForm orderForm) {
        BaseModel.preInsert(orderForm);
        orderForm.setIsPay("0");
        boolean result = orderFormWriteDao.insert(orderForm);
        if (result){
            return orderForm.getId();
        }
        return null;
    }

    /**
     * 商品订单更新
     * @param orderForm 商品订单
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(OrderForm orderForm) {
//        orderForm.setUpdateDate(new Date());
        return orderFormWriteDao.update(orderForm);
    }

    /**
     * 逻辑删除商品订单
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return orderFormWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除商品订单
     * @param id 实体主键
     * @return 删除结果
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return orderFormWriteDao.delete(id);
    }


    /**
     * 批量逻辑删除订单信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return orderFormWriteDao.batchDeleteLogic(ids);
    }


    /**
     * 批量物理删除订单信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return orderFormWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取订单信息
     * @param id 主键
     * @return 订单信息
     */
    @Override
    public OrderForm get(String id) {
        return orderFormReadDao.get(id);
    }


    /**
     * 分页查询订单信息
     * @param orderForm 订单信息
     * @param page 分页信息
     * @return 订单列表
     */
    @Override
    public List<OrderForm> listPage(OrderForm orderForm, Page page) {
        return orderFormReadDao.listPage(orderForm, page);
    }

    /**
     * 查询所有订单信息
     * @param orderForm 订单信息
     * @return 订单列表
     */
    @Override
    public List<OrderForm> list(OrderForm orderForm) {
        return orderFormReadDao.listPage(orderForm, null);
    }

    /**
     * 批量查询订单信息
     * @param ids 主键集合
     * @param orderForm 订单信息
     * @param page 分页信息
     * @return 订单列表
     */
    @Override
    public List<OrderForm> batchList(@NotNull Set<String> ids, OrderForm orderForm, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("status", orderForm.getStatus());
        param.put("title", orderForm.getTitle());
        return orderFormReadDao.batchList(ids, param, page);
    }
	
	@Override
	public List<OrderForm> getOrder(String goodsId, String memberId, Boolean isPay) {
		Set<Integer> status = new HashSet<Integer>();
		if (isPay) {
			status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 付款成功
		} else {
			status.add(OrderStatus.ORDER_STATUS_REFUND.getCode()); // 退款
			status.add(OrderStatus.ORDER_STATUS_OTHER.getCode()); // 其他
		}
		return orderFormReadDao.getOrderSuccess(goodsId, memberId, isPay, status);
	}
	
	/**
	 * 计算下单份数
	 * 
	 * @param goodsId
	 *            商品id
	 * @param memberId
	 *            用户id
	 * @param isPay
	 *            true表示查已支付的订单 false表示查该商品的有效订单
	 * @return
	 */
	@Override
	public int calculateBuyNum(String goodsId, String memberId, Boolean isPay, Integer type) {
		int total = 0;
		if (type == OrderType.ORDER_ACTIVITY.getCode()) {
			MemberAct memberAct = new MemberAct();
			memberAct.setActId(goodsId);
			memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			Set<Integer> status = new HashSet<Integer>();
			status.add(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode());
			status.add(ActStatus.ACT_STATUS_CANCEL.getCode());
			status.add(ActStatus.ACT_STATUS_NO_JOIN.getCode());
			List<MemberAct> list = memberActReadDao.getSuccessMemberAct(memberAct, status);
			total = list.size();
		} else if (type == OrderType.ORDER_NOMAL.getCode()){
			List<OrderForm> orderForms = getOrder(goodsId, memberId, isPay);
			for (OrderForm order : orderForms) {
				total += order.getUnit();
			}
			return total;
		}
		return total;
	}
	
	/**
	 * 计算下单份数
	 * 
	 * @param goodsId
	 *            商品id
	 * @param memberId
	 *            用户id
	 * @param isPay
	 *            true表示查已支付的订单 false表示查该商品的有效订单
	 * @return
	 */
	@Override
	public int calculateBuyNum(String goodsId, String memberId, Boolean isPay, Integer type, Set<Integer> status) {
		int total = 0;
		if (type == OrderType.ORDER_ACTIVITY.getCode()) {
			MemberAct memberAct = new MemberAct();
			memberAct.setActId(goodsId);
			memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			status = new HashSet<Integer>();
			status.add(ActStatus.ACT_STATUS_CANCEL.getCode());
			status.add(ActStatus.ACT_STATUS_NO_JOIN.getCode());
			List<MemberAct> list = memberActReadDao.getSuccessMemberAct(memberAct, status);
			total = list.size();
		} else if (type == OrderType.ORDER_NOMAL.getCode()){
			if (isPay) {
				status = new HashSet<Integer>();
				status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 付款成功
			}
			List<OrderForm> orderForms = orderFormReadDao.getOrderSuccess(goodsId, memberId, isPay, status);
			for (OrderForm order : orderForms) {
				total += order.getUnit();
			}
			return total;
		}
		return total;
	}

	@Override
	public List<OrderForm> listPage(OrderForm orderForm, Page page, Set<Integer> status) {
		return orderFormReadDao.listPage(orderForm, status, page);
	}

	@Override
	public List<OrderForm> webListPage(OrderForm orderForm, Map<String, Object> params, Page page) {
		return orderFormReadDao.webListPage(orderForm, params, page);
	}

	/**
	 * 作废订单列表
	 * @return 作废订单列表
	 */
	@Override
	public List<OrderForm> invalidList() {
		OrderForm orderForm = new OrderForm();
		Date endTime = DateUtils.addHour(new Date(), -2);
		orderForm.setEndDate(endTime);
		orderForm.setExcludeType(2);
		Set<Integer> status = Sets.newHashSet();
		status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
		status.add(OrderStatus.ORDER_STATUS_IN_REVIEW.getCode());

		List<OrderForm> orderFormList = orderFormReadDao.listPage(orderForm, status, null);
		return orderFormList;
	}

	@Override
	public Double getTotalPayment(OrderForm orderForm, Map<String, Object> params) {
		return orderFormReadDao.getTotalPayment(orderForm, params);
	}

	/**
	 * 分销订单
	 * @param orderForm 订单信息
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 订单列表
	 */
	@Override
	public List<OrderForm> distributorListPage(OrderForm orderForm, Map<String, Object> params, Page page) {
		return orderFormReadDao.distributorListPage(orderForm, params, page);
	}

	/**
	 * 统计每天的下单量
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 统计结果
	 */
	@Override
	public List<HashMap<String, Integer>> countByDate(String startDate, String endDate) {
		HashMap<String, Object> parameter = Maps.newHashMap();
		parameter.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
		parameter.put("startDate", startDate);
		parameter.put("endDate", endDate);
		parameter.put("payment", 0);
		parameter.put("isCrowdfund", Constant.NOT_CROWDFUNDED);
		return orderFormReadDao.countByDate(parameter);
	}


	/**
	 * 查询所有订单统计数
	 * @return 统计结果
	 */
	@Override
	public Integer allCount() {
		HashMap<String, Object> parameter = Maps.newHashMap();
		parameter.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
		parameter.put("payment", 0);
		parameter.put("isCrowdfund", Constant.NOT_CROWDFUNDED);
		return orderFormReadDao.count(parameter);
	}

	@Override
	public OrderForm getOrderByCondition(OrderForm t, Map<String, Object> params) {
		return orderFormReadDao.getOrderByCondition(t, params);
	}
}
