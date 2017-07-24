package com.party.core.model.member;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 达人实体
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public class Expert extends BaseModel implements Serializable {

    private static final long serialVersionUID = 3515236469734474106L;


    //会员主键
    private String memberId;

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

        Expert expert = (Expert) o;

        return memberId != null ? memberId.equals(expert.memberId) : expert.memberId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "memberId='" + memberId + '\'' +
                '}';
    }
}
