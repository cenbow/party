package com.party.core.service.circle.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.party.core.model.circle.*;
import com.party.core.service.circle.ICircleApplyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.service.circle.ICircleMemberTagService;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.impl.CircleMemberService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;

/**
 * 圈子业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleMemberBizService {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private ICircleMemberTagService circleMemberTagService;
    @Autowired
    private ICircleService circleService;
    @Autowired
    private CircleMemberService circleMemberService;
    @Autowired
    private CircleTopicBizService circleTopicBizService;
    @Autowired
    private ICircleApplyService circleApplyService;

    /**
     * 查询所有圈子成员关系
     *
     * @param circleMember
     * @param params
     *@param tags  @return
     */
    public List<ListAllMember> listAll(CircleMember circleMember, Map<String, Object> params, String tags) {
        circleMember.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

        Set set = null;
        if (!Strings.isNullOrEmpty(tags)) {
            List<String> tagList = Arrays.asList(tags.split(","));
            set = new HashSet(tagList);
        }

        List<ListAllMember> circleList = circleMemberService.listAll(circleMember,params, set);
        Circle circle = circleService.get(circleMember.getCircle());


        return circleList;
    }

    /**
     * 通过类型查询所有圈子成员关系
     *
     * @param circleMember
     * @param params
     *@param tags  @return
     */
    public List<ListAllMember> listAllByType(CircleMember circleMember, Map<String, Object> params, String tags) {
        circleMember.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

        Set set = null;
        if (!Strings.isNullOrEmpty(tags)) {
            List<String> tagList = Arrays.asList(tags.split(","));
            set = new HashSet(tagList);
        }

        List<ListAllMember> circleList = circleMemberService.listAllByType(circleMember,params, set);
        Circle circle = circleService.get(circleMember.getCircle());


        return circleList;
    }

    /**
     * 移出会员
     *
     * @param circleMember
     */
    @Transactional
    public void out(CircleMember circleMember,String curUserId) {
        Circle circle = circleService.get(circleMember.getCircle());
        if (circle != null) {
            if (circle.getCreateBy().equals(circleMember.getMember())) {
                throw new BusinessException("不能移除创建者");
            }else if(circleMember.getMember().equals(curUserId)){
                throw new BusinessException("自己不能移除自己");
            } else {
                // 删除用户标签
                CircleMemberTag search = new CircleMemberTag();
                search.setMember(circleMember.getMember());
                search.setCircle(circleMember.getCircle());
                List<CircleMemberTag> tagList = circleMemberTagService.list(search);
                for (CircleMemberTag tag : tagList) {
                    circleMemberTagService.delete(tag.getId());
                }
                // 删除circleMember关系
                CircleMember cMember = circleMemberService.getUnique(circleMember);
                if (cMember != null) {
                    circleMemberService.delete(cMember.getId());
                }
                // 删除用户申请
                CircleApply applySearch = new CircleApply();
                applySearch.setCircle(cMember.getCircle());
                applySearch.setMember(cMember.getMember());
                circleApplyService.delBySearch(applySearch);
                // 删除圈子话题关系（不删除话题）
                CircleTopic topicSearch = new CircleTopic();
                topicSearch.setCreateBy(cMember.getMember());
                topicSearch.setCircle(circleMember.getCircle());
                circleTopicBizService.delBySearch(topicSearch);
                // 更新圈子会员数
                updateMemberNum(circle.getId());
            }
        }
    }

    /**
     * 根据圈子id删除会员信息
     *
     * @param id 圈子id
     */
    @Transactional
    public void delByCircle(String id) {
        CircleMember search = new CircleMember();
        search.setCircle(id);
        List<CircleMember> list = circleMemberService.list(search);
        for (CircleMember circleMember : list) {
            circleMemberService.delete(circleMember.getId());
        }
    }

    /**
     * 保存圈子成员
     * @param cId 圈子
     * @param ids 用戶
     * @param curUserId 當前用戶
     * @param source 用戶來源
     */
    @Transactional
    public void saveBiz(String cId, String ids, String curUserId, Integer source) {
        if (!Strings.isNullOrEmpty(ids) && !Strings.isNullOrEmpty(cId)) {
            String[] idsArray = ids.split(",");
            int count = 0;
            for (String id : idsArray) {
            	id = id.trim();
                CircleMember cMember = circleMemberService.getUnique(new CircleMember(cId, id));
                if (cMember == null) {
                    Member member = memberService.get(id);
                    if (member != null) {
                    	CircleMember circleMember = new CircleMember();
                        circleMember.setCircle(cId);
                        circleMember.setMember(id);
                        circleMember.setCreateBy(curUserId);
                        circleMember.setUpdateBy(curUserId);
                        circleMember.setIsAdmin(YesNoStatus.NO.getCode());
                        circleMember.setSource(source);
                        circleMemberService.insert(circleMember);
                        ++count;
                    }
                }
            }
            Circle circle = circleService.get(cId);
            int memberNum = circle.getMemberNum() == null ? 0 : circle.getMemberNum();
            memberNum += count;
            circle.setMemberNum(memberNum);
            circleService.update(circle);
        } else {
            throw new BusinessException(PartyCode.PARAMETER_ILLEGAL, "cId或ids不能为空");
        }
    }

    /**
     * 设置为管理员
     * @param id
     */
    @Transactional
    public CircleMember setMgrBiz(String id) {
        CircleMember circleMember = circleMemberService.get(id);
        if (circleMember != null) {
            if(circleMember.getIsAdmin() == 1){
                throw new BusinessException("管理员已存在，请勿重复设置");
            }
            CircleMember search = new CircleMember();
            search.setCircle(circleMember.getCircle());
            search.setIsAdmin(1);
            List<CircleMember> list = circleMemberService.list(search);
            if(null != list && list.size() >= 5){
                throw new BusinessException("管理员人数不能超过5个");
            }
            circleMember.setIsAdmin(1);
            circleMemberService.update(circleMember);
            return circleMember;
        }else{
            throw new BusinessException(PartyCode.PARAMETER_ILLEGAL, "id错误");
        }
    }

    /**
     * 取消管理员
     * @param id
     */
    @Transactional
    public void cancelMgrBiz(String id) {
        CircleMember circleMember = circleMemberService.get(id);
        if (circleMember != null) {
            circleMember.setIsAdmin(0);
            circleMemberService.update(circleMember);
        }else{
            throw new BusinessException(PartyCode.PARAMETER_ILLEGAL, "id错误");
        }
    }
    
    /**
     * 更新圈子会员数
     * @param circleId
     */
    public void updateMemberNum(String circleId){
		List<CircleMember> results = circleMemberService.list(new CircleMember(circleId));
		Circle circle = circleService.get(circleId);
		circle.setMemberNum(results.size());
		circleService.update(circle);
    }
    
    /**
	 * 圈子成員管理
	 * @param memberActs
	 * @param circleId
	 * @param businessMemberIds 
	 */
	@SuppressWarnings("unchecked")
	public void circleMemberManage(List<MemberAct> memberActs, String circleId, String type, List<String> businessMemberIds, String currentUserId) {
		
//		Circle circle = circleService.get(circleId);
		
		// 获取圈子会员
		List<CircleMember> circleMembers = circleMemberService.list(new CircleMember(circleId));
		List<String> circleMemberIds = (List<String>) CollectionUtils.collect(circleMembers, new Transformer() {

			@Override
			public Object transform(Object input) {
				CircleMember mm = (CircleMember) input;
				return mm.getMember();
			}
		});

		// 报名人员
		List<String> actMemberIds = (List<String>) CollectionUtils.collect(memberActs, new Transformer() {

			@Override
			public Object transform(Object input) {
				MemberAct mm = (MemberAct) input;
				return mm.getMemberId();
			}
		});
		
		actMemberIds.addAll(businessMemberIds);

//		String oldMemberIds = actMemberIds.toString().replace("[", "").replace("]", "");
		
		// 添加未加入圈子的报名人员
		actMemberIds.removeAll(circleMemberIds);
		if (actMemberIds.size() > 0) {
			String memberIds = actMemberIds.toString().replace("[", "").replace("]", "");
			saveBiz(circleId, memberIds, currentUserId, 1); // 业务生成圈子成员
		}
		
//		if (type.equals("1")) { // 已成功人员圈子 移除圈子中已取消的报名人员
//			if (circleMemberIds.size() > 0) {
//				List<String> oldMemberIdList = new ArrayList<String>();
//				for (String memberId : oldMemberIds.split(",")) {
//					oldMemberIdList.add(memberId.trim());
//				}
//				circleMemberIds.removeAll(oldMemberIdList);
//				if (circleMemberIds.size() > 0) {
//					for (String memberId : circleMemberIds) {
//						if (!memberId.equals(circle.getCreateBy())) {
//							CircleMember circleMember = new CircleMember(circle.getId(), memberId);
//							out(circleMember,currentUserId);
//						}
//					}
//				}
//			}
//		}

		// 更新圈子创建者为管理员	
		updateCreateBy(circleId);

		// 更新会员数
		updateMemberNum(circleId);
	}

	/**
	 * 更新圈子创建者为管理员
	 * 
	 * @param circleId
	 */
	public void updateCreateBy(String circleId) {
		Circle circle = circleService.get(circleId);
		CircleMember searchMember = new CircleMember();
		searchMember.setCircle(circleId);
		searchMember.setMember(circle.getCreateBy());
		CircleMember circleMember = circleMemberService.getUnique(searchMember);
		if (circleMember != null && circleMember.getIsAdmin() != null && circleMember.getIsAdmin().equals(YesNoStatus.NO.getCode())) {
			circleMember.setIsAdmin(YesNoStatus.YES.getCode());
			circleMemberService.update(circleMember);
		}
	}

    /**
     * 设置圈主
     * @param id
     */
    @Transactional
    public void setCreatorBiz(String id) {
        CircleMember circleMember = circleMemberService.get(id);
        if(null != circleMember){
            circleMember.setIsAdmin(YesNoStatus.NO.getCode());
            circleMemberService.update(circleMember);
            Circle circle = circleService.get(circleMember.getCircle());
            circle.setCreateBy(circleMember.getMember());
            circleService.update(circle);
        }else{
            throw new BusinessException("用户不是圈子成员");
        }
    }
}
