package com.party.core.model.notify;

import com.party.core.model.BaseModel;

/**
 * 消息通道实体
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:30
 */
public class MsgChannel extends BaseModel{

    //名称
    private String name;

    //编码
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MsgChannel channel = (MsgChannel) o;

        if (name != null ? !name.equals(channel.name) : channel.name != null) return false;
        return code != null ? code.equals(channel.code) : channel.code == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
