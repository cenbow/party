package com.party.core.model.thirdParty;

import com.party.core.model.BaseModel;

/**
 * 第三方公司管理实体
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */
public class ThirdParty extends BaseModel {
    private static final long serialVersionUID = -437330706780269874L;

    //公司名称
    private String comName;

    //官方网站
    private String officalUrl;

    //城市主键
    private String cityId;

    //区主键
    private String areaId;

    //联系人
    private String linkman;

    //联系电话
    private String telephone;

    //地址
    private String address;

    //邮编
    private String postcode;

    //排序
    private Integer sort;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getOfficalUrl() {
        return officalUrl;
    }

    public void setOfficalUrl(String officalUrl) {
        this.officalUrl = officalUrl;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ThirdParty that = (ThirdParty) o;

        if (comName != null ? !comName.equals(that.comName) : that.comName != null) return false;
        if (officalUrl != null ? !officalUrl.equals(that.officalUrl) : that.officalUrl != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (linkman != null ? !linkman.equals(that.linkman) : that.linkman != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        return sort != null ? sort.equals(that.sort) : that.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comName != null ? comName.hashCode() : 0);
        result = 31 * result + (officalUrl != null ? officalUrl.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (linkman != null ? linkman.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThirdParty{" +
                "comName='" + comName + '\'' +
                ", officalUrl='" + officalUrl + '\'' +
                ", cityId='" + cityId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", linkman='" + linkman + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", sort=" + sort +
                '}';
    }
}
