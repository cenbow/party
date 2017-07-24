package com.party.notify.appPush.model;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:25
 */
public class AppPushMessage {

    //别名
    private String alias;

    //通知信息
    private String alert;

    //通知类型
    private String noticeType;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }
}
