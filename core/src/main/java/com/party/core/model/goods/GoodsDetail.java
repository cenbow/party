package com.party.core.model.goods;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * GoodsDetail
 *
 * @author Wesley
 * @data 16/9/7 15:41 .
 */
public class GoodsDetail extends BaseModel implements Serializable {

    private static final long serialVersionUID = -20881861619384719L;

    private String refId;    //相关活动id
    private String content;  // 详情

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GoodsDetail that = (GoodsDetail) o;

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
        return "GoodsDetail{" +
                "refId='" + refId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
