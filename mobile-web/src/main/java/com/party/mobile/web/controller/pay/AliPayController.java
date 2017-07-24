package com.party.mobile.web.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.alipay.output.Content;
import com.party.mobile.web.dto.alipay.output.PayRequest;
import com.party.mobile.web.utils.AlipayConstant;
import com.party.mobile.web.utils.AlipayUtils;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 支付报支付控制层
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */

@Controller
@RequestMapping(value = "/pay/alipay")
public class AliPayController {

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    IGoodsService goodsService;

    //支付宝开发者编号
    @Value("${alipay.appid}")
    private String appid;

    //支付宝回调地址
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    //支付宝支付私钥
    @Value("${alipay.privatekey}")
    private String alipayPrivatekey;

    @Autowired
    IActivityService activityService;


    /**
     * 支付宝支付参数
     * @param orderId 订单号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getPayData")
    public AjaxResult getPayData(String orderId){

        //验证参数
        if (Strings.isNullOrEmpty(orderId))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "订单号不能为空");
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

        //支付参数
        Content content = new Content();
        if (orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode())
        {
            Activity activity = activityService.get(orderForm.getGoodsId());
            content.setSubject(activity.getTitle());//商品的标题
        }
        else {
            Goods goods = goodsService.get(orderForm.getGoodsId());
            content.setSubject(goods.getTitle());//商品的标题
        }


        content.setOutTradeNo(orderForm.getId());//商户网站唯一订单号
        content.setTotalAmount(orderForm.getPayment().toString());//订单总金额
        content.setProductCode(AlipayConstant.PRODUCT_CODE);

        PayRequest payRequest = new PayRequest();
        payRequest.setAppId(appid);//开发者编号
        payRequest.setMethod(AlipayConstant.METHOD);//接口名称
        payRequest.setCharset(AlipayConstant.CHARSET);//请求使用的编码格式
        payRequest.setSignType(AlipayConstant.SIGN_TYPE);//签名算法类型
        payRequest.setTimestamp(DateUtils.nowDateTime());//发送请求的时间
        payRequest.setVersion(AlipayConstant.VERSION);//调用的接口版本
        payRequest.setNotifyUrl(notifyUrl);//回调地址
        payRequest.setBizContent(JSONObject.toJSONString(content));//支付参数、

        //签名
        String sign = AlipayUtils.getSign(payRequest, alipayPrivatekey, AlipayConstant.CHARSET);
        payRequest.setSign(sign);
        String request = AlipayUtils.getRequestStr(payRequest);
        return AjaxResult.success((Object) request);
    }
}
