package com.party.core.model.notify;

import com.party.core.model.BaseModel;

/**
 * 消息开关
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 18:34
 */
public class MemberSet extends BaseModel{

    //事项编号
    private String eventId;

    //开关
    private Integer setSwitch;

    //会员编号
    private String memberId;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getSetSwitch() {
        return setSwitch;
    }

    public void setSetSwitch(Integer setSwitch) {
        this.setSwitch = setSwitch;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MemberSet memberSet = (MemberSet) o;

        if (eventId != null ? !eventId.equals(memberSet.eventId) : memberSet.eventId != null) return false;
        if (setSwitch != null ? !setSwitch.equals(memberSet.setSwitch) : memberSet.setSwitch != null) return false;
        return memberId != null ? memberId.equals(memberSet.memberId) : memberSet.memberId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (setSwitch != null ? setSwitch.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberSet{" +
                "eventId='" + eventId + '\'' +
                ", setSwitch=" + setSwitch +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
