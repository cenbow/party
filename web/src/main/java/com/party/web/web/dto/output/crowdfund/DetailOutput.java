package com.party.web.web.dto.output.crowdfund;

import com.party.core.model.crowdfund.Project;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by wei.li
 *
 * @date 2017/2/23 0023
 * @time 12:12
 */
public class DetailOutput {

    //主键
    private String id;

    //创建人
    private String createBy;

    //创建时间
    private Date createDate;

    //更新人
    private String updateBy;

    //更新时间
    private Date updateDate;

    //备注
    private String remarks;

    //删除标记
    private String delFlag;

    //项目标题
    private String title;

    //项目内容
    private String content;

    //支持者数目
    private Integer favorerNum;

    //目标筹集资金
    private Float targetAmount;

    //实际筹集资金
    private Float actualAmount;

    //发布者编号
    private String authorId;

    //照片地址
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public static DetailOutput transform(Project project){
        DetailOutput detailOutput = new DetailOutput();
        BeanUtils.copyProperties(project, detailOutput);
        return detailOutput;
    }
}
