package com.party.core.model.wechat;

import com.party.core.model.BaseModel;

/**
 * 微信模板消息实体
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 15:19
 */
public class WechatTemplateMassage extends BaseModel{
    private static final long serialVersionUID = -5928998626515258738L;

    //会员编号
    private String memberId;

    //时间通道编号
    private String eventChannelId;

    //消息类型（SYSTEM/PARTNER）
    private String type;

    //消息头部
    private String first;

    //消息备注
    private String remark;

    //消息连接
    private String url;

    //模板编号
    private String templateId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getEventChannelId() {
        return eventChannelId;
    }

    public void setEventChannelId(String eventChannelId) {
        this.eventChannelId = eventChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WechatTemplateMassage that = (WechatTemplateMassage) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (eventChannelId != null ? !eventChannelId.equals(that.eventChannelId) : that.eventChannelId != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return templateId != null ? templateId.equals(that.templateId) : that.templateId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (eventChannelId != null ? eventChannelId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatTemplateMassage{" +
                "memberId='" + memberId + '\'' +
                ", eventChannelId='" + eventChannelId + '\'' +
                ", type='" + type + '\'' +
                ", first='" + first + '\'' +
                ", remark='" + remark + '\'' +
                ", url='" + url + '\'' +
                ", templateId='" + templateId + '\'' +
                '}';
    }
}
