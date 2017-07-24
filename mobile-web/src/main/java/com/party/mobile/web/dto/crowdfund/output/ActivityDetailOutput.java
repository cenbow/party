package com.party.mobile.web.dto.crowdfund.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.CrowfundResources;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 众筹活动详情输出视图 Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 17:42
 */
public class ActivityDetailOutput {

	// 编号
	private String id;

	// 标题
	private String title;

	// 图片
	private String pic;

	// 活动开始时间
	private Date startTime;

	// 结束时间
	private Date endTime;

	// 价格
	private Float price;

	// 报名人数
	private Integer joinNum;

	// 支持人数
	private Integer favorerNum;

	// 众筹中人数
	private Integer beCrowdfundNum;

	// 已经筹满人数
	private Integer crowdfundedNum;

	// 活动详情
	private String content;

	//报名相关
	private String applyRelated;

	//参赛标准
	private String matchStandard;

	// 创建者编号
	private String authorId;

	// 创建者名称
	private String authorName;

	// 创建者图像
	private String authorLogo;

	// 众筹编号
	private String myProjectId;

	// 代言数
	private Integer representNum;

	// 人数上限
	private Integer limitNum;

	// 众筹宣言
	private String crowdDeclaration;

	// 代言宣言
	private String representDeclaration;

	// 描述
	private String remarks;
	
	// 跑马灯
	private List<CrowfundResources> picList;

	// 视频
	private CrowfundResources video;

	//代言父编号
	private String parentId;

	//代言者编号
	private String distributorId;

	//隐藏众筹数据
	private Integer templateStyle;

	//是否已经截止
	@JSONField(name = "isClosed")
	private boolean isClosed;

	public String getId() {
		return id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	public Integer getFavorerNum() {
		return favorerNum;
	}

	public void setFavorerNum(Integer favorerNum) {
		this.favorerNum = favorerNum;
	}

	public Integer getBeCrowdfundNum() {
		return beCrowdfundNum;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorLogo() {
		return authorLogo;
	}

	public void setAuthorLogo(String authorLogo) {
		this.authorLogo = authorLogo;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMyProjectId() {
		return myProjectId;
	}

	public void setMyProjectId(String myProjectId) {
		this.myProjectId = myProjectId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Integer getRepresentNum() {
		return representNum;
	}

	public void setRepresentNum(Integer representNum) {
		this.representNum = representNum;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public String getRepresentDeclaration() {
		return representDeclaration;
	}

	public void setRepresentDeclaration(String representDeclaration) {
		this.representDeclaration = representDeclaration;
	}

	public String getCrowdDeclaration() {
		return crowdDeclaration;
	}

	public void setCrowdDeclaration(String crowdDeclaration) {
		this.crowdDeclaration = crowdDeclaration;
	}

	public String getApplyRelated() {
		return applyRelated;
	}

	public void setApplyRelated(String applyRelated) {
		this.applyRelated = applyRelated;
	}

	public String getMatchStandard() {
		return matchStandard;
	}

	public void setMatchStandard(String matchStandard) {
		this.matchStandard = matchStandard;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public Integer getTemplateStyle() {
		return templateStyle;
	}

	public void setTemplateStyle(Integer templateStyle) {
		this.templateStyle = templateStyle;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean closed) {
		isClosed = closed;
	}

	public static ActivityDetailOutput transform(Activity activity) {
		ActivityDetailOutput activityDetailOutput = new ActivityDetailOutput();
		BeanUtils.copyProperties(activity, activityDetailOutput);
		return activityDetailOutput;
	}

}
