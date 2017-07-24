package com.party.core.model.sign;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 签到小组实体
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:32
 */
public class SignGroup extends BaseModel implements Serializable {

    private static final long serialVersionUID = -6087765385342935183L;

    //签到项目
    private String projectId;

    //小组名称
    private String name;

    //步数
    private Long stepNum;

    //报名数
    private Integer applyNum;

    //通过数
    private Integer passNum;

    //拒绝数
    private Integer refuseNum;

    //待审核数
    private Integer notAuditNum;

    //成员数（审核+成绩有效）
    private Integer memberNum;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStepNum() {
        return stepNum;
    }

    public void setStepNum(Long stepNum) {
        this.stepNum = stepNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Integer getRefuseNum() {
        return refuseNum;
    }

    public void setRefuseNum(Integer refuseNum) {
        this.refuseNum = refuseNum;
    }

    public Integer getNotAuditNum() {
        return notAuditNum;
    }

    public void setNotAuditNum(Integer notAuditNum) {
        this.notAuditNum = notAuditNum;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SignGroup signGroup = (SignGroup) o;

        if (projectId != null ? !projectId.equals(signGroup.projectId) : signGroup.projectId != null) return false;
        if (name != null ? !name.equals(signGroup.name) : signGroup.name != null) return false;
        if (stepNum != null ? !stepNum.equals(signGroup.stepNum) : signGroup.stepNum != null) return false;
        if (applyNum != null ? !applyNum.equals(signGroup.applyNum) : signGroup.applyNum != null) return false;
        if (passNum != null ? !passNum.equals(signGroup.passNum) : signGroup.passNum != null) return false;
        if (refuseNum != null ? !refuseNum.equals(signGroup.refuseNum) : signGroup.refuseNum != null) return false;
        if (notAuditNum != null ? !notAuditNum.equals(signGroup.notAuditNum) : signGroup.notAuditNum != null)
            return false;
        return memberNum != null ? memberNum.equals(signGroup.memberNum) : signGroup.memberNum == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (stepNum != null ? stepNum.hashCode() : 0);
        result = 31 * result + (applyNum != null ? applyNum.hashCode() : 0);
        result = 31 * result + (passNum != null ? passNum.hashCode() : 0);
        result = 31 * result + (refuseNum != null ? refuseNum.hashCode() : 0);
        result = 31 * result + (notAuditNum != null ? notAuditNum.hashCode() : 0);
        result = 31 * result + (memberNum != null ? memberNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignGroup{" +
                "projectId='" + projectId + '\'' +
                ", name='" + name + '\'' +
                ", stepNum=" + stepNum +
                ", applyNum=" + applyNum +
                ", passNum=" + passNum +
                ", refuseNum=" + refuseNum +
                ", notAuditNum=" + notAuditNum +
                ", memberNum=" + memberNum +
                '}';
    }
}
