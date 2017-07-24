package com.party.mobile.web.dto.crowdfund.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 众筹
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 10:30
 */
public class TargetOutput {

    //众筹目标编号
    private String id;

    //封面图
    private String pic;

    //标题
    private String title;

    //描叙
    private String remarks;

    //创建时间
    private Date createDate;

    //报名结束时间
    private Date endTime;

    //支持人数
    private Integer favorerNum;

    //众筹人数
    private Integer crowdfundNum;

    //代言人数
    private Integer representNum;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;

    //隐藏众筹数据
    private Integer templateStyle;

    public TargetOutput() {
        this.representNum = 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Integer getCrowdfundNum() {
        return crowdfundNum;
    }

    public void setCrowdfundNum(Integer crowdfundNum) {
        this.crowdfundNum = crowdfundNum;
    }

    public Integer getRepresentNum() {
        return representNum;
    }

    public void setRepresentNum(Integer representNum) {
        this.representNum = representNum;
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


    public Integer getTemplateStyle() {
        return templateStyle;
    }

    public void setTemplateStyle(Integer templateStyle) {
        this.templateStyle = templateStyle;
    }

    public static TargetOutput transform(Activity activity){
        TargetOutput targetOutput = new TargetOutput();
        BeanUtils.copyProperties(activity, targetOutput);
        targetOutput.setCrowdfundNum(activity.getBeCrowdfundNum() + activity.getCrowdfundedNum());
        return targetOutput;
    }
}
