package com.party.web.biz.distribution;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.Member;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.web.web.dto.output.activity.MemberActOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分销报名业务接口
 * Created by wei.li
 *
 * @date 2017/3/14 0014
 * @time 10:38
 */

@Service
public class DistributionApplyBizService {

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IMemberActService memberActService;


    @Autowired
    private IMemberService memberService;

    /**
     * 报名列表
     * @param distributionId 分销编号
     * @param page 分页参数
     * @return 报名列表
     */
    public List<MemberActOutput> list(String distributionId, Page page){
        List<DistributorDetail> distributorDetailList = distributorDetailService.findByRelationId(distributionId);
        List<String> applyIdList = LangUtils.transform(distributorDetailList, new Function<DistributorDetail, String>() {
            @Override
            public String apply(DistributorDetail distributorDetail) {
                return distributorDetail.getTargetId();
            }
        });

        List<ApplyWithActivity> applyWithActivityList = memberActService.batchWithActivityList(Sets.newHashSet(applyIdList), new ApplyWithActivity(), page);
        List<MemberActOutput> list = LangUtils.transform(applyWithActivityList, new Function<ApplyWithActivity, MemberActOutput>() {
            @Override
            public MemberActOutput apply(ApplyWithActivity applyWithActivity) {
                MemberActOutput memberActOutput = MemberActOutput.transform(applyWithActivity);
                Member member = memberService.get(applyWithActivity.getMemberId());
                memberActOutput.setMember(member);
                return memberActOutput;
            }
        });
        return list;
    }
}
