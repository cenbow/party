package com.party.core.model.distributor;

import com.party.core.model.BaseModel;

/**
 * 分销详情实体
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 13:46
 */
public class DistributorDetail extends BaseModel{
    private static final long serialVersionUID = -4389277810607051121L;

    //分销详情目标编号
    private String targetId;

    //分销关系编号
    private String distributorRelationId;

    //分销详情类型(0:订单 1：报名 3:众筹 4：支持)
    private Integer type;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDistributorRelationId() {
        return distributorRelationId;
    }

    public void setDistributorRelationId(String distributorRelationId) {
        this.distributorRelationId = distributorRelationId;
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

        DistributorDetail that = (DistributorDetail) o;

        if (targetId != null ? !targetId.equals(that.targetId) : that.targetId != null) return false;
        if (distributorRelationId != null ? !distributorRelationId.equals(that.distributorRelationId) : that.distributorRelationId != null)
            return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (distributorRelationId != null ? distributorRelationId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}