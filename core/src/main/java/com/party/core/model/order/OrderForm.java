package com.party.core.model.order;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

import java.util.Date;

/**
 * 商品订单实体
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class OrderForm extends BaseModel{


    private static final long serialVersionUID = -1235414454582833887L;

    //商品编号
    private String goodsId;
    
    // 业务发起者（活动，众筹，商品）
    private String initiatorId;

    //会员编号
    private String memberId;
    
    private Member member;

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

    //排序
    private Integer sort;

    //支付方式（0：支付宝，1微信支付）
    private Integer paymentWay;
    
    // 交易类型（微信：APP/JSAPI）
    private String tradeType;

    //订单类型（0：正常预定商品订单，1：定制商品订单，2：活动订单，3：众筹订单，4：等级套餐订单）
    private Integer type;
    private String typeName;

    // 订单状态（0：审核中，1：待支付，2：支付成功，3：其它）
    private Integer status;
    private String statusName;
    
	/**
	 * SUCCESS—支付成功
	 * 
	 * REFUND—转入退款
	 * 
	 * NOTPAY—未支付
	 * 
	 * CLOSED—已关闭
	 * 
	 * REVOKED—已撤销（刷卡支付）
	 * 
	 * USERPAYING--用户支付中
	 * 
	 * PAYERROR--支付失败(其他原因，如银行返回失败)
	 */
    private String tradeState;

    //商铺商品主键
    private String storeGoodsId;
    
    private String title;

    //结束时间
    private Date endDate;

    //排除类型
    private Integer excludeType;
    
    // 商户号
    private String merchantId;
    
    // 交易单号
    private String transactionId;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getExcludeType() {
        return excludeType;
    }

    public void setExcludeType(Integer excludeType) {
        this.excludeType = excludeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderForm orderForm = (OrderForm) o;

        if (goodsId != null ? !goodsId.equals(orderForm.goodsId) : orderForm.goodsId != null) return false;
        if (memberId != null ? !memberId.equals(orderForm.memberId) : orderForm.memberId != null) return false;
        if (unit != null ? !unit.equals(orderForm.unit) : orderForm.unit != null) return false;
        if (payment != null ? !payment.equals(orderForm.payment) : orderForm.payment != null) return false;
        if (isPay != null ? !isPay.equals(orderForm.isPay) : orderForm.isPay != null) return false;
        if (linkman != null ? !linkman.equals(orderForm.linkman) : orderForm.linkman != null) return false;
        if (phone != null ? !phone.equals(orderForm.phone) : orderForm.phone != null) return false;
        if (sort != null ? !sort.equals(orderForm.sort) : orderForm.sort != null) return false;
        if (paymentWay != null ? !paymentWay.equals(orderForm.paymentWay) : orderForm.paymentWay != null) return false;
        if (type != null ? !type.equals(orderForm.type) : orderForm.type != null) return false;
        if (status != null ? !status.equals(orderForm.status) : orderForm.status != null) return false;
        return storeGoodsId != null ? storeGoodsId.equals(orderForm.storeGoodsId) : orderForm.storeGoodsId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (isPay != null ? isPay.hashCode() : 0);
        result = 31 * result + (linkman != null ? linkman.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (paymentWay != null ? paymentWay.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (storeGoodsId != null ? storeGoodsId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "goodsId='" + goodsId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", unit=" + unit +
                ", payment=" + payment +
                ", isPay='" + isPay + '\'' +
                ", linkman='" + linkman + '\'' +
                ", phone='" + phone + '\'' +
                ", sort=" + sort +
                ", paymentWay=" + paymentWay +
                ", type=" + type +
                ", status=" + status +
                ", storeGoodsId='" + storeGoodsId + '\'' +
                '}';
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(String initiatorId) {
		this.initiatorId = initiatorId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
