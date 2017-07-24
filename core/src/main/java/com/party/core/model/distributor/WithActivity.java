package com.party.core.model.distributor;

import java.util.Date;

/**
 * 包含活动信息分销关系
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 15:42
 */
public class WithActivity extends DistributorRelation {
    private static final long serialVersionUID = 6953407562119953621L;

    // 封面图
    private String pic;

    //题目
    private String title;

    //支持人数
    private Integer favorerNum;

    //代言人数
    private Integer representNum;

    //众筹人数
    private Integer crowdfundNum;

    //众筹中人数
    private Integer beCrowdfundNum;

    //已经筹满人数
    private Integer crowdfundedNum;

    //报名结束时间
    private Date endTime;

    //创建者名称
    private String authorName;

    //创建者图像
    private String authorLogo;


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

    public Integer getFavorerNum() {
        return favorerNum;
    }

    public void setFavorerNum(Integer favorerNum) {
        this.favorerNum = favorerNum;
    }

    public Integer getRepresentNum() {
        return representNum;
    }

    public void setRepresentNum(Integer representNum) {
        this.representNum = representNum;
    }

    public Integer getCrowdfundNum() {
        return crowdfundNum;
    }

    public void setCrowdfundNum(Integer crowdfundNum) {
        this.crowdfundNum = crowdfundNum;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}
