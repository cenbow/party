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
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.goods.Goods;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberMerchantService;
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
 * 此类描述的是：微信微网站支付控制层
 * 
 * @author: Administrator
 * @version: 2017年2月4日 下午6:25:48
 */

@Controller
@RequestMapping(value = "/pay/wechat/wwz")
public class PayWechatWwzController {

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
	IProjectService projectService;

	@Autowired
	IMemberMerchantService merchantService;
	
	@Autowired
	IOrderFormInfoService orderFormInfoService;

	// 公众号商户编码
	@Value("#{pay_wechat_wwz['wechat.wwz.mchId']}")
	private String mchId;

	// 公众号回调地址
	@Value("#{pay_wechat_wwz['wechat.wwz.notifyUrl']}")
	private String notifyUrl;

	// 微信接口密钥
	@Value("#{pay_wechat_wwz['wechat.wwz.apiKey']}")
	private String apiKey;

	// 公众号编号
	@Value("#{pay_wechat_wwz['wechat.wwz.appId']}")
	private String appId;

	protected static Logger logger = LoggerFactory.getLogger(PayWechatWwzController.class);

	@Authorization
	@ResponseBody
	@RequestMapping(value = "/getPayData")
	public AjaxResult getPayData(String orderId, String openId, HttpServletRequest request) {
		String newMchId = mchId;
		String newAppId = appId;
		String newApiKey = apiKey;

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

		if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
			// 订单绑定的业务发起者 获取绑定商户
			MemberMerchant merchant = merchantService.findByMemberId(orderForm.getInitiatorId());
			if (merchant != null && merchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
				logger.info("取业务发起者支付信息开始");
				if (StringUtils.isNotEmpty(merchant.getOfficialAccountId())) {
					newAppId = merchant.getOfficialAccountId().trim();
					logger.info("取业务发起者支付信息——appId={}", newAppId);
				}
				if (StringUtils.isNotEmpty(merchant.getMerchantId())) {
					newMchId = merchant.getMerchantId().trim();
					logger.info("取业务发起者支付信息——mchId={}", newMchId);
				}
				if (StringUtils.isNotEmpty(merchant.getMerchantApiKey())) {
					newApiKey = merchant.getMerchantApiKey().trim();
					logger.info("取业务发起者支付信息——apiKey={}", newApiKey);
				}
				logger.info("取业务发起者支付信息结束");
			}
		}

		// 订单状态验证
		if (!orderForm.getIsPay().equals(PaymentState.NO_PAY.getCode()) || orderForm.getStatus() != OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()) {
			return AjaxResult.error(PartyCode.PAYMENT_STATUS_ERROR, "订单状态不正确");
		}
		// 统一下单
		String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
		String ipAddress = request.getRemoteAddr();// 终端IP

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		logger.info("支付时订单信息{}",orderForm.toString());
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
		else if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())){
			Project project = projectService.get(orderForm.getGoodsId());
			String body = wechatPayBizService.subTitle(project.getTitle(), 125);
			unifiedOrderRequest.setBody(body);// 商品描叙
		}

		unifiedOrderRequest.setAppid(newAppId);// 公众账号ID
		unifiedOrderRequest.setMchId(newMchId);// 商户编号
		unifiedOrderRequest.setOpenid(openId);// 用户编号
		unifiedOrderRequest.setNonceStr(nonceStr);// 随机数
		unifiedOrderRequest.setOutTradeNo(orderForm.getId());// 订单号
		double total = BigDecimalUtils.mul(orderForm.getPayment(), 100);
		total = BigDecimalUtils.round(total, 2);
		unifiedOrderRequest.setTotalFee((int)total);// 单位分
		unifiedOrderRequest.setSpbillCreateIp(ipAddress);// 请求IP
		unifiedOrderRequest.setNotifyUrl(notifyUrl);// 回调地址
		unifiedOrderRequest.setTradeType(WechatConstant.TRADE_TYPE);// 交易类型
		UnifiedOrderResponse unifiedorderResponse = wechatPayBizService.unifiedOrder(unifiedOrderRequest, newApiKey);
		if (null == unifiedorderResponse) {
			return AjaxResult.error(PartyCode.UNIFIEDORDER_ERROR, "微信统一下单异常");
		}
		if (Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getResultCode())) {
			// 平台
			orderForm.setTradeType(Constant.CLIENT_WX_WWZ);
			// 商户号
			orderForm.setMerchantId(newMchId);
			// 支付类型
			orderForm.setPaymentWay(PaymentWay.WECHAT_PAY.getCode());
			orderFormService.update(orderForm);
			
			// 保存订单附属信息
			orderFormInfoService.saveOrderFormInfo(newAppId, newMchId, newApiKey, orderForm.getId());
		}

		// 发起下单
		String prepayId = unifiedorderResponse.getPrepayId();
		PayRequest payRequest = new PayRequest();
		payRequest.setAppId(newAppId);// 开放平台编号
		payRequest.setTimeStamp(String.valueOf(System.currentTimeMillis()));// 时间戳
		payRequest.setNonceStr(nonceStr);// 随机数
		payRequest.setPackages(WechatConstant.PREPAY_ID + prepayId);// 预授权码
		payRequest.setSignType(WechatConstant.MD5_TYPE);// 加密方式

		// 获取签名
		String sign = WechatPayUtils.getSign(payRequest, newApiKey);
		payRequest.setPaySign(sign);

		logger.info("微信微网站发起支付请求参数:{}", JSONObject.toJSON(payRequest));
		return AjaxResult.success(payRequest);
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
		NotifyRequest notifyRequest = WechatPayUtils.deserialize(requestStr, NotifyRequest.class);
		
		logger.info("订单编号{}", notifyRequest.getOutTradeNo());
		OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
		logger.info("订单信息{}", orderForm.toString());
		
		String newMchId = mchId;
		String newAppId = appId;
		String newApiKey = apiKey;
		
		if (orderForm != null) {
			if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
				// 订单绑定的业务发起者 获取绑定商户
				MemberMerchant merchant = merchantService.findByMemberId(orderForm.getInitiatorId());
				if (merchant != null && merchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
					logger.info("取业务发起者支付信息开始");
					if (StringUtils.isNotEmpty(merchant.getOfficialAccountId())) {
						newAppId = merchant.getOfficialAccountId().trim();
						logger.info("取业务发起者支付信息——appId={}", newAppId);
					}
					if (StringUtils.isNotEmpty(merchant.getMerchantId())) {
						newMchId = merchant.getMerchantId().trim();
						logger.info("取业务发起者支付信息——mchId={}", newMchId);
					}
					if (StringUtils.isNotEmpty(merchant.getMerchantApiKey())) {
						newApiKey = merchant.getMerchantApiKey().trim();
						logger.info("取业务发起者支付信息——apiKey={}", newApiKey);
					}
					logger.info("取业务发起者支付信息结束");
				}
			}
		}

		// 验证数据安全
		boolean verify = wechatPayBizService.verify(notifyRequest, newApiKey, newAppId, newMchId);
		if (!verify) {
			return wechatPayBizService.responseNotify(NotifyResponse.error("安全验证不通过"));
		}

		// 支付结果验证
		if ((!WechatConstant.SUCCESS.equals(notifyRequest.getReturnCode())) || (!WechatConstant.SUCCESS.equals(notifyRequest.getResultCode()))) {
			return wechatPayBizService.responseNotify(NotifyResponse.error("支付结果不成功"));
		}

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
