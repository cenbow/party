package com.party.admin.web.dto.output.distribution;

import com.party.core.model.order.OrderForm;
import org.springframework.beans.BeanUtils;

/**
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 15:56
 */
public class OrderListOutput {

    //编号
    private String id;


    //名称
    private String title;

    //买家
    private String buyer;

    //付款金额
    private Float payment;

    // 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
    private Integer status;

    //购买份数
    private Integer unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public static OrderListOutput transform(OrderForm orderForm){
        OrderListOutput orderListOutput = new OrderListOutput();
        BeanUtils.copyProperties(orderForm, orderListOutput);
        return orderListOutput;
    }
}
