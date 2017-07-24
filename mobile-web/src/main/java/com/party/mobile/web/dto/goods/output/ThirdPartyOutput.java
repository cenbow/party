package com.party.mobile.web.dto.goods.output;

import com.party.core.model.thirdParty.ThirdParty;
import org.springframework.beans.BeanUtils;

/**
 * 第三方供应商输出视图
 * @Author: Wesley
 * @Description:
 * @Date: Created in 18:43 16/11/4
 * @Modified by:
 */
public class ThirdPartyOutput {
    //公司名称
    private String comName;

    //联系人
    private String linkman;

    //联系电话
    private String telephone;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 转换为标准商品输出视图
     * @param thirdParty 商品实体
     * @return 商品输出视图
     */
    public static ThirdPartyOutput transform(ThirdParty thirdParty){
        ThirdPartyOutput thirdPartyOutput = new ThirdPartyOutput();
        BeanUtils.copyProperties(thirdParty, thirdPartyOutput);
        return thirdPartyOutput;
    }

}
