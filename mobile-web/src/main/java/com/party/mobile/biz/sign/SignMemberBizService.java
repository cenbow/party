package com.party.mobile.biz.sign;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.sign.*;
import com.party.core.service.sign.*;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.MyRankOutput;
import com.party.mobile.web.dto.sign.output.SignMemberOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 签到成员业务接口
 * Created by wei.li
 *
 * @date 2017/6/9 0009
 * @time 15:27
 */

@Service
public class SignMemberBizService {

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private ISignRecordService signRecordService;

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private ISignRankService signRankService;


    /**
     * 我的排行
     * @param projectId 项目编号
     * @param currentUser 当前用户
     * @return 我的排行信息
     */
    public MyRankOutput myProjectRank(String projectId, CurrentUser currentUser){
        MyRankOutput myRankOutput = null;
        if (null == currentUser){
            return null;
        }

        GroupMember groupMember = signApplyService.get(currentUser.getId(), projectId);

        if (null != groupMember){
            if (!SignApplyStatus.PASS_STATUS.getCode().equals(groupMember.getStatus())){
                return null;
            }
            if (SignGradeStatus.INVALID.getCode().equals(groupMember.getGradeStatus())){
                return null;
            }
            myRankOutput = MyRankOutput.transform(groupMember);
            // 我的排行
            Integer rank = signApplyService.projectRank(projectId, groupMember.getId());
            myRankOutput.setRank(rank);
        }
        return myRankOutput;
    }

    /**
     * 我的排行
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param currentUser 当前用户
     * @return 排名信息
     */
    public MyRankOutput projectRankRecord(String projectId, Integer type, CurrentUser currentUser){
        MyRankOutput myRankOutput = null;
        if (null == currentUser){
            return null;
        }

        GroupMember groupMember = signApplyService.get(currentUser.getId(), projectId);
        if (null != groupMember){
            if (!SignApplyStatus.PASS_STATUS.getCode().equals(groupMember.getStatus())){
                return null;
            }
            if (SignGradeStatus.INVALID.getCode().equals(groupMember.getGradeStatus())){
                return null;
            }
            myRankOutput = MyRankOutput.transform(groupMember);

            // 我的排行
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
            Long stepNum = signRecordService.stepNum(groupMember.getId(), startTime, endTime);
            Integer rank = signRankService.projectRankRecord(projectId, groupMember.getId(), startTime, endTime);

            //没有签到
            if (0 == stepNum){
                if (null != type){
                    return null;
                }
            }


            myRankOutput.setStepNum(stepNum);
            myRankOutput.setRank(rank);
        }
        return myRankOutput;
    }

    /**
     * 我的排行
     * @param groupId 小组编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param currentUser 用户
     * @return 排行信息
     */
    public MyRankOutput groupRankRecord(String groupId, Integer type, CurrentUser currentUser){
        MyRankOutput myRankOutput = null;
        if (null == currentUser){
            return null;
        }

        GroupMember groupMember = signApplyService.getByAuthorAndGroup(currentUser.getId(), groupId);
        if (null != groupMember){
            if (!SignApplyStatus.PASS_STATUS.getCode().equals(groupMember.getStatus())){
                return null;
            }
            myRankOutput = MyRankOutput.transform(groupMember);
            // 我的排行
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
            Long stepNum = signRecordService.stepNum(groupMember.getId(), startTime, endTime);
            Integer rank = signRankService.groupRankRecord(groupId, groupMember.getId(), startTime, endTime);
            //没有签到
            if (0 == stepNum){
                if (null != type){
                    return null;
                }
            }

            myRankOutput.setStepNum(stepNum);
            myRankOutput.setRank(rank);
        }
        return myRankOutput;
    }


    /**
     * 小组排行
     * @param groupId 小组编号
     * @param currentUser 当前用户
     * @return 排行
     */
    public MyRankOutput myGroupRank(String groupId, CurrentUser currentUser){
        MyRankOutput myRankOutput = null;
        if (null == currentUser){
            return null;
        }

        GroupMember groupMember = signApplyService.getByAuthorAndGroup(currentUser.getId(), groupId);
        if (null != groupMember){
            if (!SignApplyStatus.PASS_STATUS.getCode().equals(groupMember.getStatus())){
                return null;
            }
            myRankOutput = MyRankOutput.transform(groupMember);
            // 我的排行
            Integer rank = signApplyService.groupRank(groupId, groupMember.getId());
            myRankOutput.setRank(rank);
        }
        return myRankOutput;
    }


    /**
     * 打卡报名详情页面
     * @param id 编号
     * @param currentUser 当前用户
     * @return  详情
     */
    public SignMemberOutput detail(String id, CurrentUser currentUser){
        GroupMember groupMember = signApplyService.getGroupMember(id);
        if (null == groupMember){
            throw new BusinessException("签到报名信息不存在");
        }
        SignMemberOutput signMemberOutput = SignMemberOutput.transform(groupMember);

        //签到项目
        SignProject signProject = signProjectService.get(signMemberOutput.getProjectId());
        signMemberOutput.setPic(signProject.getSignLogo());
        signMemberOutput.setRankShow(signProject.getRankShow());
        signMemberOutput.setStartTime(DateUtils.formatDate(signProject.getStartTime(), "yyyy-MM-dd"));
        signMemberOutput.setEndTime(DateUtils.formatDate(signProject.getEndTime(), "yyyy-MM-dd"));

        //今日步数
        Long todayStep = signRecordService.todayStep(groupMember.getId());

        //月累计步数
        Long mothStep = signRecordService.monthStep(groupMember.getId());

        //打卡次数
        Integer countSign = signRecordService.getCount(groupMember.getId());

        //今天是否打卡
        SignRecord signRecord = signRecordService.getToday(groupMember.getId());
        if (null != signRecord){
            signMemberOutput.setSign(true);
        }

        if (null != currentUser){
            SignApply signApply = signApplyService.get(currentUser.getId(), groupMember.getProjectId());
            if (null != signApply){
                signMemberOutput.setStatus(signApply.getStatus());
            }
            else {
                signMemberOutput.setStatus(null);
            }
        }
        else {
            signMemberOutput.setStatus(null);
        }

        //是否在有效时间内
        Date now = new Date();
        if (DateUtils.compareDate(now, signProject.getStartTime()) == 1
                && DateUtils.compareDate(signProject.getEndTime(), now) == 1){
            signMemberOutput.setValidTime(true);
        }
        signMemberOutput.setTodayStep(todayStep);
        signMemberOutput.setMonthStep(mothStep);
        signMemberOutput.setCountSign(countSign);
        signMemberOutput.setRemarks(signProject.getRemarks());
        return signMemberOutput;
    }

    /**
     * 排行榜
     * @param projectId 项目编号
     * @param type （默认：全部， 0：今天，1：本周，2：本月，3：一月前）
     * @param page 分页参数
     * @return 排行列表
     */
    public List<GroupMember> list(String projectId, Integer type, Page page){
        GroupMember groupMember = new GroupMember();
        groupMember.setProjectId(projectId);
        groupMember.setStatus(SignApplyStatus.PASS_STATUS.getCode());
        groupMember.setGradeStatus(SignGradeStatus.EFFECTIVE.getCode());
        List<GroupMember> list = signApplyService.groupMemberList(groupMember, page);
        return list;
    }

    /**
     * 个人成绩排行
     * @param projectId 项目编号
     * @param type 类型
     * @param page 分页参数
     * @return 排名列表
     */
    public List<GroupMember> rankList(String projectId, Integer type, Page page){

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
        List<GroupMember> list = signApplyService.rankList(projectId, startTime, endTime, page);
        return list;
    }

    /**
     * 签到报名
     * @param projectId 项目编号
     * @param groupId 分组编号
     * @param currentUser 当前用户
     */
    @Transactional
    public String apply(String projectId, String groupId, CurrentUser currentUser){

        //是否已经报名
        GroupMember groupMember = signApplyService.get(currentUser.getId(), projectId);
        if (null != groupMember){
            return groupMember.getId();
        }

        //报名
        SignApply signApply = new SignApply();
        signApply.setCreateBy(currentUser.getId());
        signApply.setUpdateBy(currentUser.getId());
        signApply.setProjectId(projectId);
        signApply.setGroupId(groupId);
        signApply.setMemberId(currentUser.getId());

        //是否需要审核
        SignProject signProject = signProjectService.get(projectId);
        signProject.setApplyNum(signProject.getApplyNum() + 1);
        if (signProject.getApplyCheck().equals(YesNoStatus.YES.getCode())){
            signApply.setStatus(SignApplyStatus.NOT_AUDIT_STATUS.getCode());
            signProject.setNotAuditNum(signProject.getNotAuditNum() + 1);

        }
        else if (signProject.getApplyCheck().equals(YesNoStatus.NO.getCode())){
            signApply.setStatus(SignApplyStatus.PASS_STATUS.getCode());
            signProject.setPassNum(signProject.getPassNum() + 1);
        }

        Integer gradeStatus = YesNoStatus.YES.getCode().equals(signProject.getGradeCheck()) ?
                SignGradeStatus.INVALID.getCode() : SignGradeStatus.EFFECTIVE.getCode();
        signApply.setGradeStatus(gradeStatus);

        signApplyService.insert(signApply);
        signProjectService.update(signProject);

        //是否包含小组
        if (!Strings.isNullOrEmpty(groupId)){
            SignGroup signGroup = signGroupService.get(groupId);
            signGroup.setApplyNum(signGroup.getApplyNum() + 1);
            if (signProject.getApplyCheck().equals(YesNoStatus.YES.getCode())){
                signGroup.setNotAuditNum(signGroup.getNotAuditNum() +1);
            }
            else if (signProject.getApplyCheck().equals(YesNoStatus.NO.getCode())){
                signGroup.setPassNum(signGroup.getPassNum() + 1);
                if (YesNoStatus.NO.getCode().equals(signProject.getGradeCheck())){
                    signGroup.setMemberNum(signGroup.getMemberNum() + 1);
                }
            }
            signGroupService.update(signGroup);
        }
        return signApply.getId();
    }
}
