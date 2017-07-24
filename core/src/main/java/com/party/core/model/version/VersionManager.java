package com.party.core.model.version;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 第三方登录开启实体
 * party
 * Created by Wesley
 * on 2016/11/23
 */
public class VersionManager extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1242763703630768846L;
    //qq登录开关
    private Integer loginQQ;

    //wx登录开关
    private Integer loginWX;

    //是否debug版本
    private Integer isDebug;

    //版本号
    private String versionNumber;

    //是否审核通过
    private Integer isOpen;
    
    public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getLoginQQ() {
        return loginQQ;
    }

    public void setLoginQQ(Integer loginQQ) {
        this.loginQQ = loginQQ;
    }

    public Integer getLoginWX() {
        return loginWX;
    }

    public void setLoginWX(Integer loginWX) {
        this.loginWX = loginWX;
    }

    public Integer getIsDebug() {
        return isDebug;
    }

    public void setIsDebug(Integer isDebug) {
        this.isDebug = isDebug;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VersionManager that = (VersionManager) o;

        if (loginQQ != null ? !loginQQ.equals(that.loginQQ) : that.loginQQ != null) return false;
        if (loginWX != null ? !loginWX.equals(that.loginWX) : that.loginWX != null) return false;
        if (isDebug != null ? !isDebug.equals(that.isDebug) : that.isDebug != null) return false;
        return versionNumber != null ? versionNumber.equals(that.versionNumber) : that.versionNumber == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (loginQQ != null ? loginQQ.hashCode() : 0);
        result = 31 * result + (loginWX != null ? loginWX.hashCode() : 0);
        result = 31 * result + (isDebug != null ? isDebug.hashCode() : 0);
        result = 31 * result + (versionNumber != null ? versionNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VersionManager{" +
                "loginQQ=" + loginQQ +
                ", loginWX=" + loginWX +
                ", isDebug=" + isDebug +
                ", versionNumber='" + versionNumber + '\'' +
                '}';
    }
}
