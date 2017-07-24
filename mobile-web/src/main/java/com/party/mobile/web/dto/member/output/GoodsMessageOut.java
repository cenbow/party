package com.party.mobile.web.dto.member.output;

import com.party.core.model.message.Message;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 会员消息输出视图(商品，活动消息)
 * party
 * Created by Wesley
 * on 2016/11/6
 */
public class GoodsMessageOut extends MessageOut{

    //商品或者活动的订单id
    private String orderId;

    //商品或者活动的订单状态
    private Integer orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 消息转消息输出视图
     * @param message 会员
     * @return 交互数据
     */
    public static GoodsMessageOut transform(Message message){
        GoodsMessageOut messageOut = new GoodsMessageOut();
        BeanUtils.copyProperties(message, messageOut);
        return messageOut;
    }
}