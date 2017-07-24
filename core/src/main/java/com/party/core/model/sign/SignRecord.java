package com.party.core.model.sign;

import com.party.core.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 签到记录
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:41
 */
public class SignRecord extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1122141278431533995L;

    //报名编号
    @NotBlank(message = "报名不能为空")
    private String applyId;

    //凭证地址
    @NotBlank(message = "凭证连接不能为空")
    private String proofUrl;

    //步数
    @NotNull(message = "步数不能为空")
    private Long stepNum;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getProofUrl() {
        return proofUrl;
    }

    public void setProofUrl(String proofUrl) {
        this.proofUrl = proofUrl;
    }

    public Long getStepNum() {
        return stepNum;
    }

    public void setStepNum(Long stepNum) {
        this.stepNum = stepNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SignRecord that = (SignRecord) o;

        if (applyId != null ? !applyId.equals(that.applyId) : that.applyId != null) return false;
        if (proofUrl != null ? !proofUrl.equals(that.proofUrl) : that.proofUrl != null) return false;
        return stepNum != null ? stepNum.equals(that.stepNum) : that.stepNum == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (applyId != null ? applyId.hashCode() : 0);
        result = 31 * result + (proofUrl != null ? proofUrl.hashCode() : 0);
        result = 31 * result + (stepNum != null ? stepNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignRecord{" +
                "applyId='" + applyId + '\'' +
                ", proofUrl='" + proofUrl + '\'' +
                ", stepNum=" + stepNum +
                '}';
    }
}
