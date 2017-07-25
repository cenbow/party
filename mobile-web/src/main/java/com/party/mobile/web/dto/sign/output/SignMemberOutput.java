package com.party.mobile.web.dto.sign.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.sign.GroupMember;
import org.springframework.beans.BeanUtils;

/**
 * 签到会员输出视图
 * Created by wei.li
 *
 * @date 2017/6/9 0009
 * @time 18:19
 */
public class SignMemberOutput extends GroupMember {

    //项目题目
    private String title;

    //主页图片
    private String pic;

    //小组排行是否需要显示
    private Integer rankShow;

    //今日步数
    private Long todayStep;

    //月累计步数
    private Long monthStep;

    //打卡次数
    private Integer countSign;

    //是否签到
    @JSONField(name = "isSign")
    private boolean isSign;

    //备注
    private String remarks;

    //是否在有效时间
    @JSONField(name = "isValidTime")
    private boolean isValidTime;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getTodayStep() {
        return todayStep;
    }

    public void setTodayStep(Long todayStep) {
        this.todayStep = todayStep;
    }

    public Long getMonthStep() {
        return monthStep;
    }

    public void setMonthStep(Long monthStep) {
        this.monthStep = monthStep;
    }

    public Integer getCountSign() {
        return countSign;
    }

    public void setCountSign(Integer countSign) {
        this.countSign = countSign;
    }

    public Integer getRankShow() {
        return rankShow;
    }

    public void setRankShow(Integer rankShow) {
        this.rankShow = rankShow;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isValidTime() {
        return isValidTime;
    }

    public void setValidTime(boolean validTime) {
        isValidTime = validTime;
    }

    public static SignMemberOutput transform(GroupMember groupMember){
        SignMemberOutput signMemberOutput = new SignMemberOutput();
        BeanUtils.copyProperties(groupMember, signMemberOutput);
        return signMemberOutput;
    }
}
