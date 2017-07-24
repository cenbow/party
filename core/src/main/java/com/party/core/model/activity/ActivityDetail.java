package com.party.core.model.activity;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * ActivityDetail
 *
 * @author Wesley
 * @data 16/9/6 14:26 .
 */
public class ActivityDetail extends BaseModel implements Serializable {

    private static final long serialVersionUID = 2627621857400511011L;
    private String refId;    //相关活动id
    private String content;  // 详情
    private String applyRelated; //报名相关
    private String matchStandard; //参赛标准

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApplyRelated() {
        return applyRelated;
    }

    public void setApplyRelated(String applyRelated) {
        this.applyRelated = applyRelated;
    }

    public String getMatchStandard() {
        return matchStandard;
    }

    public void setMatchStandard(String matchStandard) {
        this.matchStandard = matchStandard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ActivityDetail that = (ActivityDetail) o;

        if (refId != null ? !refId.equals(that.refId) : that.refId != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityDetail{" +
                "refId='" + refId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
