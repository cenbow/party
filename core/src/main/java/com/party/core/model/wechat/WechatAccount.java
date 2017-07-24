package com.party.core.model.wechat;



import java.io.Serializable;
import java.util.Date;

/**
 * 微信账户实体
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */
public class WechatAccount implements Serializable {

    private static final long serialVersionUID = 7702446175493938695L;

    //主键
    private String id;

    //微信接口交互令牌
    private String token;

    //微信 jsAPI ticket
    private String ticket;

    //会员编号
    private String memberId;

    //类型
    private String type;

    //更新时间
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WechatAccount that = (WechatAccount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (ticket != null ? !ticket.equals(that.ticket) : that.ticket != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return updateDate != null ? updateDate.equals(that.updateDate) : that.updateDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WechatAccount{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", ticket='" + ticket + '\'' +
                ", memberId='" + memberId + '\'' +
                ", type='" + type + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
