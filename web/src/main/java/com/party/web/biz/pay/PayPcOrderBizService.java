package com.party.web.biz.pay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.ProductPackage;
import com.party.core.model.order.*;
import com.party.core.model.system.MemberSysRole;
import com.party.core.service.charge.IPackageMemberService;
import com.party.core.service.charge.IPackageService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.pay.model.pay.wechat.NotifyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付业务处理
 */
@Service
public class PayPcOrderBizService {
    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private IOrderTradeService orderTradeService;
    @Autowired
    private IPackageService packageService;
    @Autowired
    private IPackageMemberService packageMemberService;
    @Autowired
    private INotifySendService notifySendService;
    @Autowired
    private IMemberSysRoleService memberSysRoleService;

    protected static Logger logger = LoggerFactory.getLogger(PayPcOrderBizService.class);

    public <T> void updatePayBusiness(OrderForm orderForm, T data, Integer paymentWay) {
        logger.info("修改订单状态");
        // 修改订单状态
        orderForm.setIsPay(PaymentState.IS_PAY.getCode());
        orderForm.setPaymentWay(paymentWay);
        orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        orderFormService.update(orderForm);

        // 保存支付流水
        saveOrderTrade(orderForm, data, paymentWay);

        // 保存用户等级 更改用户角色
        saveLevelMember(orderForm);

        notifySendService.sendBuyLevel(orderForm);
    }

    private Map<String, Date> calculateDate(ProductPackage productPackage, PackageMember packageMember) {
        Date startTime = null;
        Date endTime = null;
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTime(); // 开始时间
        if (packageMember != null) {
            calendar.setTime(packageMember.getEndTime());
        }
        if (productPackage.getUnit().equals(ProductPackage.UNIT_MONTH)) {
            calendar.add(Calendar.MONTH, 1);
        } else if (productPackage.getUnit().equals(ProductPackage.UNIT_QUARTER)) {
            calendar.add(Calendar.MONTH, 3);
        } else if (productPackage.getUnit().equals(ProductPackage.UNIT_HALF_YEAR)) {
            calendar.add(Calendar.MONTH, 6);
        } else if (productPackage.getUnit().equals(ProductPackage.UNIT_YEAR)) {
            calendar.add(Calendar.YEAR, 1);
        }
        endTime = calendar.getTime(); // 结束时间

        Map<String, Date> result = Maps.newHashMap();
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        logger.info("开始时间与结束时间{}", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 保存用户等级 更改用户角色
     *
     * @param orderForm
     */
    public void saveLevelMember(OrderForm orderForm) {
        logger.info("保存用户等级 更改用户角色");
        PackageMember packageMember = packageMemberService.findByMemberId(new PackageMember(orderForm.getGoodsId(), orderForm.getMemberId()));
        logger.info("商品Id{}用户Id{}", orderForm.getGoodsId(), orderForm.getMemberId());
        ProductPackage productPackage = packageService.get(orderForm.getGoodsId());
        logger.info("商品{}", productPackage);

        Map<String, Date> resultMap = calculateDate(productPackage, packageMember);
        Date startTime = resultMap.get("startTime");
        logger.info("开始时间{}", startTime);
        Date endTime = resultMap.get("endTime");
        logger.info("结束时间{}", endTime);

        if (packageMember != null) {
            // 续期
            packageMember.setEndTime(endTime);
            packageMemberService.update(packageMember);
        } else {
            // 开通
            packageMember = new PackageMember();
            packageMember.setLevelId(orderForm.getGoodsId());
            packageMember.setMemberId(orderForm.getMemberId());
            packageMember.setSysRoleId(productPackage.getSysRoleId());
            packageMember.setStartTime(startTime);
            packageMember.setEndTime(endTime);
            packageMember.setStatus(1);
            packageMemberService.insert(packageMember);


            List<MemberSysRole> roles = memberSysRoleService.list(new MemberSysRole(orderForm.getMemberId(), productPackage.getSysRoleId()));
            if (roles.size() == 0) {
                MemberSysRole sysRole = new MemberSysRole();
                sysRole.setMemberId(orderForm.getMemberId());
                sysRole.setRoleId(productPackage.getSysRoleId());
                memberSysRoleService.insert(sysRole);
            }
        }
    }

    /**
     * 持久化交易信息
     *
     * @param orderForm  订单信息
     * @param object     支付信息
     * @param paymentWay 支付方式
     */
    public void saveOrderTrade(OrderForm orderForm, Object object, Integer paymentWay) {
        logger.info("保存支付流水");
        OrderForm t = orderFormService.get(orderForm.getId());

        // 持久化交易信息
        OrderTrade orderTrade = new OrderTrade();
        orderTrade.setOrderFormId(orderForm.getId());

        orderTrade.setOrigin(OrderOrigin.ORDER_ORIGIN_LEVEL.getCode());

        // 判断支付方式
        if (PaymentWay.ALI_PAY.getCode().equals(paymentWay)) {
            Map<String, String> params = (Map<String, String>) object;
            orderTrade.setType(PaymentWay.ALI_PAY.getCode());
            orderTrade.setTransactionId(params.get("trade_no"));
            orderTrade.setData(JSONObject.toJSONString(params));
            t.setPayment(Float.valueOf(params.get("total_amount")));
            t.setTradeState(params.get("trade_status"));
            t.setTransactionId(params.get("trade_no"));
            orderFormService.update(t);
        } else if (PaymentWay.WECHAT_PAY.getCode().equals(paymentWay)) {
            NotifyRequest notifyRequest = (NotifyRequest) object;
            orderTrade.setTransactionId(notifyRequest.getTransactionId());
            orderTrade.setType(PaymentWay.WECHAT_PAY.getCode());
            orderTrade.setData(JSONObject.toJSONString(notifyRequest));
            double price = BigDecimalUtils.div(Double.valueOf(notifyRequest.getTotalFee()), 100);
            t.setPayment((float) price);
            t.setMerchantId(notifyRequest.getMchId());
            t.setTradeState(notifyRequest.getResultCode());
            t.setTransactionId(notifyRequest.getTransactionId());
            orderFormService.update(t);
        }
        orderTradeService.insert(orderTrade);
    }

}
