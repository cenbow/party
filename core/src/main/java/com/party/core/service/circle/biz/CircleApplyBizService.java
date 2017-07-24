package com.party.core.service.circle.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.party.core.model.circle.CircleCode;
import com.party.core.service.circle.ICircleApplyService;
import com.party.core.service.circle.ICircleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.core.exception.BusinessException;
import com.party.core.model.circle.CircleApply;
import com.party.core.model.circle.CircleMember;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.impl.CircleApplyService;
import com.party.core.service.circle.impl.CircleMemberService;
import com.party.core.service.city.impl.AreaService;
import com.party.core.service.member.impl.IndustryService;
import com.party.core.service.member.impl.MemberService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 圈子申请业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleApplyBizService{
	@Autowired
	MemberService memberService;
	@Autowired
	AreaService areaService;
	@Autowired
	IndustryService industryService;
	@Autowired
	ICircleMemberService circleMemberService;
	@Autowired
	ICircleApplyService circleApplyService;
	@Autowired
	CircleMemberBizService circleMemberBizService;
	@Autowired
	ICircleService circleService;

	public void getApplyData(String memberId, CircleApply apply) {
		//查询当前用户是否已经为圈子成员
		CircleMember cmSearch = new CircleMember();
		cmSearch.setMember(memberId);
		cmSearch.setCircle(apply.getCircle());
		CircleMember circleMember = circleMemberService.getUnique(cmSearch);
		if(null == circleMember) {
			CircleApply search = new CircleApply();
			search.setMember(memberId);
			search.setCircle(apply.getCircle());
			CircleApply dbCircleApply = circleApplyService.getUnique(search);
			if (null == dbCircleApply) {
				// 没有报名，则新建报名信息
				CircleApply circleApply = new CircleApply();
				circleApply.setCircle(apply.getCircle());
				circleApply.setCreateBy(memberId);
				circleApply.setMember(memberId);
				circleApply.setRemarks(apply.getRemarks());
				circleApply.setCheckStatus(CircleCode.Apply_STATUS_AUDITING.getCode());
				circleApplyService.insert(circleApply);
			} else {
				dbCircleApply.setCheckStatus(CircleCode.Apply_STATUS_AUDITING.getCode());
				dbCircleApply.setRemarks(apply.getRemarks());
				circleApplyService.update(dbCircleApply);
			}
		}else{
			throw new BusinessException("您已经加入圈子了");
		}
	}

	/**
	 * 拒绝
	 * @param id
	 * @param curUserId
	 */
	public void reject(String id,String curUserId) {
		CircleApply dbApply = circleApplyService.get(id);
		if(dbApply == null){
			throw new BusinessException("找不到申请关系");
		}else{
			dbApply.setUpdateBy(curUserId);
			dbApply.setCheckStatus(CircleCode.Apply_STATUS_AUDIT_REJECT.getCode());
			circleApplyService.update(dbApply);
			
			// 更新圈子会员数
			circleMemberBizService.updateMemberNum(dbApply.getCircle());
		}
	}

	/**
	 * 通过
	 * @param id
	 * @param curUserId
	 */
	public void pass(String id,String curUserId) {
		CircleApply dbApply = circleApplyService.get(id);
		if(dbApply == null){
			throw new BusinessException("找不到申请关系");
		}else{
			dbApply.setUpdateBy(curUserId);
			dbApply.setCheckStatus(CircleCode.Apply_STATUS_AUDIT_PASS.getCode());
			circleApplyService.update(dbApply);

			CircleMember sCircleMember = new CircleMember();// 检索对象
			sCircleMember.setCircle(dbApply.getCircle());
			sCircleMember.setMember(dbApply.getMember());
			CircleMember dbCircleMember = circleMemberService.getUnique(sCircleMember);
			if(dbCircleMember == null){
				circleMemberBizService.saveBiz(dbApply.getCircle(),dbApply.getMember(),curUserId, 3); // 前端申请生成圈子成员
			}
		}
	}


}
