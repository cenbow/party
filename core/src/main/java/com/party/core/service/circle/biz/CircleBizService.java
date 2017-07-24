package com.party.core.service.circle.biz;

import com.party.common.paging.Page;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.circle.*;
import com.party.core.model.member.Member;
import com.party.core.service.circle.*;
import com.party.core.service.member.IMemberService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 圈子业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleBizService {

    @Autowired
    ICircleService circleService;
    @Autowired
    IMemberService memberService;
    @Autowired
    ICircleMemberService circleMemberService;
    @Autowired
    ICircleApplyService circleApplyService;
    @Autowired
    CircleMemberTagBizService circleMemberTagBizService;
    @Autowired
    CircleTagBizService circleTagBizService;
    @Autowired
    ICircleTagService circleTagService;
    @Autowired
    CircleMemberBizService circleMemberBizService;
    @Autowired
    ICircleBusinessService circleBusinessService;
    @Autowired
    CircleTopicBizService circleTopicBizService;
    @Autowired
    ICircleTopicTagService circleTopicTagService;

    /**
     * 分页查询所有圈子
     *
     * @param circle
     * @param page
     * @return
     */
    public List<Circle> list(Circle circle, Page page) {
        circle.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        List<Circle> circleList = circleService.listPage(circle, page);

        if (!CollectionUtils.isEmpty(circleList)) {

            return circleList;
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 获取加入的圈子
     *
     * @param member 用户
     * @param page
     * @return
     */
    public List<Circle> joinList(Circle cSearch, Member member, Page page) {
        CircleMember search = new CircleMember();
        search.setMember(member.getId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cAreaId", cSearch.getArea());
        params.put("cName", cSearch.getName());
        List<CircleMember> circleMemberList = circleMemberService.listPageSearch(search, params, page);
        List<Circle> circleList = new ArrayList<Circle>();
        for (CircleMember cmember : circleMemberList) {
            Circle circle = circleService.get(cmember.getCircle());
            circleList.add(circle);
        }
        return circleList;
    }

    /**
     * 创建圈子
     *
     * @return
     */
    @Transactional
    public String createCircleBiz(Circle circle, String curId) {
        circle.setCreateBy(curId);
        circle.setMemberNum(1);
        String id = circleService.insert(circle);
        CircleMember circleMember = new CircleMember();
        circleMember.setCircle(id);
        circleMember.setMember(curId);
        circleMember.setCreateBy(curId);
        circleMember.setUpdateBy(curId);
        circleMember.setIsAdmin(1);
        circleMemberService.insert(circleMember);
        return id;
    }

    /**
     * 获取会员名片信息（对圈主和管理员可见，其他成员根据权限配置读取）
     *
     * @param circleMember
     * @param curUser
     * @return
     */
    public Member getBusinessCarInfo(CircleMember circleMember, Member curUser) {
        CircleMember dbCMember = circleMemberService.getUnique(circleMember);
        if (dbCMember != null) {
            Boolean isAuth = false;//是否有权限
            Member member = memberService.get(dbCMember.getMember());
            //判断圈子隐私权限
            Circle circle = circleService.get(circleMember.getCircle());
            if (null != curUser) {
                //当前用户是否为圈主
                if (circle.getCreateBy().equals(curUser.getId()) || curUser.getId().equals(member.getId())) {
                    isAuth = true;
                } else {
                    //获取当前登录用户是否为圈子成员
                    Member loginMember = memberService.get(curUser.getId());
                    CircleMember searchCM = new CircleMember();
                    searchCM.setCircle(circleMember.getCircle());
                    searchCM.setMember(curUser.getId());
                    CircleMember searchRet = circleMemberService.getUnique(searchCM);
                    if (null != searchRet) {//当前登录用户是圈子成员
                        if(searchRet.getIsAdmin() == YesNoStatus.YES.getCode()){//圈子用户是管理员
                            isAuth = true;
                        }else {
                            if (circle.getIsOpen() == CircleCode.CIRCLE_AUTH_OPEN.getCode()) {//对内部公开
                                isAuth = true;
                            } else if (circle.getIsOpen() == CircleCode.CIRCLE_AUTH_TYPE_AUTH.getCode()) {//针对类型
                                //获取要访问的用户在当前圈子的类型列表
                                CircleMemberTag cmTagSearch = new CircleMemberTag();
                                cmTagSearch.setCircle(circleMember.getCircle());
                                cmTagSearch.setMember(circleMember.getMember());
                                List<CircleMemberTag> vcmTagList = circleMemberTagBizService.list(cmTagSearch);
                                //获取当前登录用户在当前圈子的类型列表
                                cmTagSearch.setMember(curUser.getId());
                                List<CircleMemberTag> ncmTagList = circleMemberTagBizService.list(cmTagSearch);
                                for (CircleMemberTag cmTag : vcmTagList) {
                                    CircleTag vCircleTag = circleTagService.get(cmTag.getTag());
                                    //判断权限 对内部人员公开
                                    if (vCircleTag.getIsOpen() == CircleCode.TYPE_AUTH_OPEN.getCode()) {
                                        isAuth = true;
                                        break;
                                    }
                                    for (CircleMemberTag ncmTag : ncmTagList) {
                                        if (cmTag.getTag().equals(ncmTag.getTag())) {
                                            //对本类型公开
                                            if (vCircleTag.getIsOpen() == CircleCode.TYPE_AUTH_INNER_OPEN.getCode()) {
                                                isAuth = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (vcmTagList.size() == 0) {//要访问的用户没有设置类型
                                    if (circle.getNoTypeIsOpen() == CircleCode.TYPE_AUTH_OPEN.getCode()) {
                                        isAuth = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!isAuth) {
                member = memberService.hideImportantInfo(member);
            }
            return member;
        } else {
            throw new BusinessException(PartyCode.CREATE_BUSINESS_CARD_ERROR, "生成名片信息错误，会员数据不存在");
        }
    }

    /**
     * 解散圈子
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Transactional
    public boolean dissolveCircle(String id) {
        Circle circle = circleService.get(id);
        //删除标签用户关系
        circleMemberTagBizService.delByCircle(id);
        //删除圈子标签
        circleTagBizService.delByCircle(id);
        //删除圈子用户关系
        circleMemberBizService.delByCircle(id);
        //删除圈子与业务关系
        circleBusinessService.delByCircle(id);
        //删除圈子话题关系（不删除话题）
        CircleTopic search = new CircleTopic();
		search.setCircle(id);
        circleTopicBizService.delBySearch(search);
        //删除圈子话题标签
        circleTopicTagService.delByCircle(id);
        //删除圈子
        circleService.delete(id);
        return true;
    }
    
    /**
	 * 创建圈子与业务的关系
	 * 
	 * @param businessId
	 * @param type 1已成功 2全部
	 */
	public String createCircleBusiness(String businessId, Activity activity, String type) {
		CircleBusiness circleBusiness = circleBusinessService.findByBusinessId(businessId, type);
		if (circleBusiness != null) {
			Circle circle = circleService.get(circleBusiness.getCircleId());
			if (circle == null) {
				circle = new Circle();
				String circleId = saveCircle(circle, activity);
				circleBusiness.setCircleId(circleId);
				circleBusinessService.update(circleBusiness);
				return circleId;
			} else {
				circle.setName(activity.getTitle());
				circle.setLogo(activity.getPic());
				circle.setRemarks(activity.getRemarks());
				circleService.update(circle);
				return circle.getId();
			}
		} else {
			// 创建圈子
			Circle circle = new Circle();
			String circleId = saveCircle(circle, activity);

			// 圈子圈子与业务的关联
			circleBusiness = new CircleBusiness();
			circleBusiness.setCircleId(circleId);
			circleBusiness.setBusinessId(businessId);
			circleBusiness.setType(type);
			circleBusinessService.insert(circleBusiness);
			return circleId;
		}
	}

	/**
	 * 新增圈子
	 * 
	 * @param circle
	 * @return
	 */
	public String saveCircle(Circle circle, Activity activity) {
		circle.setName(activity.getTitle());
		circle.setLogo(activity.getPic());
		circle.setShowFront(YesNoStatus.NO.getCode()); // 默认不显示到前端
		circle.setIsOpen(YesNoStatus.NO.getCode()); // 默认不公开
		circle.setCreateBy(activity.getMember());
		circle.setUpdateBy(activity.getMember());
		circle.setRemarks(activity.getRemarks());
		return circleService.insert(circle);
	}


    /**
     * 获取当前用户在圈子角色
     *
     * @param id     圈子id
     * @param mapret
     * @param curId
     */
    public Map<String, Object>  getRole(String id, Map<String, Object> mapret, String curId) {
        Circle circle = circleService.get(id);
        if(null != circle) {
            //是否为会员，是否为管理员
            mapret.put("isJoin", false);
            mapret.put("isAdmin", false);
            mapret.put("isCreator", false);
            if (StringUtils.isNotBlank(curId)) {
                CircleMember search = new CircleMember();
                search.setCircle(id);
                search.setMember(curId);
                CircleMember circleMember = circleMemberService.getUnique(search);
                if (circleMember != null) {
                    mapret.put("isJoin", true);
                    if (circleMember.getIsAdmin() != null && circleMember.getIsAdmin() == 1) {
                        mapret.put("isAdmin", true);
                    }
                }
                if (circle.getCreateBy().equals(curId)) {
                    mapret.put("isCreator", true);
                }
            }
        }
        return mapret;
    }

    /**
     * 获取当前用户的审核状态
     *
     * @param id     圈子id
     * @param mapret
     * @param curId
     */
    public void getApplyCheckStatus(String id, Map<String, Object> mapret, String curId) {
        Circle circle = circleService.get(id);
        if (StringUtils.isNotBlank(curId)) {
            CircleApply applySearch = new CircleApply();
            applySearch.setCircle(id);
            applySearch.setMember(curId);
            CircleApply apply = circleApplyService.getUnique(applySearch);
            if (apply != null) {
                mapret.put("checkStatus", apply.getCheckStatus());
            }
        }
    }

    /**
     * 获取审核人数
     *
     * @param id     圈子id
     * @param mapret
     * @param curId
     */
    public void getVerifyNum(String id, Map<String, Object> mapret, String curId) {
        Circle circle = circleService.get(id);
        if (StringUtils.isNotBlank(curId)) {
            //获取待审核的人数
            CircleApply caSearch = new CircleApply();
            caSearch.setCheckStatus(CircleCode.Apply_STATUS_AUDITING.getCode());
            caSearch.setCircle(id);
            Long verifyCount = circleApplyService.count(caSearch);
            mapret.put("verifyNum", verifyCount);
        }
    }
}
