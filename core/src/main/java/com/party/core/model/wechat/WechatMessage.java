package com.party.core.model.wechat;

import com.party.core.model.BaseModel;

/**
 * 微信消息实体
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */
public class WechatMessage extends BaseModel{

    private static final long serialVersionUID = -552761814585865080L;


    //微信消息类型
    private Integer type;

    //微信消息内容
    private String content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WechatMessage that = (WechatMessage) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
