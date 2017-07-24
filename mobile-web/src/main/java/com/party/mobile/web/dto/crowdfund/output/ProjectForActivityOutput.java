package com.party.mobile.web.dto.crowdfund.output;

import com.party.core.model.crowdfund.ProjectWithAuthor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 活动的众筹列表输出视图
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 18:27
 */
public class ProjectForActivityOutput {

    //编号
    private String id;

    //创建者名称
    private String authorName;

    //创建者图片
    private String authorLogo;

    //创建时间
    private Date createDate;

    //支持者数目
    private Integer favorerNum;

    //备注
    private String remarks;

    //实际筹集资金
    private Float actualAmount;

    //宣言
    private String declaration;

    //支持者列表
    private List<FavorerOutput> favorerOutputList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public List<FavorerOutput> getFavorerOutputList() {
        return favorerOutputList;
    }

    public void setFavorerOutputList(List<FavorerOutput> favorerOutputList) {
        this.favorerOutputList = favorerOutputList;
    }

    public static ProjectForActivityOutput transform(ProjectWithAuthor projectWithAuthor){
        ProjectForActivityOutput projectForActivityOutput = new ProjectForActivityOutput();
        BeanUtils.copyProperties(projectWithAuthor, projectForActivityOutput);
        return projectForActivityOutput;
    }
}
