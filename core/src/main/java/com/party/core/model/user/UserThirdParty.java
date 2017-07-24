package com.party.core.model.user;


import java.io.Serializable;

/**
 * 用户供应商关系实体
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */
public class UserThirdParty implements Serializable{

    private static final long serialVersionUID = -8652402228261920305L;
    //主键
    private String id;

    //用户主键
    private String userId;

    //商家主键
    private String thirdPartyId;


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

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserThirdParty that = (UserThirdParty) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return thirdPartyId != null ? thirdPartyId.equals(that.thirdPartyId) : that.thirdPartyId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (thirdPartyId != null ? thirdPartyId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserThirdParty{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                '}';
    }
}
