package com.party.core.service.order;

import com.party.core.model.order.OrderFormInfo;
import com.party.core.service.IBaseService;

/**
 * 订单附属信息
 * 
 * @author Administrator
 *
 */
public interface IOrderFormInfoService extends IBaseService<OrderFormInfo> {

	OrderFormInfo findByOrderId(String orderId);

	void saveOrderFormInfo(String appid, String mchId, String apiKey, String orderFormId);
}
