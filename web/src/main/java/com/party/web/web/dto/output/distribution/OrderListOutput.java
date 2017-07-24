package com.party.web.web.dto.output.distribution;

import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.order.OrderForm;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 分销订单输出视图
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

    //买家编号
    private String buyerId;

    //买家头像
    private String buyerLogo;

    //付款金额
    private Float payment;

    // 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
    private Integer status;

    //购买份数
    private Integer unit;

    //联系人
    private String linkman;

    //联系电话
    private String phone;

    //支付方式（0：支付宝，1微信支付）
    private Integer paymentWay;

    //订单类型（0：正常预定商品订单，1：定制商品订单，2：活动订单）
    private Integer type;

    //创建时间
    private Date createDate;

    //核销码
    private List<GoodsCoupons> goodsCouponsList;

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

    public String getBuyerLogo() {
        return buyerLogo;
    }

    public void setBuyerLogo(String buyerLogo) {
        this.buyerLogo = buyerLogo;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Integer paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<GoodsCoupons> getGoodsCouponsList() {
        return goodsCouponsList;
    }

    public void setGoodsCouponsList(List<GoodsCoupons> goodsCouponsList) {
        this.goodsCouponsList = goodsCouponsList;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public static OrderListOutput transform(OrderForm orderForm){
        OrderListOutput orderListOutput = new OrderListOutput();
        BeanUtils.copyProperties(orderForm, orderListOutput);
        return orderListOutput;
    }
}
