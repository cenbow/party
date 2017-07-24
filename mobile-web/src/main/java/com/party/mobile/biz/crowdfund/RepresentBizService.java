package com.party.mobile.biz.crowdfund;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.model.distributor.WithActivity;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.distributor.DistributorFunction;
import com.party.mobile.web.dto.crowdfund.output.GetRepresentOutput;
import com.party.mobile.web.dto.crowdfund.output.ListForTargetOutput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * 代言业务接口
 * Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 14:12
 */

@Service
public class RepresentBizService {

    @Autowired
    private DistributorBizService distributorBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IDistributorRelationService distributorRelationService;


    @Autowired
    private IMemberService memberService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IDistributorTargetAttacheService distributorTargetAttacheService;

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private IOrderFormService orderFormService;

    /**
     * 分销
     * @param input 输入视图
     * @param currentUser 当前用户
     * @return 分销者编号
     */
    @Transactional
    public void represent(GetDistributorInput input, CurrentUser currentUser){
        distributorBizService.distributor(input, currentUser, new DistributorFunction() {
            @Nullable
            @Override
            public void success(@Nullable String distributorId) {
               //修改代言人数
                Activity activity = activityService.get(input.getTargetId());
                if (null == activity){
                    throw new BusinessException("该活动不存在");
                }
                activity.setRepresentNum(activity.getRepresentNum() + 1);
                activityService.update(activity);

                //新增附属信息
                DistributorTargetAttache distributorTargetAttache = new DistributorTargetAttache();
                distributorTargetAttache.setStyle(input.getStyle());
                distributorTargetAttache.setContent(input.getContent());
                distributorTargetAttache.setDistributorRelationId(distributorId);
                distributorTargetAttacheService.insert(distributorTargetAttache);
            }
        });
    }

    /**
     * 获取代言信息
     * @param input
     * @param currentUser
     * @return
     */
    public GetRepresentOutput getRepresent(GetDistributorInput input, CurrentUser currentUser){
        DistributorRelation distributorRelation
                = distributorRelationService.get(input.getType(), input.getTargetId(),input.getDistributorId());
        if (null == distributorRelation){
            throw new BusinessException("分销关系不存在");
        }

        Activity activity = activityService.get(distributorRelation.getTargetId());
        if (null == activity){
            throw new BusinessException("活动不存在");
        }

        GetRepresentOutput getRepresentOutput = GetRepresentOutput.transform(activity);
        getRepresentOutput.setTargetAuthorId(activity.getMember());
        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        getRepresentOutput.setJoinNum(joinNum);
        getRepresentOutput.setTargetBeCrowdfundNum(activity.getBeCrowdfundNum());
        getRepresentOutput.setTargetCrowdfundedNum(activity.getCrowdfundedNum());

        //众筹人数
        Integer beCrowdfundNum = projectBizService.projectForRepresentList(false, distributorRelation.getId(), null).size();
        Integer crowdfundedNum = projectBizService.projectForRepresentList(true, distributorRelation.getId(), null).size();
        getRepresentOutput.setBeCrowdfundNum(beCrowdfundNum);
        getRepresentOutput.setCrowdfundedNum(crowdfundedNum);

        //创建者
        Member member = memberService.get(distributorRelation.getDistributorId());
        getRepresentOutput.setAuthorName(member.getRealname());
        getRepresentOutput.setAuthorLogo(member.getLogo());
        getRepresentOutput.setAuthorId(member.getId());
        getRepresentOutput.setStartTime(distributorRelation.getCreateDate());
        getRepresentOutput.setDistributorRelationId(distributorRelation.getId());

        //详情
        ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
        if (null != activityDetail){
            getRepresentOutput.setContent(StringUtils.stringtohtml(activityDetail.getContent()));
            getRepresentOutput.setApplyRelated(StringUtils.stringtohtml(activityDetail.getApplyRelated()));
            getRepresentOutput.setMatchStandard(StringUtils.stringtohtml(activityDetail.getMatchStandard()));
        }

        //代言详情
        DistributorTargetAttache distributorTargetAttache
                = distributorTargetAttacheService.findByRelationId(distributorRelation.getId());
        getRepresentOutput.setStyle(distributorTargetAttache.getStyle());
        getRepresentOutput.setDeclaration(distributorTargetAttache.getContent());

        boolean isMyRepresent = false;
        if (null != currentUser){
            //是否报名过
            MemberAct memberAct = memberActService.findByMemberAct(currentUser.getId(), activity.getId());
            if (memberAct != null){
                TargetProject targetProject = targetProjectService.findByOrderId(memberAct.getOrderId());
                Project project = projectService.get(targetProject.getProjectId());
                getRepresentOutput.setMyProjectId(project.getId());
            }

            if (currentUser.getId().equals(distributorRelation.getDistributorId())){
               isMyRepresent = true;
            }
        }
        getRepresentOutput.setMyRepresent(isMyRepresent);

        //如果没有绑定事件
        if (!Strings.isNullOrEmpty(activity.getEventId())){
            Activity count = activityService.countForEventId(activity.getEventId());
            getRepresentOutput.setJoinNum(count.getJoinNum());
            getRepresentOutput.setFavorerNum(count.getFavorerNum());
            getRepresentOutput.setTargetCrowdfundedNum(count.getCrowdfundedNum());
            getRepresentOutput.setTargetBeCrowdfundNum(count.getBeCrowdfundNum());
        }

        getRepresentOutput.setTemplateStyle(activity.getTemplateStyle());

        //是否已经截止
        Integer result = DateUtils.compareDate(new Date(), activity.getEndTime());
        if (result == 1){
            getRepresentOutput.setClosed(true);
        }
        return  getRepresentOutput;
    }


    /**
     * 我的代言列表
     * @param page 分页参数
     * @param status 状态
     * @param currentUser 当前用户
     * @return 代言列表
     */
    public List<WithActivity> list(Page page, Integer status, CurrentUser currentUser){
        List<WithActivity> withActivityList = distributorRelationService.activityList(currentUser.getId(), status, page);
        for (WithActivity withActivity : withActivityList){
            Member member = memberService.get(withActivity.getDistributorId());
            if (null != member){
                withActivity.setAuthorName(member.getRealname());
                withActivity.setAuthorLogo(member.getLogo());
                withActivity.setCrowdfundNum(withActivity.getBeCrowdfundNum() +withActivity.getCrowdfundedNum());
            }
        }
        return withActivityList;
    }

    /**
     * 项目下代言
     * @param page 分页参数
     * @param id 编号
     * @return 代言列表
     */
    public List<ListForTargetOutput> listForTarget(Page page, String id){

        DistributorRelation distributorRelation = new DistributorRelation();
        distributorRelation.setTargetId(id);
        distributorRelation.setType(GoodsType.GOODS_CROWD_FUND.getCode());
        List<DistributorRelation> distributorRelationList = distributorRelationService.listPage(distributorRelation, page);

        List<ListForTargetOutput> list = LangUtils.transform(distributorRelationList, new Function<DistributorRelation, ListForTargetOutput>() {
            @Nullable
            @Override
            public ListForTargetOutput apply(@Nullable DistributorRelation distributorRelation) {
                ListForTargetOutput listForTargetOutput = ListForTargetOutput.transform(distributorRelation);
                Member member = memberService.get(distributorRelation.getDistributorId());
                listForTargetOutput.setAuthorName(member.getRealname());
                listForTargetOutput.setAuthorLogo(member.getLogo());

                Activity activity = activityService.get(distributorRelation.getTargetId());
                listForTargetOutput.setBeCrowdfundNum(activity.getBeCrowdfundNum());
                listForTargetOutput.setCrowdfundedNum(activity.getCrowdfundedNum());
                listForTargetOutput.setRepresentNum(activity.getRepresentNum());

                DistributorTargetAttache distributorTargetAttache = distributorTargetAttacheService.findByRelationId(distributorRelation.getId());
                if (null != distributorRelation){
                    listForTargetOutput.setDeclaration(distributorTargetAttache.getContent());
                }
                return listForTargetOutput;
            }
        });
        return list;
    }


    /**
     * 修改代言
     * @param id 代言编号
     * @param declaration 代言宣言
     */
    public void edite(String id, String declaration, String style){
        //代言详情
        DistributorTargetAttache distributorTargetAttache
                = distributorTargetAttacheService.findByRelationId(id);
        if (null != distributorTargetAttache){
            distributorTargetAttache.setContent(declaration);
            distributorTargetAttache.setStyle(style);
            distributorTargetAttacheService.update(distributorTargetAttache);
        }
    }
}
