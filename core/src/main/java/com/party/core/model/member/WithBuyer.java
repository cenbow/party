package com.party.core.model.member;

/**
 * 报名信息包含报名者
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 11:41
 */
public class WithBuyer extends MemberAct {

    //头像
    private String logo;

    //姓名
    private String realname;

    //活动名称
    private String activityName;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WithBuyer withBuyer = (WithBuyer) o;

        if (logo != null ? !logo.equals(withBuyer.logo) : withBuyer.logo != null) return false;
        if (realname != null ? !realname.equals(withBuyer.realname) : withBuyer.realname != null) return false;
        return activityName != null ? activityName.equals(withBuyer.activityName) : withBuyer.activityName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
    }
}
