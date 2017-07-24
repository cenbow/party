package com.party.core.model.sign;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 签到报名实体
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:36
 */
public class SignApply extends BaseModel implements Serializable {
    private static final long serialVersionUID = 5326962346614205530L;

    //项目编号
    private String projectId;

    //分组编号
    private String groupId;

    //会员编号
    private String memberId;

    //步数
    private Long stepNum;

    //点赞数
    private Integer loveNum;

    //状态
    private Integer status;

    //成绩状态
    private Integer gradeStatus;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Long getStepNum() {
        return stepNum;
    }

    public void setStepNum(Long stepNum) {
        this.stepNum = stepNum;
    }

    public Integer getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(Integer loveNum) {
        this.loveNum = loveNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGradeStatus() {
        return gradeStatus;
    }

    public void setGradeStatus(Integer gradeStatus) {
        this.gradeStatus = gradeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SignApply signApply = (SignApply) o;

        if (projectId != null ? !projectId.equals(signApply.projectId) : signApply.projectId != null) return false;
        if (groupId != null ? !groupId.equals(signApply.groupId) : signApply.groupId != null) return false;
        if (memberId != null ? !memberId.equals(signApply.memberId) : signApply.memberId != null) return false;
        if (stepNum != null ? !stepNum.equals(signApply.stepNum) : signApply.stepNum != null) return false;
        if (loveNum != null ? !loveNum.equals(signApply.loveNum) : signApply.loveNum != null) return false;
        if (status != null ? !status.equals(signApply.status) : signApply.status != null) return false;
        return gradeStatus != null ? gradeStatus.equals(signApply.gradeStatus) : signApply.gradeStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (stepNum != null ? stepNum.hashCode() : 0);
        result = 31 * result + (loveNum != null ? loveNum.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (gradeStatus != null ? gradeStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignApply{" +
                "projectId='" + projectId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", stepNum=" + stepNum +
                ", loveNum=" + loveNum +
                ", status=" + status +
                ", gradeStatus=" + gradeStatus +
                '}';
    }
}
