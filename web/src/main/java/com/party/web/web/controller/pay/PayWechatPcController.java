package com.party.web.web.controller.pay;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.constant.WechatConstant;
import com.party.common.utils.VerifyCodeUtils;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.pay.wechat.NotifyRequest;
import com.party.pay.model.pay.wechat.NotifyResponse;
import com.party.pay.model.pay.wechat.UnifiedOrderRequest;
import com.party.pay.model.pay.wechat.UnifiedOrderResponse;
import com.party.pay.model.pay.wechat.pc.NativePayRequest;
import com.party.pay.model.pay.wechat.pc.QrCodeResponse;
import com.party.web.biz.pay.PayPcOrderBizService;
import com.party.web.biz.pay.WechatPcBizService;
import com.party.web.utils.QRCodeUtil;
import com.party.web.utils.WechatPayUtils;
import com.party.web.web.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 微信PC端扫码支付
 */
@Controller
@RequestMapping("pay/wechat/pc")
public class PayWechatPcController {

    // 公众号商户编码
    @Value("#{pay_wechat_pc['wechat.pc.mchId']}")
    private String mchId;

    // 公众号编号
    @Value("#{pay_wechat_pc['wechat.pc.appId']}")
    private String appId;

    // 微信接口密钥
    @Value("#{pay_wechat_pc['wechat.pc.apiKey']}")
    private String apiKey;

    // 扫码回调地址
    @Value("#{pay_wechat_pc['wechat.pc.notifyUrl']}")
    private String notifyUrl;

    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private IOrderFormInfoService orderFormInfoService;
    @Autowired
    private WechatPcBizService wechatPcBizService;
    @Autowired
    private PayPcOrderBizService payPcOrderBizService;

    protected static Logger logger = LoggerFactory.getLogger(PayWechatPcController.class);


    /**
     * 生成二维码
     *
     * @param orderId
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("{orderId}/getQrCodeImg")
    public void getQrCodeImg(@PathVariable("orderId") String orderId, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);

        String qrCodeUrl = wechatPcBizService.getQrCodeUrl(orderId, appId, mchId, apiKey);
        String shortUrl = wechatPcBizService.longToShort(qrCodeUrl, appId, mchId, apiKey);
        BufferedImage image = QRCodeUtil.encode(shortUrl);
        ImageIO.write(image, "png", response.getOutputStream());
    }

    /**
     * 扫码回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("scanNotify")
    public AjaxResult scanNotify(HttpServletRequest request) {
        logger.info("微信支付系统收到客户端请求，发起对商户后台系统支付回调URL的调用");
        try {
            String responseData = wechatPcBizService.getNotifyXml(request);
            // 扫描二维码响应
            logger.info("微信扫描二维码响应数据：{}", responseData);
            QrCodeResponse qrCodeResponse = WechatPayUtils.xmlToBean(responseData, QrCodeResponse.class);

            // 统一下单请求参数
            UnifiedOrderRequest unifiedOrderRequest = wechatPcBizService.getUnifiedOrderRequest(request, qrCodeResponse, notifyUrl);
            // 统一下单响应结果
            UnifiedOrderResponse unifiedorderResponse = wechatPcBizService.unifiedOrder(unifiedOrderRequest, apiKey);

            if (null == unifiedorderResponse) {
                return AjaxResult.error("微信统一下单异常");
            }
            OrderForm orderForm = orderFormService.get(qrCodeResponse.getProductId());
            if (Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(unifiedorderResponse.getResultCode())) {
                // 平台
                orderForm.setTradeType(Constant.CLIENT_WX_PC);
                // 商户号
                orderForm.setMerchantId(mchId);
                // 支付类型
                orderForm.setPaymentWay(PaymentWay.WECHAT_PAY.getCode());
                orderFormService.update(orderForm);

                // 保存订单附属信息
                orderFormInfoService.saveOrderFormInfo(appId, mchId, apiKey, orderForm.getId());

                // 发起下单
                NativePayRequest payRequest = new NativePayRequest();
                payRequest.setReturnCode(Constant.WECHAT_SUCCESS); // SUCCESS
                payRequest.setResultCode(Constant.WECHAT_SUCCESS); // SUCCESS
                payRequest.setReturnMsg("OK");
                payRequest.setErrCodeDes("OK");
                String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);// 获取随机数
                payRequest.setNonceStr(nonceStr);// 随机数
                payRequest.setPrepayId(unifiedorderResponse.getPrepayId()); //交易会话标识 ,2小时内有效
                payRequest.setAppId(unifiedorderResponse.getAppid());// 开放平台编号
                payRequest.setMchId(unifiedorderResponse.getMchId()); // 商户号

                // 获取签名
                String sign = WechatPayUtils.getSign(payRequest, apiKey);
                payRequest.setSign(sign);

                String responseXml = WechatPayUtils.beanToXml(payRequest);
                logger.info("微信发起Native扫码支付请求参数:{}", responseXml);
                return AjaxResult.success(responseXml);
            }
        } catch (Exception e) {
            logger.error("扫描二维码回调异常", e);
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error("");
    }

    /**
     * 获取微信支付异步通知
     *
     * @param request 请求参数
     * @return 返回参数
     */
    @ResponseBody
    @RequestMapping("payNotify")
    public String payNotify(HttpServletRequest request) {
        try {
            // String requestStr = "<xml><appid><![CDATA[wx4a3cd04f189325db]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[2]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1371766102]]></mch_id><nonce_str><![CDATA[r4dTOXJaYxTiv0zHIpMoruOc7m4ZSrRc]]></nonce_str><openid><![CDATA[osScduNn3hOxKQXkFAMuII_Ow-l0]]></openid><out_trade_no><![CDATA[a4c8f693ec78418791797228ed514467]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[AD52D99B5220C4B2951F809F4CF8913B]]></sign><time_end><![CDATA[20170719170039]]></time_end><total_fee>2</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4000142001201707191581188468]]></transaction_id></xml>";
            String requestStr = wechatPcBizService.getNotifyXml(request);
            logger.info("获取微信支付异步通知数据:{}", requestStr);

            if (Strings.isNullOrEmpty(requestStr)) {
                return wechatPcBizService.responseNotify(NotifyResponse.error("请求参数为空"));
            }
            NotifyRequest notifyRequest = WechatPayUtils.deserialize(requestStr, NotifyRequest.class);

            logger.info("订单编号{}", notifyRequest.getOutTradeNo());
            OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
            logger.info("订单信息{}", orderForm.toString());

            // 验证数据安全
            boolean verify = wechatPcBizService.verify(notifyRequest, apiKey, appId, mchId);
            if (!verify) {
                return wechatPcBizService.responseNotify(NotifyResponse.error("安全验证不通过"));
            }

            // 支付结果验证
            if ((!WechatConstant.SUCCESS.equals(notifyRequest.getReturnCode())) || (!WechatConstant.SUCCESS.equals(notifyRequest.getResultCode()))) {
                return wechatPcBizService.responseNotify(NotifyResponse.error("支付结果不成功"));
            }

            if (OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode().equals(orderForm.getStatus())) {
                logger.info("准备更改状态");
                payPcOrderBizService.updatePayBusiness(orderForm, notifyRequest, PaymentWay.WECHAT_PAY.getCode());
                logger.info("准备更改状态成功");
            }
            return wechatPcBizService.responseNotify(NotifyResponse.success());
        } catch (Exception e) {
            logger.error("支付回调异常", e);
            return wechatPcBizService.responseNotify(NotifyResponse.error("支付回调异常"));
        }
    }

}
