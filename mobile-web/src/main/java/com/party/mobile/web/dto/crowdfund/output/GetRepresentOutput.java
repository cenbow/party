package com.party.mobile.web.dto.crowdfund.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 19:57
 */
public class GetRepresentOutput {

    //编号
    private String id;

    //标题
    private String title;

    //图片
    private String pic;

    //活动开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //价格
    private Float price;

    //报名人数
    private Integer joinNum;

    //支持人数
    private Integer favorerNum;

    //众筹中人数
    private Integer beCrowdfundNum;

    //已经筹满人数
    private Integer crowdfundedNum;

    // 众筹中人数
    private Integer targetBeCrowdfundNum;

    // 已经筹满人数
    private Integer targetCrowdfundedNum;

    //活动详情
    private String content;

    //创建者编号
    private String authorId;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;

    //众筹编号
    private String myProjectId;

    //是否是我的代言
    @JSONField(name = "isMyRepresent")
    private boolean isMyRepresent;

    //风格
    private String style;

    //宣言
    private String declaration;

    //报名相关
    private String applyRelated;

    //参赛标准
    private String matchStandard;

    //项目发起者编号
    private String targetAuthorId;

    //分销关系编号
    private String distributorRelationId;

    //隐藏众筹数据
    private Integer templateStyle;

    //是否已经截止
    @JSONField(name = "isClosed")
    private boolean isClosed;

    public String getId() {
        return id;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public String getMyProjectId() {
        return myProjectId;
    }

    public void setMyProjectId(String myProjectId) {
        this.myProjectId = myProjectId;
    }

    public boolean isMyRepresent() {
        return isMyRepresent;
    }

    public void setMyRepresent(boolean myRepresent) {
        isMyRepresent = myRepresent;
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

    public String getTargetAuthorId() {
        return targetAuthorId;
    }

    public void setTargetAuthorId(String targetAuthorId) {
        this.targetAuthorId = targetAuthorId;
    }

    public String getDistributorRelationId() {
        return distributorRelationId;
    }

    public void setDistributorRelationId(String distributorRelationId) {
        this.distributorRelationId = distributorRelationId;
    }

    public Integer getTargetBeCrowdfundNum() {
        return targetBeCrowdfundNum;
    }

    public void setTargetBeCrowdfundNum(Integer targetBeCrowdfundNum) {
        this.targetBeCrowdfundNum = targetBeCrowdfundNum;
    }

    public Integer getTargetCrowdfundedNum() {
        return targetCrowdfundedNum;
    }

    public void setTargetCrowdfundedNum(Integer targetCrowdfundedNum) {
        this.targetCrowdfundedNum = targetCrowdfundedNum;
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

    public static GetRepresentOutput transform(Activity activity){
        GetRepresentOutput getRepresentOutput = new GetRepresentOutput();
        BeanUtils.copyProperties(activity, getRepresentOutput);
        return getRepresentOutput;
    }
}
