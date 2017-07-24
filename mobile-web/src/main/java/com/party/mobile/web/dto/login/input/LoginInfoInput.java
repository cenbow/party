package com.party.mobile.web.dto.login.input;

import com.party.core.model.version.VersionManager;
import com.party.mobile.web.dto.member.input.FillInfoInput;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * 第三方登录开关输入视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 10:55 16/11/23
 * @Modified by:
 */
public class LoginInfoInput {
    //是否debug版本(0：否，1：是)
    @NotNull(message = "是否debug版本不能为空")
    private Integer isDebug;

    //版本号
    @NotBlank(message = "版本号不能为空")
    private String versionNumber;

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

    /**
     * 将输入视图转换为实体
     * @param versionManager 第三方登录开关实体
     * @param infoInput 输入视图
     * @return 第三方登录开关实体
     */
    public static VersionManager transform(VersionManager versionManager, LoginInfoInput infoInput){
        BeanUtils.copyProperties(infoInput, versionManager);
        return versionManager;
    }
}
