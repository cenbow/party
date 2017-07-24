package com.party.core.model.order;

import com.party.core.model.BaseModel;

/**
 * 订单交易信息实体
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class OrderTrade extends BaseModel{

    private static final long serialVersionUID = -1117695402070621757L;

    //支付类型
    private Integer type;

    //本地订单编号
    private String orderFormId;

    //交易订单号
    private String transactionId;

    //订单交易详情
    private String data;

    //订单处理数据来源（0：商品，1：活动）
    private Integer origin;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(String orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderTrade that = (OrderTrade) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (orderFormId != null ? !orderFormId.equals(that.orderFormId) : that.orderFormId != null) return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return origin != null ? origin.equals(that.origin) : that.origin == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (orderFormId != null ? orderFormId.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderTrade{" +
                "type=" + type +
                ", orderFormId='" + orderFormId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", data='" + data + '\'' +
                ", origin=" + origin +
                '}';
    }
}
