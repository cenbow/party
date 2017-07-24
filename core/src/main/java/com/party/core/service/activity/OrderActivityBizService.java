package com.party.core.service.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.party.core.model.activity.Activity;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.impl.ActivityService;
import com.party.core.service.order.impl.OrderFormService;

/**
 * 活动订单业务接口
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = false)
public class OrderActivityBizService {

	@Autowired
	private OrderFormService orderFormService;
	
	@Autowired
	private IActivityService activityService;

	/**
	 * 更新活动订单状态
	 * 
	 * @param orderId
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void updateActivityOrderStatus(String orderId, int status) {
		OrderForm orderForm = orderFormService.get(orderId);

		orderForm.setUpdateDate(new Date());
		orderForm.setStatus(status);
		orderFormService.update(orderForm);
	}

	/**
	 * 活动报名审核通过，生成订单
	 * 
	 * @param memberAct
	 * @param orderStatus
	 */
	@Transactional(readOnly = false)
	public String insertOrderForm(String goodsTitle, MemberAct memberAct, Integer orderStatus) {
		OrderForm orderForm = new OrderForm();
		orderForm.setGoodsId(memberAct.getActId()); // 活动
		orderForm.setMemberId(memberAct.getMemberId()); // 报名用户
		orderForm.setLinkman(memberAct.getName()); // 联系人
		orderForm.setPhone(memberAct.getMobile()); // 手机号
		orderForm.setType(OrderType.ORDER_ACTIVITY.getCode()); // 类型为活动
		orderForm.setStatus(orderStatus); // 待支付
		orderForm.setIsPay(PaymentState.NO_PAY.getCode()); // 未付款
		orderForm.setUnit(1); // 份数为1份
		orderForm.setPayment(memberAct.getPayment());
		orderForm.setTitle(goodsTitle);
		Activity activity = activityService.get(memberAct.getActId());
		orderForm.setInitiatorId(activity.getMember());
		orderFormService.insert(orderForm);

		return orderForm.getId();
	}
	
	/**
	 * 更新活动信息 joinNum limitNum
	 * @param activityId
	 */
	public Activity updateActivityInfo(String activityId){
		Activity activity = activityService.get(activityId);
		int joinNum = orderFormService.calculateBuyNum(activityId, null, false, OrderType.ORDER_ACTIVITY.getCode());
		boolean flag = false;
		if (activity.getLimitNum() == null) {
			activity.setLimitNum(0);
			flag = true;
		}
		if (activity.getJoinNum() == null) {
			activity.setJoinNum(0);
			flag  = true;
		} else if (!activity.getJoinNum().equals(joinNum)) {
			activity.setJoinNum(joinNum);
			flag = true;
		}
		
		if (flag) {
			activityService.update(activity);
		}
		return activity;
	}
}
