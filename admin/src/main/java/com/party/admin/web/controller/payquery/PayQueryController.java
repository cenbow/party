package com.party.admin.web.controller.payquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.party.admin.biz.payquery.OrderQueryBizService;
import com.party.admin.biz.payquery.QueryBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.constant.Constant;
import com.party.common.utils.StringUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderTrade;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.query.AliPayQueryResponse;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.query.WechatPayQueryResponse;

@Controller
@RequestMapping("/payquery")
public class PayQueryController {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	OrderQueryBizService orderQueryBizService;

	@Autowired
	IOrderTradeService orderTradeService;

	@Autowired
	IActivityService activityService;

	@Autowired
	IOrderFormInfoService orderFormInfoService;
	
	@Autowired
	QueryBizService queryBizService;

	// 微信签名令牌
	@Value("#{pay_wechat_app['wechat.app.apiKey']}")
	private String app_apiKey;

	// 微信接口密钥
	@Value("#{pay_wechat_wwz['wechat.wwz.apiKey']}")
	private String wwz_apiKey;

	// 微信接口密钥
	@Value("#{pay_wechat_xcx['wechat.xcx.apiKey']}")
	private String xcx_apiKey;

	protected static Logger logger = LoggerFactory.getLogger(PayQueryController.class);

	/**
	 * 订单查询校正
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("orderQuery")
	public AjaxResult wechatOrderQuery(String orderId) throws Exception {
		if (StringUtils.isEmpty(orderId)) {
			return AjaxResult.error("订单编号不能为空");
		}

		List<OrderForm> exportOrderForms = new ArrayList<OrderForm>();

		OrderForm orderForm = orderFormService.get(orderId);
		
		Object responseObject = queryBizService.orderquery(orderForm.getId());
		if (responseObject != null) {
			if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
				WechatPayQueryResponse response = (WechatPayQueryResponse) responseObject;
				if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
					orderQueryBizService.wxCallback(response, orderForm, exportOrderForms);
					return AjaxResult.success();
				} else {
					orderForm.setTradeState(response.getErrCode());
					orderFormService.update(orderForm);
					return AjaxResult.error(response.getErrCode().equals("ORDERNOTEXIST") ? "交易不存在" : response.getErrCode());
				}
			} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
				AliPayQueryResponse response = (AliPayQueryResponse) responseObject;
				if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
					orderQueryBizService.aliCallback(response, orderForm, exportOrderForms);
					return AjaxResult.success();
				} else {
					orderForm.setTradeState(response.getSubCode());
					orderForm.setMerchantId(null);
					orderFormService.update(orderForm);
					return AjaxResult.error(response.getSubCode().equals("ACQ.TRADE_NOT_EXIST") ? "交易不存在" : response.getSubCode());
				}
			}
		}
		return AjaxResult.error("交易不存在");
	}

	/**
	 * 更新交易方式，商户号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("updateTradeType")
	public String updateTradeType() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		for (OrderForm orderForm : orderForms) {
			OrderTrade orderTrade = orderTradeService.findByOrderId(orderForm.getId());
			if (orderTrade != null) {
				Map<String, Object> dataMap = (Map<String, Object>) JSONObject.parse(orderTrade.getData());
				if (orderTrade.getType().equals(PaymentWay.WECHAT_PAY.getCode())) {
					String tradeType = dataMap.get("tradeType").toString();
					if (tradeType.equals(Constant.JSAPI)) {
						tradeType = Constant.CLIENT_WX_WWZ;
					} else if (tradeType.equals(Constant.APP)) {
						tradeType = Constant.CLIENT_WX_APP;
					}
					String mchId = dataMap.get("mchId").toString();
					OrderForm t = orderFormService.get(orderForm.getId());
					t.setTransactionId(orderTrade.getTransactionId());
					t.setPaymentWay(orderTrade.getType());
					t.setTradeType(tradeType);
					t.setMerchantId(mchId);
					orderFormService.update(t);
				} else if (orderTrade.getType().equals(PaymentWay.ALI_PAY.getCode())) {
					OrderForm t = orderFormService.get(orderForm.getId());
					t.setTransactionId(orderTrade.getTransactionId());
					t.setTradeType(Constant.CLIENT_ALI_APP);
					t.setPaymentWay(orderTrade.getType());
					orderFormService.update(t);
				}
			}
		}
		return null;
	}

	/**
	 * 查询所有的订单进行校正
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("orderQueryUpdateBusiness")
	public String orderQueryUpdateBusiness() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		// params.put("tradeState", "1");
		List<OrderForm> exportOrderForms = new ArrayList<OrderForm>();
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		for (OrderForm orderForm : orderForms) {
			orderQueryBizService.doUpdateBusiness(orderForm, exportOrderForms);
		}
		orderQueryBizService.exportExcel(exportOrderForms, "d:/excel");
		return null;
	}

	/**
	 * 更新0元业务订单，众筹项目订单MerchantId字段为Null
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("orderUpdateMerchantId")
	public String orderUpdateMerchantId() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "1");
		params.put("payment", 0);
		params.put("flag", true);
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		for (OrderForm orderForm : orderForms) {
			OrderForm t = orderFormService.get(orderForm.getId());
			boolean flag = false;
			if (orderForm.getPayment().equals(new Float(0.0))) {
				flag = true;
				t.setMerchantId(TradeStatus.FREE.getCode());
			} else if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
				Activity activity = activityService.get(orderForm.getGoodsId());
				if (activity.getIsCrowdfunded() == 1) {
					flag = true;
					t.setMerchantId(TradeStatus.ZCXM.getCode());
				}
			}
			if (flag) {
				t.setTradeState(null);
				t.setTradeType(null);
				t.setTransactionId(null);
				orderFormService.update(t);
			}
		}
		return null;
	}

	/**
	 * 查询所有的交易状态为空的订单进行校正
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("orderUpdateMerchantId2")
	public String orderUpdateMerchantId2() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		params.put("tradeState", "1");
		List<OrderForm> exportOrderForms = new ArrayList<OrderForm>();
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);
		for (OrderForm orderForm : orderForms) {
			orderQueryBizService.doUpdateBusiness(orderForm, exportOrderForms);
		}
		orderQueryBizService.exportExcel(exportOrderForms, "d:/excel");
		return null;
	}

	/**
	 * 数据迁移
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("orderUpdateOrderFormInfo")
	public String orderUpdateOrderFormInfo() throws Exception {
		List<OrderTrade> orderTrades = orderTradeService.list(new OrderTrade());
		for (OrderTrade orderTrade : orderTrades) {
			OrderForm orderForm = orderFormService.get(orderTrade.getOrderFormId());
			if (orderForm != null) {
				orderForm.setTransactionId(orderTrade.getTransactionId());
				orderFormService.update(orderForm);
			}
			if (orderTrade.getType().equals(PaymentWay.WECHAT_PAY.getCode())) {
				if (orderForm != null) {
					@SuppressWarnings("unchecked")
					Map<String, String> dataMap = (Map<String, String>) JSONObject.parse(orderTrade.getData());
					String apiKey = "";
					if (StringUtils.isNotEmpty(orderForm.getTradeType())) {
						if (orderForm.getTradeType().equals(Constant.CLIENT_WX_WWZ)) {
							apiKey = wwz_apiKey;
						} else if (orderForm.getTradeType().equals(Constant.CLIENT_WX_APP)) {
							apiKey = app_apiKey;
						} else if (orderForm.getTradeType().equals(Constant.CLIENT_WX_XCX)) {
							apiKey = xcx_apiKey;
						}
					}
					orderFormInfoService.saveOrderFormInfo(dataMap.get("appid"), dataMap.get("mchId"), apiKey, orderForm.getId());
				}
			}
		}
		return null;
	}
}
