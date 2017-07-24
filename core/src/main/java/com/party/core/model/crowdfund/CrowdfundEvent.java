package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

/**
 * 众筹事件
 * Created by wei.li
 *
 * @date 2017/4/25 0025
 * @time 16:15
 */
public class CrowdfundEvent extends BaseModel{
    private static final long serialVersionUID = 4778148333184476369L;

    //事件名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CrowdfundEvent that = (CrowdfundEvent) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CrowdfundEvent{" +
                "name='" + name + '\'' +
                '}';
    }
}
