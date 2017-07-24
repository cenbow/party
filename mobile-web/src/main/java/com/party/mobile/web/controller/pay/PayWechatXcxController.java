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
import com.party.common.utils.BigDecimalUtils;
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
import com.party.mobile.web.dto.wechat.input.UnifiedOrderResponse;
import com.party.mobile.web.dto.wechat.output.NotifyResponse;
import com.party.mobile.web.dto.wechat.output.PayRequest;
import com.party.mobile.web.dto.wechat.output.UnifiedOrderRequest;
import com.party.mobile.web.utils.VerifyCodeUtils;
import com.party.common.constant.WechatConstant;
import com.party.mobile.web.utils.WechatPayUtils;
import com.party.pay.model.pay.wechat.NotifyRequest;

/**
 * 
 * 此类描述的是：此类描述的是：微信小程序支付控制层
 * 
 * @author: Administrator
 * @version: 2017年2月5日 上午10:50:47
 */

@Controller
@RequestMapping(value = "/pay/wechat/xcx")
public class PayWechatXcxController {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	WechatPayBizService wechatPayBizService;

	@Autowired
	OrderBizService orderBizService;

	@Autowired
	IActivityService activityService;
	
	@Autowired
	IOrderFormInfoService orderFormInfoService;

	// 小程序编号
	@Value("#{pay_wechat_xcx['wechat.xcx.appId']}")
	private String appId;

	// 小程序商户号
	@Value("#{pay_wechat_xcx['wechat.xcx.mchId']}")
	private String mchId;

	// 小程序回调地址
	@Value("#{pay_wechat_xcx['wechat.xcx.notifyUrl']}")
	private String notifyUrl;

	// 微信接口密钥
	@Value("#{pay_wechat_xcx['wechat.xcx.apiKey']}")
	private String apiKey;

	protected static Logger logger = LoggerFactory.getLogger(PayWechatXcxController.class);

	@Authorization
	@ResponseBody
	@RequestMapping(value = "/getPayData")
	public AjaxResult getPayData(String orderId, String openId, HttpServletRequest request) {

		// 数据验证
		if (Strings.isNullOrEmpty(orderId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "订单号不能为空");
		}

		if (Strings.isNullOrEmpty(openId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "缺少支付参数 openId");
		}

		// 订单验证
		OrderForm orderForm = orderFormService.get(orderId);
		if (null == orderForm) {
			return AjaxResult.error(PartyCode.ORDER_UNEXIST, "订单不存在");
		}

		// 订单状态验证
		if (!orderForm.getIsPay().equals(PaymentState.NO_PAY.getCode()) || orderForm.getStatus() != OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()) {
			return AjaxResult.error(PartyCode.PAYMENT_STATUS_ERROR, "订单状态不正确");
		}
		// 统一下单
		String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
		String ipAddress = request.getRemoteAddr();// 终端IP

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode()) ) {
			Activity activity = activityService.get(orderForm.getGoodsId());
			String body = wechatPayBizService.subTitle(activity.getTitle(), 125);
			unifiedOrderRequest.setBody(body);// 商品描叙
		} else if (orderForm.getType().equals(OrderType.ORDER_NOMAL.getCode())
				|| orderForm.getType().equals(OrderType.ORDER_CUSTOMIZED.getCode())){
			Goods goods = goodsService.get(orderForm.getGoodsId());
			String body = wechatPayBizService.subTitle(goods.getTitle(), 125);
			unifiedOrderRequest.setBody(body);// 商品描叙
		}

		unifiedOrderRequest.setAppid(appId);// 开放平台编号
		unifiedOrderRequest.setMchId(mchId);// 商户编号
		unifiedOrderRequest.setOpenid(openId);// 用户编号
		unifiedOrderRequest.setNonceStr(nonceStr);// 随机数
		unifiedOrderRequest.setOutTradeNo(orderForm.getId());// 订单号
		double total = BigDecimalUtils.mul(orderForm.getPayment(), 100);
		total = BigDecimalUtils.round(total, 2);
		unifiedOrderRequest.setTotalFee((int)total);// 单位分
		unifiedOrderRequest.setSpbillCreateIp(ipAddress);// 请求IP
		unifiedOrderRequest.setNotifyUrl(notifyUrl);// 回调地址
		unifiedOrderRequest.setTradeType(WechatConstant.TRADE_TYPE);// 交易类型
		UnifiedOrderResponse unifiedorderResponse = wechatPayBizService.unifiedOrder(unifiedOrderRequest, apiKey);
		if (null == unifiedorderResponse) {
			return AjaxResult.error(PartyCode.UNIFIEDORDER_ERROR, "微信统一下单异常");
		}
		
		if (Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getResultCode())) {
			logger.info("微信统一下单成功，开始更改订单数据");
			// 平台
			orderForm.setTradeType(Constant.CLIENT_WX_XCX);
			// 商户号
			orderForm.setMerchantId(mchId);
			// 支付类型
			orderForm.setPaymentWay(PaymentWay.WECHAT_PAY.getCode());
			orderFormService.update(orderForm);
			
			// 保存订单附属信息
			orderFormInfoService.saveOrderFormInfo(appId, mchId, apiKey, orderForm.getId());
		}

		// 发起下单
		String prepayId = unifiedorderResponse.getPrepayId();
		PayRequest payRequest = new PayRequest();
		payRequest.setAppId(appId);// 开放平台编号
		payRequest.setTimeStamp(String.valueOf(System.currentTimeMillis()));// 时间戳
		payRequest.setNonceStr(nonceStr);// 随机数
		payRequest.setPackages(WechatConstant.PREPAY_ID + prepayId);// 预授权码
		payRequest.setSignType(WechatConstant.MD5_TYPE);// 加密方式

		// 获取签名
		String sign = WechatPayUtils.getSign(payRequest, apiKey);
		payRequest.setPaySign(sign);

		logger.info("微信小程序发起支付请求参数:{}", JSONObject.toJSON(payRequest));
		return AjaxResult.success(payRequest);
	}

	/**
	 * 获取微信支付异步通知 回调
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
		if ((!WechatConstant.SUCCESS.equals(notifyRequest.getReturnCode())) || (!WechatConstant.SUCCESS.equals(notifyRequest.getResultCode()))) {
			logger.info("支付结果不成功");
			return wechatPayBizService.responseNotify(NotifyResponse.error("支付结果不成功"));
		}

		OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
		logger.info("订单查询{},{}", orderForm.getId(), orderForm.getIsPay());
		if (OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode().equals(orderForm.getStatus())){
			// 支付订单
			try {
				logger.info("准备更改状态");
				orderBizService.updatePayBusiness(orderForm, notifyRequest, PaymentWay.WECHAT_PAY.getCode());
			} catch (Exception e) {
				logger.error("订单支付异常", e);
				return wechatPayBizService.responseNotify(NotifyResponse.error("同行者支付失败"));
			}
		}
		return wechatPayBizService.responseNotify(NotifyResponse.success());
	}
}
