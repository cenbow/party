package com.party.core.service.circle.biz;

import com.google.common.base.Strings;
import com.party.core.model.circle.CircleMemberTag;
import com.party.core.service.circle.impl.CircleMemberTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 圈子标签业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleMemberTagBizService{
	
	@Autowired
	CircleMemberTagService circleMemberTagService;
	
    @Transactional
	public void saveBiz(CircleMemberTag memberTag, String tags) {
		CircleMemberTag search = new CircleMemberTag();
		search.setCircle(memberTag.getCircle());
		search.setMember(memberTag.getMember());
		List<CircleMemberTag> list = circleMemberTagService.list(search);
		for(CircleMemberTag item:list){
			circleMemberTagService.delete(item.getId());
		}
		if(!Strings.isNullOrEmpty(tags)){
			String[] tagIds = tags.split(",");
			for(String tagId :tagIds){
				CircleMemberTag saveMTag = new CircleMemberTag();
				saveMTag.setMember(memberTag.getMember());
				saveMTag.setCircle(memberTag.getCircle());
				saveMTag.setTag(tagId);
				circleMemberTagService.insert(saveMTag);
			}			
		}
	}

	public void insert(CircleMemberTag memberTag) {
		circleMemberTagService.insert(memberTag);
	}

	public List<CircleMemberTag> list (CircleMemberTag tag){return circleMemberTagService.list(tag);}
	@Transactional
	public void delByCircle(String id) {
		CircleMemberTag search = new CircleMemberTag();
		search.setCircle(id);
		List<CircleMemberTag> list = circleMemberTagService.list(search);
		for(CircleMemberTag memberTag:list){
			circleMemberTagService.delete(memberTag.getId());
		}
	}

}
