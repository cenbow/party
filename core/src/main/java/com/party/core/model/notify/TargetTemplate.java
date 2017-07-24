package com.party.core.model.notify;

import com.party.core.model.BaseModel;

/**
 * 业务短信模板
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 9:44
 */
public class TargetTemplate extends BaseModel {

    private static final long serialVersionUID = -257606948504068295L;

    //目标编号
    private String targetId;

    //模板内容
    private String template;

    //模板类型
    public Integer type;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TargetTemplate that = (TargetTemplate) o;

        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        if (template != null ? !template.equals(that.template) : that.template != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TargetTemplate{" +
                "targetId='" + targetId + '\'' +
                ", template='" + template + '\'' +
                ", type=" + type +
                '}';
    }
}
