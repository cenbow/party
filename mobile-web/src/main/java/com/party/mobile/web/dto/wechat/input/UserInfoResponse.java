package com.party.mobile.web.dto.wechat.input;

/**
 * 拉取用户信息响应参数
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */
public class UserInfoResponse {

    //用户的唯一标识
    private String openid;

    //用户昵称
    private String nickname;

    //用户的性别
    private Integer sex;

    //用户个人资料填写的省份
    private String province;

    //普通用户个人资料填写的城市
    private String city;

    //国家
    private String country;

    //用户头像
    private String headimgurl;

    //用户特权信息
    private String[] privilege;

    //只有在用户将公众号绑定到微信开放平台帐号后才会出现该字段
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
