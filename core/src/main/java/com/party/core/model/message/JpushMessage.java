package com.party.core.model.message;

import com.party.core.model.BaseModel;

/**
 * Jpush推送消息实体
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public class JpushMessage extends BaseModel {
    private static final long serialVersionUID = 2491794645720581037L;

    //标签
    private String tag;

    //别名
    private String alias;

    //接受者编号
    private String registrationId;

    //消息内容
    private String message;

    //是否已发送
    private Integer isSend;

    //消息类型
    private String messageType;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JpushMessage that = (JpushMessage) o;

        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (registrationId != null ? !registrationId.equals(that.registrationId) : that.registrationId != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (isSend != null ? !isSend.equals(that.isSend) : that.isSend != null) return false;
        return messageType != null ? messageType.equals(that.messageType) : that.messageType == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (registrationId != null ? registrationId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (isSend != null ? isSend.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JpushMessage{" +
                "tag='" + tag + '\'' +
                ", alias='" + alias + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", message='" + message + '\'' +
                ", isSend=" + isSend +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
