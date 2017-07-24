package com.party.admin.biz.refund;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.party.admin.web.dto.AjaxResult;
import com.party.core.model.order.OrderFormInfo;
import com.party.core.model.order.OrderTrade;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.refund.AliPayRefundInput;
import com.party.pay.model.refund.WechatPayRefundInput;
import com.party.pay.service.refund.RefundAliBizService;
import com.party.pay.service.refund.RefundOrderBizService;
import com.party.pay.service.refund.RefundWechatBizService;

/**
 * 退款业务
 * 
 * @author yy-pc
 *
 */
@Service
public class RefundBizService {

	/********************** 支付宝 ***********************/
	// 支付网关
	@Value("#{refund_ali['alipay.gateway']}")
	private String gateway;

	// 接口名称
	@Value("#{refund_ali['alipay.method']}")
	private String method;

	// 应用id
	@Value("#{refund_ali['alipay.appid']}")
	private String appId;

	// 应用私钥
	@Value("#{refund_ali['alipay.privatekey']}")
	private String privateKey;

	// 支付宝公钥
	@Value("#{refund_ali['alipay.publickey']}")
	private String publickey;

	// 签名方式
	@Value("#{refund_ali['alipay.sign_type']}")
	private String signType;
	/********************** 支付宝 ***********************/

	/********************** 微信 ***********************/
	// 微信接口密钥
	@Value("#{refund_wechat['wechat.apikey']}")
	private String apiKey;

	// 微信商户证书
	@Value("#{refund_wechat['wechat.cert.path']}")
	private String certPath;

	// 微信退款接口路径
	@Value("#{refund_wechat['wechat.refundUrl']}")
	private String refundUrl;
	/********************** 微信 ***********************/

	@Autowired
	IOrderTradeService orderTradeService;

	@Autowired
	RefundAliBizService refundAliBizService;

	@Autowired
	RefundWechatBizService refundWechatBizService;

	@Autowired
	RefundOrderBizService refundOrderBizService;

	@Autowired
	MessageOrderBizService messageOrderBizService;
	
	@Autowired
	IOrderFormInfoService orderFormInfoService;

	protected static Logger logger = LoggerFactory.getLogger(RefundBizService.class);

	/**
	 * 处理退款
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Object refund(String orderId) throws Exception {
		if (StringUtils.isEmpty(orderId)) {
			logger.info("订单号不能为空");
			return null;
		}
		OrderTrade orderTrade = orderTradeService.findByOrderId(orderId);
		if (orderTrade == null) {
			logger.info("订单支付流水不存在");
			return null;
		}
		if (orderTrade.getType().equals(PaymentWay.ALI_PAY.getCode())) {
			// 支付宝
			AliPayRefundInput input = new AliPayRefundInput();
			input.setAppId(appId);
			input.setGateway(gateway);
			input.setMethod(method);
			input.setPrivateKey(privateKey);
			input.setPublickey(publickey);
			input.setSignType(signType);
			return refundAliBizService.getRefundResponse(input, orderId);
		} else if (orderTrade.getType().equals(PaymentWay.WECHAT_PAY.getCode())) {
			// 微信
			WechatPayRefundInput input = new WechatPayRefundInput();
			OrderFormInfo orderFormInfo = orderFormInfoService.findByOrderId(orderId);
			if (orderFormInfo != null) {
				input.setAppId(orderFormInfo.getAppId());
				input.setMchId(orderFormInfo.getMchId());
				input.setApiKey(orderFormInfo.getApiKey());
			} else {
				input.setApiKey(apiKey);
			}
			
			input.setRefundUrl(refundUrl);
			input.setCertPath(certPath);
			return refundWechatBizService.getRefundResponse(input, orderId);
		}
		return null;
	}

	/**
	 * 活动退款退款回调
	 * 
	 * @param orderId
	 * @param refundResponse
	 * @param paymentWay
	 * @return
	 */
	public AjaxResult activityRefundCallback(String orderId, Object refundResponse, Integer paymentWay) {
		String paymentWayStr = null;
		if (paymentWay.equals(PaymentWay.WECHAT_PAY.getCode())) {
			paymentWayStr = "微信";
		} else if (paymentWay.equals(PaymentWay.ALI_PAY.getCode())) {
			paymentWayStr = "支付宝";
		}
		try {
			logger.info(paymentWayStr + "准备处理退款业务");
			refundOrderBizService.updateRefundBusiness(orderId, refundResponse, paymentWay);
			logger.info(paymentWayStr + "处理退款业务成功");
		} catch (Exception e) {
			logger.info(paymentWayStr + "处理退款业务出错{}", e);
			return new AjaxResult(false, "退款失败");
		}
		try {
			logger.info(paymentWayStr + "准备退款消息推送");
			messageOrderBizService.activityRefundSend(orderId);
			logger.info(paymentWayStr + "退款消息推送成功");
		} catch (Exception e) {
			logger.info(paymentWayStr + "退款消息推送异常{}", e);
			return new AjaxResult(false, "退款失败");
		}
		logger.info(paymentWayStr + "退款成功");
		return new AjaxResult(true, "退款成功");
	}
}
