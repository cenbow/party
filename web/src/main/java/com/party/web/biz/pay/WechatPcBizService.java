package com.party.web.biz.pay;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.VerifyCodeUtils;
import com.party.common.utils.refund.WechatPayUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.order.OrderForm;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.pay.wechat.NotifyRequest;
import com.party.pay.model.pay.wechat.NotifyResponse;
import com.party.pay.model.pay.wechat.UnifiedOrderResponse;
import com.party.pay.model.pay.wechat.pc.QrCodeRequest;
import com.party.pay.model.pay.wechat.pc.QrCodeResponse;
import com.party.pay.model.pay.wechat.pc.UnifiedOrderRequest;

/**
 * 微信Pc扫码支付业务
 */
@Service
public class WechatPcBizService {

    @Autowired
    private IOrderFormService orderFormService;

    protected static Logger logger = LoggerFactory.getLogger(WechatPcBizService.class);

    /**
     * 获取商品的支付二维码连接
     *
     * @param orderId 订单id
     * @param appId
     * @param mchId
     * @param apiKey
     * @return
     */
    public String getQrCodeUrl(String orderId, String appId, String mchId, String apiKey) {
        QrCodeRequest request = new QrCodeRequest();
        request.setAppId(appId);
        request.setMchId(mchId);
        String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
        request.setNonceStr(nonceStr);
        request.setProductId(orderId);
        request.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        // 获取签名
        String sign = WechatPayUtils.getSign(request, apiKey);
        request.setSign(sign);
        String qrCodeUrl = WechatConstant.QR_PAY_CONTENT_URL;
        qrCodeUrl = qrCodeUrl.replace("SIGN", request.getSign());
        qrCodeUrl = qrCodeUrl.replace("APPID", request.getAppId());
        qrCodeUrl = qrCodeUrl.replace("MCH_ID", request.getMchId());
        qrCodeUrl = qrCodeUrl.replace("PRODUCT_ID", orderId); // 订单id
        qrCodeUrl = qrCodeUrl.replace("TIME_STAMP", request.getTimeStamp());
        qrCodeUrl = qrCodeUrl.replace("NONCE_STR", request.getNonceStr());
        logger.info("微信二维码链接：{}", qrCodeUrl);
        return qrCodeUrl;
    }

    /**
     * 支付二维码连接转短链接
     *
     * @param url    长链接
     * @param appId
     * @param mchId
     * @param apiKey
     * @return
     */
    public String longToShort(String url, String appId, String mchId, String apiKey) {
        try {
            String encodeUrl = URLEncoder.encode(url, Constant.UTF_8);
            Map<String, String> params = Maps.newHashMap();
            params.put("appid", appId);
            params.put("mch_id", mchId);
            params.put("long_url", encodeUrl);
            String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
            params.put("nonce_str", nonceStr);
            params.put("sign_type", WechatConstant.MD5_TYPE);
            String sign = WechatPayUtils.getSign(params, apiKey);
            params.put("sign", sign);
            String requestData = WechatPayUtils.mapToXml(params);
            logger.info("转换短链接请求：{}", requestData);
            String responseData = WechatPayUtils.httpsPost(WechatConstant.QR_LONG_TO_SHORT_URL, requestData);
            Map<String, String> responseMap = WechatPayUtils.xmlToMap(responseData);
            logger.info("转换短链接响应：{}", responseData);
            if (responseMap.get("return_code").equals(Constant.WECHAT_SUCCESS) && responseMap.get("result_code").equals(Constant.WECHAT_SUCCESS)) {
                String shortUrl = responseMap.get("short_url");
                return shortUrl;
            }
            logger.info(responseData);
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return null;
    }

    /**
     * 获取统一下单的请求参数
     *
     * @param request
     * @param response
     * @param notifyUrl
     * @return
     * @throws Exception
     */
    public UnifiedOrderRequest getUnifiedOrderRequest(HttpServletRequest request, QrCodeResponse response, String notifyUrl) throws Exception {
        OrderForm orderForm = orderFormService.get(response.getProductId()); // 订单
        String ipAddress = request.getRemoteAddr();// 终端IP
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setAppid(response.getAppid());// 公众账号ID
        unifiedOrderRequest.setMchId(response.getMchId());// 商户编号
        String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
        unifiedOrderRequest.setNonceStr(nonceStr);// 随机数
        String body = WechatPayUtils.subTitle(orderForm.getTitle(), 125);
        unifiedOrderRequest.setBody(body); // 描述
        unifiedOrderRequest.setOutTradeNo(orderForm.getId()); //商户订单号
        double total = BigDecimalUtils.mul(orderForm.getPayment(), 100);
        total = BigDecimalUtils.round(total, 2);
        unifiedOrderRequest.setTotalFee((int) total);// 单位分
        unifiedOrderRequest.setSpbillCreateIp(ipAddress);// 请求IP
        unifiedOrderRequest.setNotifyUrl(notifyUrl);// 回调地址
        unifiedOrderRequest.setTradeType(WechatConstant.TRADE_TYPE_NATIVE);// 交易类型
        unifiedOrderRequest.setProductId(response.getProductId()); //商品ID
        unifiedOrderRequest.setOpenid(response.getOpenid());
        return unifiedOrderRequest;
    }

    /**
     * 微信统一下单
     *
     * @param unifiedorderRequest 统一下单请求参数
     * @param apiKey              接口秘钥
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
     * @param apiKey        接口秘钥
     * @param appId         小程序编号/公众号编号
     * @param mchId         小程序商户号/公众号商户编码
     * @return
     */
    public boolean verify(NotifyRequest notifyRequest, String apiKey, String appId, String mchId) {

        // 签名验证
        String sign = WechatPayUtils.getSign(notifyRequest, apiKey);
        if (!sign.equals(notifyRequest.getSign())) {
            logger.info("签名验证失败");
            return false;
        }

        logger.info("签名验证通过");

        // 订单验证
        OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
        if (null == orderForm) {
            return false;
        }
        // 验证 微信返回的金额和订单金额是否一致
        if (null != orderForm.getPayment()) {
            double total = BigDecimalUtils.mul(orderForm.getPayment(), 100);
            int payment = (int) (BigDecimalUtils.round(total, 2));
            String totalFee = notifyRequest.getTotalFee();
            if (Integer.valueOf(payment).equals(Integer.valueOf(totalFee))) {
                logger.info("订单验证通过");
            }
        }

        // 卖家身份验证
        if ((!notifyRequest.getAppid().equals(appId)) || (!notifyRequest.getMchId().equals(mchId))) {
            return false;
        }
        logger.info("卖家身份验证通过");
        return true;
    }

    /**
     * 响应参数转换
     *
     * @param notifyResponse 响应参数
     * @return 响应字符
     */
    public String responseNotify(NotifyResponse notifyResponse) {
        return WechatPayUtils.beanToXml(notifyResponse);
    }
}
