package com.party.mobile.web.dto.crowdfund.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.member.Member;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 众筹项目输出视图
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 9:51
 */
public class ProjectOutput {

    //项目编号
    private String id;

    //题目
    private String title;

    //图片
    private String pic;

    //发起时间
    private Date createDate;

    //发起者编号
    private String authorId;

    //发起者昵称
    private String authorName;

    //发起者照片
    private String authorLogo;

    //项目描叙
    private String describe;

    //目标筹集资金
    private Float targetAmount;

    //实际筹集资金
    private Float actualAmount;

    //剩余筹集资金
    private Float surplusAmount;

    //支持者数目
    private Integer favorerNum;

    //宣言
    private String declaration;

    //项目内容
    private String content;

    //报名相关
    private String applyRelated;

    //参赛标准
    private String matchStandard;

    //众筹项目编号
    private String targeId;

    //是不是自己的项目
    @JSONField(name = "isMy")
    private boolean isMy;

    //风格
    private String style;

    //众筹是否成功
    private Integer isSuccess;

    //众筹编号
    private String myProjectId;

    //项目发起者编号
    private String targetAuthorId;

    //众筹中人数
    private Integer beCrowdfundNum;

    //已经筹满人数
    private Integer crowdfundedNum;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(Float surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getTargeId() {
        return targeId;
    }

    public void setTargeId(String targeId) {
        this.targeId = targeId;
    }

    public boolean isMy() {
        return isMy;
    }

    public void setMy(boolean my) {
        isMy = my;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMyProjectId() {
        return myProjectId;
    }

    public void setMyProjectId(String myProjectId) {
        this.myProjectId = myProjectId;
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

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    /**
     * 转输出视图
     * @param projectWithAuthor 项目视图
     * @return 输出视图
     */
    public static ProjectOutput  transform(ProjectWithAuthor projectWithAuthor){
        ProjectOutput projectOutput = new ProjectOutput();
        BeanUtils.copyProperties(projectWithAuthor, projectOutput);
        return projectOutput;
    }

}
