package com.party.web.biz.distribution;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.WithCount;
import com.party.core.model.member.Member;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.member.IMemberService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.input.distribution.ChildListInput;
import com.party.web.web.dto.input.distribution.DistributionListInput;
import com.party.web.web.dto.output.distribution.ChildListOutput;
import com.party.web.web.dto.output.distribution.DistributionListOutput;
import org.h2.mvstore.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分销业务接口
 * Created by wei.li
 *
 * @date 2017/3/13 0013
 * @time 10:56
 */

@Service
public class DistributionBizService {

    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IMemberService memberService;


    /**
     * 我分销的列表
     * @param distributionListInput 分销关系
     * @param page 分页参数
     * @return 分销列表
     */
    public List<DistributionListOutput> list(DistributionListInput distributionListInput, Page page){
        Member member = RealmUtils.getCurrentUser();


        List<WithCount> withCountList = distributorRelationService.listWithCount(distributionListInput.getTitle(),
                distributionListInput.getType(), member.getId(), null, null,  page);

        List<DistributionListOutput> listOutputs = LangUtils.transform(withCountList, new Function<WithCount, DistributionListOutput>() {
            @Override
            public DistributionListOutput apply(WithCount withCount) {
                DistributionListOutput distributionListOutput = DistributionListOutput.transform(withCount);
                Member member = memberService.get(withCount.getParentId());
                distributionListOutput.setParentName(member.getRealname());
                return distributionListOutput;
            }
        });
        return listOutputs;
    }

    /**
     * 根据目标查询分销关系
     * @param targetId 目标编号
     * @param distributionListInput 输入视图
     * @param page 分页参数
     * @return 分销列表
     */
    public List<DistributionListOutput> targetList(String targetId, DistributionListInput distributionListInput, Page page){

        String startTime = null;
        String endTime = null;
        if (null != distributionListInput.getTimeType() && !distributionListInput.getTimeType().equals(0)){
            //今天
            if (distributionListInput.getTimeType().equals(1)){
                String now = DateUtils.getTodayStr();
                startTime = now;
                endTime = now;
            }
            //本周
            else if (distributionListInput.getTimeType().equals(2)){
                String now = DateUtils.getTodayStr();
                startTime = DateUtils.formatDate(DateUtils.addDay(now, -7));
                endTime = now;
            }
            //本月
            else if (distributionListInput.getTimeType().equals(3)){
                String now = DateUtils.getTodayStr();
                startTime = DateUtils.formatDate(DateUtils.addMonth(DateUtils.getTodayDate(), -1));
                endTime = now;
            }
        }

        startTime = Strings.isNullOrEmpty(distributionListInput.getStartDate()) ? startTime : distributionListInput.getStartDate();
        endTime = Strings.isNullOrEmpty(distributionListInput.getEndDate()) ? endTime : distributionListInput.getEndDate();

        List<WithCount> withCountList
                = distributorRelationService.listWithCount(targetId, distributionListInput.getTitle(),
                distributionListInput.getType(), startTime, endTime,  page);

        List<DistributionListOutput> listOutputs = LangUtils.transform(withCountList, new Function<WithCount, DistributionListOutput>() {
            @Override
            public DistributionListOutput apply(WithCount withCount) {
                DistributionListOutput distributionListOutput = DistributionListOutput.transform(withCount);
                Member member = memberService.get(withCount.getParentId());
                distributionListOutput.setParentName(member.getRealname());

                Member distributor = memberService.get(withCount.getDistributorId());
                distributionListOutput.setDistributorName(distributor.getRealname());
                distributionListOutput.setDistributorLogo(distributor.getLogo());
                return distributionListOutput;
            }
        });
        return listOutputs;
    }


    /**
     * 子级分销列表
     * @param distributionId 分销编号
     * @param input 输入视图
     * @param page 分页参数
     * @return 子级分销列表
     */
    public List<ChildListOutput> childList(String distributionId, ChildListInput input, Page page){

        String startTime = null;
        String endTime = null;
        if (null != input.getTimeType() && !input.getTimeType().equals(0)){
            //今天
            if (input.getTimeType().equals(1)){
                String now = DateUtils.getTodayStr();
                startTime = now;
                endTime = now;
            }
            //本周
            else if (input.getTimeType().equals(2)){
                String now = DateUtils.getTodayStr();
                startTime = DateUtils.formatDate(DateUtils.addDay(now, -7));
                endTime = now;
            }
            //本月
            else if (input.getTimeType().equals(3)){
                String now = DateUtils.getTodayStr();
                startTime = DateUtils.formatDate(DateUtils.addMonth(DateUtils.getTodayDate(), -1));
                endTime = now;
            }
        }

        startTime = Strings.isNullOrEmpty(input.getStartDate()) ? startTime : input.getStartDate();
        endTime = Strings.isNullOrEmpty(input.getEndDate()) ? endTime : input.getEndDate();

        //查询本级分销信息
        DistributorRelation distributorRelation = distributorRelationService.get(distributionId);

        //下级分销商
        List<WithCount> list = distributorRelationService.findByParentId(distributorRelation.getDistributorId(),
                distributorRelation.getTargetId(), input.getTitle(), input.getDistributorName(), input.getType(), startTime, endTime, page);
        List<ChildListOutput> childList = LangUtils.transform(list, new Function<WithCount, ChildListOutput>() {
            @Override
            public ChildListOutput apply(WithCount withCount) {
                ChildListOutput childListOutput = ChildListOutput.transform(withCount);
                Member member = memberService.get(withCount.getDistributorId());
                childListOutput.setDistributorName(member.getRealname());
                childListOutput.setDistributorLogo(member.getLogo());
                return childListOutput;
            }
        });
        return childList;
    }
}
