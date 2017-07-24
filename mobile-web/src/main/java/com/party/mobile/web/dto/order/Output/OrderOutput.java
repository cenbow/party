package com.party.mobile.web.dto.order.Output;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.party.core.model.order.OrderForm;

/**
 * 订单输出视图
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class OrderOutput {

    //订单主键
    private String id;

    //订单创建时间
    private String createDate;

    //商品编号
    private String goodsId;
    
    // 业务发起者（活动，众筹，商品）
    private String initiatorId;

    //购买份数
    private Integer unit;

    //付款金额
    private Float payment;

    //是否已付款（0：否；1：是）
    private String isPay;

    //联系人
    private String linkman;

    //联系电话
    private String phone;

    //支付方式（0：支付宝，1微信支付）
    private Integer paymentWay;

    //订单类型（0：标准预定商品订单，1：定制商品订单，2：活动订单）
    private Integer type;

    // 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
    private Integer status;

    //活动title
    private String description;

    //活动封面图
    private String logo;

    //活动开始时间
    private String startTime;

    //活动结束时间
    private String endTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
        this.createDate = formatter.format(createDate);
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
        this.startTime = formatter.format(startTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
        this.endTime = formatter.format(endTime);
    }


    /**
     * 商品实体转输出视图
     * @param orderForm 商品实体
     * @return 输出视图
     */
    public static OrderOutput transform(OrderForm orderForm){
        OrderOutput orderOutput = new OrderOutput();
        BeanUtils.copyProperties(orderForm, orderOutput);
        return orderOutput;
    }

	public String getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(String initiatorId) {
		this.initiatorId = initiatorId;
	}
}
