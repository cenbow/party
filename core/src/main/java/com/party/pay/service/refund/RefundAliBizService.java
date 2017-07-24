package com.party.pay.service.refund;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.common.utils.refund.AlipayUtils;
import com.party.common.utils.refund.ObjectUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.core.model.order.OrderTrade;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.refund.AliPayRefundInput;
import com.party.pay.model.refund.AliPayRefundRequest;
import com.party.pay.model.refund.AliPayRefundResponse;

/**
 * 支付宝退款业务
 * 
 * @author yy-pc
 *
 */
@Service
public class RefundAliBizService {

	@Autowired
	IOrderTradeService orderTradeService;

	protected static Logger logger = LoggerFactory.getLogger(RefundAliBizService.class);

	/**
	 * 获取支付宝退款响应结果
	 * 
	 * @param input
	 *            退款相关参数
	 * @param orderTradeData
	 *            支付流水
	 * @return 退款响应
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public AliPayRefundResponse getRefundResponse(AliPayRefundInput input, String orderId) throws Exception {
		if (StringUtils.isNotEmpty(orderId)) {
			OrderTrade orderTrade = orderTradeService.findByOrderId(orderId);
			if (orderTrade != null && StringUtils.isNotEmpty(orderTrade.getData())) {
				AliPayRefundRequest refundRequest = new AliPayRefundRequest();
				refundRequest.setMethod(input.getMethod());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				refundRequest.setTimestamp(sdf.format(new Date()));
				refundRequest.setAppid(input.getAppId());
				refundRequest.setSignType(input.getSignType());

				Map<String, Object> dataMap = (Map<String, Object>) JSONObject.parse(orderTrade.getData());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("out_trade_no", dataMap.get("outTradeNo"));
				map.put("trade_no", dataMap.get("tradeNo"));
				map.put("refund_amount", dataMap.get("totalFee"));
				map.put("refund_reason", "正常退款");
				refundRequest.setBiz_content(JSONObject.toJSONString(map));

				String sign = AlipayUtils.getSign(refundRequest, input.getPrivateKey(), Constant.UTF_8, false, false);
				if (StringUtils.isEmpty(sign)) {
					logger.info("生成签名错误");
					return null;
				}
				logger.info("签名" + sign);
				refundRequest.setSign(sign);

				map.put("refund_reason", URLEncoder.encode(map.get("refund_reason").toString(), Constant.UTF_8));
				refundRequest.setTimestamp(URLEncoder.encode(refundRequest.getTimestamp(), Constant.UTF_8));
				refundRequest.setBiz_content(JSONObject.toJSONString(map));

				logger.info("支付宝发起退款请求参数:{}", JSONObject.toJSONString(refundRequest));

				return refund(refundRequest, input.getGateway(), input.getPublickey());
			}
		}
		return null;
	}

	/**
	 * 执行退款
	 * 
	 * @param refundRequest
	 *            退款请求参数
	 * @param gateway
	 *            退款网关
	 * @param publicKey
	 *            支付宝公钥
	 * @return 退款响应
	 */
	@SuppressWarnings("unchecked")
	public AliPayRefundResponse refund(AliPayRefundRequest refundRequest, String gateway, String publicKey) {
		Map<String, Object> params = ObjectUtils.getFieldValues(refundRequest);
		String content = AlipayUtils.returnLinkString(params);
		content += "&sign=" + refundRequest.getSign();

		String requestUrl = gateway + "?" + content;
		logger.info("支付宝退款请求参数{}", requestUrl);
		String responseData = null;
		try {
			responseData = WechatPayUtils.httpsPost(requestUrl, null);
			logger.info("支付宝响应数据{}", responseData);
		} catch (Exception e) {
			logger.info("支付宝退款请求异常{}", e);
			return null;
		}

		if (StringUtils.isNotEmpty(responseData)) {
			Map<String, Object> responseMap = (Map<String, Object>) JSONObject.parse(responseData);
			String wait_sign = responseMap.get("sign").toString();
			
			String jsonStr = isValid(responseMap.get("alipay_trade_refund_response"));
			if (StringUtils.isNotEmpty(jsonStr)) {
				responseMap = (Map<String, Object>) JSONObject.parse(jsonStr);
				AliPayRefundResponse refundResponse = new AliPayRefundResponse();
				String code = isValid(responseMap.get("code"));
				refundResponse.setCode(code);
				refundResponse.setMsg(isValid(responseMap.get("msg")));
				refundResponse.setSubCode(isValid(responseMap.get("sub_code")));
				refundResponse.setSubMsg(isValid(responseMap.get("sub_msg")));
				refundResponse.setBuyerLogonId(isValid(responseMap.get("buyer_logon_id")));
				refundResponse.setBuyerUserId(isValid(responseMap.get("buyer_user_id")));
				refundResponse.setFundChange(isValid(responseMap.get("fund_change")));
				refundResponse.setGmtRefundPay(isValid(responseMap.get("gmt_refund_pay")));
				refundResponse.setOutTradeNo(isValid(responseMap.get("out_trade_no")));
				refundResponse.setRefundFee(isValid(responseMap.get("refund_fee")));
				refundResponse.setSendBackFee(isValid(responseMap.get("send_back_fee")));
				refundResponse.setTradeNo(isValid(responseMap.get("trade_no")));
				refundResponse.setOpenId(isValid(responseMap.get("open_id")));
				
				// 同步返回验签
				boolean result = verify(refundResponse, wait_sign, publicKey);
				if (!result) {
					logger.info("数据验证结果不通过");
					return null;
				}
				return refundResponse;
			}
		}
		return null;
	}
	
	public String isValid(Object result) {
		if (result != null) {
			return result.toString();
		}
		return "";
	}

	/**
	 * 同步返回验签
	 * 
	 * @param refundResponse
	 * @param publicKey
	 * @return
	 */
	public boolean verify(AliPayRefundResponse refundResponse, String sign, String publicKey) {
		// 待验证的签名
		logger.info("待验证的签名：{}", sign);

		Map<String, Object> params = ObjectUtils.getFieldValues(refundResponse);
		Map<String, Object> waitSignMap = AlipayUtils.returnWaitSignString(params);

		// 获取待签名字符串
		String content = JSONObject.toJSONString(waitSignMap);
		logger.info("待签名json字符串：{}", content);

		boolean result = AlipayUtils.verify(content, sign, publicKey, Constant.UTF_8, false);
		logger.info("验证结果：{}", result);
		if (!result) {
			return false;
		}
		return true;
	}
}
