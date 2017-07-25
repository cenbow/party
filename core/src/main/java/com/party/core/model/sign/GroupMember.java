package com.party.core.model.sign;


/**
 * 小组成员实体
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 11:22
 */
public class GroupMember extends SignApply{

    private static final long serialVersionUID = 5419574008208219600L;

    //会员名称
    private String memberName;

    //会员图像
    private String memberLogo;

    //会员公司
    private String memberCompany;

    //会员职位
    private String memberJobTitle;

    //会员电话
    private String memberMobile;

    //小组名称
    private String groupName;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    //排名数
    private Integer rank;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCompany() {
        return memberCompany;
    }

    public void setMemberCompany(String memberCompany) {
        this.memberCompany = memberCompany;
    }

    public String getMemberJobTitle() {
        return memberJobTitle;
    }

    public void setMemberJobTitle(String memberJobTitle) {
        this.memberJobTitle = memberJobTitle;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberLogo() {
        return memberLogo;
    }

    public void setMemberLogo(String memberLogo) {
        this.memberLogo = memberLogo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


}
