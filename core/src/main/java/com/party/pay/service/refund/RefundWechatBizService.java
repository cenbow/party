package com.party.pay.service.refund;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.common.utils.UUIDUtils;
import com.party.common.utils.VerifyCodeUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.core.model.order.OrderTrade;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.refund.WechatPayRefundInput;
import com.party.pay.model.refund.WechatPayRefundRequest;
import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * 微信退款业务
 * 
 * @author yy-pc
 *
 */
@Service
public class RefundWechatBizService {

	@Autowired
	IOrderTradeService orderTradeService;

	//退款来源
	private String[] refundAccounts = {"REFUND_SOURCE_UNSETTLED_FUNDS", "REFUND_SOURCE_RECHARGE_FUNDS"};

	protected static Logger logger = LoggerFactory.getLogger(RefundWechatBizService.class);

	/**
	 * 获取微信退款响应结果
	 * 
	 * @param input
	 *            退款相关参数
	 * @param orderId
	 *            订单编号
	 * @return 退款响应
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public WechatPayRefundResponse getRefundResponse(WechatPayRefundInput input, String orderId) throws Exception {
		String certPath = "";
		if (StringUtils.isNotEmpty(orderId)) {
			OrderTrade orderTrade = orderTradeService.findByOrderId(orderId);
			if (orderTrade != null && StringUtils.isNotEmpty(orderTrade.getData())) {
				Map<String, String> dataMap = (Map<String, String>) JSONObject.parse(orderTrade.getData());
				WechatPayRefundRequest refundRequest = new WechatPayRefundRequest();
				if (StringUtils.isNotEmpty(input.getAppId())) {
					refundRequest.setAppid(input.getAppId()); // 微信开放平台审核通过的应用APPID
				} else {
					refundRequest.setAppid(dataMap.get("appid")); // 微信开放平台审核通过的应用APPID
				}
				if (StringUtils.isNotEmpty(input.getMchId())) {
					refundRequest.setMchId(input.getMchId()); // 微信支付分配的商户号
				} else {
					refundRequest.setMchId(dataMap.get("mchId")); // 微信支付分配的商户号
				}
				String nonceStr = VerifyCodeUtils.RandomString(Constant.RANDOM_LENGTH);// 获取随机数
				refundRequest.setNonceStr(nonceStr); // 随机字符串
				refundRequest.setOpUserId(refundRequest.getMchId()); // 操作员帐号
				String uuid = UUIDUtils.generateRandomUUID();// 默认为商户号
				refundRequest.setOutRefundNo(uuid); // 商户系统内部的退款单号
				refundRequest.setOutTradeNo(dataMap.get("outTradeNo"));// 商户订单号
				refundRequest.setRefundFee(Integer.valueOf(dataMap.get("totalFee"))); // 退款总金额，订单总金额，单位为分
				refundRequest.setTotalFee(Integer.valueOf(dataMap.get("totalFee"))); // 订单总金额，单位为分
				refundRequest.setTransactionId(""); // 微信订单号
				// 退款证书
				certPath = input.getCertPath().replace("{mchId}", refundRequest.getMchId());

				WechatPayRefundResponse response = null;
				for (String type : refundAccounts){
					refundRequest.setRefundAccount(type);
					String sign = WechatPayUtils.getSign(refundRequest, input.getApiKey()); // 获取签名
					if (StringUtils.isEmpty(sign)) {
						logger.info("生成签名错误");
						return null;
					}
					refundRequest.setSign(sign); // 签名

					logger.info("微信发起退款请求参数:{}", JSONObject.toJSONString(refundRequest));
					response = refund(refundRequest, certPath, input.getRefundUrl());
					if (null != response && "NOTENOUGH".equals(response.getErrCode())){
						continue;
					}
					break;
				}
				return response;
			}
		}
		return null;
	}

	/**
	 * 微信退款
	 * 
	 * @param refundRequest
	 *            退款请求参数
	 * @param wechatCert
	 *            退款证书
	 * @param refundUrl
	 *            退款接口
	 * @return
	 */
	public WechatPayRefundResponse refund(WechatPayRefundRequest refundRequest, String wechatCert, String refundUrl) {
		String requestData = WechatPayUtils.beanToXml(refundRequest);
		logger.info("微信退款请求参数{}", requestData);
		String responseData = null;
		try {
			responseData = WechatPayUtils.httpsPost(refundUrl, requestData, refundRequest.getMchId(), wechatCert);
			logger.info("微信退款响应参数{}", responseData);
		} catch (Exception e) {
			logger.info("微信退款请求异常{}", e);
			return null;
		}
		if (StringUtils.isNotEmpty(responseData)) {
			return WechatPayUtils.xmlToBean(responseData, WechatPayRefundResponse.class);
		}
		return null;
	}
}
