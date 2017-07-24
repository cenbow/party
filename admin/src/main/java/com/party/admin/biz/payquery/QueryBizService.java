package com.party.admin.biz.payquery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.party.common.constant.Constant;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.refund.AliPayRefundInput;
import com.party.pay.model.refund.WechatPayRefundInput;
import com.party.pay.service.query.QueryAliBizService;
import com.party.pay.service.query.QueryWechatBizService;

@Service
public class QueryBizService {
	/********************** 支付宝 ***********************/
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
	/********************* APP *************************/
	@Value("#{pay_wechat_app['wechat.app.appId']}")
	private String app_appId;

	// 微信商户平台商户号
	@Value("#{pay_wechat_app['wechat.app.mchId']}")
	private String app_mchId;

	// 微信签名令牌
	@Value("#{pay_wechat_app['wechat.app.apiKey']}")
	private String app_apiKey;
	/********************* APP *************************/

	/********************* 微网站 *************************/
	// 公众号商户编码
	@Value("#{pay_wechat_wwz['wechat.wwz.mchId']}")
	private String wwz_mchId;

	// 微信接口密钥
	@Value("#{pay_wechat_wwz['wechat.wwz.apiKey']}")
	private String wwz_apiKey;

	// 公众号编号
	@Value("#{pay_wechat_wwz['wechat.wwz.appId']}")
	private String wwz_appId;
	/********************* 微网站 *************************/

	/********************* 小程序 *************************/
	// 小程序编号
	@Value("#{pay_wechat_xcx['wechat.xcx.appId']}")
	private String xcx_appId;

	// 小程序商户号
	@Value("#{pay_wechat_xcx['wechat.xcx.mchId']}")
	private String xcx_mchId;

	// 微信接口密钥
	@Value("#{pay_wechat_xcx['wechat.xcx.apiKey']}")
	private String xcx_apiKey;
	/********************* 小程序 *************************/
	/********************** 微信 ***********************/

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	QueryAliBizService queryAliBizService;
	@Autowired
	QueryWechatBizService queryWechatBizService;

	protected static Logger logger = LoggerFactory.getLogger(QueryBizService.class);

	/**
	 * 执行查询
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Object orderquery(String orderId) throws Exception {
		if (StringUtils.isEmpty(orderId)) {
			logger.info("订单号不能为空");
			return null;
		}
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm.getPaymentWay() == null) {
			return null;
		}
		if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
			// 支付宝
			AliPayRefundInput input = new AliPayRefundInput();
			input.setGateway("https://openapi.alipay.com/gateway.do");
			input.setMethod("alipay.trade.query");
			input.setAppId(appId);
			input.setPrivateKey(privateKey);
			input.setPublickey(publickey);
			input.setSignType(signType);
			return queryAliBizService.getQueryResponse(input, orderId);
		} else if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
			// 微信
			String appId = "";
			String mchId = "";
			String apiKey = "";
			if (orderForm.getTradeType() == null) {
				return null;
			}
			if (orderForm.getTradeType().equals(Constant.CLIENT_WX_WWZ)) {
				appId = wwz_appId;
				mchId = wwz_mchId;
				apiKey = wwz_apiKey;
			} else if (orderForm.getTradeType().equals(Constant.CLIENT_WX_APP)) {
				appId = app_appId;
				mchId = app_mchId;
				apiKey = app_apiKey;
			} else if (orderForm.getTradeType().equals(Constant.CLIENT_WX_XCX)) {
				appId = xcx_appId;
				mchId = xcx_mchId;
				apiKey = xcx_apiKey;
			}

			WechatPayRefundInput input = new WechatPayRefundInput();
			input.setAppId(appId);
			input.setMchId(mchId);
			input.setApiKey(apiKey);
			input.setRefundUrl("https://api.mch.weixin.qq.com/pay/orderquery");
			return queryWechatBizService.getQueryResponse(input, orderId);
		}
		return null;
	}
}
