package com.party.mobile.biz.crowdfund;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.AuditStatus;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.web.dto.crowdfund.output.TargetOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 众筹目标业务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 10:45
 */

@Service
public class TargetBizService {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberService memberService;

    /**
     * 查询自己的众筹目标信息
     * @param page 分页信息
     * @param status 状态(0:进行中, 1:已经截止)
     * @param page 分页信息
     * @param id 用户编号
     * @return
     */
    public List<TargetOutput> list(Page page, Integer status,String id){

        Activity activity = new Activity();
        activity.setMember(id);
        activity.setIsCrowdfunded(Constant.IS_CROWDFUNDED);
        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        activity.setCheckStatus(AuditStatus.PASS.getCode());//审核通过
        activity.setStatus(status);
        List<Activity> activityList = activityService.listPage(activity, page);
        List<TargetOutput> targetOutputList = LangUtils.transform(activityList, new Function<Activity, TargetOutput>() {
            @Nullable
            @Override
            public TargetOutput apply(@Nullable Activity activity) {
                TargetOutput targetOutput = TargetOutput.transform(activity);
                if (null == activity.getMember()){
                    throw new BusinessException("找不到项目发布者信息");
                }

                if (!Strings.isNullOrEmpty(activity.getPublisher()) && !Strings.isNullOrEmpty(activity.getPublisherLogo())){
                    targetOutput.setAuthorName(activity.getPublisher());
                    targetOutput.setAuthorLogo(activity.getPublisherLogo());
                }
                else {
                    Member member = memberService.get(activity.getMember());
                    targetOutput.setAuthorName(member.getRealname());
                    targetOutput.setAuthorLogo(member.getLogo());
                }
                targetOutput.setTemplateStyle(activity.getTemplateStyle());
                return targetOutput;
            }
        });
        return targetOutputList;
    }
}
