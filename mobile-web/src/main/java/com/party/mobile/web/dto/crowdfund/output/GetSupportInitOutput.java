package com.party.mobile.web.dto.crowdfund.output;

/**
 * 支持初始化输出视图
 * Created by wei.li
 *
 * @date 2017/3/6 0006
 * @time 14:37
 */
public class GetSupportInitOutput {

    //最大支持数
    private Float MaxNum;

    //支持宣言
    private String supportDeclaration;

    public Float getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(Float maxNum) {
        MaxNum = maxNum;
    }

    public String getSupportDeclaration() {
        return supportDeclaration;
    }

    public void setSupportDeclaration(String supportDeclaration) {
        this.supportDeclaration = supportDeclaration;
    }
}
