package com.party.web.web.dto.output.index;

import com.party.core.model.BaseModel;

/**
 * 我收到的消息输出
 */
public class MessageOutput extends BaseModel {
    //消息类型
    private String messageType;

    //消息图标
    private String logo;

    //标题
    private String title;

    //关联ID
    private String relId;

    //用户编号
    private String memberId;

    //标签
    private String tag;

    //消息内容
    private String content;

    //图片URL
    private String picUrl;

    //是否新消息（0：否， 1：是）
    private Integer isNew;

    //商品或者活动的订单id
    private String orderId;

    //商品或者活动的订单状态
    private Integer orderStatus;

    private String dateDiff;

    public String getDateDiff() {
        return dateDiff;
    }

    public void setDateDiff(String dateDiff) {
        this.dateDiff = dateDiff;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

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
}
