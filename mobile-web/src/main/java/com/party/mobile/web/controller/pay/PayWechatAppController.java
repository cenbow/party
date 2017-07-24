package com.party.mobile.web.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.constant.Constant;
import com.party.common.utils.HttpServletRequestUtils;
import com.party.common.utils.PartyCode;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.biz.wechat.WechatPayBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.appAlipay.input.AppPayConstant;
import com.party.mobile.web.dto.wechat.output.NotifyResponse;
import com.party.mobile.web.dto.wechat.output.UnifiedOrderRequest;
import com.party.mobile.web.utils.VerifyCodeUtils;
import com.party.mobile.web.utils.WechatPayUtils;
import com.party.pay.model.pay.wechat.NotifyRequest;
import com.party.pay.model.query.TradeStatus;

/**
 * 
 * 此类描述的是：微信APP支付控制层
 * 
 * @author: Administrator
 * @version: 2017年2月4日 下午6:25:48
 */
@Controller
@RequestMapping(value = "/pay/wechat/app")
public class PayWechatAppController {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	IActivityService activityService;

	@Autowired
	WechatPayBizService wechatPayBizService;

	@Autowired
	OrderBizService orderFormBizService;
	
	@Autowired
	IOrderFormInfoService orderFormInfoService;

	// 微信开发者ID
	@Value("#{pay_wechat_app['wechat.app.appId']}")
	private String appId;

	// 微信商户平台商户号
	@Value("#{pay_wechat_app['wechat.app.mchId']}")
	private String mchId;

	// 微信支付回调地址
	@Value("#{pay_wechat_app['wechat.app.notifyUrl']}")
	private String notifyUrl;

	// 微信签名令牌
	@Value("#{pay_wechat_app['wechat.app.apiKey']}")
	private String apiKey;

	protected static Logger logger = LoggerFactory.getLogger(PayWechatAppController.class);

	/**
	 * 获取微信支付请求参数
	 * 
	 * @param OrderFormId
	 *            订单号
	 * @param request
	 *            请求参数
	 * @return
	 */
	@Authorization
	@ResponseBody
	@RequestMapping("getPayData")
	public AjaxResult getPayData(String OrderFormId, HttpServletRequest request) {

		// 数据验证
		if (Strings.isNullOrEmpty(OrderFormId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "订单号不能为空");
		}

		// 订单验证
		OrderForm orderForm = orderFormService.get(OrderFormId);
		if (null == orderForm) {
			return AjaxResult.error(PartyCode.ORDER_UNEXIST, "订单不存在");
		}

		// 订单状态验证
		if (!orderForm.getIsPay().equals(PaymentState.NO_PAY.getCode()) || orderForm.getStatus() != OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()) {
			return AjaxResult.error(PartyCode.PAYMENT_STATUS_ERROR, "订单状态不正确");
		}

		// 统一下单
		String nonceStr = VerifyCodeUtils.RandomString(32);// 获取随机数
		String ipAddress = HttpServletRequestUtils.getRemoteIpAddress(request);// 获取ip地址

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			Activity activity = activityService.get(orderForm.getGoodsId());
			unifiedOrderRequest.setBody(activity.getTitle());
		} else {
			Goods goods = goodsService.get(orderForm.getGoodsId());
			unifiedOrderRequest.setBody(goods.getTitle());
		}

		unifiedOrderRequest.setAppid(appId);// 开放平台编号
		unifiedOrderRequest.setMchId(mchId);// 商户编号
		unifiedOrderRequest.setNonceStr(nonceStr);// 随机数
		unifiedOrderRequest.setNotifyUrl(notifyUrl);// 回调地址
		unifiedOrderRequest.setSpbillCreateIp(ipAddress);// 请求IP
		unifiedOrderRequest.setTradeType("APP");// 交易类型
		unifiedOrderRequest.setOutTradeNo(orderForm.getId());// 订单号
		unifiedOrderRequest.setTotalFee((int) (orderForm.getPayment() * 100));// 单位为分
		unifiedOrderRequest.setApikey(apiKey);

		logger.info("微信APP发起支付请求参数:{}", JSONObject.toJSON(unifiedOrderRequest));
		return AjaxResult.success(unifiedOrderRequest);
	}

	/**
	 * 获取微信支付异步通知
	 * 
	 * @param request
	 *            请求参数
	 * @return 返回参数
	 */
	@ResponseBody
	@RequestMapping("acceptNotify")
	public String acceptNotify(HttpServletRequest request) {
		String requestStr = wechatPayBizService.getNotifyXml(request);
		logger.info("获取微信支付异步通知数据:{}", requestStr);

		if (Strings.isNullOrEmpty(requestStr)) {
			return wechatPayBizService.responseNotify(NotifyResponse.error("请求参数为空"));
		}
		NotifyRequest notifyRequest = WechatPayUtils.xmlToBean(requestStr, NotifyRequest.class);

		logger.info("报文转换成功");
		// 验证数据安全
		boolean verify = wechatPayBizService.verify(notifyRequest, apiKey, appId, mchId);

		logger.info("安全验证{}", verify);
		if (!verify) {
			return wechatPayBizService.responseNotify(NotifyResponse.error("安全验证不通过"));
		}

		// 支付结果验证
		if ((!AppPayConstant.SUCCESS.equals(notifyRequest.getReturnCode())) || (!AppPayConstant.SUCCESS.equals(notifyRequest.getResultCode()))) {
			logger.info("支付结果不成功");
			return wechatPayBizService.responseNotify(NotifyResponse.error("支付结果不成功"));
		}

		OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
		logger.info("订单查询{},{}", orderForm.getId(), orderForm.getIsPay());
		if (OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode().equals(orderForm.getStatus())){
			// 支付订单
			try {
				logger.info("准备更改状态");
				orderFormBizService.updatePayBusiness(orderForm, notifyRequest, PaymentWay.WECHAT_PAY.getCode());
			} catch (Exception e) {
				logger.error("订单支付异常", e);
				return wechatPayBizService.responseNotify(NotifyResponse.error("同行者支付失败"));
			}
		}
		return wechatPayBizService.responseNotify(NotifyResponse.success());
	}
	
	/**
	 * 更新订单来源
	 * 
	 * @param orderId
	 * @param apiKey
	 * @param appId
	 * @param mchId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateOrderFormInfo")
	public AjaxResult updateOrderFormInfo(String orderId, String apiKey, String appId, String mchId) {
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm != null) {
			// 平台
			orderForm.setTradeType(Constant.CLIENT_WX_APP);
			// 商户号
			orderForm.setMerchantId(mchId);
			// 支付类型
			orderForm.setPaymentWay(PaymentWay.WECHAT_PAY.getCode());
			orderFormService.update(orderForm);
		}
		if (orderForm != null) {
			orderFormInfoService.saveOrderFormInfo(appId, mchId, apiKey, orderId);
		}
		return AjaxResult.success();
	}
}
