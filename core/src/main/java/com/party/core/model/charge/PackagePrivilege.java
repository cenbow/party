package com.party.core.model.charge;

import com.party.core.model.BaseModel;

/**
 * 套餐对应的权限
 *
 * @author Administrator
 * @date 2017/7/27 0027 10:27
 */
public class PackagePrivilege extends BaseModel {
    private String packageId; // 套餐id
    private String privilegeId; // 权限id
    private Integer isNumber; // 是否为数字  1表示是，-1表示不是
    private String paramValue; // 参数值

    public PackagePrivilege() {
    }

    public PackagePrivilege(String packageId, String privilegeId) {
        this.packageId = packageId;
        this.privilegeId = privilegeId;
    }

    public Integer getIsNumber() {
        return isNumber;
    }

    public void setIsNumber(Integer isNumber) {
        this.isNumber = isNumber;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
