package com.party.mobile.web.dto.crowdfund.output;

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

    //创建时间
    private Date createDate;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;

    //分销对象编号
    private String targetId;

    //被分销者编号
    private String parentId;

    //分销者编号
    private String distributorId;

    //代言宣言
    private String declaration;



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

    public static ListForTargetOutput transform(DistributorRelation distributorRelation){
        ListForTargetOutput listForTargetOutput = new ListForTargetOutput();
        BeanUtils.copyProperties(distributorRelation, listForTargetOutput);
        return listForTargetOutput;
    }
}
