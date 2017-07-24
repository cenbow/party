package com.party.notify.appNotify.model;


import com.party.core.model.message.Message;
import com.party.core.model.notify.Instance;
import org.springframework.beans.BeanUtils;

/**
 * User: wei.li
 * Date: 2017/4/5
 * Time: 23:32
 */
public class AppMessage {

    //消息title
    private String title;

    //消息图标
    private String logo;

    //消息类型
    private String messageType;

    //消息关联id
    private String relId;

    //推送用户
    private String memberId;

    //标签
    private String tag;

    //商品或者活动的订单id
    private String orderId;

    //商品或者活动的订单状态
    private Integer orderStatus;

    //通道类型
    private String channelType;

    //消息内容
    private String content;

    //图片URL
    private String picUrl;

    //创建人
    private String createBy;

    //更新人
    private String updateBy;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public static Message transform(AppMessage appMessage){
        Message message = new Message();
        BeanUtils.copyProperties(appMessage, message);
        return message;
    }
}
