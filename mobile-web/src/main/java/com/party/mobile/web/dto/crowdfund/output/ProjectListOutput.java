package com.party.mobile.web.dto.crowdfund.output;

import com.party.core.model.crowdfund.Project;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 众筹项目列表输出视图
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 10:15
 */
public class ProjectListOutput {

    //项目编号
    private String id;

    //项目标题
    private String title;

    //项目照片
    private String pic;

    //目标筹集资金
    private Float targetAmount;

    //实际筹集资金
    private Float actualAmount;

    //我支持的金额
    private Float myFavorerAmount;

    //创建时间
    private Date createDate;

    //支持人数
    private Integer favorerNum;

    //创建者编号
    private String authorId;

    //创建者用户状态
    private Integer authorStatus;

    //创建者是否达人(0：不是 1：是)
    private Integer authorIsExpert;

    //创建者名称
    private String authorName;

    //创建者图片
    private String authorLogo;

    //结束时间
    private Date endDate;

    //众筹宣言
    private String declaration;

    //众筹是否成功
    private Integer isSuccess;

    //支持者列表
    private List<FavorerOutput> favorerOutputList;

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

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Integer getAuthorStatus() {
        return authorStatus;
    }

    public void setAuthorStatus(Integer authorStatus) {
        this.authorStatus = authorStatus;
    }

    public Integer getAuthorIsExpert() {
        return authorIsExpert;
    }

    public void setAuthorIsExpert(Integer authorIsExpert) {
        this.authorIsExpert = authorIsExpert;
    }

    public List<FavorerOutput> getFavorerOutputList() {
        return favorerOutputList;
    }

    public void setFavorerOutputList(List<FavorerOutput> favorerOutputList) {
        this.favorerOutputList = favorerOutputList;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Float getMyFavorerAmount() {
        return myFavorerAmount;
    }

    public void setMyFavorerAmount(Float myFavorerAmount) {
        this.myFavorerAmount = myFavorerAmount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * 众筹项目转输出视图
     * @param project 项目信息
     * @return 输出视图
     */
    public static ProjectListOutput transform(Project project){
        ProjectListOutput projectListOutput = new ProjectListOutput();
        BeanUtils.copyProperties(project, projectListOutput);
        return projectListOutput;
    }
}
