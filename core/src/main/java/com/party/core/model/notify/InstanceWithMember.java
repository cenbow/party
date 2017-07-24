package com.party.core.model.notify;

/**
 * Created by wei.li
 *
 * @date 2017/7/21 0021
 * @time 10:39
 */
public class InstanceWithMember extends Instance{

    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InstanceWithMember that = (InstanceWithMember) o;

        return memberName != null ? memberName.equals(that.memberName) : that.memberName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstanceWithMember{" +
                "memberName='" + memberName + '\'' +
                '}';
    }
}
