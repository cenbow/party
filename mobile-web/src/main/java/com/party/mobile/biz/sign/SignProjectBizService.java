package com.party.mobile.biz.sign;

import com.google.common.base.Strings;
import com.party.core.model.member.Member;
import com.party.core.model.sign.*;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import com.party.core.service.sign.ISignProjectService;
import com.party.core.service.sign.ISignRecordService;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.ApplyDetailOutput;
import com.party.mobile.web.dto.sign.output.MyRankOutput;
import com.party.mobile.web.dto.sign.output.SignProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 签到项目业务接口
 * Created by wei.li
 *
 * @date 2017/6/8 0008
 * @time 18:24
 */

@Service
public class SignProjectBizService {

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private ISignRecordService signRecordService;

    /**
     * 获取签到项目信息
     * @param id 项目编号
     * @param currentUser 当前用户
     * @return 签到项目
     */
    public SignProjectOutput get(String id, CurrentUser currentUser){

        SignProjectAuthor signProjectAuthor = signProjectService.getProjectAuthor(id);
        SignProjectOutput signProjectOutput = SignProjectOutput.transform(signProjectAuthor);
        if (Strings.isNullOrEmpty(signProjectAuthor.getPublisher())){
            signProjectOutput.setPublisher(signProjectAuthor.getAuthorName());
            signProjectOutput.setPublisherLogo(signProjectAuthor.getAuthorLogo());
        }

        if (null != currentUser){
            //是否报名
            GroupMember groupMember = signApplyService.get(currentUser.getId(), id);
            if (null == groupMember){
                signProjectOutput.setApply(false);
            }
            else {
                signProjectOutput.setApply(true);
                signProjectOutput.setApplyStatus(groupMember.getStatus());
                signProjectOutput.setApplyId(groupMember.getId());

                //今天是否签到
                SignRecord signRecord = signRecordService.getToday(groupMember.getId());
                if (null != signRecord){
                    signProjectOutput.setSign(true);
                }
            }

        }
        else {
            signProjectOutput.setApply(false);
        }

        return signProjectOutput;
    }


    /**
     * 报名详情
     * @param id 编号
     * @return 报名详情
     */
    public ApplyDetailOutput getApplyDetail(String id){
        SignProject signProject = signProjectService.get(id);
        ApplyDetailOutput applyDetailOutput = ApplyDetailOutput.transform(signProject);

        SignGroup signGroup = new SignGroup();
        signGroup.setProjectId(id);
        List<SignGroup> signGroupList = signGroupService.list(signGroup);
        applyDetailOutput.setSignGroupList(signGroupList);
        return applyDetailOutput;
    }


}
