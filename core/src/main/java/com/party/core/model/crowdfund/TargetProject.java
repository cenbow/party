package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;

/**
 * 目标众筹关联表
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 12:08
 */
public class TargetProject extends BaseModel{


    private static final long serialVersionUID = 6798048591068190731L;

    //众筹类型（activity/order）
    private String type;

    //众筹目标订单号
    private String orderId;

    //众筹目标编号
    private String targetId;

    //众筹项目编号
    private String projectId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TargetProject that = (TargetProject) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        return projectId != null ? projectId.equals(that.projectId) : that.projectId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        return result;
    }
}
