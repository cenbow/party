package com.party.admin.biz.distribution;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.party.admin.web.dto.output.distribution.ApplyListOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分销活动报名业务服务
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 17:51
 */

@Service
public class DistributionApplyBizService {

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberService memberService;


    /**
     * 分销报名列表
     * @param distributionId 分销编号
     * @param applyWithActivity 报名信息
     * @param page 分页参数
     * @return 报名列表
     */
    public List<ApplyListOutput> list(String distributionId, ApplyWithActivity applyWithActivity, Page page){
        List<DistributorDetail> distributorDetailList = distributorDetailService.findByRelationId(distributionId);
        List<String> applyIdList = LangUtils.transform(distributorDetailList, new Function<DistributorDetail, String>() {
            @Override
            public String apply(DistributorDetail distributorDetail) {
                return distributorDetail.getTargetId();
            }
        });

        List<ApplyWithActivity> applyWithActivityList = memberActService.batchWithActivityList(Sets.newHashSet(applyIdList), applyWithActivity, page);
        List<ApplyListOutput> listOutputs = LangUtils.transform(applyWithActivityList, new Function<ApplyWithActivity, ApplyListOutput>() {
            @Override
            public ApplyListOutput apply(ApplyWithActivity applyWithActivity) {
                ApplyListOutput applyListOutput = ApplyListOutput.transform(applyWithActivity);
                applyListOutput.setActivityName(applyWithActivity.getActivityTitle());
                Member member = memberService.get(applyWithActivity.getMemberId());
                if (null != member){
                    applyListOutput.setApplicant(member.getRealname());
                }
                return applyListOutput;
            }
        });
        return listOutputs;
    }
}
