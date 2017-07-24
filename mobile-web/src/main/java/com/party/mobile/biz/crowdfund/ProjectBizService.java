package com.party.mobile.biz.crowdfund;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.*;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.crowdfund.*;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.*;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.web.dto.crowdfund.input.ApplyInput;
import com.party.mobile.web.dto.crowdfund.input.SupportInput;
import com.party.mobile.web.dto.crowdfund.output.*;
import com.party.mobile.web.dto.login.output.CurrentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 众筹项目业务接口
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 10:21
 */
@Service
public class ProjectBizService {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private IProjectContentService projectContentService;

    @Autowired
    private IMemberActService memberActService;
    
    @Autowired
    private IProjectCommentService projectCommentService;

    @Autowired
    private IAnalyzeService analyzeService;


    //众筹订单默认单位
    private static final int CROWD_FUND_UNIT = 1;

    //我支持的类型
    private static final Integer SUPPORT_TYPE = 1;

    //我发布的类型
    private static final Integer LAUNCH_TYPE = 0;


    /**
     * 分页查询众筹项目
     * @param type 列表类型（0:我发布的 1:我支持的）
     * @param isSuccess 列表状态（0:众筹中 1:成功 2:失败）
     * @param page 分页参数
     * @return 项目列表
     */
    public List<ProjectListOutput> list(Integer type, Integer isSuccess, Page page, CurrentUser currentUser){
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        if (SUPPORT_TYPE.equals(type)){
            projectWithAuthor.setFavorerId(currentUser.getId());
            projectWithAuthor.setOrderStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        }
        else if (LAUNCH_TYPE.equals(type)){
            projectWithAuthor.setAuthorId(currentUser.getId());
        }
        projectWithAuthor.setIsSuccess(isSuccess);
        projectWithAuthor.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        List<ProjectListOutput> projectListOutputList = LangUtils.transform(projectWithAuthorList, new Function<ProjectWithAuthor, ProjectListOutput>() {
            @Nullable
            @Override
            public ProjectListOutput apply(@Nullable ProjectWithAuthor projectWithAuthor) {
                ProjectListOutput projectListOutput = ProjectListOutput.transform(projectWithAuthor);

                Float myFavorerAmount = supportService.myFavorerAmount(currentUser.getId(), projectWithAuthor.getId());
                projectListOutput.setMyFavorerAmount(myFavorerAmount);

                //查询支持列表
                SupportWithMember supportWithMember = new SupportWithMember();
                supportWithMember.setProjectId(projectWithAuthor.getId());
                supportWithMember.setPayStatus(Constant.IS_SUCCESS);
                List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, new Page(1, 8));
                List<FavorerOutput> favorerOutputList = LangUtils.transform(supportWithMemberList, new Function<SupportWithMember, FavorerOutput>() {
                    @Nullable
                    @Override
                    public FavorerOutput apply(@Nullable SupportWithMember supportWithMember) {
                        return FavorerOutput.transform(supportWithMember);
                    }
                });
                projectListOutput.setFavorerOutputList(favorerOutputList);
                return projectListOutput;
            }
        });
        return projectListOutputList;
    }

    /**
     * 获取众筹详情
     * @param id 编号
     * @param currentUser 当前用户
     * @return 项目详情
     */
    public ProjectOutput getDetails(String id, CurrentUser currentUser){
        ProjectWithAuthor projectWithAuthor = projectService.getWithAuthor(id);
        if (null == projectWithAuthor){
            throw new BusinessException("该众筹项目不存在");
        }

        ProjectOutput projectOutput = ProjectOutput.transform(projectWithAuthor);
        //剩余资金
        float surplusAmount = (float)(Math.round((projectWithAuthor.getTargetAmount() - projectWithAuthor.getActualAmount())*100)/100);//如果要求精确4位就*10000然后/10000;
        projectOutput.setSurplusAmount(surplusAmount);

        //根据众筹查找众筹众筹目标
        TargetProject targetProject = targetProjectService.findByProjectId(projectWithAuthor.getId());
        projectOutput.setTargeId(targetProject.getTargetId());
        projectOutput.setDescribe(projectWithAuthor.getRemarks());

        //项目详情
        Activity activity = activityService.get(targetProject.getTargetId());
        if (null == activity){
            throw new BusinessException("众筹目标不存在");
        }

        //验证众筹是否到期
        Integer result = DateUtils.compareDate(new Date(), activity.getEndTime());
        if (result == 1){
            projectOutput.setClosed(true);
        }

        //所有支持者众筹
        Integer beCrowdfundNum = this.supportProjectList(id, null).size();
        //Integer crowdfundedNum = this.supportProjectList(true, id, null).size();
        //projectOutput.setCrowdfundedNum(crowdfundedNum);
        projectOutput.setBeCrowdfundNum(beCrowdfundNum);
        projectOutput.setTargetAuthorId(activity.getMember());
        projectOutput.setTitle(activity.getTitle());
        projectOutput.setPic(activity.getPic());

        //报名相关
        ActivityDetail activityDetail = activityDetailService.getByRefId(targetProject.getTargetId());
        if (null != activityDetail){
            projectOutput.setApplyRelated(StringUtils.stringtohtml(activityDetail.getApplyRelated()));
            projectOutput.setMatchStandard(StringUtils.stringtohtml(activityDetail.getMatchStandard()));
            projectOutput.setContent(StringUtils.stringtohtml(activityDetail.getContent()));
        }

        //是不是自己的项目
        boolean isMy = false;
        if (null != currentUser && currentUser.getId().equals(projectWithAuthor.getAuthorId())){
            isMy = true;
        }
        projectOutput.setMy(isMy);

        if (null != currentUser){
            //是否报名过
            MemberAct memberAct = memberActService.findByMemberAct(currentUser.getId(), targetProject.getTargetId());
            if (null != memberAct){
                TargetProject myTargetProject = targetProjectService.findByOrderId(memberAct.getOrderId());
                Project project = projectService.get(myTargetProject.getProjectId());
                projectOutput.setMyProjectId(project.getId());
            }
        }

        return projectOutput;
    }

    /**
     * 查询众筹支持列表
     * @param page 分页参数
     * @param projectId 项目编号
     * @return 众筹支持列表
     */
    public List<SupportOutput> listSupport(Page page, String projectId){
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setProjectId(projectId);
        supportWithMember.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, page);
        List<SupportOutput> supportOutputList = LangUtils.transform(supportWithMemberList, new Function<SupportWithMember, SupportOutput>() {
            @Nullable
            @Override
            public SupportOutput apply(@Nullable SupportWithMember supportWithMember) {
                SupportOutput supportOutput = SupportOutput.transform(supportWithMember);
                ProjectComment projectComment = new ProjectComment();
                projectComment.setRefId(supportOutput.getId());
                //获取回复列表
                List<ProjectComment> proCommentList = projectCommentService.list(projectComment);
                List<ProjectCommentOutput> replyList = LangUtils.transform(proCommentList, new Function<ProjectComment, ProjectCommentOutput>() {
					@Override
					public ProjectCommentOutput apply(ProjectComment proCmt) {
						ProjectCommentOutput proCmtOutput = ProjectCommentOutput.transform(proCmt);
						Member member = memberService.get(proCmt.getCreateBy());
						proCmtOutput.setCommentName(member.getRealname());
						proCmtOutput.setCommentLogo(member.getLogo());
						return proCmtOutput;
					}                	
                });                
                supportOutput.setReplyList(replyList);
                
                OrderForm orderForm = orderFormService.get(supportWithMember.getOrderId());
                supportOutput.setPayment(orderForm.getPayment());
                return supportOutput;
            }
        });
        return supportOutputList;
    }

    /**
     * 众筹支持
     * @param supportInput 输入视图
     * @param currentUser 当前用户
     * @return 订单号
     */
    @Transactional
    public String support(SupportInput supportInput, CurrentUser currentUser){

        Float amount = projectService.getMaxAmount(supportInput.getProjectId());
        if (BigDecimalUtils.round(supportInput.getMoney(), 2) > BigDecimalUtils.round(amount, 2)){
            throw new BusinessException(PartyCode.CROWDFUNF_SUPPORT_PASS, "支持金额不能大于众筹剩余金额");
        }

        //校正众筹数据
        this.revise(supportInput.getProjectId());

        //生成订单
        Member member = memberService.get(currentUser.getId());
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(currentUser.getId());
        orderForm.setGoodsId(supportInput.getProjectId());
        orderForm.setUnit(CROWD_FUND_UNIT);
        orderForm.setLinkman(member.getUsername());
        orderForm.setPhone(member.getMobile());
        orderForm.setType(OrderType.ORDER_CROWD_FUND.getCode());
        orderForm.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
        orderForm.setIsPay(PaymentState.NO_PAY.getCode());
        orderForm.setPayment(supportInput.getMoney());
        Project project = projectService.get(supportInput.getProjectId());
        if (StringUtils.isNotEmpty(project.getId())) {
        	TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
        	if (targetProject.getType().equals(Constant.CROWD_FUND_TYPE_ACTIVITY)) {
        		Activity activity = activityService.get(targetProject.getTargetId());
        		orderForm.setInitiatorId(activity.getMember()); // 众筹项目发起者
        	}
		}
        
        orderForm.setTitle(project.getTitle());
        
        String id = orderFormService.insert(orderForm);

        //持久化支持
        Support support = SupportInput.transform(supportInput);
        support.setOrderId(id);
        support.setFavorerId(currentUser.getId());
        supportService.insert(support);
        return orderForm.getId();
    }

    /**
     * 查询活动下的众筹
     * @param isSuccess 是否筹集成功
     * @param targetId 目标编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> projectForActivityList(boolean isSuccess, String targetId, Page page){
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();

        //众筹成功？
        Integer success = isSuccess ? 1: 0;
        projectWithAuthor.setIsSuccess(success);
        projectWithAuthor.setTargetId(targetId);
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }

    /**
     * 查询活动下兄弟活动的众筹
     * @param isSuccess 是否筹集成功
     * @param targetId 目标编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> projectForBrotherList(boolean isSuccess, String targetId, Page page){
        Activity activity = activityService.get(targetId);
        if (null == activity || Strings.isNullOrEmpty(activity.getEventId())){
            return Collections.EMPTY_LIST;
        }

        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        //众筹成功？
        Integer success = isSuccess ? 1: 0;
        projectWithAuthor.setIsSuccess(success);
        projectWithAuthor.setExcludeTargetId(targetId);
        projectWithAuthor.setEventId(activity.getEventId());
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }

    /**
     * 活动下所有的众筹
     * @param isSuccess 是否筹集成功
     * @param targetId 目标编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> projectForAllList(boolean isSuccess, String targetId, Page page){
        Activity activity = activityService.get(targetId);
        if (null == activity){
            return Collections.EMPTY_LIST;
        }

        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        if (Strings.isNullOrEmpty(activity.getEventId())){
            projectWithAuthor.setTargetId(targetId);
        }
        else {
            projectWithAuthor.setEventId(activity.getEventId());
        }

        //众筹成功？
        Integer success = isSuccess ? 1: 0;
        projectWithAuthor.setIsSuccess(success);
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }

    /**
     * 查询代言下的众筹
     * @param isSuccess 是否众筹成功
     * @param representId 代言编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> projectForRepresentList(boolean isSuccess, String representId, Page page){
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();

        //众筹成功？
        Integer success = isSuccess ? 1: 0;
        projectWithAuthor.setIsSuccess(success);
        projectWithAuthor.setRelationId(representId);
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }


    /**
     * 查询活动下我的支持者的众筹
     * @param isSuccess 是否众筹成功
     * @param projectId 众筹编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> supportProjectList(boolean isSuccess, String projectId, Page page){

        //查询该项目支持者
        Support support = new Support();
        support.setProjectId(projectId);
        List<Support> supportList = supportService.list(support);

        //是否存在支持者
        if (CollectionUtils.isEmpty(supportList)){
            return Collections.EMPTY_LIST;
        }

        List<String> supportIds = LangUtils.transform(supportList, new Function<Support, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Support support) {
                return support.getFavorerId();
            }
        });


        //众筹项目编号
        TargetProject targetProject = targetProjectService.findByProjectId(projectId);
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        Integer success = isSuccess ? 1: 0;
        projectWithAuthor.setIsSuccess(success);
        projectWithAuthor.setTargetId(targetProject.getTargetId());
        projectWithAuthor.setAuthorIds(Sets.newHashSet(supportIds));
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }

    /**
     * 查询活动下我的支持者的众筹
     * @param projectId 众筹编号
     * @param page 分页参数
     * @return 众筹列表
     */
    public List<ProjectForActivityOutput> supportProjectList( String projectId, Page page){
        //查询该项目支持者
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setProjectId(projectId);
        supportWithMember.setPayStatus(Constant.IS_SUCCESS);
        List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, null);

        //是否存在支持者
        if (CollectionUtils.isEmpty(supportWithMemberList)){
            return Collections.EMPTY_LIST;
        }

        //众筹项目编号
        TargetProject targetProject = targetProjectService.findByProjectId(projectId);
        Activity activity = activityService.get(targetProject.getTargetId());
        if (null == activity){
            return Collections.EMPTY_LIST;
        }

        List<String> supportIds = LangUtils.transform(supportWithMemberList, new Function<Support, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Support support) {
                return support.getFavorerId();
            }
        });
        //众筹项目编
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        if (Strings.isNullOrEmpty(activity.getEventId())){
            projectWithAuthor.setTargetId(targetProject.getTargetId());
        }
        else {
            projectWithAuthor.setEventId(activity.getEventId());
        }
        projectWithAuthor.setAuthorIds(Sets.newHashSet(supportIds));
        List<ProjectWithAuthor> projectWithAuthorList = projectService.listPage(projectWithAuthor, page);
        return this.transformOutput(projectWithAuthorList);
    }

    /**
     * 转输出视图
     * @param projectWithAuthorList 众筹列表
     * @return 输出视图列表
     */
    public List<ProjectForActivityOutput> transformOutput(List<ProjectWithAuthor> projectWithAuthorList){
        List<ProjectForActivityOutput> projectForActivityOutputList = LangUtils.transform(projectWithAuthorList, new Function<ProjectWithAuthor, ProjectForActivityOutput>() {
            @Nullable
            @Override
            public ProjectForActivityOutput apply(@Nullable ProjectWithAuthor projectWithAuthor) {
                ProjectForActivityOutput projectForActivityOutput =  ProjectForActivityOutput.transform(projectWithAuthor);
                //查询支持列表
                SupportWithMember supportWithMember = new SupportWithMember();
                supportWithMember.setProjectId(projectWithAuthor.getId());
                supportWithMember.setPayStatus(Constant.IS_SUCCESS);
                List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, new Page(1, 8));
                List<FavorerOutput> favorerOutputList = LangUtils.transform(supportWithMemberList, new Function<SupportWithMember, FavorerOutput>() {
                    @Nullable
                    @Override
                    public FavorerOutput apply(@Nullable SupportWithMember supportWithMember) {
                        return FavorerOutput.transform(supportWithMember);
                    }
                });
                projectForActivityOutput.setFavorerOutputList(favorerOutputList);
                return projectForActivityOutput;
            }
        });
        return projectForActivityOutputList;
    }

    /**
     * 插入众筹
     * @param memberAct 活动报名
     * @param memberId 会员编号
     * @param applyInput 众筹宣言
     * @return 众筹编号
     */
    @Transactional
    public String insert(MemberAct memberAct, String memberId, ApplyInput applyInput){

        //持久化众筹
        Activity activity = activityService.get(memberAct.getActId());

        //是否是众筹活动
        if (!activity.getIsCrowdfunded().equals(Constant.IS_CROWDFUNDED)){
            throw new BusinessException("该活动不是众筹类型");
        }
        Project project = new Project();
        project.setTitle(activity.getTitle());
        project.setTargetAmount(activity.getPrice());
        project.setPic(activity.getPic());
        project.setEndDate(activity.getEndTime());
        project.setDeclaration(applyInput.getDeclaration());
        project.setTitle(applyInput.getProjectTitle());
        project.setStyle(applyInput.getStyle());
        String content = null;
        //活动详情
        ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
        if (null != activityDetail && null != activityDetail.getContent()){
            content = activityDetail.getContent();
        }

        String id = projectContentService.insert(new ProjectContent(content));
        project.setContentId(id);
        project.setAuthorId(memberId);
        String projectId = projectService.insert(project);

        //持久化众筹关联
        TargetProject targetProject = new TargetProject();
        targetProject.setTargetId(activity.getId());
        targetProject.setOrderId(memberAct.getOrderId());
        targetProject.setProjectId(projectId);
        targetProject.setType(Constant.CROWD_FUND_TYPE_ACTIVITY);
        targetProjectService.insert(targetProject);

        //统计分析
        Analyze analyze = new Analyze();
        analyze.setTargetId(projectId);
        analyzeService.insert(analyze);

        return project.getId();
    }

    /**
     * 获取支持初始化数据
     * @param projectId 项目编号
     * @return输出视图
     */
    public GetSupportInitOutput getSupportInit(String projectId){
        GetSupportInitOutput getSupportInitOutput = new GetSupportInitOutput();
        //float num = projectService.getMaxAmount(projectId);
        TargetProject targetProject = targetProjectService.findByProjectId(projectId);
        Activity activity = activityService.get(targetProject.getTargetId());
        if (null == activity){
            throw new BusinessException("众筹项目不存在");
        }
        //getSupportInitOutput.setMaxNum(num);
        getSupportInitOutput.setSupportDeclaration(activity.getSupportDeclaration());
        return getSupportInitOutput;
    }


    /**
     * 编辑众筹项目
     * @param id 众筹编号
     * @param declaration 众筹代言
     */
    public void edite(String id, String declaration, String style){
        Project project = projectService.get(id);
        project.setDeclaration(declaration);
        project.setStyle(style);
        projectService.update(project);
    }


    /**
     * 校正已筹金额
     * @param projectId 众筹编号
     */
    public void revise(String projectId){
        Project project = projectService.get(projectId);
        Float sum = supportService.sumByProjectId(projectId);
        if (!project.getActualAmount().equals(sum)){
            Float amount = sum > project.getTargetAmount() ? project.getTargetAmount() : sum;
            amount = amount < 0 ? 0 : amount;
            project.setActualAmount(amount);
            projectService.update(project);
        }
    }
}
