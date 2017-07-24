package com.party.mobile.biz.sign;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.sign.*;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import com.party.core.service.sign.ISignProjectService;
import com.party.core.service.sign.ISignRecordService;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.SignGroupOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 签到小组业务接口
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 10:05
 */

@Service
public class SignGroupBizService {

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignRecordService signRecordService;

    /**
     * 小组排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 排行列表
     */
    public List<GroupAuthor> list(String projectId,Integer type, Page page){
        GroupAuthor groupAuthor = new GroupAuthor();
        groupAuthor.setProjectId(projectId);
        List<GroupAuthor> list = signGroupService.groupAuthorList(groupAuthor, page);
        return list;
    }


    /**
     * 小组排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 排行列表
     */
    public List<GroupAuthor> rankList(String projectId,Integer type, Page page){
        //按时间段查
        String startTime = null;
        String endTime = null;
        if (null != type){

            String today = DateUtils.getTodayStr();

            //今天
            if (0 == type){
                startTime = today;
                endTime = today;
            }
            //本周
            else if (1 == type){
                startTime = DateUtils.formatDateTime(DateUtils.getFirstDayOfWeek(0));
                endTime = today;
            }
            //本月
            else if (2 == type){
                startTime = DateUtils.formatDateTime(DateUtils.getFirstDayOfMonth(0));
                endTime = today;
            }
            //历史
            else if (3 == type){
                endTime = DateUtils.formatDateTime(DateUtils.getLastDayOfMonth(-1));
            }
        }
        List<GroupAuthor> list = signGroupService.rankList(projectId,startTime, endTime, page);
        return list;
    }

    /**
     * 获取小组详情
     * @param id 编号
     * @param currentUser 当前用户
     * @return 输出视图
     */
    public SignGroupOutput get(String id, CurrentUser currentUser){
        GroupAuthor groupAuthor = signGroupService.getGroupAuthor(id);
        if (null == groupAuthor){
            return null;
        }
        SignProject signProject = signProjectService.get(groupAuthor.getProjectId());
        SignGroupOutput signGroupOutput = SignGroupOutput.transform(groupAuthor);
        if (null != currentUser){
            SignApply signApply = signApplyService.get(currentUser.getId(), groupAuthor.getProjectId());
            if (null != signApply){
                signGroupOutput.setStatus(signApply.getStatus());
                signGroupOutput.setApplyId(signApply.getId());

                //是否加入小组
                if (!Strings.isNullOrEmpty(signApply.getGroupId())){
                    signGroupOutput.setGroup(true);
                }

                //今天是否签到
                SignRecord signRecord = signRecordService.getToday(signApply.getId());
                if (null != signRecord){
                    signGroupOutput.setSign(true);
                }
            }
        }
        signGroupOutput.setRemarks(signProject.getRemarks());
        signGroupOutput.setPic(signProject.getSignLogo());
        return signGroupOutput;
    }


    /**
     * 加入小组
     * @param groupId 小组编号
     * @param currentUser 当前用户
     */
    @Transactional
    public void join(String groupId, CurrentUser currentUser){
        SignGroup signGroup = signGroupService.get(groupId);
        SignApply apply = signApplyService.get(currentUser.getId(), signGroup.getProjectId());
        if (null == apply){
            throw new BusinessException(PartyCode.SIGN_APPLY_NOT, "您在所属项目下未报名");
        }
        if (!Strings.isNullOrEmpty(apply.getGroupId())){
            throw new BusinessException(PartyCode.SIGIN_GROUP_HAVE, "您已经加入小组");
        }
        if (SignApplyStatus.PASS_STATUS.getCode().equals(apply.getStatus())){
            signGroup.setPassNum(signGroup.getPassNum() + 1);
            if (SignGradeStatus.EFFECTIVE.getCode().equals(apply.getGradeStatus())){
                signGroup.setMemberNum(signGroup.getMemberNum() + 1);
            }
        }
        else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(apply.getStatus())){
            signGroup.setRefuseNum(signGroup.getRefuseNum() + 1);
        }
        else if (SignApplyStatus.NOT_AUDIT_STATUS.getCode().equals(apply.getStatus())){
            signGroup.setNotAuditNum(signGroup.getNotAuditNum() + 2);
        }
        apply.setGroupId(groupId);
        signGroup.setApplyNum(signGroup.getApplyNum() + 1);
        if (SignGradeStatus.EFFECTIVE.getCode().equals(apply.getGradeStatus())){
            signGroup.setStepNum(signGroup.getStepNum() + apply.getStepNum());
        }

        signGroupService.update(signGroup);
        signApplyService.update(apply);
    }
}
