package com.party.mobile.web.dto.login.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.Nonnull;
import javax.validation.constraints.Size;

/**
 * Created by Wesley on 16/10/31.
 *
 * @Author: Wesley
 * @Description: 第三方授权登陆输入视图
 * @Date: Created in 14:33 16/10/31
 * @Modified by:
 */
public class ThirdPartyUserInput {
    //用户名
    @NotBlank(message = "openId不能为空")
    @Size(min = 20,message = "openId格式不正确")
    private String openId;

    private String unionId;

    //商户编号
    private String merchantId;

    //类型
    @Nonnull()
    private Integer type;   //openId类型(0:app微信授权，1：qq，2：微博，3:微信公众号授权)

    private String logo;			// 头像

    private String realname;		// 姓名

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
