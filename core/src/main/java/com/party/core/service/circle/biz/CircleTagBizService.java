package com.party.core.service.circle.biz;

import com.party.core.model.circle.CircleMemberTag;
import com.party.core.model.circle.CircleTag;
import com.party.core.service.circle.ICircleMemberTagService;
import com.party.core.service.circle.ICircleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 圈子业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleTagBizService {	

	@Autowired
	ICircleMemberTagService circleMemberTagService;
	@Autowired
	ICircleTagService circleTagService;
	
	@Transactional
	public void remove(CircleTag circleTag) {
		//删除标签用户关系
		CircleMemberTag search = new CircleMemberTag();
		search.setCircle(circleTag.getCircle());
		search.setTag(circleTag.getId());
		List<CircleMemberTag> list = circleMemberTagService.list(search);
		for(CircleMemberTag item:list){
			circleMemberTagService.delete(item.getId());
		}
		//删除标签
		circleTagService.delete(circleTag.getId());
	}

	public List<CircleTag> list(CircleTag circleTag) {
		return circleTagService.list(circleTag);
	}

	@Transactional
	public void delByCircle(String id) {
		CircleTag search = new CircleTag();
		search.setCircle(id);
		List<CircleTag> list = circleTagService.list(search);
		for(CircleTag tag:list){
			circleTagService.delete(tag.getId());
		}
	}

}
