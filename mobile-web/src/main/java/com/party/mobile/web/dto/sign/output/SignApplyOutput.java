package com.party.mobile.web.dto.sign.output;

import com.party.mobile.web.dto.login.output.MemberOutput;

/**
 * 报名输出视图
 * Created by wei.li
 *
 * @date 2017/6/13 0013
 * @time 17:04
 */
public class SignApplyOutput {

    //报名编号
    private String applyId;

    //用户
    private MemberOutput memberOutput;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public MemberOutput getMemberOutput() {
        return memberOutput;
    }

    public void setMemberOutput(MemberOutput memberOutput) {
        this.memberOutput = memberOutput;
    }
}
