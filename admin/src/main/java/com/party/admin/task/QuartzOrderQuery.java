package com.party.admin.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.party.admin.biz.payquery.OrderQueryBizService;
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.query.TradeStatus;

/**
 * 订单查询校正
 * 
 * @author Administrator
 *
 */
@Component(value = "quartzOrderQuery")
public class QuartzOrderQuery {

	@Autowired
	IOrderFormService orderFormService;
	@Autowired
	OrderQueryBizService orderQueryBizService;
	@Autowired
	IActivityService activityService;

	protected static Logger logger = LoggerFactory.getLogger(QuartzOrderQuery.class);

	/**
	 * 检查交易状态为空的订单.更新众筹项目订单标记为众筹项目
	 * 
	 * @throws Exception
	 */
	public void orderQueryUpdateBusiness() {
		logger.info("开始对账~~~~~");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeState", "1");
		params.put("otherMerchantId", "1");
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		List<OrderForm> exportOrderForms = new ArrayList<OrderForm>();
		for (OrderForm orderForm : orderForms) {
			try {
				boolean flag = true;
				if (orderForm.getPayment().equals(new Float(0.0))) {
					flag = false;
					OrderForm t = orderFormService.get(orderForm.getId());
					if (StringUtils.isEmpty(t.getMerchantId())) {
						t.setMerchantId(TradeStatus.FREE.getCode());
						orderFormService.update(t);
					}
				} else if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
					Activity activity = activityService.get(orderForm.getGoodsId());
					if (activity == null) {
						flag = false;
					} else if (activity != null && activity.getIsCrowdfunded() != null && activity.getIsCrowdfunded().equals(YesNoStatus.YES.getCode())) {
						flag = false;
						OrderForm t = orderFormService.get(orderForm.getId());
						if (StringUtils.isEmpty(t.getMerchantId())) {
							t.setMerchantId(TradeStatus.ZCXM.getCode());
							orderFormService.update(t);
						}
					}
				}
				if (flag) {
					orderQueryBizService.doUpdateBusiness(orderForm, exportOrderForms);
				}
			} catch (Exception e) {
				logger.error("订单对账错误", e);
			}
		}

		try {
			orderQueryBizService.exportExcel(exportOrderForms, "d:/excel");
		} catch (Exception e) {
			logger.error("订单对账生成Excel错误", e);
		}
	}
}
