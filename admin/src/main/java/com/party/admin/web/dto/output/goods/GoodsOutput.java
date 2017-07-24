package com.party.admin.web.dto.output.goods;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.goods.Goods;

public class GoodsOutput {
	private String title; // 标题
	private String thirdPartyId; // 供应商Id
	private String thirdPartyName; // 供应商名称 
	private String picsURL; // 封面图片
	private String cityId; // 城市Id
	private String area; // 区
	private Date startTime; // 开始时间
	private Date endTime; // 结束时间
	private String place; // 集合地点
	private String lng; // 经度
	private String lat; // 纬度
	private Float price; // 价格
	private Integer joinNum; // 预订人数
	private String recommend; // 推荐理由
	private String categoryId; // 类型
	private String notice; // 注意事项
	private String isUseCoupon; // 是否可以用优惠券(0 否， 1 是)
	private String checkStatus; // 审核状态(0 不通过， 1 通过)
	private Integer isAllowedOutside; // 是否允许在其它城市频道显示
	private Integer sort; // 排序
	private Integer type; // 商品类型（0：标准，1：定制，2活动）
	private Integer minnum; // 最少人数
	private Integer maxnum; // 最多人数
	private Integer maxBuyNum; // 限购数量
	private Integer limitNum; // 库存 为空表示库存无限
	private String memberId; // 会员编号
	private String memberName;//名称
	private String qrCodeUrl; // 二维码
	private String disQrCode;//分销二维码
	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getDisQrCode() {
		return disQrCode;
	}

	public void setDisQrCode(String disQrCode) {
		this.disQrCode = disQrCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public static GoodsOutput transform(Goods goods) {
		GoodsOutput output = new GoodsOutput();
		try {
			BeanUtils.copyProperties(output, goods);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String getThirdPartyName() {
		return thirdPartyName;
	}

	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}

}
