package com.party.mobile.web.controller.appPay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.redis.StringJedis;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.order.*;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.biz.wechat.WechatPayBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.appAlipay.input.AppPayConstant;
import com.party.mobile.web.dto.appWechatPay.input.AppNotifyResponse;
import com.party.mobile.web.dto.appWechatPay.output.AppNotifyRequest;
import com.party.mobile.web.dto.wechat.input.UnifiedOrderResponse;
import com.party.mobile.web.dto.wechat.output.PayRequest;
import com.party.mobile.web.dto.wechat.output.UnifiedOrderRequest;
import com.party.common.utils.PartyCode;
import com.party.mobile.web.utils.VerifyCodeUtils;
import com.party.common.constant.WechatConstant;
import com.party.mobile.web.utils.WechatPayUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * app微信支付controller
 * @author wei.li
 * @version 2016/7/15 0015
 */

@Controller
@RequestMapping("/apppay/wechatpay")
public class AppWechatPayController {


    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    WechatPayBizService wechatPayBizService;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    IActivityService activityService;
    //微信开发者ID
    @Value("${app.wechatpay.appid}")
    private String appId;

    //微信商户平台商户号
    @Value("${app.wechat.mchid}")
    private String mchId;

    //微信支付回调地址
    @Value("${app.wechat.notifyurl}")
    private String notifyUrl;

    //微信签名令牌
    @Value("${wechat.apikey}")
    private String apiKey;

    protected static Logger logger = LoggerFactory.getLogger(AppWechatPayController.class);

    /**
     * 获取微信支付请求参数
     * @param orderId 订单号
     * @param request 请求参数
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping("/createPayData")
    public AjaxResult getPayData(String orderId , String openId,  HttpServletRequest request){

        //数据验证
        if (Strings.isNullOrEmpty(orderId)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL,"订单号不能为空");
        }

        //TODO 测试数据
        /*openId = "oLaigtz183G7SfqR2IVEKXLzobGs";*/
        if (Strings.isNullOrEmpty(openId)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "缺少支付参数 openId");
        }

        //订单验证
        OrderForm orderForm = orderFormService.get(orderId);
        if (null == orderForm){
            return AjaxResult.error(PartyCode.ORDER_UNEXIST, "订单不存在");
        }
        if (!orderForm.getIsPay().equals(PaymentState.NO_PAY.getCode()) || orderForm.getStatus() != OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode())
        {
            return AjaxResult.error(PartyCode.PAYMENT_STATUS_ERROR, "订单状态不正确");
        }
        //统一下单
        String nonceStr = VerifyCodeUtils.RandomString(WechatConstant.RANDOM_LENGTH);//获取随机数
        String ipAddress = request.getRemoteAddr();//终端IP

        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

        if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode())
        {
            Activity activity = activityService.get(orderForm.getGoodsId());
            unifiedOrderRequest.setBody(activity.getTitle());//商品描叙
        }
        else {
            Goods goods = goodsService.get(orderForm.getGoodsId());
            unifiedOrderRequest.setBody(goods.getTitle());//商品描叙

        }


        unifiedOrderRequest.setAppid(appId);//开放平台编号
        unifiedOrderRequest.setMchId(mchId);//商户编号
//        unifiedOrderRequest.setOpenid(openId);//用户编号
        unifiedOrderRequest.setNonceStr(nonceStr);//随机数
        unifiedOrderRequest.setOutTradeNo(orderForm.getId());//订单号
        unifiedOrderRequest.setTotalFee((int)(orderForm.getPayment()*100));//单位分
        unifiedOrderRequest.setSpbillCreateIp(ipAddress);//请求IP
        unifiedOrderRequest.setNotifyUrl(notifyUrl);//回调地址
        unifiedOrderRequest.setTradeType("APP");//交易类型
        UnifiedOrderResponse unifiedorderResponse = wechatPayBizService.unifiedOrder(unifiedOrderRequest, apiKey);
        if (null == unifiedorderResponse){
            return AjaxResult.error(PartyCode.UNIFIEDORDER_ERROR, "微信统一下单异常");
        }

        //发起下单
        String prepayId = unifiedorderResponse.getPrepayId();
        PayRequest payRequest = new PayRequest();
        payRequest.setAppId(appId);//开放平台编号
        payRequest.setTimeStamp(String.valueOf(System.currentTimeMillis()));//时间戳
        payRequest.setNonceStr(nonceStr);//随机数
        payRequest.setPackages(WechatConstant.PREPAY_ID + prepayId);//预授权码
        payRequest.setSignType(WechatConstant.MD5_TYPE);//加密方式
        
        //获取签名
        String sign = WechatPayUtils.getSign(payRequest, apiKey);
        payRequest.setPaySign(sign);
        payRequest.setMchId(mchId);
        payRequest.setPrepayId(unifiedorderResponse.getPrepayId());
        System.out.println(JSONObject.toJSON(payRequest));
        return AjaxResult.success(payRequest);
    }



    /**
     * 获取微信支付异步通知
     * @param request 请求参数
     * @return 返回参数
     */
    @ResponseBody
    @RequestMapping("acceptNotify")
    public String acceptNotify(HttpServletRequest request){
        String requestStr = getNotifyXml(request);
        logger.info("获取微信支付异步通知数据:{}",requestStr);

        if (Strings.isNullOrEmpty(requestStr)){
            return responseNotify(AppNotifyResponse.error("请求参数为空"));
        }
        AppNotifyRequest notifyRequest = WechatPayUtils.xmlToBean(requestStr, AppNotifyRequest.class);

        // 验证数据安全
        boolean verify = verify(notifyRequest);
        if (!verify){
            return responseNotify(AppNotifyResponse.error("安全验证不通过"));
        }

        //支付结果验证
        if ((!AppPayConstant.SUCCESS.equals(notifyRequest.getReturnCode()))
                || (!AppPayConstant.SUCCESS.equals(notifyRequest.getResultCode()))){
            return responseNotify(AppNotifyResponse.error("支付结果不成功"));
        }

        OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
        if (orderForm.getIsPay().equals(PaymentState.NO_PAY.getCode())){
            //支付订单
            try {
                orderBizService.payOrder(orderForm, notifyRequest , PaymentWay.WECHAT_PAY.getCode());
            } catch (Exception e) {
                logger.error("订单支付异常",e);
                return responseNotify(AppNotifyResponse.error("同行者支付失败"));
            }
        }
        return responseNotify(AppNotifyResponse.success());
    }


    /**
     * 微信异步通知数据安全验证
     * @param notifyRequest
     * @return
     */
    private boolean verify(AppNotifyRequest notifyRequest){

        //签名验证
        String sign = WechatPayUtils.getSign(notifyRequest, apiKey);
        if (!sign.equals(notifyRequest.getSign())){
            return false;
        }

        //订单验证
        OrderForm orderForm = orderFormService.get(notifyRequest.getOutTradeNo());
        if (null == orderForm){
            return false;
        }

        //卖家身份验证
        if ((!notifyRequest.getAppid().equals(appId)) || (!notifyRequest.getMchId().equals(mchId))){
            return false;
        }
        return true;
    }


    /**
     * 获取微信通知数据
     * @param request 请求参数
     * @return 通知数据
     */
    private String getNotifyXml(HttpServletRequest request) {

        StringBuilder requestStr = new StringBuilder();

        BufferedReader bufferedReader = null;
        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                requestStr.append(line);
            }
        }
        catch (IOException e) {
            logger.error("支付结果通知异常",e);
        }
        finally {
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
     * @param notifyResponse 响应参数
     * @return 响应字符
     */
    private String responseNotify(AppNotifyResponse notifyResponse){
        return WechatPayUtils.beanToXml(notifyResponse);
    }
}
