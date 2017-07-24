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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GroupMember that = (GroupMember) o;

        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) return false;
        if (memberLogo != null ? !memberLogo.equals(that.memberLogo) : that.memberLogo != null) return false;
        if (memberCompany != null ? !memberCompany.equals(that.memberCompany) : that.memberCompany != null)
            return false;
        if (memberJobTitle != null ? !memberJobTitle.equals(that.memberJobTitle) : that.memberJobTitle != null)
            return false;
        if (memberMobile != null ? !memberMobile.equals(that.memberMobile) : that.memberMobile != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return endTime != null ? endTime.equals(that.endTime) : that.endTime == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (memberLogo != null ? memberLogo.hashCode() : 0);
        result = 31 * result + (memberCompany != null ? memberCompany.hashCode() : 0);
        result = 31 * result + (memberJobTitle != null ? memberJobTitle.hashCode() : 0);
        result = 31 * result + (memberMobile != null ? memberMobile.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "memberName='" + memberName + '\'' +
                ", memberLogo='" + memberLogo + '\'' +
                ", memberCompany='" + memberCompany + '\'' +
                ", memberJobTitle='" + memberJobTitle + '\'' +
                ", memberMobile='" + memberMobile + '\'' +
                ", groupName='" + groupName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
