package com.party.core.model.distributor;

import com.party.core.model.BaseModel;

/**
 * 分销目标附属表
 * Created by wei.li
 *
 * @date 2017/3/3 0003
 * @time 18:29
 */
public class DistributorTargetAttache extends BaseModel{
    private static final long serialVersionUID = -1085330883922258150L;

    //风格
    private String style;

    //题目
    private String title;

    //内容
    private String content;

    //分销关系
    private String distributorRelationId;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDistributorRelationId() {
        return distributorRelationId;
    }

    public void setDistributorRelationId(String distributorRelationId) {
        this.distributorRelationId = distributorRelationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistributorTargetAttache that = (DistributorTargetAttache) o;

        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return distributorRelationId != null ? distributorRelationId.equals(that.distributorRelationId) : that.distributorRelationId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (distributorRelationId != null ? distributorRelationId.hashCode() : 0);
        return result;
    }
}
