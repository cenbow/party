package com.party.core.model.notify;

import com.party.core.model.BaseModel;

/**
 * 消息实例实体
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:45
 */

public class Instance extends BaseModel {


    //标题
    private String title;

    //通道类型
    private String channelType;

    //手机号
    private String receiver;

    //发送结果
    private String result;

    //是否成功
    private Integer isSuccess;

    //发送次数
    private Integer time;

    //目标编号
    private String targetId;

    //会员编号
    private String memberId;


    /**
     * 消息logo url
     */
    public static final String MSG_LOGO_ACT = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_act.jpg";
    public static final String MSG_LOGO_GOODS = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_goods.jpg";
    public static final String MSG_LOGO_DISCOVERY = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_discovery.jpg";
    public static final String MSG_LOGO_CIRCLE = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_circle.jpg";
    public static final String MSG_LOGO_MEMBER = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_member.jpg";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Instance instance = (Instance) o;

        if (title != null ? !title.equals(instance.title) : instance.title != null) return false;
        if (channelType != null ? !channelType.equals(instance.channelType) : instance.channelType != null)
            return false;
        if (receiver != null ? !receiver.equals(instance.receiver) : instance.receiver != null) return false;
        if (result != null ? !result.equals(instance.result) : instance.result != null) return false;
        if (isSuccess != null ? !isSuccess.equals(instance.isSuccess) : instance.isSuccess != null) return false;
        if (time != null ? !time.equals(instance.time) : instance.time != null) return false;
        if (targetId != null ? !targetId.equals(instance.targetId) : instance.targetId != null) return false;
        return memberId != null ? memberId.equals(instance.memberId) : instance.memberId == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (title != null ? title.hashCode() : 0);
        result1 = 31 * result1 + (channelType != null ? channelType.hashCode() : 0);
        result1 = 31 * result1 + (receiver != null ? receiver.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (isSuccess != null ? isSuccess.hashCode() : 0);
        result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
        result1 = 31 * result1 + (targetId != null ? targetId.hashCode() : 0);
        result1 = 31 * result1 + (memberId != null ? memberId.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "title='" + title + '\'' +
                ", channelType='" + channelType + '\'' +
                ", receiver='" + receiver + '\'' +
                ", result='" + result + '\'' +
                ", isSuccess=" + isSuccess +
                ", time=" + time +
                ", targetId='" + targetId + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
