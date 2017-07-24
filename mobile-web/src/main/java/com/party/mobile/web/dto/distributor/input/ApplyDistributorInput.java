package com.party.mobile.web.dto.distributor.input;

import com.party.core.model.distributor.DistributorDetail;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * 分销下单输入视图
 * Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 11:24
 */
public class ApplyDistributorInput {

    //分销详情目标编号
    @NotBlank(message = "目标编号不能为空")
    private String targetId;

    //分销详情类型
    @NotNull(message = "类型不能为空")
    private Integer type;

    //分销关系类型
    @NotNull(message = "类型不能为空")
    private Integer distributorType;

    //分销对象编号
    @NotBlank(message = "分销对象编号不能为空")
    private String distributorTargetId;

    //被分销者编号
    @NotBlank(message = "分销父对象不能空")
    private String distributorParentId;

    @NotBlank(message = "分销者不能为空")
    private String distributorId;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getDistributorType() {
        return distributorType;
    }

    public void setDistributorType(Integer distributorType) {
        this.distributorType = distributorType;
    }

    public String getDistributorTargetId() {
        return distributorTargetId;
    }

    public void setDistributorTargetId(String distributorTargetId) {
        this.distributorTargetId = distributorTargetId;
    }

    public String getDistributorParentId() {
        return distributorParentId;
    }

    public void setDistributorParentId(String distributorParentId) {
        this.distributorParentId = distributorParentId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public static DistributorDetail transform(ApplyDistributorInput input){
        DistributorDetail distributorDetail = new DistributorDetail();
        BeanUtils.copyProperties(input, distributorDetail);
        return distributorDetail;
    }
}
