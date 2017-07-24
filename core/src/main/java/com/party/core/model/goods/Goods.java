package com.party.core.model.goods;

import com.party.core.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Goods
 *
 * @author Wesley
 * @data 16/9/7 14:13 .
 */
public class Goods  extends BaseModel implements Serializable {

    private static final long serialVersionUID = 8575416283320405337L;

    private String title;		    //标题
    private String thirdPartyId;    //供应商Id
    private String picsURL;		    // 封面图片
    private String cityId;		    // 城市Id
    private String area;		    // 区
    private Date startTime;		    // 开始时间
    private Date endTime;		    // 结束时间
    private String place;		    // 集合地点
    private String lng;		        // 经度
    private String lat;		        // 纬度
    private Float price;		    // 价格
    private Integer joinNum;		// 预订人数
    private String recommend;		// 推荐理由
    private String categoryId;		// 类型
    private String notice;	        //注意事项
    private String isUseCoupon;		// 是否可以用优惠券(0 否， 1 是)
    private String checkStatus;		// 审核状态(0 不通过， 1 通过)
    private Integer isAllowedOutside; //是否允许在其它城市频道显示
    private Integer sort;		    //排序
    private Integer type;           //商品类型（0：标准，1：定制，2活动）
    private Integer minnum;         //最少人数
    private Integer maxnum;         //最多人数
    private Integer maxBuyNum;		// 限购数量
    private Integer limitNum;		// 库存 为空表示库存无限
    private String memberId;        //会员编号
    private String memberName;      //会员名称

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getPicsURL() {
        return picsURL;
    }

    public void setPicsURL(String picsURL) {
        this.picsURL = picsURL;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(String isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getIsAllowedOutside() {
        return isAllowedOutside;
    }

    public void setIsAllowedOutside(Integer isAllowedOutside) {
        this.isAllowedOutside = isAllowedOutside;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMinnum() {
        return minnum;
    }

    public void setMinnum(Integer minnum) {
        this.minnum = minnum;
    }

    public Integer getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Integer maxnum) {
        this.maxnum = maxnum;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

	public Integer getMaxBuyNum() {
		return maxBuyNum;
	}

	public void setMaxBuyNum(Integer maxBuyNum) {
		this.maxBuyNum = maxBuyNum;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Goods goods = (Goods) o;

        if (title != null ? !title.equals(goods.title) : goods.title != null) return false;
        if (thirdPartyId != null ? !thirdPartyId.equals(goods.thirdPartyId) : goods.thirdPartyId != null) return false;
        if (picsURL != null ? !picsURL.equals(goods.picsURL) : goods.picsURL != null) return false;
        if (cityId != null ? !cityId.equals(goods.cityId) : goods.cityId != null) return false;
        if (area != null ? !area.equals(goods.area) : goods.area != null) return false;
        if (startTime != null ? !startTime.equals(goods.startTime) : goods.startTime != null) return false;
        if (endTime != null ? !endTime.equals(goods.endTime) : goods.endTime != null) return false;
        if (place != null ? !place.equals(goods.place) : goods.place != null) return false;
        if (lng != null ? !lng.equals(goods.lng) : goods.lng != null) return false;
        if (lat != null ? !lat.equals(goods.lat) : goods.lat != null) return false;
        if (price != null ? !price.equals(goods.price) : goods.price != null) return false;
        if (joinNum != null ? !joinNum.equals(goods.joinNum) : goods.joinNum != null) return false;
        if (recommend != null ? !recommend.equals(goods.recommend) : goods.recommend != null) return false;
        if (categoryId != null ? !categoryId.equals(goods.categoryId) : goods.categoryId != null) return false;
        if (notice != null ? !notice.equals(goods.notice) : goods.notice != null) return false;
        if (isUseCoupon != null ? !isUseCoupon.equals(goods.isUseCoupon) : goods.isUseCoupon != null) return false;
        if (checkStatus != null ? !checkStatus.equals(goods.checkStatus) : goods.checkStatus != null) return false;
        if (isAllowedOutside != null ? !isAllowedOutside.equals(goods.isAllowedOutside) : goods.isAllowedOutside != null)
            return false;
        if (sort != null ? !sort.equals(goods.sort) : goods.sort != null) return false;
        if (type != null ? !type.equals(goods.type) : goods.type != null) return false;
        if (minnum != null ? !minnum.equals(goods.minnum) : goods.minnum != null) return false;
        if (maxnum != null ? !maxnum.equals(goods.maxnum) : goods.maxnum != null) return false;
        if (maxBuyNum != null ? !maxBuyNum.equals(goods.maxBuyNum) : goods.maxBuyNum != null) return false;
        if (limitNum != null ? !limitNum.equals(goods.limitNum) : goods.limitNum != null) return false;
        return memberId != null ? memberId.equals(goods.memberId) : goods.memberId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thirdPartyId != null ? thirdPartyId.hashCode() : 0);
        result = 31 * result + (picsURL != null ? picsURL.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (joinNum != null ? joinNum.hashCode() : 0);
        result = 31 * result + (recommend != null ? recommend.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (isUseCoupon != null ? isUseCoupon.hashCode() : 0);
        result = 31 * result + (checkStatus != null ? checkStatus.hashCode() : 0);
        result = 31 * result + (isAllowedOutside != null ? isAllowedOutside.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (minnum != null ? minnum.hashCode() : 0);
        result = 31 * result + (maxnum != null ? maxnum.hashCode() : 0);
        result = 31 * result + (maxBuyNum != null ? maxBuyNum.hashCode() : 0);
        result = 31 * result + (limitNum != null ? limitNum.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }
}
