package com.party.pay.service.query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.party.common.constant.Constant;
import com.party.common.utils.VerifyCodeUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderFormInfo;
import com.party.core.model.order.OrderType;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.query.WechatPayQueryRequest;
import com.party.pay.model.query.WechatPayQueryResponse;
import com.party.pay.model.refund.WechatPayRefundInput;

@Service
public class QueryWechatBizService {

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	IMemberMerchantService memberMerchantService;

	@Autowired
	IOrderFormInfoService orderFormInfoService;

	protected static Logger logger = LoggerFactory.getLogger(QueryWechatBizService.class);

	/**
	 * 获取微信查询响应结果
	 * 
	 * @param input
	 *            查询相关参数
	 * @param orderId
	 *            订单编号
	 * @return 查询响应
	 * @throws Exception
	 */
	public WechatPayQueryResponse getQueryResponse(WechatPayRefundInput input, String orderId) throws Exception {
		OrderForm orderForm = orderFormService.get(orderId);
		String appId = "";
		String mchId = "";
		String apiKey = "";
		OrderFormInfo orderFormInfo = orderFormInfoService.findByOrderId(orderId);
		if (orderFormInfo != null) {
			appId = orderFormInfo.getAppId();
			mchId = orderFormInfo.getMchId();
			apiKey = orderFormInfo.getApiKey();
		} else {
			appId = input.getAppId();
			mchId = input.getMchId();
			apiKey = input.getApiKey();
			// 如果订单为众筹，获取合作商对应的商户信息
			if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
				// 获取绑定商户
				MemberMerchant merchant = memberMerchantService.findByMemberId(orderForm.getInitiatorId());
				if (merchant != null && merchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
					appId = merchant.getOfficialAccountId();
					mchId = merchant.getMerchantId();
					apiKey = merchant.getMerchantApiKey();
				}
			}
		}

		WechatPayQueryRequest request = new WechatPayQueryRequest();
		request.setAppId(appId); // 微信开放平台审核通过的应用APPID
		request.setMchId(mchId); // 微信支付分配的商户号
		request.setOutTradeNo(orderId); // 订单编号
		String nonceStr = VerifyCodeUtils.RandomString(Constant.RANDOM_LENGTH);// 获取随机数
		request.setNonceStr(nonceStr); // 随机字符串

		String sign = WechatPayUtils.getSign(request, apiKey); // 获取签名
		if (StringUtils.isEmpty(sign)) {
			logger.info("生成签名错误");
			return null;
		}
		request.setSign(sign);
		logger.info("微信发起查询请求参数:{}", JSONObject.toJSONString(request));
		WechatPayQueryResponse response = orderquery(request, input.getRefundUrl());
		if (response != null && Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
			orderFormInfoService.saveOrderFormInfo(appId, mchId, apiKey, orderId);
		}
		return response;
	}

	/**
	 * 微信查询
	 * 
	 * @param refundRequest
	 *            查询请求参数
	 * @param wechatCert
	 *            查询证书
	 * @param refundUrl
	 *            查询接口
	 * @return
	 */
	public WechatPayQueryResponse orderquery(WechatPayQueryRequest request, String queryUrl) {
		String requestData = WechatPayUtils.beanToXml(request);
		logger.info("微信查询请求参数{}", requestData);

		String responseData = null;
		try {
			responseData = WechatPayUtils.httpsPost(queryUrl, requestData);
			logger.info("微信查询响应参数{}", responseData);
		} catch (Exception e) {
			logger.info("微信查询请求异常{}", e);
			return null;
		}
		if (StringUtils.isNotEmpty(responseData)) {
			return WechatPayUtils.xmlToBean(responseData, WechatPayQueryResponse.class);
		}
		return null;
	}
}
