package com.party.mobile.web.controller.pay;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.constant.Constant;
import com.party.common.utils.DateUtils;
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
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.appAlipay.input.AppPayConstant;
import com.party.mobile.web.dto.appAlipay.output.AppPayRequest;
import com.party.mobile.web.utils.ObjectUtils;
import com.party.mobile.web.utils.appPay.AlipayUtils;
import com.party.pay.model.pay.ali.PayResponse;
import com.party.pay.model.query.TradeStatus;

/**
 * 
 * 此类描述的是：支付宝APP支付控制层
 * 
 * @author: Administrator
 * @version: 2017年2月4日 下午6:25:48
 */
@Controller
@RequestMapping("/pay/ali/app")
public class PayAliAppController {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IGoodsService goodsService;

	@Autowired
	IActivityService activityService;

	@Autowired
	OrderBizService orderFormBizService;

	@Value("#{pay_ali_app['ali.app.service']}")
	private String alipayService;

	// 支付宝支付合作者身份ID
	@Value("#{pay_ali_app['ali.app.partner']}")
	private String alipayPartner;

	// 支付宝支付卖家支付宝账号
	@Value("#{pay_ali_app['ali.app.sellerId']}")
	private String alipaySellerid;

	// 支付宝支付方式
	@Value("#{pay_ali_app['ali.app.signType']}")
	private String alipaySigntype;

	// 支付宝支付服务器异步通知页面路径
	@Value("#{pay_ali_app['ali.app.notifyUrl']}")
	private String alipayNotifyurl;

	// 支付宝支付方式
	@Value("#{pay_ali_app['ali.app.paymentType']}")
	private String alipayPaymenttype;

	// 支付宝支付私钥
	@Value("#{pay_ali_app['ali.app.privateKey']}")
	private String alipayPrivatekey;

	// 支付宝支付公钥
	@Value("#{pay_ali_app['ali.app.publicKey']}")
	private String alipayPublickey;

	// 未付款交易的超时时间
	@Value("#{pay_ali_app['ali.app.itBpay']}")
	private String itBpay;

	// 返回正确状态码
	private static String SUCCESS = "success";

	protected static Logger logger = LoggerFactory.getLogger(PayAliAppController.class);

	/**
	 * 为前端生成支付参数
	 * 
	 * @param OrderFormId
	 *            订单ID
	 * @return 订单参数
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value = "createPayData")
	public AjaxResult getPayData(String OrderFormId) {

		// 验证参数
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
		
		// 支付类型
		orderForm.setPaymentWay(PaymentWay.ALI_PAY.getCode());
		orderForm.setTradeType(Constant.CLIENT_ALI_APP);
		orderFormService.update(orderForm);

		AppPayRequest payRequest = new AppPayRequest();
		payRequest.setInputCharset(AppPayConstant.UTF_8);
		payRequest.setItBpay(itBpay);
		payRequest.setService(alipayService);
		payRequest.setPartner(alipayPartner);
		payRequest.setSellerId(alipaySellerid);
		payRequest.setSignType(alipaySigntype);
		payRequest.setNotifyUrl(alipayNotifyurl);
		payRequest.setPaymentType(alipayPaymenttype);

		if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()) {
			Activity activity = activityService.get(orderForm.getGoodsId());
			payRequest.setBody(activity.getTitle());
			payRequest.setSubject(activity.getTitle());
		} else {
			Goods goods = goodsService.get(orderForm.getGoodsId());
			payRequest.setBody(goods.getTitle());
			payRequest.setSubject(goods.getTitle());
		}

		payRequest.setOutTradeNo(orderForm.getId());
		payRequest.setTotalFee(String.valueOf(orderForm.getPayment()));

		// 获取签名字段
		String sign = AlipayUtils.getSign(payRequest, alipayPrivatekey, AppPayConstant.UTF_8);
		if (Strings.isNullOrEmpty(sign)) {
			AjaxResult.error(PartyCode.ALIPAY_SIGN_ERROR, "生成签名错误");
		}

		payRequest.setSign(sign);
		String request = AlipayUtils.getRequestStr(payRequest);
		logger.info("支付宝支付请求数据{}", request);
		return AjaxResult.success((Object) request);
	}

	/**
	 * 接受异步通知
	 * 
	 * @param request
	 *            请求参数
	 */
	@ResponseBody
	@RequestMapping("acceptNotify")
	public String acceptNotify(HttpServletRequest request) {

		PayResponse payResponse = getNotifyData(request);
		logger.info("支付宝异步通知数据{}", payResponse.toString());

		// 验证数据安全
		boolean result = verify(payResponse);
		if (!result) {
			logger.info("数据验证不通过");
			return "数据验证不通过";
		}

		// 验证支付结果
		if (!AppPayConstant.TRADE_SUCCESS.equals(payResponse.getTradeStatus())) {
			logger.info("支付类型未成功");
			return "支付类型未成功";
		}

		OrderForm orderForm = orderFormService.get(payResponse.getOutTradeNo());
		if (OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode().equals(orderForm.getStatus())){
			try {
				logger.info("准备更改状态");
				orderFormBizService.updatePayBusiness(orderForm, payResponse, PaymentWay.ALI_PAY.getCode());
			} catch (Exception e) {
				logger.error("订单支付异常{}", e.getMessage());
				return "订单支付异常";
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取异步通知数据
	 * 
	 * @param request
	 *            请求参数
	 * @return 请求参数数据
	 */
	private PayResponse getNotifyData(HttpServletRequest request) {

		PayResponse payResponse = new PayResponse();
		payResponse.setNotifyTime(request.getParameter("notify_time"));
		payResponse.setNotifyType(request.getParameter("notify_type"));
		payResponse.setNotifyId(request.getParameter("notify_id"));
		payResponse.setSignType(request.getParameter("sign_type"));
		payResponse.setSign(request.getParameter("sign"));
		payResponse.setOutTradeNo(request.getParameter("out_trade_no"));
		payResponse.setSubject(request.getParameter("subject"));
		payResponse.setPaymentType(request.getParameter("payment_type"));
		payResponse.setTradeNo(request.getParameter("trade_no"));
		payResponse.setTradeStatus(request.getParameter("trade_status"));
		payResponse.setSellerId(request.getParameter("seller_id"));
		payResponse.setSellerEmail(request.getParameter("seller_email"));
		payResponse.setBuyerId(request.getParameter("buyer_id"));
		payResponse.setBuyerEmail(request.getParameter("buyer_email"));
		payResponse.setTotalFee(Float.parseFloat(request.getParameter("total_fee")));
		payResponse.setQuantity(Integer.valueOf(request.getParameter("quantity")));
		payResponse.setPrice(Float.valueOf(request.getParameter("price")));
		payResponse.setBody(request.getParameter("body"));
		payResponse.setGmtCreate(request.getParameter("gmt_create"));
		payResponse.setGmtPayment(request.getParameter("gmt_payment"));
		payResponse.setIsTotalFeeAdjust(request.getParameter("is_total_fee_adjust"));
		payResponse.setUseCoupon(request.getParameter("use_coupon"));
		payResponse.setDiscount(request.getParameter("discount"));
		payResponse.setRefundStatus(request.getParameter("refund_status"));
		try {
			payResponse.setGmtRefund(DateUtils.parse(request.getParameter("refund_status")));
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}

		return payResponse;
	}

	/**
	 * 验证通知数据安全
	 * 
	 * @param payResponse
	 *            支付回调数据
	 * @return 验证结果（true/false）
	 */

	public boolean verify(PayResponse payResponse) {

		Map<String, Object> params = ObjectUtils.getFieldValues(payResponse);

		// 验证消息来源
		String responseTxt = "false";
		if (payResponse.getNotifyId() != null) {
			String notifyId = payResponse.getNotifyId();
			responseTxt = AlipayUtils.verifyResponse(notifyId, alipayPartner);
		}

		logger.info("responseTxt:{}", responseTxt);
		if (!responseTxt.equals("true")) {
			return false;
		}

		// 验证签名
		String sign = "";
		if (payResponse.getSign() != null) {
			sign = payResponse.getSign();
		}
		// 获取待签名字符串
		String content = AlipayUtils.returnLinkString(params);
		logger.info("待签名内容:{}", content);

		// 获得签名验证结果
		boolean isSign = false;
		if (null != payResponse.getSignType() && alipaySigntype.equals(payResponse.getSignType())) {
			isSign = AlipayUtils.verify(content, sign, alipayPublickey, AppPayConstant.UTF_8);
		}

		logger.info("isSign:{}", isSign);
		if (!isSign) {
			return false;
		}

		// 验证订单号
		if (Strings.isNullOrEmpty(payResponse.getOutTradeNo())) {
			return false;
		} else {
			OrderForm orderForm = orderFormService.get(payResponse.getOutTradeNo());
			if (null == orderForm) {
				return false;
			}
			// 验证金额
			else {
				logger.info("orderForm信息" + orderForm.toString());
				logger.info("金额" + orderForm.getPayment() + "" + payResponse.getTotalFee());
				if (!orderForm.getPayment().equals(payResponse.getTotalFee())) {
					logger.info("6666");
					return false;
				}
			}
		}

		// 验证卖家ID
		if (!payResponse.getSellerEmail().equals(alipaySellerid)) {
			return false;
		}
		logger.info("验证通过");
		return true;
	}
}
