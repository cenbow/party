package com.party.mobile.web.dto.sign.output;

import com.party.core.model.sign.SignGroup;
import com.party.core.model.sign.SignProject;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 签到报名详情
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 17:56
 */
public class ApplyDetailOutput extends SignProject {

    //签到小组
    private List<SignGroup> signGroupList;

    public List<SignGroup> getSignGroupList() {
        return signGroupList;
    }

    public void setSignGroupList(List<SignGroup> signGroupList) {
        this.signGroupList = signGroupList;
    }

    public static ApplyDetailOutput transform(SignProject signProject){
        ApplyDetailOutput applyDetailOutput = new ApplyDetailOutput();
        BeanUtils.copyProperties(signProject, applyDetailOutput);
        return applyDetailOutput;
    }
}
