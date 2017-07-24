package com.party.pay.service.query;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.common.utils.refund.AlipayUtils;
import com.party.common.utils.refund.ObjectUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.pay.model.query.AliPayQueryRequest;
import com.party.pay.model.query.AliPayQueryResponse;
import com.party.pay.model.refund.AliPayRefundInput;

/**
 * 支付宝订单查询
 * 
 * @author yy-pc
 *
 */
@Service
public class QueryAliBizService {
	protected static Logger logger = LoggerFactory.getLogger(QueryAliBizService.class);

	/**
	 * 获取支付宝查询响应结果
	 * 
	 * @param input
	 *            查询相关参数
	 * @param orderTradeData
	 *            支付流水
	 * @return 查询响应
	 * @throws Exception
	 */
	public AliPayQueryResponse getQueryResponse(AliPayRefundInput input, String orderId) throws Exception {
		AliPayQueryRequest refundRequest = new AliPayQueryRequest();
		refundRequest.setAppid(input.getAppId());
		refundRequest.setMethod(input.getMethod());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		refundRequest.setTimestamp(sdf.format(new Date()));
		refundRequest.setSignType(input.getSignType());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("out_trade_no", orderId);
		refundRequest.setBiz_content(JSONObject.toJSONString(map));

		String sign = AlipayUtils.getSign(refundRequest, input.getPrivateKey(), Constant.UTF_8, false, false);
		if (StringUtils.isEmpty(sign)) {
			logger.info("生成签名错误");
			return null;
		}
		logger.info("签名" + sign);
		refundRequest.setSign(sign);

		refundRequest.setTimestamp(URLEncoder.encode(refundRequest.getTimestamp(), Constant.UTF_8));
		refundRequest.setBiz_content(JSONObject.toJSONString(map));

		logger.info("支付宝发起订单查询请求参数:{}", JSONObject.toJSONString(refundRequest));
		return getQuery(refundRequest, input.getGateway(), input.getPublickey());
	}

	/**
	 * 执行查询
	 * 
	 * @param refundRequest
	 *            查询请求参数
	 * @param gateway
	 *            查询网关
	 * @param publicKey
	 *            支付宝公钥
	 * @return 查询响应
	 */
	@SuppressWarnings("unchecked")
	public AliPayQueryResponse getQuery(AliPayQueryRequest refundRequest, String gateway, String publickey) throws ParseException {
		Map<String, Object> params = ObjectUtils.getFieldValues(refundRequest);
		String content = AlipayUtils.returnLinkString(params);
		content += "&sign=" + refundRequest.getSign();

		String requestUrl = gateway + "?" + content;
		logger.info("支付宝订单查询请求参数{}", requestUrl);
		String responseData = null;
		try {
			responseData = WechatPayUtils.httpsPost(requestUrl, null);
			logger.info("支付宝响应数据{}", responseData);
		} catch (Exception e) {
			logger.info("支付宝订单查询请求异常{}", e);
			return null;
		}

		if (StringUtils.isNotEmpty(responseData)) {
			Map<String, Object> responseMap = (Map<String, Object>) JSONObject.parse(responseData);
			String wait_sign = responseMap.get("sign").toString();
			String queryResponse = isValid(responseMap.get("alipay_trade_query_response"));
			if (StringUtils.isNotEmpty(queryResponse)) {
				responseMap = (Map<String, Object>) JSONObject.parse(queryResponse);
				AliPayQueryResponse response = new AliPayQueryResponse();
				String code = isValid(responseMap.get("code"));
				response.setCode(code);
				response.setMsg(isValid(responseMap.get("msg")));
				response.setSubCode(isValid(responseMap.get("sub_code")));
				response.setSubMsg(isValid(responseMap.get("sub_msg")));
				if (code.equals(Constant.ALI_SUCCESS_CODE)) {
					response.setBuyerLogonId(isValid(responseMap.get("buyer_logon_id")));
					response.setBuyerUserId(isValid(responseMap.get("buyer_user_id")));
					response.setOutTradeNo(isValid(responseMap.get("out_trade_no")));
					response.setTradeNo(isValid(responseMap.get("trade_no").toString()));
					response.setTradeStatus(isValid(responseMap.get("trade_status")));
					response.setTotalAmount(isValid(responseMap.get("total_amount")));
					response.setReceiptAmount(isValid(responseMap.get("receipt_amount")));
					response.setSendPayDate(isValid(responseMap.get("send_pay_date")));
					response.setBuyerPayAmount(isValid(responseMap.get("buyer_pay_amount")));
					response.setInvoiceAmount(isValid(responseMap.get("invoice_amount")));
					response.setOpenId(isValid(responseMap.get("open_id")));
					response.setPointAmount(isValid(responseMap.get("point_amount")));
					// 同步返回验签
					boolean result = verify(response, wait_sign, publickey);
					if (!result) {
						logger.info("数据验证结果不通过");
						return null;
					}
				}
				return response;
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
	public boolean verify(AliPayQueryResponse refundResponse, String sign, String publicKey) {
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
