package com.party.core.model.message;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 消息实体
 * party
 * Created by wei.li
 * on 2016/8/23 0023.
 */
public class SysMessage extends BaseModel  implements Serializable {

    private static final long serialVersionUID = 6790970673115408860L;


    //消息类型
    private Integer type;

    //消息内容
    private String content;

    //消息状态（0：未处理，1：已处理）
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SysMessage that = (SysMessage) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
