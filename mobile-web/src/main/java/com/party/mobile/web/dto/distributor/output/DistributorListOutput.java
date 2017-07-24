package com.party.mobile.web.dto.distributor.output;

import com.party.core.model.distributor.WithCount;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 分销列表输出视图
 * Created by wei.li
 *
 * @date 2017/3/8 0008
 * @time 16:02
 */
public class DistributorListOutput {

    //编号
    private String id;

    //分销者名称
    private String distributorName;

    //分销者图像
    private String distributorLogo;

    //创建时间
    private Date createDate;

    //分销关系类型
    private Integer type;

    //分销对象编号
    private String targetId;

    //分销者编号
    private String distributorId;

    //被分销者编号
    private String parentId;

    //题目
    private String title;

    //图片
    private String pic;

    //价格
    private Float price;

    //目标结束时间
    private Date targetEndDate;

    //浏览量
    private Integer viewNum;

    //分享量
    private Integer shareNum;

    //报名量
    private Integer applyNum;

    //销售量
    private Integer salesNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getTargetEndDate() {
        return targetEndDate;
    }

    public void setTargetEndDate(Date targetEndDate) {
        this.targetEndDate = targetEndDate;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public static DistributorListOutput transform(WithCount withCount){
        DistributorListOutput distributorListOutput = new DistributorListOutput();
        BeanUtils.copyProperties(withCount, distributorListOutput);
        return distributorListOutput;
    }
}
