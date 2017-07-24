package com.party.mobile.biz.sign;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.party.common.utils.DateUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.sign.*;
import com.party.core.service.sign.ISignApplyService;
import com.party.core.service.sign.ISignGroupService;
import com.party.core.service.sign.ISignProjectService;
import com.party.core.service.sign.ISignRecordService;
import com.party.mobile.web.dto.sign.output.SignRecodListOutput;
import org.h2.mvstore.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 签到记录业务接口
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 10:45
 */

@Service
public class SignRecordBizService {

    @Autowired
    private ISignProjectService signProjectService;

    @Autowired
    private ISignApplyService signApplyService;

    @Autowired
    private ISignRecordService signRecordService;

    @Autowired
    private ISignGroupService signGroupService;

    /**
     * 签到集合列表
     * @param applyId 报名编号
     * @return 签到集合
     */
    public List<SignRecodListOutput> list(String applyId){
        SignApply signApply = signApplyService.get(applyId);
        if (null == signApply){
            throw new BusinessException("报名信息不存在");
        }
        SignProject signProject = signProjectService.get(signApply.getProjectId());
        Date now = new Date();

        List<Date> dateList;
        //如果当前小于开始时间
        if (DateUtils.compareDate(now, signProject.getStartTime()) == -1){
            return Collections.EMPTY_LIST;
        }
        //如果当前大于结束时间
        dateList = DateUtils.getDateDifference(signProject.getStartTime(), signProject.getEndTime());

        SignRecord signRecord = new SignRecord();
        signRecord.setApplyId(applyId);
        List<SignRecord> signRecordList = signRecordService.list(signRecord);
        List<SignRecodListOutput> listOutputList = isSign(dateList, signRecordList);
        return listOutputList;
    }

    /**
     * 是否签到列表
     * @param dateList 时间列表
     * @param signRecordList 签到列表
     * @return 是否签到的列表
     */
    public List<SignRecodListOutput> isSign(List<Date> dateList, List<SignRecord> signRecordList){
        List<SignRecodListOutput> listOutputList = Lists.newArrayList();
        Date today = new Date();
        for (Date date : dateList){
            SignRecodListOutput signRecodListOutput = new SignRecodListOutput();
            signRecodListOutput.setDate(date);
            for (SignRecord signRecord : signRecordList){
                if (DateUtils.formatDate(date, "yyyy-MM-dd").equals(DateUtils.formatDate(signRecord.getCreateDate(), "yyyy-MM-dd"))){
                    signRecodListOutput.setId(signRecord.getId());
                    signRecodListOutput.setSign(true);
                    break;
                }
            }

            if (null == signRecodListOutput.getSign()){
                if (DateUtils.getDay(date) < DateUtils.getDay(today)){
                    signRecodListOutput.setSign(false);
                }
            }
            listOutputList.add(signRecodListOutput);
        }
        return listOutputList;
    }


    /**
     * 签到
     * @param signRecord 签到记录
     */
    @Transactional
    public void sign(SignRecord signRecord){

        //报名叠加
        SignApply signApply = signApplyService.get(signRecord.getApplyId());

        signApply.setStepNum(signApply.getStepNum() + signRecord.getStepNum());
        signApplyService.update(signApply);

        //小组叠加
        if (!Strings.isNullOrEmpty(signApply.getGroupId())
                && SignGradeStatus.EFFECTIVE.getCode().equals(signApply.getGradeStatus())){
            SignGroup signGroup = signGroupService.get(signApply.getGroupId());
            signGroup.setStepNum(signGroup.getStepNum() + signRecord.getStepNum());
            signGroupService.update(signGroup);
        }

        //保存记录
        signRecordService.insert(signRecord);
    }
}
