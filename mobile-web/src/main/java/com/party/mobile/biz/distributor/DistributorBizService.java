package com.party.mobile.biz.distributor;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.activity.Activity;
import com.party.core.model.article.Article;
import com.party.core.model.count.DataCount;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.distributor.*;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.article.IArticleService;
import com.party.core.service.count.IDataCountService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.web.dto.distributor.input.ApplyDistributorInput;
import com.party.mobile.web.dto.distributor.input.DistributorInput;
import com.party.mobile.web.dto.distributor.input.DistributorListInput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.distributor.output.DistributorFillOutput;
import com.party.mobile.web.dto.distributor.output.DistributorListOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 分销业务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 17:26
 */

@Service
public class DistributorBizService {

    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IDistributorCountService distributorCountService;

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IDistributorTargetAttacheService distributorTargetAttacheService;

    @Autowired
    private IDataCountService dataCountService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IProjectService projectService;
    /**
     * 分销
     * @param input 输入视图
     * @param currentUser 当前用户
     * @return 分销者编号
     */
    @Transactional
    public void distributor(GetDistributorInput input, CurrentUser currentUser, DistributorFunction distributorFunction){

        DistributorRelation distributorRelation
                = distributorRelationService.get(input.getType(), input.getTargetId(), currentUser.getId());
        if (distributorRelation != null){
            throw new BusinessException(PartyCode.DISTRIBUTOR_EXSIT, "已经分销");
        }

        distributorRelation = GetDistributorInput.transform(input);
        if (GoodsType.GOODS_ACTIVITY.getCode().equals(input.getType()) || GoodsType.GOODS_CROWD_FUND.getCode().equals(input.getType())){
            //生成分销
            Activity activity = activityService.get(input.getTargetId());
            distributorRelation.setTitle(activity.getTitle());
            distributorRelation.setPic(activity.getPic());
            distributorRelation.setPrice(activity.getPrice());
            distributorRelation.setTargetEndDate(activity.getEndTime());
        }
        else if (GoodsType.GOODS_NOMAL.getCode().equals(input.getType()) || GoodsType.GOODS_CUSTOMIZED.getCode().equals(input.getType())){
            Goods goods = goodsService.get(input.getTargetId());
            distributorRelation.setTitle(goods.getTitle());
            distributorRelation.setPic(goods.getPicsURL());
            distributorRelation.setPrice(goods.getPrice());
            distributorRelation.setTargetEndDate(goods.getEndTime());
        }
        else if (GoodsType.GOODS_ARTICLE.getCode().equals(input.getType())){
            Article article = articleService.get(input.getTargetId());
            distributorRelation.setTitle(article.getTitle());
            distributorRelation.setPic(article.getPic());
            distributorRelation.setPrice(0f);
            distributorRelation.setTargetEndDate(DateUtils.addYear(DateUtils.getTodayDate(), 1));
        }
        distributorRelation.setDistributorId(currentUser.getId());
        distributorRelationService.insert(distributorRelation);
        this.distributorCount(distributorRelation);
        this.targetCouint(input.getTargetId());

        if (null != distributorFunction){
            distributorFunction.success(distributorRelation.getId());
        }
    }

    /**
     * 分销
     * @param input 输入视图
     * @param currentUser 当前用户
     */
    public void distributor(DistributorInput input, CurrentUser currentUser){
        this.distributor(DistributorInput.transform(input), currentUser, new DistributorFunction() {
            @Nullable
            @Override
            public void success(@Nullable String distributorId) {
                //新增附属信息
                DistributorTargetAttache distributorTargetAttache = new DistributorTargetAttache();
                distributorTargetAttache.setStyle(input.getStyle());
                distributorTargetAttache.setContent(input.getDeclaration());
                distributorTargetAttache.setDistributorRelationId(distributorId);
                distributorTargetAttacheService.insert(distributorTargetAttache);
            }
        });
    }

    /**
     * 分销统计
     * @param distributorRelation 分销关系
     */
    public void distributorCount(DistributorRelation distributorRelation){

        //分销统计
        DistributorCount distributorCount = new DistributorCount(distributorRelation.getId());
        distributorCountService.insert(distributorCount);

        //父分销关系
        DistributorRelation parent
                = distributorRelationService.get(distributorRelation.getType(), distributorRelation.getTargetId(), distributorRelation.getParentId());
        //是不是存在
        if (null != parent){
            DistributorCount parentCount = distributorCountService.findByRelationId(parent.getId());
            parentCount.setShareNum(parentCount.getShareNum() + 1);
            distributorCountService.update(parentCount);
        }

    }

    /**
     * 统计目标的分销量
     * @param targetId 目标编号
     */
    public void targetCouint(String targetId){
        DataCount dataCount = dataCountService.findByTargetId(targetId);
        dataCount.setShareNum(dataCount.getShareNum() + 1);
        dataCountService.update(dataCount);
    }


    /**
     * 分销报名统计
     * @param input 输入视图
     */
    @Transactional
    public void setDistributorData(ApplyDistributorInput input){
        DistributorRelation distributorRelation
                = distributorRelationService.get(input.getDistributorType(),
                input.getDistributorTargetId(),input.getDistributorId());

        if (null == distributorRelation){
            throw new BusinessException("分销关系不存在");
        }

        //保存分销详情
        DistributorDetail distributorDetail = ApplyDistributorInput.transform(input);
        distributorDetail.setDistributorRelationId(distributorRelation.getId());
        distributorDetailService.insert(distributorDetail);
        this.distributorCount(distributorRelation.getId(), input.getType());

    }

    /**
     * 分销统计报名
     * @param distributorRelationId 分销关系编号
     * @param targetId 统计目标编号
     * @param type 类型
     */
    @Transactional
    public void setDistributorData(String distributorRelationId, String targetId, Integer type){
        DistributorRelation distributorRelation = distributorRelationService.get(distributorRelationId);
        if (null == distributorRelation){
            throw new BusinessException("分销关系不存在");
        }

        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setType(type);
        distributorDetail.setTargetId(targetId);
        distributorDetail.setDistributorRelationId(distributorRelationId);
        distributorDetailService.insert(distributorDetail);
        this.distributorCount(distributorRelation.getId(), type);
    }

    /**
     * 统计数据
     * @param distributorRelationId 分销关系编号
     * @param type 类型
     */
    @Transactional
    public  void distributorCount(String distributorRelationId, Integer type){
        DistributorCount distributorCount = distributorCountService.findByRelationId(distributorRelationId);
        if (null == distributorCount){
            throw new BusinessException("分销统计不存在");
        }

        if (DistributorDetailType.DISTRIBUTOR_TYPE_APPLY.getCode().equals(type)){
            distributorCount.setApplyNum(distributorCount.getApplyNum() + 1);
        }
        else if (DistributorDetailType.DISTRIBUTOR_TYPE_ORDER.getCode().equals(type)){
            distributorCount.setSalesNum(distributorCount.getSalesNum() + 1);
        }
        else if (DistributorDetailType.DISTRIBUTOR_TYPE_CROWDFUN.getCode().equals(type)){
            distributorCount.setCrowdfundNum(distributorCount.getCrowdfundNum() + 1);
        }
        else if (DistributorDetailType.DISTRIBUTOR_TYPE_SUPPORT.getCode().equals(type)){
            distributorCount.setFavorerNum(distributorCount.getFavorerNum() + 1);
        }
        else {
            throw new BusinessException("分销详情类型错误");
        }
        distributorCountService.update(distributorCount);
    }


    /**
     * 是否是自己的分销
     * @param currentUser 请求参数
     * @param targetId 活动编号
     * @param distributorId 分销编号
     * @param parentId 父分销编号
     * @return
     */
    public boolean isMyDistribution(CurrentUser currentUser,Integer targetType, String targetId, String distributorId, String parentId){
        DistributorRelation distributorRelation
                = distributorRelationService.get(targetType, targetId, distributorId);
        if (null == distributorRelation){
            throw new BusinessException("分销关系不存在");
        }

        boolean isMyDistribution = false;
        if (null != currentUser){
            //是否是我的分销
            if (currentUser.getId().equals(distributorRelation.getDistributorId())){
                isMyDistribution = true;
            }
        }
        return isMyDistribution;
    }


    /**
     * 查询分销统计列表
     * @param distributorListInput 输入视图
     * @param page 分页
     * @return 统计列表
     */
    public List<DistributorListOutput> list(DistributorListInput distributorListInput, CurrentUser currentUser, Page page){
        List<WithCount> withCountList
                = distributorRelationService.listWithCount(distributorListInput.getStatus(),
                distributorListInput.getType(), distributorListInput.getSort(), currentUser.getId(), page);

        List<DistributorListOutput> outputList = LangUtils.transform(withCountList, new Function<WithCount, DistributorListOutput>() {
            @Nullable
            @Override
            public DistributorListOutput apply(@Nullable WithCount withCount) {
                DistributorListOutput distributorListOutput = DistributorListOutput.transform(withCount);
                Member member = memberService.get(withCount.getDistributorId());
                distributorListOutput.setDistributorLogo(member.getLogo());
                distributorListOutput.setDistributorName(member.getRealname());
                return distributorListOutput;
            }
        });
        return outputList;
    }


    /**
     * 刷新当前统计
     * @param distributorCount 分销统计
     */
    @Transactional
    public void refreshCount(DistributorCount distributorCount){

        //下级分销量
        DistributorRelation distributorRelation = distributorRelationService.get(distributorCount.getDistributorRalationId());
        if (null != distributorRelation){
            List<WithCount> withCountList = distributorRelationService.findByParentId(distributorRelation.getDistributorId(), distributorRelation.getTargetId());
            distributorCount.setShareNum(withCountList.size());
        }

        //报名量
        List<DistributorDetail> applyList = distributorDetailService.applyList(distributorCount.getDistributorRalationId());
        distributorCount.setApplyNum(applyList.size());

        //下单量
        List<DistributorDetail> buyList = distributorDetailService.buyList(distributorCount.getDistributorRalationId());
        distributorCount.setSalesNum(buyList.size());

        this.reviseCrowdfund(distributorCount);

        distributorCountService.update(distributorCount);
    }


    /**
     * 校正众筹数据
     * @param distributorCount 分销统计
     */
    @Transactional
    public void reviseCrowdfund(DistributorCount distributorCount){

        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        projectWithAuthor.setRelationId(distributorCount.getDistributorRalationId());
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, null);



        List<String> projectIds = LangUtils.transform(projectWithAuthorList, new Function<ProjectWithAuthor, String>() {
            @Nullable
            @Override
            public String apply(@Nullable ProjectWithAuthor projectWithAuthor) {
                if (null == projectWithAuthor){
                    return null;
                }
                return projectWithAuthor.getId();
            }
        });

        if (projectIds != null && projectIds.size() > 0){
            List<Support> crowdSupportList = supportService.findByProjectIds(Sets.newHashSet(projectIds));
            List<Support> distribSupportList = supportService.findByRelationId(distributorCount.getDistributorRalationId());
            crowdSupportList.removeAll(distribSupportList);

            for (Support support : crowdSupportList){
                DistributorDetail distributorDetail = new DistributorDetail();
                distributorDetail.setTargetId(support.getOrderId());
                distributorDetail.setDistributorRelationId(distributorCount.getDistributorRalationId());
                distributorDetail.setCreateDate(support.getCreateDate());
                distributorDetail.setUpdateDate(support.getUpdateDate());
                distributorDetail.setType(DistributorDetailType.DISTRIBUTOR_TYPE_SUPPORT.getCode());
                distributorDetailService.insert(distributorDetail);
            }

            Integer favorerNum = 0;
            Float favorerAmount = 0f;

            //矫正统计数据
            for (ProjectWithAuthor projectWithAuthor1 : projectWithAuthorList){
                favorerNum = favorerNum + projectWithAuthor1.getFavorerNum();
                favorerAmount = BigDecimalUtils.add(favorerAmount, projectWithAuthor1.getActualAmount());
                favorerAmount = BigDecimalUtils.round(favorerAmount, 2);
            }
            distributorCount.setFavorerNum(favorerNum);
            distributorCount.setFavorerAmount(favorerAmount);
            distributorCount.setCrowdfundNum(projectIds.size());
        }
    }


    /**
     * 获取填写详情
     * @param id 编号
     * @param type 类型
     * @return 输出视图
     */
    public DistributorFillOutput getFill(String id , Integer type){
        DistributorFillOutput distributorFillOutput = new DistributorFillOutput();
        if (type.equals(GoodsType.GOODS_ACTIVITY.getCode())){
            Activity activity = activityService.get(id);
            distributorFillOutput = DistributorFillOutput.transform(activity);
            distributorFillOutput.setType(type);
        }
        else if (type.equals(GoodsType.GOODS_CUSTOMIZED.getCode()) || type.equals(GoodsType.GOODS_NOMAL.getCode())){
            Goods goods = goodsService.get(id);
            distributorFillOutput = DistributorFillOutput.transform(goods);
            distributorFillOutput.setPic(goods.getPicsURL());
            distributorFillOutput.setType(type);
        }
        else if (type.equals(GoodsType.GOODS_ARTICLE.getCode())){
            Article article = articleService.get(id);
            distributorFillOutput = DistributorFillOutput.transform(article);
            distributorFillOutput.setType(type);
        }
        return distributorFillOutput;
    }
}
