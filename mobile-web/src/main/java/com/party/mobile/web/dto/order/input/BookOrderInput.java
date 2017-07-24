package com.party.mobile.web.dto.order.input;

import com.party.core.model.order.OrderForm;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 下单输入视图
 * party
 * Created by wei.li
 * on 2016/9/23 0023.
 */
public class BookOrderInput {

    //商品编号
    @NotBlank(message = "商品编号不能为空")
    private String goodsId;

    //购买份数
    @NotNull(message = "购买份数不能为空")
    private Integer unit;

    //联系人
    @NotBlank(message = "联系人不能为空")
    private String linkman;

    //联系电话
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    //手机验证码
    private String verifyCode;

    //remark信息
    private String remarks;

    //商铺商品编号
    private String storeGoodsId;

    // 订单类型（0：正常商品订单，1：定制商品订单，2：活动）
    private Integer type;

    // 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
    private Integer status;
    
    private Float payment;

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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
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

    public static OrderForm transform(BookOrderInput input){
        OrderForm orderForm = new OrderForm();
        BeanUtils.copyProperties(input, orderForm);
        return orderForm;
    }

	public Float getPayment() {
		return payment;
	}

	public void setPayment(Float payment) {
		this.payment = payment;
	}
}
