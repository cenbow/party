package com.party.web.web.dto.output.crowdfund;

import com.party.common.annotation.ExcelField;
import com.party.core.model.distributor.DistributorRelation;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 根据项目查代言输出视图
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 17:06
 */
public class ListForTargetOutput {

    //代言编号
    private String id;

    //众筹中人数
    private Integer beCrowdfundNum;

    //已经筹满人数
    private Integer crowdfundedNum;

    //代言数
    private Integer representNum;

    //创建者区域
    private String cityName;

    //创建时间
    private Date createDate;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;

    //创建者公司
    private String authorCompany;

    //创建者职务
    private String authorJobTitle;

    //创建者电话
    private String authorMobile;

    //浏览量
    private Integer viewNum;

    //分享量
    private Integer shareNum;

    //众筹量
    private Integer crowdfundNum;

    //支持者数目
    private Integer favorerNum;

    //实际筹集资金
    private Float actualAmount;

    //分销对象编号
    private String targetId;

    //被分销者编号
    private String parentId;

    //分销者编号
    private String distributorId;

    //代言宣言
    private String declaration;


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

    public Integer getRepresentNum() {
        return representNum;
    }

    public void setRepresentNum(Integer representNum) {
        this.representNum = representNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ExcelField(title = "姓名", align = 2, sort = 1)
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    @ExcelField(title = "公司", align = 2, sort = 2)
    public String getAuthorCompany() {
        return authorCompany;
    }

    public void setAuthorCompany(String authorCompany) {
        this.authorCompany = authorCompany;
    }

    @ExcelField(title = "职位", align = 2, sort = 3)
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

    @ExcelField(title = "浏览量", align = 2, sort = 8)
    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    @ExcelField(title = "分享量", align = 2, sort = 9)
    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    @ExcelField(title = "众筹人数", align = 2, sort = 5)
    public Integer getCrowdfundNum() {
        return crowdfundNum;
    }

    public void setCrowdfundNum(Integer crowdfundNum) {
        this.crowdfundNum = crowdfundNum;
    }

    @ExcelField(title = "支持人数", align = 2, sort = 6)
    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    @ExcelField(title = "支持金额", align = 2, sort = 7 )
    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public static ListForTargetOutput transform(DistributorRelation distributorRelation){
        ListForTargetOutput listForTargetOutput = new ListForTargetOutput();
        BeanUtils.copyProperties(distributorRelation, listForTargetOutput);
        return listForTargetOutput;
    }
}
