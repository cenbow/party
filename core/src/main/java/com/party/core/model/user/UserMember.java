package com.party.core.model.user;


import java.io.Serializable;

/**
 * 会员所属用户关系表
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public class UserMember implements Serializable {

    private static final long serialVersionUID = 4295795363809123049L;
    //主键
    private String id;

    //系统用户主键
    private String userId;

    //会员主键
    private String memberId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

        UserMember that = (UserMember) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return memberId != null ? memberId.equals(that.memberId) : that.memberId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMember{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
