package com.party.mobile.biz.dynamic;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.PinyinUtil;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.city.Area;
import com.party.core.model.dynamic.*;
import com.party.core.model.fans.Fans;
import com.party.core.model.love.Love;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.service.circle.biz.CircleTopicBizService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.dynamic.ICommentService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.fans.IFansService;
import com.party.core.service.love.ILoveService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.circle.input.TopicInput;
import com.party.mobile.web.dto.dynamic.input.DynamicInput;
import com.party.mobile.web.dto.dynamic.output.*;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.common.utils.PartyCode;

import com.party.notify.notifyPush.servce.INotifySendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * 动态业务
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:27 16/11/8
 * @Modified by:
 */
@Service
public class DynamicBizService {
    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ILoveService loveService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFansService fansService;

    @Autowired
    private FansBizService fansBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private IDypicsService dypicsService;

    @Autowired
    private IIndustryService industryService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private INotifySendService notifySendService;

    @Autowired
    private CircleTopicBizService circleTopicBizService;

    @Autowired
    private DynamicBaseBizService dynamicBaseBizService;


    /**
     * 获取动态详情
     * @param dynamic 动态
     * @param request
     * @throws BusinessException
     */
    public DynamicOutput getDetails(Dynamic dynamic, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String currentUserId = null;
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }

        if (null == dynamic)
        {
            throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
        }
        DynamicOutput output = this.getDynamicOutputData(dynamic, currentUserId);
        return output;
    }

    /**
     * 获取话题详情
     * @param dynamic 动态
     * @param request
     * @throws BusinessException
     */
    public DynamicOutput getTopicDetails(TopicMap dynamic, HttpServletRequest request) throws BusinessException
    {
        DynamicOutput output = this.getDetails(dynamic, request);
        output.setTagName(dynamic.getTagName());
        return output;
    }
    /**
     * 获取话题列表
     * @param dynamic
     * @param page
     * @param curUserId
     * @return
     */
    @Transactional
    public List<DynamicOutput> listTopicNew(CircleTopic topic, Dynamic dynamic, Map<String,Object> params,  Page page, String curUserId)
    {
        if(params == null){
            params = Maps.newHashMap();
        }
        params.put("startRow",(page.getPage() - 1) * page.getLimit());
        params.put("pageSize",page.getLimit());
        params.put("commentNum",3);
        params.put("topic",topic);
        //获取动态列表
        List<TopicMap> dblist = dynamicService.listTopicMapPage(dynamic,params);

        return transformTopicOutput(dblist,curUserId);
    }
    /**
     * 获取动态列表
     * @param dynamic
     * @param page
     * @param curUserId
     * @return
     */
    @Transactional
    public List<DynamicOutput> listDynamicNew(Dynamic dynamic, Map<String,Object> params,  Page page, String curUserId)
    {
        if(params == null){
            params = Maps.newHashMap();
        }
        params.put("startRow",(page.getPage() - 1) * page.getLimit());
        params.put("pageSize",page.getLimit());
        params.put("commentNum",3);
        //获取动态列表
        List<Dynamic> dblist = dynamicService.listDynamicMapPage(dynamic,params);

        return transformDynamicOutput(dblist,curUserId);
    }

    private List<DynamicOutput> transformDynamicOutput(List<Dynamic> dblist, String currentUserId) {
        List<DynamicOutput> dynamicOutputs = LangUtils.transform(dblist, input -> {
            Dynamic dynamic = input;
            DynamicOutput transform = DynamicOutput.transform(dynamic);
            //设置动态评论列表
            transform.setCommentList(input.getCommentList());
            //设置动态作者
            transform.setAuthor(this.getDyMember(dynamic.getCreateBy(), currentUserId));
            //设置图片列表
            if(!Strings.isNullOrEmpty(dynamic.getPics())) {
                transform.setPicList(Arrays.asList(dynamic.getPics().split("\\|")));
            }
            //设置当前用户是否已经点赞该动态
            transform.setIsLove(this.getIsLove(dynamic.getId(), currentUserId));
            //设置分享链接
            transform.setShareLink(setShareLink(dynamic.getId()));
            return transform;
        });
        return  dynamicOutputs;
    }

    private List<DynamicOutput> transformTopicOutput(List<TopicMap> dblist, String currentUserId) {
        List<DynamicOutput> dynamicOutputs = LangUtils.transform(dblist, input -> {
            TopicMap dynamic = input;
            DynamicOutput transform = DynamicOutput.transform(dynamic);
            //设置动态评论列表
            transform.setCommentList(input.getCommentList());
            //设置动态作者
            transform.setAuthor(this.getDyMember(dynamic.getCreateBy(), currentUserId));
            //设置图片列表
            if(!Strings.isNullOrEmpty(dynamic.getPics())) {
                transform.setPicList(Arrays.asList(dynamic.getPics().split("\\|")));
            }
            //设置当前用户是否已经点赞该动态
            transform.setIsLove(this.getIsLove(dynamic.getId(), currentUserId));
            //设置分享链接
            transform.setShareLink(setShareLink(dynamic.getId()));
            return transform;
        });
        return  dynamicOutputs;
    }
    /**
     * 当前用户关注人的动态列表
     * @param dynamic 筛选条件
     * @param page
     * @param request
     * @return
     */
    @Transactional
    public List<DynamicOutput> listFocusDynamic(Dynamic dynamic, Page page, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String currentUserId = null;
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }
        else
        {
            throw new BusinessException(PartyCode.LIST_FOCUS_DYNAMIC_NOT_LOGIN_ERROR,"没有登录，改为推荐达人动态列表");
        }

        //获取关注的人列表
        Fans fans = new Fans();
        fans.setFollowerId(currentUserId);
        List<Fans> focusList = fansService.list(fans);

        Set<String> memberIds = new HashSet<>();
        List<Dynamic> dblist = new ArrayList<>();

        //有关注的人，取关注的人的动态列表
        if (focusList.size() > 0) {
            for (int i = 0; i < focusList.size(); i++) {
                memberIds.add(focusList.get(i).getMemberId());
            }
            //根据关注的人列表，获取动态列表
            dblist = dynamicService.batchListByMemberId(memberIds, dynamic, page);
        }
        else//没有关注的人
        {
            throw new BusinessException(PartyCode.LIST_FOCUS_DYNAMIC_ERROR, "没有关注的人，改为推荐达人动态列表");
        }
        return getDynamicList(dblist, currentUserId);
    }

    /**
     * 达人动态列表
     * @param
     * @param page
     * @param request
     * @return
     */
	public List<List<DynamicOutput>> listExpertDynamic(Member member, Page page,HttpServletRequest request) {	
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		List<Member> memberList = memberService.listPage(member, page);
		Dynamic dynamic = new Dynamic();
        dynamic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        Page pageDyn = new Page();
        pageDyn.setLimit(9);
        List<List<DynamicOutput>> result = new ArrayList<List<DynamicOutput>>();        
       	for(Member m : memberList){       		
			dynamic.setCreateBy(m.getId());
			List<Dynamic> dynamicList = dynamicService.listPage(dynamic, pageDyn);
			if(dynamicList.size() > 0){
				List<DynamicOutput> memberDynamicList =  getDynamicList(dynamicList, null == currentUser? null : currentUser.getId());
				result.add(memberDynamicList);
			}
		}
		return result;
	}
    /**
     * 将动态实体列表转换为动态输出视图列表
     * @param list 动态实体列表
     * @param currentUserId 当前登录用户id
     * @return
     */
    @Transactional
    public List<DynamicOutput> getDynamicList(List<Dynamic> list, String currentUserId)
    {
        List<DynamicOutput> dynamicOutputList = new ArrayList<>();

        for (int i = 0; i< list.size(); i++)
        {
            Dynamic dy = list.get(i);
            //加入列表
            dynamicOutputList.add(this.getDynamicOutputData(dy, currentUserId));
        }

        return dynamicOutputList;
    }
    /**
     * 将话题实体列表转换为话题输出视图列表
     * @param list 动态实体列表
     * @param currentUserId 当前登录用户id
     * @return
     */
    @Transactional
    public List<DynamicOutput> getTopicList(List<TopicMap> list, String currentUserId)
    {
        List<DynamicOutput> dynamicOutputList = new ArrayList<>();

        for (int i = 0; i< list.size(); i++)
        {
            TopicMap dy = list.get(i);
            //加入列表
            DynamicOutput topic = this.getDynamicOutputData(dy, currentUserId);
            topic.setTagName(dy.getTagName());
            topic.setIsTop(dy.getIsTop());
            dynamicOutputList.add(topic);
        }

        return dynamicOutputList;
    }
    /**
     * 获取动态输出视图
     * @param dy 动态实体
     * @param currentUserId 当前登录用户id
     * @return 动态输出视图
     */
    private DynamicOutput getDynamicOutputData(Dynamic dy, String currentUserId)
    {
        DynamicOutput dynamicOutput = new DynamicOutput();
        //设置动态主键id
        dynamicOutput.setId(dy.getId());
        //设置动态创建时间
        dynamicOutput.setCreateDate(dy.getCreateDate());
        //设置动态作者
        dynamicOutput.setAuthor(this.getDyMember(dy.getCreateBy(), currentUserId));
        //设置动态内容
        dynamicOutput.setContent(dy.getContent());
        //设置动态类型
        dynamicOutput.setDynamicType(dy.getDynamicType());
        //设置图片列表
//            dynamicOutput.setPicList(this.getPicList(dy));
        dynamicOutput.setPicList(this.getPicList(dy.getId()));
        //设置动态评论数
        dynamicOutput.setCommentNum(dy.getCommentNum());
        //设置评论列表(暂时不给评论列表)
        if (dynamicOutput.getCommentNum() > 0) {
//            dynamicOutput.setCommentList(this.getDyCommentList(dy.getId(), currentUserId));
        }
        else
        {
            dynamicOutput.setCommentList(Collections.EMPTY_LIST);
        }
        //设置点赞数
        dynamicOutput.setLoveNum(dy.getLoveNum());
        //设置点赞列表（暂时不给点赞列表）
//            if (dynamicOutput.getLoveNum() > 0) {
//                dynamicOutput.setLoverList(this.getDyLoveList(dy.getId(), currentUserId));
//            }
//            else
//            {
//                dynamicOutput.setLoverList(Collections.EMPTY_LIST);
//            }

        //设置当前用户是否已经点赞该动态
        dynamicOutput.setIsLove(this.getIsLove(dy.getId(), currentUserId));
        //设置分享链接
        dynamicOutput.setShareLink(setShareLink(dy.getId()));
        return dynamicOutput;
    }

    /**
     *
     * @param authorId 动态或者评论或者点赞的作者主键id
     * @param currentUserId 当前登录用户id
     * @return
     */
    @Transactional
    public DyMemberOutput getDyMember(String authorId, String currentUserId)
    {
        DyMemberOutput dyMemberOutput = new DyMemberOutput();

        if (Strings.isNullOrEmpty(authorId))
        {
            return dyMemberOutput;
        }

        Member dbMember = memberService.get(authorId);
        if (null == dbMember) {
            return dyMemberOutput;
        }

        dyMemberOutput = DyMemberOutput.transform(dbMember);

        Fans fans = new Fans();
        fans.setMemberId(dbMember.getId());
        fans.setFollowerId(dbMember.getId());
        //设置粉丝个数
        dyMemberOutput.setFansNum(fansService.countFans(fans));
        //设置关注个数
        dyMemberOutput.setFocusNum(fansService.countFocus(fans));

        //设置该作者与当前登录用户的相互关注状态
        if(Strings.isNullOrEmpty(authorId)){
        	dyMemberOutput.setFocusStatus(0);
        }else{
        	dyMemberOutput.setFocusStatus(fansBizService.getFocusStatus(dbMember.getId(), currentUserId));
        }

        return dyMemberOutput;
    }

    /**
     * 获取动态的评论列表（默认3条）
     * @param dyId 动态id
     * @param currentUserId 当前登录用户id
     * @return 评论输出视图列表
     */
    @Transactional
    public List<DyCommentOutput> getDyCommentList(String dyId, String currentUserId)
    {
        //默认给3条
        return listDynamicComment(dyId, new Page(1,3), currentUserId);
    }

    /**
     * 获取动态的点赞列表（默认3条）
     * @param dyId 动态id
     * @param currentUserId 当前登录用户id
     * @return 评论输出视图列表
     */
    @Transactional
    public List<DyLoveOutput> getDyLoveList(String dyId, String currentUserId)
    {
        //默认给3条
        return listDynamicLove(dyId, new Page(1,3), currentUserId);
    }

    /**
     * 老存储方式
     * 获取一条动态的所有图片列表
     * @param entity 动态实体
     * @return
     */
    private List<String> getPicList(Dynamic entity)
    {
        List<String> list = new ArrayList<String>();
        if(StringUtils.isNotBlank(entity.getPics())){
            String picArray[] = entity.getPics().split("\\|");

            for(int i = 0; i < picArray.length; i++)
            {
                if(picArray[i].length() > 0)
                {
                    list.add(picArray[i]);
                }
            }
        }
        return list;
    }

    /**
     * 新存储方式
     * 根据动态id获取动态的图片列表
     * @param dynamicId 动态主键id
     * @return
     */
    @Transactional
    private List<String> getPicList(String dynamicId)
    {
        if(Strings.isNullOrEmpty(dynamicId))
        {
            return Collections.EMPTY_LIST;
        }

        List<Dypics> dypicsList = dypicsService.findByDynamicId(dynamicId, null);

        List<String> picUrlList = new ArrayList<String>();

        for (int i = 0; i < dypicsList.size(); i++)
        {
            Dypics t = dypicsList.get(i);

            if (!Strings.isNullOrEmpty(t.getPicUrl()))
                picUrlList.add(t.getPicUrl());
        }
        return picUrlList;
    }

    /**
     * 新存储方式
     * 根据会员id获取图片列表
     * @param memberId 会员主键id
     * @param page
     * @return
     */
    @Transactional
    public List<String> getPicList(String memberId, Page page)
    {
        if(Strings.isNullOrEmpty(memberId))
        {
            return Collections.EMPTY_LIST;
        }

        List<Dypics> dypicsList = dypicsService.findByMemberId(memberId, page);

        List<String> picUrlList = new ArrayList<String>();

        for (int i = 0; i < dypicsList.size(); i++)
        {
            Dypics t = dypicsList.get(i);

            if (!Strings.isNullOrEmpty(t.getPicUrl()))
                picUrlList.add(t.getPicUrl());
        }
        return picUrlList;
    }

    /**
     * 获取当前登录用户的图片列表
     * @param page
     * @param request
     * @return
     */
    @Transactional
    public List<String> getPicList(Page page, HttpServletRequest request)
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null == currentUser)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        else
        {
            return getPicList(currentUser.getId(), page);
        }

    }


    /**
     * 获取当前用户是否已经点赞
     * @param dyId 动态id
     * @param currentUserId 当前用户id
     */
    @Transactional
    public Integer getIsLove(String dyId, String currentUserId)
    {
        if (Strings.isNullOrEmpty(dyId) || Strings.isNullOrEmpty(currentUserId)) {
            return YesNoStatus.NO.getCode();//没有点赞
        }

        //对同一个动态，一个用户只能点赞一次
        Love love = loveService.findByRefId(dyId, currentUserId);
        if (null != love && YesNoStatus.YES.getCode() == love.getIsLove())
        {
            return YesNoStatus.YES.getCode();//已经点赞
        }

        return YesNoStatus.NO.getCode();//没有点赞
    }

    /**
     * 动态点赞列表业务
     * @param id 动态主键id
     * @param page 分页
     * @param currentUserId 当前登录用户id，如果没登录，填null
     * @return
     */
    @Transactional
    public List<DyLoveOutput> listDynamicLove(String id,  Page page, String currentUserId)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return Collections.EMPTY_LIST;
        }
        //设置过滤条件
        Love entity = new Love();
        entity.setRefId(id);
        entity.setIsLove(YesNoStatus.YES.getCode());//0：否，1：是
        entity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
        //获取动态点赞列表
        List<Love> dbLoveList = loveService.listPage(entity, page);

        List<DyLoveOutput> dyLoveOutputList = new ArrayList<>();

        for (int i = 0; i < dbLoveList.size(); i++)
        {
            Love t = dbLoveList.get(i);
            //设置点赞作者
            DyMemberOutput dyMemberOutput = this.getDyMember(t.getCreateBy(), currentUserId);
            DyLoveOutput dyLoveOutput = new DyLoveOutput();
            dyLoveOutput.setAuthor(dyMemberOutput);
            //加入列表
            dyLoveOutputList.add(dyLoveOutput);
        }

        return dyLoveOutputList;
    }

    /**
     * 动态点赞列表业务
     * @param id 动态主键id
     * @param page 分页
     * @param request
     * @return
     */
    @Transactional
    public List<DyLoveOutput> listDynamicLove(String id,  Page page, HttpServletRequest request)
    {
        String currentUserId = null;
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }

        return listDynamicLove(id, page, currentUserId);
    }

    /**
     * 动态评论列表业务
     * @param id 动态主键id
     * @param page 分页
     * @param currentUserId 当前登录用户id，如果没登录，填null
     * @return
     */
    @Transactional
    public List<DyCommentOutput> listDynamicComment(String id,  Page page, String currentUserId)
    {
        if (Strings.isNullOrEmpty(id))
        {
            return Collections.EMPTY_LIST;
        }

        //设置过滤条件
        Comment comment = new Comment();
        comment.setRefId(id);
        comment.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
//        comment.setCommentType(DynamicType.COMMUNITY.getCode());
        //根据动态id查找该动态的评论列表
        List<Comment> dbCommentList = commentService.listPage(comment,page);

        List<DyCommentOutput> dyCommentOutputList = new ArrayList<>();
        for (int i = 0; i < dbCommentList.size(); i++)
        {
            Comment t = dbCommentList.get(i);

            DyCommentOutput dyCommentOutput = new DyCommentOutput();

            //设置评论内容
            dyCommentOutput.setContent(t.getContent());

            //设置评论作者
            DyMemberOutput dyMemberOutput = this.getDyMember(t.getCreateBy(), currentUserId);
            dyCommentOutput.setAuthor(dyMemberOutput);

            //设置时间
            dyCommentOutput.setCreateDate(t.getCreateDate());

            //加入列表
            dyCommentOutputList.add(dyCommentOutput);
        }

        return dyCommentOutputList;
    }

    /**
     * 动态评论列表业务
     * @param id 动态主键id
     * @param page 分页
     * @param request
     * @return
     */
    @Transactional
    public List<DyCommentOutput> listDynamicComment(String id,  Page page, HttpServletRequest request) {
        String currentUserId = null;
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }

        return listDynamicComment(id, page, currentUserId);
    }

    /**
     * 设置动态点赞业务
     * @param id 动态主键id
     * @param request
     * @return
     */
    @Transactional
    public HandleLoveOutput setDynamicLove(String id, Integer isLove, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbLoveMember = memberService.get(currentUser.getId());
        //验证当前登录用户数据
        if (null == dbLoveMember)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        //动态
        Dynamic dynamic = dynamicService.get(id);
        if(null == dynamic)
        {
            throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
        }

        //对同一个动态，一个用户只能点赞一次
        Love love = loveService.findByRefId(id, currentUser.getId());
        Integer loveNum = dynamic.getLoveNum();
        //点赞
        if(isLove == 1){
            if(null == love) {
                //新建一条点赞数据
                love = new Love();
                love.setCreateBy(currentUser.getId());
                love.setUpdateBy(currentUser.getId());
                love.setCreateDate(new Date());
                love.setUpdateDate(new Date());
                love.setRefId(id);
                love.setIsLove(isLove);
                love.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
                loveService.insert(love);
                dynamic.setLoveNum(++loveNum);
            }else{
                love.setIsLove(isLove);
                loveService.update(love);
            }
        }else if(isLove == 0){
            if(null != love) {
                loveService.delete(love.getId());
                dynamic.setLoveNum(--loveNum);
            }
        }
        dynamicService.update(dynamic);
        //设置输出视图
        HandleLoveOutput loveOutput = new HandleLoveOutput();
        loveOutput.setIsLove(isLove);
        loveOutput.setId(id);
        loveOutput.setLoveNum(dynamic.getLoveNum());
        //设置点赞数据
        DyLoveOutput dyLoveOutput = new DyLoveOutput();
        if (1 == isLove) {
            DyMemberOutput dyMemberOutput = this.getDyMember(love.getCreateBy(), currentUser.getId());
            dyLoveOutput.setAuthor(dyMemberOutput);
        }
        else
        {
            dyLoveOutput = null;
        }
        loveOutput.setLove(dyLoveOutput);

        //如果是点赞，则生成app消息
        if (1 == isLove) {
            //生成App消息,发给该动态的作者
            String picUrl = "";
            if (StringUtils.isNoneBlank(dynamic.getPics())) {//有些动态没有图片
                String picArray[] = dynamic.getPics().split("\\|");
                picUrl = picArray[0];
            }

            //动态作者author
            Member author = memberService.get(dynamic.getCreateBy());
            if (null != author && !author.getId().equals(dbLoveMember.getId())) {//自己给自己点赞不生成消息
                notifySendService.sendLove(author.getId(), dbLoveMember, dbLoveMember.getLogo(), picUrl, "", dynamic.getId());
            }

        }

        return loveOutput;
    }

    /**
     * 设置动态评论业务
     * @param id 动态主键id
     * @param content 评论内容
     * @param request
     * @return
     */
    @Transactional
    public HandleCommentOutput setDynamicComment(String id, String content,HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbCommentMember = memberService.get(currentUser.getId());
        //验证当前登录用户数据
        if (null == dbCommentMember)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        //动态
        Dynamic dynamic = dynamicService.get(id);
        if(null == dynamic)
        {
            throw new BusinessException(PartyCode.DYNAMIC_NOT_EXIT, "动态的主键id错误，数据不存在");
        }

//        if (Integer.parseInt(AuditStatus.PASS.getCode()) != dbCommentMember.getUserStatus())
//        {
//            throw new BusinessException(PartyCode.USER_PROFILE_UN_PASS, "会员资料未审核通过");
//        }
        //更新动态评论数
        Integer cmtNum = dynamic.getCommentNum();
        dynamic.setCommentNum(++cmtNum);
        dynamicService.update(dynamic);
        //对同一个动态，一个用户能够评论多次
        Comment commemt = new Comment();
        commemt.setCreateBy(currentUser.getId());
        commemt.setUpdateBy(currentUser.getId());
        commemt.setCreateDate(new Date());
        commemt.setUpdateDate(new Date());
        commemt.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
        commemt.setRefId(id);
        commemt.setContent(PinyinUtil.filterEmoji(content, ""));//过滤Emoji表情
        commemt.setCommentType(DynamicType.COMMUNITY.getCode());//评论类型（1：社区，2：圈子）

        commentService.insert(commemt);

        //设置输出视图
        HandleCommentOutput output = new HandleCommentOutput();
        output.setId(id);
        output.setCommentNum(dynamic.getCommentNum());
        //设置评论
        DyCommentOutput dyCommentOutput = new DyCommentOutput();
        dyCommentOutput.setCreateDate(commemt.getCreateDate());
        dyCommentOutput.setContent(commemt.getContent());
//        dyCommentOutput.setAuthor(this.getDyMember(currentUser.getId(), currentUser.getId()));

        output.setComment(dyCommentOutput);


        //生成App消息,发给该动态的作者
        String picUrl = "";
        if (StringUtils.isNoneBlank(dynamic.getPics())) {//有些动态没有图片
            String picArray[] = dynamic.getPics().split("\\|");
            picUrl = picArray[0];
        }

        //动态作者author
        Member author = memberService.get(dynamic.getCreateBy());

        if (null != author && !author.getId().equals(dbCommentMember.getId())) {//自己给自己评论不生成消息
            notifySendService.sendComment(author.getId(), dbCommentMember, dbCommentMember.getLogo(), picUrl, content, dynamic.getId());
        }

        return output;
    }

    /**
     * 查看会员的粉丝列表
     * @param memberId 会员id
     * @param page
     * @param request
     * @return
     */
    public List<DyMemberOutput> listFans(String memberId, Page page, HttpServletRequest request)
    {
        String currentUserId = null;
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }

        //设置过滤条件
        Fans fans = new Fans();
        fans.setMemberId(memberId);
        fans.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
        //查找粉丝列表
        List<Fans> fansList = fansService.listPage(fans, page);

        //粉丝列表输出视图
        List<DyMemberOutput> outputList = new ArrayList<>();
        for (int i = 0; i < fansList.size(); i++)
        {
            Member input = memberService.get(fansList.get(i).getFollowerId());
            if (null != input) {
                DyMemberOutput output = this.getDyMember(input.getId(), currentUserId);
                outputList.add(output);
            }
        }

        return outputList;
    }

    /**
     * 查看当前登录用户的粉丝列表
     * @param page
     * @param request
     * @return
     */
    public List<DyMemberOutput> listFans(Page page, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser && !Strings.isNullOrEmpty(currentUser.getId()))
        {
            return listFans(currentUser.getId(), page, request);
        }
        else
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
    }


    /**
     * 查看会员的关注列表
     * @param memberId 会员id
     * @param page
     * @param request
     * @return
     */
    public List<DyMemberOutput> listFocus(String memberId, Page page, HttpServletRequest request)
    {
        String currentUserId = null;
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser)
        {
            currentUserId = currentUser.getId();
        }

        //设置过滤条件
        Fans fans = new Fans();
        fans.setFollowerId(memberId);
        fans.setDelFlag(BaseModel.DEL_FLAG_NORMAL);//逻辑删除标志位（0：正常，1：删除）
        //查找关注列表
        List<Fans> fansList = fansService.listPage(fans, page);

        //关注列表输出视图
        List<DyMemberOutput> outputList = new ArrayList<>();
        for (int i = 0; i < fansList.size(); i++)
        {
            Member input = memberService.get(fansList.get(i).getMemberId());
            if (null != input) {
                DyMemberOutput output = this.getDyMember(input.getId(), currentUserId);
                outputList.add(output);
            }
        }

        return outputList;
    }

    /**
     * 查看当前登录用户的关注列表
     * @param page
     * @param request
     * @return
     */
    public List<DyMemberOutput> listFocus(Page page, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser && !Strings.isNullOrEmpty(currentUser.getId()))
        {
            return this.listFocus(currentUser.getId(), page, request);
        }
        else
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
    }


    /**
     * 设置当前登录用户为其他会员的粉丝
     * @param focusMemberId 其它会员id
     * @return
     */
    @Transactional
    public DyMemberOutput setFocus(String focusMemberId, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbCurMember = memberService.get(currentUser.getId());//当前登录用户
        Member dbFocusMember = memberService.get(focusMemberId);//被点赞的用户
        //验证当前登录用户的数据
        if (null == dbCurMember)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        //验证当前要关注的会员的数据
        if (null == dbFocusMember)
        {
            throw new BusinessException(PartyCode.FOCUS_ON_MEMBER_NOT_EXIT, "要关注的会员不存在");
        }
//
//        if (Integer.parseInt(AuditStatus.PASS.getCode()) != dbFocusMember.getUserStatus())
//        {
//            throw new BusinessException(PartyCode.USER_PROFILE_UN_PASS, "会员资料未审核通过");
//        }

        //查看当前登录用户是否已经在该会议的粉丝列表中
        Fans fans = new Fans();
        fans.setMemberId(focusMemberId);
        fans.setFollowerId(currentUser.getId());

        List<Fans> fansList = fansService.list(fans);


        if (fansList.size() > 0)
        {
            throw new BusinessException(PartyCode.FOCUS_ON_MEMBER_HAVE_DOWN,"当前登录用户已经关注该会员");
        }
        else
        {
            //如果没有关注过，则插入一条fans数据
            fans.setCreateBy(currentUser.getId());
            fans.setUpdateBy(currentUser.getId());
            fans.setCreateDate(new Date());
            fans.setUpdateDate(new Date());
            fans.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
            fansService.insert(fans);

            //发给该动态的作者
            notifySendService.sendFocus(dbFocusMember.getId(), dbCurMember, dbCurMember.getLogo(), "", "", dbCurMember.getId());
        }

        DyMemberOutput output = this.getDyMember(focusMemberId, currentUser.getId());

        return output;
    }

    /**
     * 设置当前登录用户取消关注其它会员
     * @param focusMemberId 其它会员id
     * @return
     */
    @Transactional
    public DyMemberOutput removeFocus(String focusMemberId, HttpServletRequest request) throws BusinessException
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbCurMember = memberService.get(currentUser.getId());
        Member dbFocusMember = memberService.get(focusMemberId);
        //验证当前登录用户的数据
        if (null == dbCurMember)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        //验证当前要取消关注的会员的数据
        if (null == dbFocusMember)
        {
            throw new BusinessException(PartyCode.REMOVE_FOCUS_ON_MEMBER_NOT_EXIT, "要取消关注的会员不存在");
        }

        //查看当前登录用户是否已经在该会议的粉丝列表中
        Fans fans = new Fans();
        fans.setMemberId(focusMemberId);
        fans.setFollowerId(currentUser.getId());

        List<Fans> fansList = fansService.list(fans);


        if (fansList.size() > 0)
        {
            fansService.delete(fansList.get(0).getId());
        }
        else
        {
            throw new BusinessException(PartyCode.REMOVE_FOCUS_ON_MEMBER_HAVE_DOWN, "当前登录用户没有关注该会员");
        }

        DyMemberOutput output = this.getDyMember(focusMemberId, currentUser.getId());

        return output;
    }

    /**
     * 保存动态
     * @param dynamicInput
     * @param request
     * @return
     * @throws BusinessException
     */
    @Transactional
    public void saveDynamic(DynamicInput dynamicInput, HttpServletRequest request) throws Exception
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null == currentUser)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        //获取当前会员信息
        Member member = memberService.get(currentUser.getId());
        if (null == member)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
//        if (Integer.parseInt(AuditStatus.PASS.getCode()) != member.getUserStatus())
//        {
//            throw new BusinessException(PartyCode.USER_PROFILE_UN_PASS, "会员资料未审核通过");
//        }
        Dynamic dynamic = DynamicInput.transform(dynamicInput);

        //如果是达人，则默认为热门推荐
        if (member.getIsExpert() == YesNoStatus.YES.getCode())
        {
            dynamic.setIsHot(YesNoStatus.YES.getCode());
        }
        //社区动态
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        dynamicBaseBizService.saveBizDynamic(dynamic,member.getId());
    }


    /**
     * 保存话题
     * @param topicInput
     * @param request
     * @return
     * @throws BusinessException
     */
    @Transactional
    public void saveTopic(TopicInput topicInput, HttpServletRequest request) throws Exception
    {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null == currentUser)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        //获取当前会员信息
        Member member = memberService.get(currentUser.getId());
        if (null == member)
        {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        Dynamic dynamic = TopicInput.transform(topicInput);
        //圈子话题
        dynamic.setDynamicType(DynamicType.CIRCLE.getCode());
        String dynamicId = dynamicBaseBizService.saveBizDynamic(dynamic,member.getId());
        CircleTopic circleTopic = new CircleTopic();
        circleTopic.setTopicTagId(topicInput.getTagId());
        circleTopic.setCircle(topicInput.getCircleId());
        circleTopic.setDynamic(dynamicId);
        circleTopicBizService.saveTopic(circleTopic,currentUser.getId());
    }

    /**
     * 将dynamic表的图片数据插入到dypics表
     * @return
     */
    public boolean setPic()
    {
        List<Dynamic> list = dynamicService.list(new Dynamic());
        int num = 0;
        for (int i = 0; i < list.size(); i++)
        {
            Dynamic t = list.get(i);
            List<String> picList = getPicList(t);
            num = num + picList.size();
            for (int j = 0; j < picList.size(); j++) {
                Dypics dypics = new Dypics();
                dypics.setCreateBy(t.getCreateBy());
                dypics.setCreateDate(t.getCreateDate());
                dypics.setUpdateBy(t.getUpdateBy());
                dypics.setUpdateDate(t.getUpdateDate());
                dypics.setDelFlag(t.getDelFlag());
                dypics.setRemarks(t.getRemarks());

                dypics.setRefId(t.getId());
                dypics.setSort(j);
                dypics.setType(t.getDynamicType());
                dypics.setPicUrl(picList.get(j));

                dypicsService.insert(dypics);
            }
        }

        return true;
    }

    /**
     * 根据会员id，获取会员的名片信息
     * @param memberId 会员主键id
     * @return
     */
    public BusinessCardOutput getBusinessCarInfo(String memberId) throws BusinessException
    {
        Member member = memberService.get(memberId);

        if (null == member)
        {
            throw new BusinessException(PartyCode.CREATE_BUSINESS_CARD_ERROR, "生成名片信息错误，会员数据不存在");
        }
        //如果选择不公开资料,则不给其他人看
//        if (0 == member.getIsOpen())
//        {
//            throw new BusinessException(PartyCode.CREATE_BUSINESS_NOT_OPEN, "会员资料不公开");
//        }
        if (null == member.getIsOpen() || 0 == member.getIsOpen())
        {
            member = memberService.hideImportantInfo(member);
        }
        BusinessCardOutput output = BusinessCardOutput.transform(member);
        //设置行业名
        if (!Strings.isNullOrEmpty(member.getIndustry()))
        {
            Industry industry = industryService.get(member.getIndustry());
            if (null != industry)
            {
                output.setIndustryName(industry.getName());
            }
        }
        //设置城市名字
        if (!Strings.isNullOrEmpty(member.getCity()))
        {
            Area area = areaService.get(member.getCity());
            if (null != area) {
                output.setCityName(area.getName());
            }
        }
        return output;
    }
    /**
     * 设置动态的分享链接
     * @param id 动态id
     * @return
     */
    public String setShareLink(String id)
    {
        return "http://3g.tongxingzhe.cn/micWeb/html/community/dynamic_detail.html?id=" + id;
    }

}
