package com.party.web.web.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.utils.StringUtils;
import com.party.common.utils.refund.AlipayUtils;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.pay.ali.pc.TradePagePayRequest;
import com.party.pay.model.query.TradeStatus;
import com.party.web.biz.pay.AliPcBizService;
import com.party.web.biz.pay.PayPcOrderBizService;
import com.party.web.web.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TimeZone;

/**
 * 支付宝PC端扫码支付
 */
@Controller
@RequestMapping("pay/ali/pc")
public class PayAliPcController {
    /********************** 支付宝 ***********************/
    // 支付网关
    @Value("#{pay_ali_pc['ali.pc.gateway']}")
    private String gateway;

    // 接口名称
    @Value("#{pay_ali_pc['ali.pc.method']}")
    private String method;

    // 应用id
    @Value("#{pay_ali_pc['ali.pc.appid']}")
    private String appId;

    // 应用私钥
    @Value("#{pay_ali_pc['ali.pc.privatekey']}")
    private String privateKey;

    // 支付宝公钥
    @Value("#{pay_ali_pc['ali.pc.publickey']}")
    private String publicKey;

    // 签名方式
    @Value("#{pay_ali_pc['ali.pc.sign_type']}")
    private String signType;

    // 异步通知
    @Value("#{pay_ali_pc['ali.pc.notifyUrl']}")
    private String notifyUrl;

    // 同步返回
    @Value("#{pay_ali_pc['ali.pc.returnUrl']}")
    private String returnUrl;

    /********************** 支付宝 ***********************/

    // 返回正确状态码
    private static String SUCCESS = "success";

    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private AliPcBizService aliPcBizService;
    @Autowired
    private PayPcOrderBizService payPcOrderBizService;

    protected static Logger logger = LoggerFactory.getLogger(PayAliPcController.class);

    @RequestMapping("getResponse/{orderId}")
    public ModelAndView getResponse(@PathVariable("orderId") String orderId) {
        ModelAndView mv = new ModelAndView("charge/payForward");
//        AlipayClient alipayClient = new DefaultAlipayClient(gateway, appId, privateKey, "json", "utf-8", publicKey, signType);
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        alipayRequest.setReturnUrl(returnUrl);
//        alipayRequest.setNotifyUrl(notifyUrl);
//
//        OrderForm orderForm = orderFormService.get(orderId);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("out_trade_no", orderId); //商户订单号
//        map.put("product_code", "FAST_INSTANT_TRADE_PAY"); //销售产品码，与支付宝签约的产品码名称
//        map.put("total_amount", orderForm.getPayment()); //订单总金额，单位为元
//        map.put("subject", orderForm.getTitle()); //订单标题
//        alipayRequest.setBizContent(JSONObject.toJSONString(map));
//        String result = alipayClient.pageExecute(alipayRequest).getBody();
//        mv.addObject("body", result);
        return mv;
    }

    /**
     * 支付请求
     *
     * @param orderId
     * @return
     */
    @RequestMapping("{orderId}/buyOrder")
    public ModelAndView buyOrder(@PathVariable("orderId") String orderId) {
        ModelAndView mv = new ModelAndView("charge/payForward");
        try {
            OrderForm orderForm = orderFormService.get(orderId);
            if (StringUtils.isEmpty(orderId) || orderForm == null) {
                throw new Exception("订单不存在");
            }
            TradePagePayRequest request = new TradePagePayRequest();

            // 业务请求参数
            Map<String, Object> bizContentMap = Maps.newHashMap();
            bizContentMap.put("out_trade_no", orderForm.getId()); //商户订单号
            bizContentMap.put("product_code", "FAST_INSTANT_TRADE_PAY"); //销售产品码，与支付宝签约的产品码名称
            bizContentMap.put("total_amount", orderForm.getPayment()); //订单总金额，单位为元
            bizContentMap.put("subject", orderForm.getTitle()); //订单标题
            bizContentMap.put("body", orderForm.getTitle());
            Map<String, String> appParams = Maps.newHashMap();
            appParams.put(TradePagePayRequest.BIZ_CONTENT_KEY, JSONObject.toJSONString(bizContentMap));
            request.setAppParams(appParams);

            // 必要的参数
            Map<String, String> mustParams = Maps.newHashMap();
            mustParams.put(TradePagePayRequest.METHOD, method);
            mustParams.put(TradePagePayRequest.VERSION, "1.0");
            mustParams.put(TradePagePayRequest.APP_ID, appId);
            mustParams.put(TradePagePayRequest.SIGN_TYPE, signType);
            mustParams.put(TradePagePayRequest.NOTIFY_URL, notifyUrl);
            mustParams.put(TradePagePayRequest.RETURN_URL, returnUrl);
            mustParams.put(TradePagePayRequest.CHARSET, Constant.UTF_8);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            mustParams.put(TradePagePayRequest.TIMESTAMP, df.format(new Date()));
            request.setProtocalMustParams(mustParams);

            // 可选的参数
            Map<String, String> optParams = Maps.newHashMap();
            optParams.put(TradePagePayRequest.FORMAT, "json");
            request.setProtocalOptParams(optParams);

            // 支付类型
            orderForm.setPaymentWay(PaymentWay.ALI_PAY.getCode());
            orderForm.setTradeType(Constant.CLIENT_ALI_PC);
            orderFormService.update(orderForm);

            // 签名
            Map<String, String> sortedParams = Maps.newTreeMap();
            sortedParams.putAll(request.getAppParams());
            sortedParams.putAll(request.getProtocalMustParams());
            sortedParams.putAll(request.getProtocalOptParams());

            String sign = AlipayUtils.getSign(sortedParams, privateKey, Constant.UTF_8, false, true);
            mustParams.put(TradePagePayRequest.SIGN, sign);

            String requestUrl = aliPcBizService.getRequestUrl(request, Constant.UTF_8, gateway);
            String body = aliPcBizService.buildForm(requestUrl, appParams);
            mv.addObject("body", body);
        } catch (Exception e) {
            logger.error("PC端扫码支付异常", e);
        }
        return mv;
    }

    /**
     * 异步通知
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "notifyUrlResult")
    public String notifyUrlResult(HttpServletRequest request) {
        logger.info("收到支付宝异步通知！");
        Map<String, String> params = Maps.newHashMap();
        //取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }

        boolean verify = verify(params);
        if (!verify) {
            logger.info("数据验证失败");
            return "数据验证失败";
        }

        String tradeStatus = params.get("trade_status");
        if (!TradeStatus.ALI_TRADE_SUCCESS.getCode().equals(tradeStatus) && !TradeStatus.ALI_TRADE_FINISHED.getCode().equals(tradeStatus)) {
            logger.info("支付未成功");
            return "支付未成功";
        }

        OrderForm orderForm = orderFormService.get(params.get("out_trade_no"));
        if (OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode().equals(orderForm.getStatus())) {
            logger.info("准备更改状态");
            payPcOrderBizService.updatePayBusiness(orderForm, params, PaymentWay.ALI_PAY.getCode());
            logger.info("准备更改状态成功");
        }
        return SUCCESS;
    }

    /**
     * 同步返回通知
     *
     * @param request
     * @return
     */
    @RequestMapping("returnUrlResult")
    public ModelAndView returnUrlResult(HttpServletRequest request) {
        logger.info("收到支付宝同步通知！");
        ModelAndView mv = new ModelAndView("redirect:/system/member/memberIndex.do");
        Map<String, String> params = Maps.newHashMap();
        //取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }

        boolean verify = verify(params);
        if (!verify) {
            logger.info("数据验证失败");
        }
        return mv;
    }

    /**
     * 检查订单状态
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping("checkOrderStatus")
    public AjaxResult checkOrderStatus(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return AjaxResult.error("订单id不能为空");
        }
        OrderForm orderForm = orderFormService.get(orderId);
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", orderForm.getStatus());
        return AjaxResult.success(map);
    }

    /**
     * 数据验证
     *
     * @param params
     * @return
     */
    public boolean verify(Map<String, String> params) {
        try {
            // 验证签名 除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。
            String sign = params.get("sign");
            params.remove("sign");
            params.remove("sign_type");
            String signContent = aliPcBizService.getSignContent(params);
            boolean signVerified = AlipayUtils.verify(signContent, sign, publicKey, Constant.UTF_8, true);
            if (!signVerified) {
                logger.info("验签失败");
                return false;
            } else {
                // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
                String outTradeNo = params.get("out_trade_no");
                if (StringUtils.isEmpty(outTradeNo)) {
                    logger.info("订单号为空");
                    return false;
                }
                OrderForm orderForm = orderFormService.get(outTradeNo);
                if (orderForm == null) {
                    logger.info("订单为空");
                    return false;
                }

                // 2、判断total_amount是否确实为该订单的实际金额
                String totalAmount = params.get("total_amount");
                if (StringUtils.isEmpty(totalAmount)) {
                    logger.info("金额为空");
                    return false;
                } else {
                    if (!orderForm.getPayment().equals(Float.valueOf(totalAmount))) {
                        logger.info("金额不匹配");
                        return false;
                    }
                }
                // 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方
                String sellerId = params.get("seller_id");
                logger.info(sellerId);

                // 4、验证app_id是否为该商户本身
                String appid = params.get("app_id");
                if (!appId.equals(appid)) {
                    logger.info("与付款时的appid不同，此为异常通知，应忽略！");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("信息验证成功");
        return true;
    }


}
