package com.party.core.model.activity;

import com.party.core.model.BaseModel;
import com.party.core.model.counterfoil.Counterfoil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Activity实体
 *
 * @author Wesley
 * @data 16/9/5 16:57 .
 */
public class Activity extends BaseModel implements Serializable  {
    private static final long serialVersionUID = -9189492127438725378L;

    private Integer activityType;		// 活动类型
    private String thirdPartyId;    //供应商Id
    private Float price;		    // 价格
    private String title;		// 名称
    private String pic;		    // 封面图
    private String cityId;		// 城市ID
    private String area;		// 区,字符串，由后台录入
    private Integer limitNum;		// 人数上限
    private Date startTime;		// 开始时间
    private Date endTime;		// 结束时间
    private String place;		// 活动场所
    private String lng;		    // 经度
    private String lat;		    // 纬度
    private Integer isOpen;		// 报名开启
    private Integer inviteOnly;		// 持邀请码参与
    private String inviteCode;		// 邀请码
    private Integer joinHidden;		// 隐藏报名人员(1: 是 0：否)
    private String extra;		// 其他要填的选项
    private Integer shareNum;		// 分享数
    private Integer readNum;		// 阅读数
    private Integer joinNum;		// 报名数
    private Integer goodNum;		// 点赞数
    private String member;		// 会员ID
    private String checkStatus;	//审核状态
    private Integer isAllowedOutside; //是否允许在其它城市频道显示
    private Integer sort;		//排序
    private Integer micWebStatus;	//微网站效果 1 详情信息放到底部 0详情信息放头部
    private Integer showFront;//是否显示在前端
    private Integer favorerNum;  //支持人数
    private Integer beCrowdfundNum; //众筹中人数
    private Integer crowdfundedNum; //已经筹满人数
    private Integer isCrowdfunded; //是否是众筹项目
    private Integer representNum;//代言数
    private String crowdDeclaration; // 众筹宣言
    private String supportDeclaration; // 支持宣言
    private String representDeclaration; // 代言宣言
    private Integer crowdfundHintSwitch;//众筹提醒开关
    
    private String memberId;//会员编号
    private Integer status;
    private Integer templateStyle;//是否隐藏数据
    public String publisher;//发布者
    public String publisherLogo;//发布者头像
    public String eventId;//众筹事件编号
    
    private List<Counterfoil> counterfoils;

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Integer getCrowdfundHintSwitch() {
        return crowdfundHintSwitch;
    }

    public void setCrowdfundHintSwitch(Integer crowdfundHintSwitch) {
        this.crowdfundHintSwitch = crowdfundHintSwitch;
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


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


	public List<Counterfoil> getCounterfoils() {
		return counterfoils;
	}

	public void setCounterfoils(List<Counterfoil> counterfoils) {
		this.counterfoils = counterfoils;
	}

    public Integer getTemplateStyle() {
        return templateStyle;
    }

    public void setTemplateStyle(Integer templateStyle) {
        this.templateStyle = templateStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Activity activity = (Activity) o;

        if (activityType != null ? !activityType.equals(activity.activityType) : activity.activityType != null)
            return false;
        if (thirdPartyId != null ? !thirdPartyId.equals(activity.thirdPartyId) : activity.thirdPartyId != null)
            return false;
        if (price != null ? !price.equals(activity.price) : activity.price != null) return false;
        if (title != null ? !title.equals(activity.title) : activity.title != null) return false;
        if (pic != null ? !pic.equals(activity.pic) : activity.pic != null) return false;
        if (cityId != null ? !cityId.equals(activity.cityId) : activity.cityId != null) return false;
        if (area != null ? !area.equals(activity.area) : activity.area != null) return false;
        if (limitNum != null ? !limitNum.equals(activity.limitNum) : activity.limitNum != null) return false;
        if (startTime != null ? !startTime.equals(activity.startTime) : activity.startTime != null) return false;
        if (endTime != null ? !endTime.equals(activity.endTime) : activity.endTime != null) return false;
        if (place != null ? !place.equals(activity.place) : activity.place != null) return false;
        if (lng != null ? !lng.equals(activity.lng) : activity.lng != null) return false;
        if (lat != null ? !lat.equals(activity.lat) : activity.lat != null) return false;
        if (isOpen != null ? !isOpen.equals(activity.isOpen) : activity.isOpen != null) return false;
        if (inviteOnly != null ? !inviteOnly.equals(activity.inviteOnly) : activity.inviteOnly != null) return false;
        if (inviteCode != null ? !inviteCode.equals(activity.inviteCode) : activity.inviteCode != null) return false;
        if (joinHidden != null ? !joinHidden.equals(activity.joinHidden) : activity.joinHidden != null) return false;
        if (extra != null ? !extra.equals(activity.extra) : activity.extra != null) return false;
        if (shareNum != null ? !shareNum.equals(activity.shareNum) : activity.shareNum != null) return false;
        if (readNum != null ? !readNum.equals(activity.readNum) : activity.readNum != null) return false;
        if (joinNum != null ? !joinNum.equals(activity.joinNum) : activity.joinNum != null) return false;
        if (goodNum != null ? !goodNum.equals(activity.goodNum) : activity.goodNum != null) return false;
        if (member != null ? !member.equals(activity.member) : activity.member != null) return false;
        if (checkStatus != null ? !checkStatus.equals(activity.checkStatus) : activity.checkStatus != null)
            return false;
        if (isAllowedOutside != null ? !isAllowedOutside.equals(activity.isAllowedOutside) : activity.isAllowedOutside != null)
            return false;
        if (sort != null ? !sort.equals(activity.sort) : activity.sort != null) return false;
        if (micWebStatus != null ? !micWebStatus.equals(activity.micWebStatus) : activity.micWebStatus != null)
            return false;
        if (showFront != null ? !showFront.equals(activity.showFront) : activity.showFront != null) return false;
        if (favorerNum != null ? !favorerNum.equals(activity.favorerNum) : activity.favorerNum != null) return false;
        if (beCrowdfundNum != null ? !beCrowdfundNum.equals(activity.beCrowdfundNum) : activity.beCrowdfundNum != null)
            return false;
        if (crowdfundedNum != null ? !crowdfundedNum.equals(activity.crowdfundedNum) : activity.crowdfundedNum != null)
            return false;
        if (isCrowdfunded != null ? !isCrowdfunded.equals(activity.isCrowdfunded) : activity.isCrowdfunded != null)
            return false;
        if (representNum != null ? !representNum.equals(activity.representNum) : activity.representNum != null)
            return false;
        if (crowdDeclaration != null ? !crowdDeclaration.equals(activity.crowdDeclaration) : activity.crowdDeclaration != null)
            return false;
        if (supportDeclaration != null ? !supportDeclaration.equals(activity.supportDeclaration) : activity.supportDeclaration != null)
            return false;
        if (representDeclaration != null ? !representDeclaration.equals(activity.representDeclaration) : activity.representDeclaration != null)
            return false;
        if (crowdfundHintSwitch != null ? !crowdfundHintSwitch.equals(activity.crowdfundHintSwitch) : activity.crowdfundHintSwitch != null)
            return false;
        if (memberId != null ? !memberId.equals(activity.memberId) : activity.memberId != null) return false;
        if (status != null ? !status.equals(activity.status) : activity.status != null) return false;
        if (templateStyle != null ? !templateStyle.equals(activity.templateStyle) : activity.templateStyle != null)
            return false;
        if (publisher != null ? !publisher.equals(activity.publisher) : activity.publisher != null) return false;
        if (publisherLogo != null ? !publisherLogo.equals(activity.publisherLogo) : activity.publisherLogo != null)
            return false;
        if (eventId != null ? !eventId.equals(activity.eventId) : activity.eventId != null) return false;
        return counterfoils != null ? counterfoils.equals(activity.counterfoils) : activity.counterfoils == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (activityType != null ? activityType.hashCode() : 0);
        result = 31 * result + (thirdPartyId != null ? thirdPartyId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (limitNum != null ? limitNum.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (isOpen != null ? isOpen.hashCode() : 0);
        result = 31 * result + (inviteOnly != null ? inviteOnly.hashCode() : 0);
        result = 31 * result + (inviteCode != null ? inviteCode.hashCode() : 0);
        result = 31 * result + (joinHidden != null ? joinHidden.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (shareNum != null ? shareNum.hashCode() : 0);
        result = 31 * result + (readNum != null ? readNum.hashCode() : 0);
        result = 31 * result + (joinNum != null ? joinNum.hashCode() : 0);
        result = 31 * result + (goodNum != null ? goodNum.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (checkStatus != null ? checkStatus.hashCode() : 0);
        result = 31 * result + (isAllowedOutside != null ? isAllowedOutside.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (micWebStatus != null ? micWebStatus.hashCode() : 0);
        result = 31 * result + (showFront != null ? showFront.hashCode() : 0);
        result = 31 * result + (favorerNum != null ? favorerNum.hashCode() : 0);
        result = 31 * result + (beCrowdfundNum != null ? beCrowdfundNum.hashCode() : 0);
        result = 31 * result + (crowdfundedNum != null ? crowdfundedNum.hashCode() : 0);
        result = 31 * result + (isCrowdfunded != null ? isCrowdfunded.hashCode() : 0);
        result = 31 * result + (representNum != null ? representNum.hashCode() : 0);
        result = 31 * result + (crowdDeclaration != null ? crowdDeclaration.hashCode() : 0);
        result = 31 * result + (supportDeclaration != null ? supportDeclaration.hashCode() : 0);
        result = 31 * result + (representDeclaration != null ? representDeclaration.hashCode() : 0);
        result = 31 * result + (crowdfundHintSwitch != null ? crowdfundHintSwitch.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (templateStyle != null ? templateStyle.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (publisherLogo != null ? publisherLogo.hashCode() : 0);
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (counterfoils != null ? counterfoils.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityType=" + activityType +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", cityId='" + cityId + '\'' +
                ", area='" + area + '\'' +
                ", limitNum=" + limitNum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", place='" + place + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", isOpen=" + isOpen +
                ", inviteOnly=" + inviteOnly +
                ", inviteCode='" + inviteCode + '\'' +
                ", joinHidden=" + joinHidden +
                ", extra='" + extra + '\'' +
                ", shareNum=" + shareNum +
                ", readNum=" + readNum +
                ", joinNum=" + joinNum +
                ", goodNum=" + goodNum +
                ", member='" + member + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", isAllowedOutside=" + isAllowedOutside +
                ", sort=" + sort +
                ", micWebStatus=" + micWebStatus +
                ", showFront=" + showFront +
                ", favorerNum=" + favorerNum +
                ", beCrowdfundNum=" + beCrowdfundNum +
                ", crowdfundedNum=" + crowdfundedNum +
                ", isCrowdfunded=" + isCrowdfunded +
                ", representNum=" + representNum +
                ", crowdDeclaration='" + crowdDeclaration + '\'' +
                ", supportDeclaration='" + supportDeclaration + '\'' +
                ", representDeclaration='" + representDeclaration + '\'' +
                ", crowdfundHintSwitch=" + crowdfundHintSwitch +
                ", memberId='" + memberId + '\'' +
                ", status=" + status +
                ", templateStyle=" + templateStyle +
                ", publisher='" + publisher + '\'' +
                ", publisherLogo='" + publisherLogo + '\'' +
                ", eventId='" + eventId + '\'' +
                ", counterfoils=" + counterfoils +
                '}';
    }
}
