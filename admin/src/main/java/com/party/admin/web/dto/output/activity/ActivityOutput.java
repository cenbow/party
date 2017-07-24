package com.party.admin.web.dto.output.activity;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.party.core.model.activity.Activity;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.model.city.City;

/**
 * Activity实体
 *
 * @author Wesley
 * @data 16/9/5 16:57 .
 */
public class ActivityOutput {
	
	private String cityId;		// 城市ID

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
	
	private String newPrice;

	private Integer activityType; // 活动类型
	private String thirdPartyId; // 供应商Id
	private Float price; // 价格
	private String title; // 名称
	private String pic; // 封面图
	private City city; // 城市ID
	private String area; // 区,字符串，由后台录入
	private Integer limitNum; // 人数上限
	private Date startTime; // 开始时间
	private Date endTime; // 结束时间
	private String place; // 活动场所
	private String lng; // 经度
	private String lat; // 纬度
	private Integer isOpen; // 报名开启
	private Integer inviteOnly; // 持邀请码参与
	private String inviteCode; // 邀请码
	private Integer joinHidden; // 隐藏报名人员
	private String extra; // 其他要填的选项
	private Integer shareNum; // 分享数
	private Integer readNum; // 阅读数
	private Integer joinNum; // 报名数
	private Integer goodNum; // 点赞数
	private String member; // 会员ID
	private String checkStatus; // 审核状态
	private Integer isAllowedOutside; // 是否允许在其它城市频道显示
	private Integer sort; // 排序
	private Integer micWebStatus; // 微网站效果 1 详情信息放到底部 0详情信息放头部
	private Integer showFront;// 是否显示在前端

	private Integer favorerNum; // 支持人数
	private Integer beCrowdfundNum; // 众筹中人数
	private Integer crowdfundedNum; // 已经筹满人数
	private Integer isCrowdfunded; // 是否是众筹项目
	private Integer representNum;// 代言数
	private String crowdDeclaration; // 众筹宣言
	private String supportDeclaration; // 支持宣言
	private String representDeclaration; // 代言宣言

	private String qrCodeUrl; // 二维码
	private String bmQrCodeUrl; // 报名登记
	private String disQrCode;// 分销二维码

	private Integer status;
	private List<CrowfundResources> picList; // 跑马灯
	private CrowfundResources video; // 视频
	private Integer crowdfundHintSwitch;//众筹提醒开关
	public String publisher;//发布者
	public String publisherLogo;//发布者头像
	public String eventId;//众筹事件编号
	private Integer templateStyle;//是否隐藏数据

	public Integer getBeCrowdfundNum() {
		return beCrowdfundNum;
	}

	public Integer getShowFront() {
		return showFront;
	}

	public void setShowFront(Integer showFront) {
		this.showFront = showFront;
	}

	public void setBeCrowdfundNum(Integer beCrowdfundNum) {
		this.beCrowdfundNum = beCrowdfundNum;
	}

	public Integer getCrowdfundedNum() {
		return crowdfundedNum;
	}

	public void setCrowdfundedNum(Integer crowdfundedNum) {
		this.crowdfundedNum = crowdfundedNum;
	}

	public Integer getMicWebStatus() {
		return micWebStatus;
	}

	public void setMicWebStatus(Integer micWebStatus) {
		this.micWebStatus = micWebStatus;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
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

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getInviteOnly() {
		return inviteOnly;
	}

	public void setInviteOnly(Integer inviteOnly) {
		this.inviteOnly = inviteOnly;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getJoinHidden() {
		return joinHidden;
	}

	public void setJoinHidden(Integer joinHidden) {
		this.joinHidden = joinHidden;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	public Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
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

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getFavorerNum() {
		return favorerNum;
	}

	public void setFavorerNum(Integer favorerNum) {
		this.favorerNum = favorerNum;
	}

	public Integer getIsCrowdfunded() {
		return isCrowdfunded;
	}

	public void setIsCrowdfunded(Integer isCrowdfunded) {
		this.isCrowdfunded = isCrowdfunded;
	}

	public Integer getRepresentNum() {
		return representNum;
	}

	public void setRepresentNum(Integer representNum) {
		this.representNum = representNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getCrowdDeclaration() {
		return crowdDeclaration;
	}

	public void setCrowdDeclaration(String crowdDeclaration) {
		this.crowdDeclaration = crowdDeclaration;
	}

	public String getSupportDeclaration() {
		return supportDeclaration;
	}

	public void setSupportDeclaration(String supportDeclaration) {
		this.supportDeclaration = supportDeclaration;
	}

	public String getRepresentDeclaration() {
		return representDeclaration;
	}

	public void setRepresentDeclaration(String representDeclaration) {
		this.representDeclaration = representDeclaration;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public String getBmQrCodeUrl() {
		return bmQrCodeUrl;
	}

	public void setBmQrCodeUrl(String bmQrCodeUrl) {
		this.bmQrCodeUrl = bmQrCodeUrl;
	}

	public String getDisQrCode() {
		return disQrCode;
	}

	public void setDisQrCode(String disQrCode) {
		this.disQrCode = disQrCode;
	}

	public List<CrowfundResources> getPicList() {
		return picList;
	}

	public void setPicList(List<CrowfundResources> picList) {
		this.picList = picList;
	}

	public CrowfundResources getVideo() {
		return video;
	}

	public void setVideo(CrowfundResources video) {
		this.video = video;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherLogo() {
		return publisherLogo;
	}

	public void setPublisherLogo(String publisherLogo) {
		this.publisherLogo = publisherLogo;
	}

	public Integer getCrowdfundHintSwitch() {
		return crowdfundHintSwitch;
	}

	public void setCrowdfundHintSwitch(Integer crowdfundHintSwitch) {
		this.crowdfundHintSwitch = crowdfundHintSwitch;
	}



	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}


	public Integer getTemplateStyle() {
		return templateStyle;
	}

	public void setTemplateStyle(Integer templateStyle) {
		this.templateStyle = templateStyle;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public static ActivityOutput transform(Activity activity) {
		ActivityOutput output = new ActivityOutput();
		try {
			BeanUtils.copyProperties(output, activity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}
}
