package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

/**
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:45
 */
public class ProjectLabel extends BaseModel{
    private static final long serialVersionUID = -8373344194706677221L;

    //众筹编号
    private String projectId;

    //标签编号
    private String labelId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProjectLabel that = (ProjectLabel) o;

        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        return labelId != null ? labelId.equals(that.labelId) : that.labelId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (labelId != null ? labelId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectLabel{" +
                "projectId='" + projectId + '\'' +
                ", labelId='" + labelId + '\'' +
                '}';
    }
}
