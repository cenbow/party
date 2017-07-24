package com.party.mobile.web.dto.activity.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 活动输出视图
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class ActivityOutput extends CreateByOutput {

    //主键
    private String id;

    // 活动类型
    private Integer activityType;

    // 名称
    private String title;

    // 封面图
    private String pic;

    // 城市ID
    private String cityId;

    //城市名称
    private String cityName;

    // 区,字符串，由后台录入
    private String area;

    // 人数上限
    private Integer limitNum;
    
    // 报名人数
    private Integer joinNum;

    // 开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;

    // 结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;

    // 活动场所
    private String place;

    // 经度
    private String lng;

    // 纬度
    private String lat;

    // 报名开启
    private Integer isOpen;

    // 持邀请码参与
    private Integer inviteOnly;

    // 邀请码
    private String inviteCode;

    // 隐藏报名人员
    private Integer joinHidden;

    // 其他要填的选项
    private String extra;

    // 会员ID
    private String member;

    //活动详情
    private String content;

    //报名编号
    private String meberActId;

    //订单编号
    private String orderId;

    //当前用户是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //报名费
    private Float price;
    
    // 报名费
    private String newPrice;

    //活动状态：是否正在进行
    private Integer isInProgress;

    //分享链接
    private String shareLink;

    //活动报名状态(0,"审核中",1,"审核通过，待支付",2,"审核拒绝",3,"已支付，报名成功",4,"已取消",5,"未参与")
    private Integer actStatus;
    
    //微网站状态（0: 不是 1：是）
    private Integer micWebStatus;

    //描述
    private String remarks;

    //是我的分销
    @JSONField(name = "isMyDistribution")
    private boolean isMyDistribution;

    //分销者名称
    private String distributorName;

    //分销者图像
    private String distributorLogo;

    //分销时间
    private Date distributorTime;

    //风格
    private String style;

    //宣言
    private String declaration;

    //报名者
    private List<BuyerOutput> buyerOutputList;
    // 票据
    private List<Counterfoil> counterfoils;

    //报名者数量
    private Integer buyerNum;

    public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMicWebStatus() {
		return micWebStatus;
	}

	public void setMicWebStatus(Integer micWebStatus) {
		this.micWebStatus = micWebStatus;
	}

	public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMeberActId() {
        return meberActId;
    }

    public void setMeberActId(String meberActId) {
        this.meberActId = meberActId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Integer getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Integer inProgress) {
        isInProgress = inProgress;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    public boolean isMyDistribution() {
        return isMyDistribution;
    }

    public void setMyDistribution(boolean myDistribution) {
        isMyDistribution = myDistribution;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorLogo() {
        return distributorLogo;
    }

    public void setDistributorLogo(String distributorLogo) {
        this.distributorLogo = distributorLogo;
    }

    public Date getDistributorTime() {
        return distributorTime;
    }

    public void setDistributorTime(Date distributorTime) {
        this.distributorTime = distributorTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public List<BuyerOutput> getBuyerOutputList() {
        return buyerOutputList;
    }

    public void setBuyerOutputList(List<BuyerOutput> buyerOutputList) {
        this.buyerOutputList = buyerOutputList;
    }

    public Integer getBuyerNum() {
        return buyerNum;
    }

    public void setBuyerNum(Integer buyerNum) {
        this.buyerNum = buyerNum;
    }

    /**
     * 活动转输出视图
     * @param activity 活动实体
     * @return 活动输出视图
     */
    public static ActivityOutput transform(Activity activity){
        ActivityOutput activityOutput = new ActivityOutput();
        BeanUtils.copyProperties(activity, activityOutput);
        return activityOutput;
    }

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	public List<Counterfoil> getCounterfoils() {
		return counterfoils;
	}

	public void setCounterfoils(List<Counterfoil> counterfoils) {
		this.counterfoils = counterfoils;
	}

	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
}
