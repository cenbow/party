package com.party.web.web.dto.output.crowdfund;

import java.util.Date;

import com.party.common.annotation.ExcelField;
import org.springframework.beans.BeanUtils;

import com.party.core.model.crowdfund.ProjectWithAuthor;

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

    //创建者编号
    private String authorId;

    //创建者名称
    private String authorName;

    //创建者图片
    private String authorLogo;

    //创建者公司
    private String authorCompany;

    //创建者职务
    private String authorJobTitle;

    //创建者电话
    private String authorMobile;

    //创建者区域
    private String cityName;

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

    //众筹是否成功
    private Integer isSuccess;

    //众筹状态
    private String status;

    //浏览量
    private Integer viewNum;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelField(title = "众筹者", align = 2, sort = 1)
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

    @ExcelField(title = "支持人数", align = 2, sort = 5)
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

    @ExcelField(title = "支持金额", align = 2, sort = 6)
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

    @ExcelField(title = "公司", align = 2, sort = 2)
    public String getAuthorCompany() {
        return authorCompany;
    }

    public void setAuthorCompany(String authorCompany) {
        this.authorCompany = authorCompany;
    }

    @ExcelField(title = "职务", align = 2, sort = 3)
    public String getAuthorJobTitle() {
        return authorJobTitle;
    }

    public void setAuthorJobTitle(String authorJobTitle) {
        this.authorJobTitle = authorJobTitle;
    }

    @ExcelField(title = "联系电话", align = 2, sort = 4)
    public String getAuthorMobile() {
        return authorMobile;
    }

    public void setAuthorMobile(String authorMobile) {
        this.authorMobile = authorMobile;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @ExcelField(title = "众筹状态", align = 2, sort = 7)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public static ProjectForActivityOutput transform(ProjectWithAuthor projectWithAuthor){
        ProjectForActivityOutput projectForActivityOutput = new ProjectForActivityOutput();
        BeanUtils.copyProperties(projectWithAuthor, projectForActivityOutput);
        return projectForActivityOutput;
    }
}
