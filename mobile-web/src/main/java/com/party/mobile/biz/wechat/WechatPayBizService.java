package com.party.mobile.biz.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.core.exception.BusinessException;
import com.party.core.model.order.OrderForm;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.web.dto.wechat.input.UnifiedOrderResponse;
import com.party.mobile.web.dto.wechat.output.NotifyResponse;
import com.party.mobile.web.dto.wechat.output.UnifiedOrderRequest;
import com.party.common.constant.WechatConstant;
import com.party.mobile.web.utils.WechatPayUtils;
import com.party.pay.model.pay.wechat.NotifyRequest;

/**
 * 微信支付业务服务接口
 * 
 * @author wei.li
 *
 */
@Service
public class WechatPayBizService {

	protected static Logger logger = LoggerFactory.getLogger(WechatPayBizService.class);

	@Autowired
	IOrderFormService orderFormService;

	/**
	 * 微信统一下单
	 * 
	 * @param unifiedorderRequest
	 *            统一下单请求参数
	 * @param apiKey
	 *            接口秘钥
	 * @return 交互数据
	 */
	public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest unifiedorderRequest, String apiKey) {
		String sign = WechatPayUtils.getSign(unifiedorderRequest, apiKey);
		unifiedorderRequest.setSign(sign);
		String requestData = WechatPayUtils.beanToXml(unifiedorderRequest);
		logger.info("微信统一下单请求参数{}", requestData);

		String responseData = null;
		try {
			responseData = WechatPayUtils.httpsPost(WechatConstant.UNIFIEDORDER_URL, requestData);
		} catch (Exception e) {
			logger.error("微信统一下单请求异常", e);
		}
		logger.info("微信统一下单响应参数{}", responseData);
		UnifiedOrderResponse unifiedOrderResponse = WechatPayUtils.xmlToBean(responseData, UnifiedOrderResponse.class);

		if (WechatConstant.SUCCESS.equals(unifiedOrderResponse.getReturnCode())
				&& WechatConstant.SUCCESS.equals(unifiedOrderResponse.getResultCode())) {
			return unifiedOrderResponse;
		} else {
			logger.info("微信统一下单返回错误结果{}", unifiedOrderResponse.getErrCodeDes());
			throw new BusinessException(Integer.parseInt(unifiedOrderResponse.getErrCode()), unifiedOrderResponse.getErrCodeDes());
		}

	}

	/**
	 * 微信异步通知数据安全验证
	 * 
	 * @param notifyRequest
	 * @param apiKey
	 *            接口秘钥
	 * @param appId
	 *            小程序编号/公众号编号
	 * @param mchId
	 *            小程序商户号/公众号商户编码
	 * @return
	 */
	public boolean verify(NotifyRequest notifyRequest, String apiKey, String appId, String mchId) {

		// 签名验证
		String sign = WechatPayUtils.getSign(notifyRequest, apiKey);
		if (!sign.equals(notifyRequest.getSign())) {
			return false;
		}
		System.out.println("签名验证通过");
		// 订单验证
		OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
		if (null == orderForm) {
			return false;
		}
		System.out.println("订单验证通过");
		System.out.println(notifyRequest.getAppid() + "==" + appId);
		System.out.println(notifyRequest.getMchId() + "==" + mchId);
		// 卖家身份验证
		if ((!notifyRequest.getAppid().equals(appId)) || (!notifyRequest.getMchId().equals(mchId))) {
			return false;
		}
		System.out.println("卖家身份验证通过");
		return true;
	}

	/**
	 * 获取微信通知数据
	 * 
	 * @param request
	 *            请求参数
	 * @return 通知数据
	 */
	public String getNotifyXml(HttpServletRequest request) {

		StringBuilder requestStr = new StringBuilder();

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				requestStr.append(line);
			}
		} catch (IOException e) {
			logger.error("支付结果通知异常", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}

		return requestStr.toString();
	}

	/**
	 * 响应参数转换
	 * 
	 * @param notifyResponse
	 *            响应参数
	 * @return 响应字符
	 */
	public String responseNotify(NotifyResponse notifyResponse) {
		return WechatPayUtils.beanToXml(notifyResponse);
	}
	
	/**
	 * 截取字符串
	 * 
	 * @param sourceTitle
	 *            字符串
	 * @param maxNum
	 *            最大长度
	 * @return
	 */
	public String subTitle(String sourceTitle, int maxNum) {
		try {
			String newTitle = new String(sourceTitle.getBytes("UTF-8"), "UTF-8");
			byte[] charset_bytes = newTitle.getBytes("UTF-8");
			logger.info("newTitle:" + newTitle);
			int length = charset_bytes.length;
			logger.info("newTitle byte length:" + length);
			
			if (length > maxNum) {
				newTitle = newTitle.substring(0, newTitle.length() - 1);
				newTitle = catString(newTitle, maxNum);
			}
			return newTitle;
		} catch (Exception e) {
			logger.info("" + e);
		}
		return sourceTitle;
	}
	
	public String catString(String sourceTitle, int maxNum) throws UnsupportedEncodingException{
		int length = sourceTitle.getBytes("UTF-8").length;
		
		if (length > maxNum) {
			sourceTitle = sourceTitle.substring(0, sourceTitle.length() - 1);
			sourceTitle = catString(sourceTitle, maxNum);
		}
		return sourceTitle;
	}
}
