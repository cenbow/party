package com.party.admin.biz.activity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.query.TradeStatus;

/**
 * 订单逻辑业务服务 party Created by wei.li on 2016/10/8 0008.
 */

@Service
public class OrderBizService {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	IMemberActService memberActService;

	protected static Logger logger = LoggerFactory.getLogger(OrderBizService.class);

	/**
	 * 作废订单
	 * 
	 * @param orderForm
	 *            订单
	 */
	@Transactional
	public void invalid(OrderForm orderForm) {
		orderForm.setStatus(OrderStatus.ORDER_STATUS_OTHER.getCode());
		orderFormService.update(orderForm);

		// 如果是活动订单
		if (OrderType.ORDER_ACTIVITY.getCode().equals(orderForm.getType())) {
			// 取消报名
			memberActService.cancel(orderForm);

		} else if (OrderType.ORDER_NOMAL.getCode().equals(orderForm.getType()) || OrderType.ORDER_CUSTOMIZED.getCode().equals(orderForm.getType())) {
			// 释放库存
			goodsService.releaseInventory(orderForm);
		}
	}
	
	/**
	 * 订单总额
	 * @param params
	 * @param orderForm 
	 * @return
	 */
	public Double getOrderTotal(OrderForm orderForm, Map<String, Object> params){
		Map<String, Object> mm = params;
		Set<Integer> status = new HashSet<Integer>();
		status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
		mm.put("status", status);
		return orderFormService.getTotalPayment(orderForm, params);
	}
	
	/**
	 * 查询参数拼接
	 */
	public Set<String> searchParams(OrderForm orderForm, String tradeStatus, boolean flag, Map<String, Object> params) {
		Set<String> tradeStatusSet = new HashSet<String>();
		if (StringUtils.isNotEmpty(tradeStatus)) {
			switch (Integer.valueOf(tradeStatus)) {
			case 1:
				tradeStatusSet.add(TradeStatus.WX_SUCCESS.getCode());
				tradeStatusSet.add(TradeStatus.ALI_TRADE_SUCCESS.getCode());
				break;
			case 2:
				tradeStatusSet.add(TradeStatus.WX_NOTPAY.getCode());
				tradeStatusSet.add(TradeStatus.ALI_WAIT_BUYER_PAY.getCode());
				break;
			case 3:
				tradeStatusSet.add(TradeStatus.WX_REFUND.getCode());
				tradeStatusSet.add(TradeStatus.ALI_TRADE_CLOSED.getCode());
				orderForm.setStatus(OrderStatus.ORDER_STATUS_REFUND.getCode());
				break;
			case 4:
				tradeStatusSet.add(TradeStatus.WX_ORDERNOTEXIST.getCode());
				tradeStatusSet.add(TradeStatus.ALI_TRADE_NOT_EXIST.getCode());
				break;
			default:
				break;
			}
		}
		if (tradeStatusSet.size() > 0) {
			params.put("tradeStatus", tradeStatusSet);
		}

		if (flag == false) {
			Set<Integer> status = new HashSet<Integer>();
			status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
			status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
			status.add(OrderStatus.ORDER_STATUS_REFUND.getCode());
			params.put("status", status);
		}
		return tradeStatusSet;
	}

}
