package com.party.admin.biz.sign;

import com.google.common.base.Strings;
import com.party.core.model.sign.*;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import com.party.core.service.sign.ISignProjectService;
import com.party.core.service.sign.ISignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 签到报名业务接口
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 18:19
 */

@Service
public class SignApplyBizService {

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private ISignGroupService signGroupService;

    @Autowired
    private ISignRecordService signRecordService;


    /**
     * 审核
     * @param status 状态
     * @param id 报名编号
     */
    @Transactional
    public void check(Integer status, String id){
        SignApply signApply = signApplyService.get(id);
        signApply.setStatus(status);
        signApplyService.update(signApply);

        //项目数据调整
        SignProject signProject = signProjectService.get(signApply.getProjectId());
        signProject.setNotAuditNum(signProject.getNotAuditNum() - 1);

        if (SignApplyStatus.PASS_STATUS.getCode().equals(status)){
            signProject.setPassNum(signProject.getPassNum() + 1);
        }
        else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(status)){
            signProject.setRefuseNum(signProject.getRefuseNum() + 1);
        }
        signProjectService.update(signProject);

        //小组数据调整
        if (!Strings.isNullOrEmpty(signApply.getGroupId())){
            SignGroup signGroup = signGroupService.get(signApply.getGroupId());
            signGroup.setNotAuditNum(signGroup.getNotAuditNum() - 1);
            if (SignApplyStatus.PASS_STATUS.getCode().equals(status)){
                signGroup.setPassNum(signGroup.getPassNum() + 1);
            }
            else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(status)){
                signGroup.setRefuseNum(signGroup.getRefuseNum() + 1);
            }
            signGroupService.update(signGroup);
        }
    }


    /**
     * 审核成绩状态
     * @param status 状态
     * @param id 报名编号
     */
    @Transactional
    public void checkGrade(Integer status, String id){
        SignApply signApply = signApplyService.get(id);
        if (!Strings.isNullOrEmpty(signApply.getGroupId())){
            SignGroup signGroup = signGroupService.get(signApply.getGroupId());
            if (SignGradeStatus.EFFECTIVE.getCode().equals(status)){
                signGroup.setStepNum(signGroup.getStepNum() + signApply.getStepNum());
                signGroup.setMemberNum(signGroup.getMemberNum() + 1);
            }
            else if (SignGradeStatus.INVALID.getCode().equals(status)){
                signGroup.setStepNum(signGroup.getStepNum() - signApply.getStepNum());
                signGroup.setMemberNum(signGroup.getMemberNum() - 1);
            }
            signGroupService.update(signGroup);
        }
        signApply.setGradeStatus(status);
        signApplyService.update(signApply);
    }

    /**
     * 删除报名信息
     * @param id 报名编号
     */
    @Transactional
    public void delete(String id){
        SignApply signApply = signApplyService.get(id);
        SignProject signProject = signProjectService.get(signApply.getProjectId());
        if (SignApplyStatus.PASS_STATUS.getCode().equals(signApply.getStatus())){
            signProject.setPassNum(signProject.getPassNum() - 1);
        }
        else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(signApply.getStatus())){
            signProject.setRefuseNum(signProject.getRefuseNum() -1);
        }
        signProjectService.update(signProject);

        if (!Strings.isNullOrEmpty(signApply.getGroupId())){
            SignGroup signGroup = signGroupService.get(signApply.getGroupId());
            if (SignApplyStatus.PASS_STATUS.getCode().equals(signApply.getStatus())){
                signGroup.setPassNum(signGroup.getPassNum() -1);
                if (SignGradeStatus.EFFECTIVE.getCode().equals(signApply.getGradeStatus())){
                    signGroup.setMemberNum(signGroup.getMemberNum() - 1);
                }
            }
            else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(signApply.getStatus())){
                signGroup.setRefuseNum(signGroup.getRefuseNum() -1);
            }

            // 步数计算
            if (SignGradeStatus.EFFECTIVE.getCode().equals(signApply.getGradeStatus())){
                signGroup.setStepNum(signGroup.getStepNum() - signApply.getStepNum());
            }
            signGroupService.update(signGroup);
        }

        //删除打卡记录
        signRecordService.deleteByApplyId(id);
        signApplyService.delete(id);
    }

    /**
     * 移动小组
     * @param id 编号
     * @param groupId 小组号
     */
    @Transactional
    public void changeGroup(String id, String groupId){
        //改变小组数据
        SignApply dbApply = signApplyService.get(id);
        if (!Strings.isNullOrEmpty(dbApply.getGroupId())){
            SignGroup signGroup = signGroupService.get(dbApply.getGroupId());
            signGroup.setApplyNum(signGroup.getApplyNum() - 1);
            if (SignGradeStatus.EFFECTIVE.getCode().equals(dbApply.getGradeStatus())){
                signGroup.setStepNum(signGroup.getStepNum() - dbApply.getStepNum());
            }

            if (SignApplyStatus.PASS_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setPassNum(signGroup.getPassNum() - 1);
                if (SignGradeStatus.EFFECTIVE.getCode().equals(dbApply.getGradeStatus())){
                    signGroup.setMemberNum(signGroup.getMemberNum() - 1);
                }
            }
            else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setRefuseNum(signGroup.getRefuseNum() - 1);
            }
            else if (SignApplyStatus.NOT_AUDIT_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setNotAuditNum(signGroup.getNotAuditNum() - 1);
            }
            signGroupService.update(signGroup);
        }

        if (!Strings.isNullOrEmpty(groupId)){
            SignGroup signGroup = signGroupService.get(groupId);
            signGroup.setApplyNum(signGroup.getApplyNum() + 1);
            if (SignGradeStatus.EFFECTIVE.getCode().equals(dbApply.getGradeStatus())){
                signGroup.setStepNum(signGroup.getStepNum() + dbApply.getStepNum());
            }
            if (SignApplyStatus.PASS_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setPassNum(signGroup.getPassNum() + 1);
                if (SignGradeStatus.EFFECTIVE.getCode().equals(dbApply.getGradeStatus())){
                    signGroup.setMemberNum(signGroup.getMemberNum() + 1);
                }
            }
            else if (SignApplyStatus.REFUSE_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setRefuseNum(signGroup.getRefuseNum() + 1);
            }
            else if (SignApplyStatus.NOT_AUDIT_STATUS.getCode().equals(dbApply.getStatus())){
                signGroup.setNotAuditNum(signGroup.getNotAuditNum() + 1);
            }
            signGroupService.update(signGroup);
        }

        dbApply.setGroupId(groupId);
        signApplyService.update(dbApply);
    }
}
