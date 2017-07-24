package com.party.web.biz.pay;

import com.alibaba.fastjson.JSONObject;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.charge.Level;
import com.party.core.model.charge.LevelMember;
import com.party.core.model.order.*;
import com.party.core.model.system.MemberSysRole;
import com.party.core.service.charge.ILevelMemberService;
import com.party.core.service.charge.ILevelService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.pay.model.pay.wechat.NotifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    private ILevelService levelService;
    @Autowired
    private ILevelMemberService levelMemberService;
    @Autowired
    private INotifySendService notifySendService;
    @Autowired
    private IMemberSysRoleService memberSysRoleService;

    public <T> void updatePayBusiness(OrderForm orderForm, T data, Integer paymentWay) {
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

    /**
     * 保存用户等级 更改用户角色
     *
     * @param orderForm
     */
    public void saveLevelMember(OrderForm orderForm) {
        LevelMember levelMember = new LevelMember();
        levelMember.setLevelId(orderForm.getGoodsId());
        levelMember.setMemberId(orderForm.getMemberId());

        Level level = levelService.get(orderForm.getGoodsId());
        Date startTime = null;
        Date endTime = null;
        if (level.getUnit().equals(Level.UNIT_MONTH)) {
            Calendar calendar = Calendar.getInstance();
            startTime = calendar.getTime(); // 开始时间
            calendar.add(Calendar.MONTH, 1);
            endTime = calendar.getTime(); // 结束时间
        } else if (level.getUnit().equals(Level.UNIT_QUARTER)) {
            Calendar calendar = Calendar.getInstance();
            startTime = calendar.getTime(); // 开始时间
            calendar.add(Calendar.MONTH, 3);
            endTime = calendar.getTime(); // 结束时间
        } else if (level.getUnit().equals(Level.UNIT_HALF_YEAR)) {
            Calendar calendar = Calendar.getInstance();
            startTime = calendar.getTime(); // 开始时间
            calendar.add(Calendar.MONTH, 6);
            endTime = calendar.getTime(); // 结束时间
        } else if (level.getUnit().equals(Level.UNIT_YEAR)) {
            Calendar calendar = Calendar.getInstance();
            startTime = calendar.getTime(); // 开始时间
            calendar.add(Calendar.YEAR, 1);
            endTime = calendar.getTime(); // 结束时间
        }
        levelMember.setSysRoleId(level.getSysRoleId());
        levelMember.setStartTime(startTime);
        levelMember.setEndTime(endTime);
        levelMember.setStatus(1);
        levelMemberService.insert(levelMember);


        MemberSysRole sysRole = new MemberSysRole();
        sysRole.setMemberId(orderForm.getMemberId());
        sysRole.setRoleId(level.getSysRoleId());
        memberSysRoleService.insert(sysRole);
    }

    /**
     * 持久化交易信息
     *
     * @param orderForm  订单信息
     * @param object     支付信息
     * @param paymentWay 支付方式
     */
    public void saveOrderTrade(OrderForm orderForm, Object object, Integer paymentWay) {
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
