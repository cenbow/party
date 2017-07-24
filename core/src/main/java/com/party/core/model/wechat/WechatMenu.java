package com.party.core.model.wechat;

import com.party.core.model.BaseModel;

/**
 * 微信菜单实体
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */
public class WechatMenu extends BaseModel {

    private static final long serialVersionUID = 729177818816300360L;
    //菜单名
    private String name;

    //父节点id，0:一级菜单
    private String parentId;

    //菜单动作类型，0:消息触发，1:网页链接
    private Integer actionType;

    //微信消息类型，0:文本，1:图片
    private Integer messageType;

    //网页链接地址
    private String url;

    //微信菜单消息
    private String message;

    //图片消息媒体ID
    private String mediaId;

    //图片消息名称
    private String image;

    //图片消息地址
    private String imageUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WechatMenu that = (WechatMenu) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (actionType != null ? !actionType.equals(that.actionType) : that.actionType != null) return false;
        if (messageType != null ? !messageType.equals(that.messageType) : that.messageType != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (mediaId != null ? !mediaId.equals(that.mediaId) : that.mediaId != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (actionType != null ? actionType.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (mediaId != null ? mediaId.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatMenu{" +
                "name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", actionType=" + actionType +
                ", messageType=" + messageType +
                ", url='" + url + '\'' +
                ", message='" + message + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", image='" + image + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
