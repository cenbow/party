package com.party.notify.sms.model;

/**
 * 手机短信发送消息实体
 * User: wei.li
 * Date: 2017/4/5
 * Time: 23:32
 */
public class SmsMessage {

    //电话
    private String telephone;

    //发送内容
    private String content;

    //目标编号
    private String targetId;

    //会员编号
    private String memberId;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
