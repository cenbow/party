package com.party.mobile.web.dto.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.goods.Goods;
import com.party.mobile.web.dto.goods.output.ThirdPartyOutput;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 标准商品输出视图
 * party
 * Created by wei.li
 * on 2016/9/21 0021.
 */
public class GoodsOutput extends CreateByOutput {

    //商品主键
    private String id;

    //标题
    private String title;

    //城市名
    private String cityName;

    //区
    private String area;

    // 开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date startTime;

    // 结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    // 集合地点
    private String place;

    // 封面图片
    private String picsURL;

    // 经度
    private String lng;

    // 纬度
    private String lat;

    // 价格
    private Float price;

    // 推荐理由
    private String recommend;

    //注意事项
    private String notice;

    // 是否可以用优惠券(0 否， 1 是)
    private String isUseCoupon;

    //商品类型（0：标准，1：定制）
    private Integer type;

    //商品详情
    private String content;

    //第三方公司
    private ThirdPartyOutput thirdParty;

    //当前用户是否允许分销（0:：不是 1：是）
    private Integer isDistributor;

    //商品状态：是否正在进行
    private Integer isInProgress;

    //分享链接
    private String shareLink;

    //会员编号
    private String memberId;
    
    private Integer maxBuyNum;		// 限购数量
    private Integer limitNum;		// 库存
    private Integer joinNum;        // 已经购买的数量
    private Integer memberBuyNum;   // 用户已买的数量

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

    public Integer getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(Integer isDistributor) {
        this.isDistributor = isDistributor;
    }

    public String getPicsURL() {
        return picsURL;
    }

    public void setPicsURL(String picsURL) {
        this.picsURL = picsURL;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ThirdPartyOutput getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyOutput thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Integer isInProgress) {
        this.isInProgress = isInProgress;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
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

    public boolean isMyDistribution() {
        return isMyDistribution;
    }

    public void setMyDistribution(boolean myDistribution) {
        isMyDistribution = myDistribution;
    }


	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	public Integer getMemberBuyNum() {
		return memberBuyNum;
	}

	public void setMemberBuyNum(Integer memberBuyNum) {
		this.memberBuyNum = memberBuyNum;
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

    /**
     * 商品实体转输出视图
     * @param goods 商品实体
     * @return 输出视图
     */
    public static GoodsOutput transform(Goods goods){
        GoodsOutput goodsOutput = new GoodsOutput();
        BeanUtils.copyProperties(goods, goodsOutput);
        return goodsOutput;
    }
}
