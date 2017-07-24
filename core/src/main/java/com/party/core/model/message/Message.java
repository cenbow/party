package com.party.core.model.message;

import com.party.core.model.BaseModel;

/**
 * 用户消息实体
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class Message extends BaseModel{


    private static final long serialVersionUID = -3698761498565365677L;

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

    /**
     * 消息logo url
     */
    public static final String MSG_LOGO_ACT = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_act.jpg";
    public static final String MSG_LOGO_GOODS = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_goods.jpg";
    public static final String MSG_LOGO_DISCOVERY = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_discovery.jpg";
    public static final String MSG_LOGO_CIRCLE = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_circle.jpg";
    public static final String MSG_LOGO_MEMBER = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_member.jpg";
    public static final String MSG_LOGO_TZ = "http://txzapp-10052192.image.myqcloud.com/1499855879830";


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Message message = (Message) o;

        if (messageType != null ? !messageType.equals(message.messageType) : message.messageType != null) return false;
        if (logo != null ? !logo.equals(message.logo) : message.logo != null) return false;
        if (title != null ? !title.equals(message.title) : message.title != null) return false;
        if (relId != null ? !relId.equals(message.relId) : message.relId != null) return false;
        if (memberId != null ? !memberId.equals(message.memberId) : message.memberId != null) return false;
        if (tag != null ? !tag.equals(message.tag) : message.tag != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (picUrl != null ? !picUrl.equals(message.picUrl) : message.picUrl != null) return false;
        if (isNew != null ? !isNew.equals(message.isNew) : message.isNew != null) return false;
        if (orderId != null ? !orderId.equals(message.orderId) : message.orderId != null) return false;
        return orderStatus != null ? orderStatus.equals(message.orderStatus) : message.orderStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (relId != null ? relId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (isNew != null ? isNew.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType='" + messageType + '\'' +
                ", logo='" + logo + '\'' +
                ", title='" + title + '\'' +
                ", relId='" + relId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", tag='" + tag + '\'' +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", isNew=" + isNew +
                ", orderId='" + orderId + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
