package com.party.mobile.web.dto.login.output;

import com.party.core.model.member.ThirdPartyUser;
import org.springframework.beans.BeanUtils;

/**
 * 第三方登录用户输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 15:37 16/11/10
 * @Modified by:
 */
public class ThirdPartyUserOutput {
    //第三方登录id
    private String thirdPartyId;

    //类型
    private Integer type;

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public static ThirdPartyUserOutput transform(ThirdPartyUser thirdPartyUser){
        ThirdPartyUserOutput thirdPartyUserOutput = new ThirdPartyUserOutput();
        BeanUtils.copyProperties(thirdPartyUser, thirdPartyUserOutput);
        return thirdPartyUserOutput;
    }
}
