package com.party.web.web.dto.output.order;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.common.annotation.ExcelField;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;

/**
 * 商品订单实体 party Created by wei.li on 2016/9/18 0018.
 */
public class OrderFormOutput{
	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;

	// 商品编号
	private String goodsId;

	// 会员编号
	private Member member;

	// 购买份数
	private Integer unit;

	// 付款金额
	private Float payment;

	// 是否已付款（0：否；1：是）
	private String isPay;

	// 联系人
	private String linkman;

	// 联系电话
	private String phone;

	// 排序
	private Integer sort;

	// 支付方式（0：支付宝，1微信支付）
	private Integer paymentWay;
	private String paymentWayName;

	// 订单类型（0：正常预定商品订单，1：定制商品订单，2：活动订单）
	private Integer type;
	private String typeName;

	// 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
	private Integer status;
	private String statusName;

	private String title;
	private String picture;

	private List<GoodsCoupons> goodsCoupons;

	private String transactionId; // 流水号

	private String merchantId; // 商户号
	
	private String merchantName; // 商户名称

	private String tradeState;

	@ExcelField(title = "支付方式", align = 2, sort = 9)
	public String getPaymentWayName() {
		return paymentWayName;
	}

	public void setPaymentWayName(String paymentWayName) {
		this.paymentWayName = paymentWayName;
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

	@ExcelField(title = "金额", align = 2, sort = 8)
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	@ExcelField(title = "订单名称", align = 2, sort = 5)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ExcelField(title = "订单编号", align = 2, sort = 1)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@ExcelField(title = "下单时间", align = 2, sort = 12)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@ExcelField(title = "下单者", align = 2, sort = 6, fieldType = Member.class, methodName = "getRealname")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public static OrderFormOutput transform(OrderForm orderForm) {
		OrderFormOutput output = new OrderFormOutput();
		try {
			BeanUtils.copyProperties(output, orderForm);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	@ExcelField(title = "订单类型", align = 2, sort = 7)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<GoodsCoupons> getGoodsCoupons() {
		return goodsCoupons;
	}

	public void setGoodsCoupons(List<GoodsCoupons> goodsCoupons) {
		this.goodsCoupons = goodsCoupons;
	}

	@ExcelField(title = "交易编号", align = 2, sort = 2)
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@ExcelField(title = "商户编号", align = 2, sort = 3)
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@ExcelField(title = "交易状态", align = 2, sort = 11)
	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	@ExcelField(title = "订单状态", align = 2, sort = 10)
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@ExcelField(title = "商户名称", align = 2, sort = 4)
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
