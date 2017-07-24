package com.party.pay.service.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.party.common.utils.StringUtils;
import com.party.common.utils.VerifyCodeUtils;
import com.party.core.exception.OrderException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.query.TradeStatus;

/**
 * 支付回调处理
 * 
 * @author yy-pc
 *
 */
@Service
public class PayOrderBizService {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsCouponsService goodsCouponsService;

	@Autowired
	MemberService memberService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	IMemberActService memberActService;

	@Autowired
	IActivityService activityService;

	/**
	 * 免费商品或活动
	 * 
	 * @param orderId
	 * @param from 
	 * @param from 
	 * @throws OrderException
	 */
	@Transactional
	public void freeOrder(String orderId, String from) throws OrderException {
		/************* 更改业务状态 *****************/
		OrderForm orderForm = orderFormService.get(orderId);
		orderForm.setIsPay(PaymentState.IS_PAY.getCode()); // 已付款
		orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()); // 成功
		orderForm.setMerchantId(TradeStatus.FREE.getCode()); // 标识为0元订单
		orderFormService.update(orderForm);

		// 如果是活动支付成功，设置活动报名状态
		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			MemberAct dbMemberAct = memberActService.findByMemberAct(orderForm.getMemberId(), orderForm.getGoodsId());
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode()); // 已支付，报名成功
			// 从签到页面来，自动签到
            if (StringUtils.isNotEmpty(from) && from.equals("signin")) {
            	dbMemberAct.setSignin(YesNoStatus.YES.getCode());
			}
			memberActService.update(dbMemberAct);
		}

		/************* 生成核销码 *****************/
		List<GoodsCoupons> goodsCouponss = goodsCouponsService.findByOrderId(orderForm.getId());
		if (goodsCouponss.size() == 0) {
			List<String> verifyCodeList = getVerifyCode(orderForm.getId(), orderForm.getUnit());
			for (String verifyCode : verifyCodeList) {
				GoodsCoupons goodsCoupons = new GoodsCoupons();
				goodsCoupons.setOrderId(orderForm.getId());
				goodsCoupons.setMemberId(orderForm.getMemberId());
				goodsCoupons.setGoodsId(orderForm.getGoodsId());
				goodsCoupons.setVerifyCode(verifyCode);
				goodsCoupons.setIsSpending("0");
				goodsCoupons.setIsBookings("0");
				goodsCoupons.setType(orderForm.getType());
				goodsCoupons.setStatus(1);
				goodsCouponsService.insert(goodsCoupons);
			}
		}
	}
	
	/**
	 * 免费商品或活动
	 * 
	 * @param orderId
	 * @param from 
	 * @param from 
	 * @throws OrderException
	 */
	@Transactional
	public void freeOrder2(Integer type, String from, String goodsId, String currentUserId) throws OrderException {
		String businessIdTwo = ""; // 活动报名或者玩法预定ID
		// 如果是活动支付成功，设置活动报名状态
		if (type == OrderType.ORDER_ACTIVITY.getCode()) {
			MemberAct dbMemberAct = memberActService.findByMemberAct(currentUserId, goodsId);
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode()); // 已支付，报名成功
			// 从签到页面来，自动签到
            if (StringUtils.isNotEmpty(from) && from.equals("signin")) {
            	dbMemberAct.setSignin(YesNoStatus.YES.getCode());
			}
			memberActService.update(dbMemberAct);
			
			businessIdTwo = dbMemberAct.getId();
		}

		/************* 生成核销码 *****************/
		GoodsCoupons t = new GoodsCoupons();
		t.setGoodsId(goodsId);
		t.setMemberId(currentUserId);
		List<GoodsCoupons> goodsCouponss = goodsCouponsService.list(t);
		if (goodsCouponss.size() == 0) {
			List<String> verifyCodeList = getVerifyCode(businessIdTwo, 1);
			for (String verifyCode : verifyCodeList) {
				GoodsCoupons goodsCoupons = new GoodsCoupons();
				goodsCoupons.setOrderId("");
				goodsCoupons.setMemberId(currentUserId);
				goodsCoupons.setGoodsId(goodsId);
				goodsCoupons.setVerifyCode(verifyCode);
				goodsCoupons.setIsSpending("0");
				goodsCoupons.setIsBookings("0");
				goodsCoupons.setType(type);
				goodsCoupons.setStatus(1);
				goodsCouponsService.insert(goodsCoupons);
			}
		}
	}

	/**
	 * 更新预定成功人数
	 * 
	 * @param goodsId
	 * @param orderType
	 */
	@Transactional(readOnly = false)
	public void updateJoinNum(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		// 成功的订单
		List<OrderForm> orderForms = orderFormService.getOrder(orderForm.getGoodsId(), null, false);

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			// 更新活动报名成功人数
			Activity activity = activityService.get(orderForm.getGoodsId());
			activity.setJoinNum(orderForms.size());
			activityService.update(activity);
		} else if (orderForm.getType() == OrderType.ORDER_NOMAL.getCode() || orderForm.getType() == OrderType.ORDER_CUSTOMIZED.getCode()) {
			// 更新商品预定成功人数
			Goods goods = goodsService.get(orderForm.getGoodsId());
			goods.setJoinNum(orderForms.size());
			goodsService.update(goods);
		}
	}

	/**
	 * 处理支付业务
	 * 
	 * @param orderId
	 * @param from 
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updatePayBusiness(String orderId) throws Exception {
		freeOrder(orderId, null);
		updateJoinNum(orderId);
	}

	/**
	 * 生成核销码
	 * 
	 * @param orderFormId
	 *            订单号
	 * @param unit
	 *            订单份数
	 * @return 核销码列表
	 */
	public List<String> getVerifyCode(String orderFormId, Integer unit) {
		if (unit == 0) {
			throw new OrderException(10063, "生成核销码异常，订单数量为0");
		}

		List<String> verifyCodeList = Lists.newArrayList();
		for (int i = 0; i < unit; i++) {
			String verifyCode = VerifyCodeUtils.buildVerifyCode(orderFormId);
			if (StringUtils.isNotEmpty(verifyCode)) {
				verifyCodeList.add(verifyCode);
			}
		}
		return verifyCodeList;
	}
}
