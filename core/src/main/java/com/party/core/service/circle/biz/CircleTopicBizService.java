package com.party.core.service.circle.biz;

import com.party.common.paging.Page;
import com.party.core.model.YesNoStatus;
import com.party.core.model.circle.*;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.service.circle.*;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.utils.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *圈子话题
 */
@Service
public class CircleTopicBizService {
	@Autowired
	private ICircleTopicService circleTopicService;
	@Autowired
	private ICircleTopicTagService circleTopicTagService;
	@Autowired
	private DynamicBaseBizService dynamicBaseBizService;
	@Autowired
	private ICircleService circleService;

	/**
	 * 保存圈子话题关系
	 * @param topic
	 * @param id
	 */
	public void saveTopic(CircleTopic topic, String id) throws Exception {
		CircleTopic search = new CircleTopic();
		search.setCircle(topic.getCircle());
		search.setDynamic(topic.getDynamic());
		List<CircleTopic> list = circleTopicService.list(search);
		if(null != list && list.size() > 0){
			CircleTopic dbTopic = list.get(0);
			MyBeanUtils.copyBeanNotNull2Bean(topic, dbTopic);//将编辑表单中的非\NULL值覆盖数据库记录中的值
			circleTopicService.update(dbTopic);
		}else {
			topic.setCreateBy(id);
			circleTopicService.insert(topic);
			Circle circle = circleService.get(topic.getCircle());
			//更新话题数
			if(null != circle) {
				Integer topicNum = circle.getTopicNum();
				topicNum++;
				circle.setTopicNum(topicNum);
				circleService.update(circle);
			}
		}
	}

	/**
	 * 删除话题标签
	 * @param circleTopicTag
	 */
	@Transactional
	public void delTagBiz(CircleTopicTag circleTopicTag) {
		//清除动态字段
		CircleTopic search = new CircleTopic();
		search.setCircle(circleTopicTag.getCircle());
		search.setTopicTagId(circleTopicTag.getId());
		List<CircleTopic> list = circleTopicService.list(search);
			for(CircleTopic ct:list){
			ct.setTopicTagId(null);
			circleTopicService.update(ct);
		}
		//删除话题标签
		circleTopicTagService.delete(circleTopicTag.getId());
	}

	/**
	 * 删除话题
	 * @param id
	 */
	@Transactional
	public void delBiz(String id) {
		dynamicBaseBizService.delBiz(id);
		CircleTopic search = new CircleTopic();
		search.setDynamic(id);
		List<CircleTopic> list = circleTopicService.list(search);
		if(null != list && list.size() > 0){
			CircleTopic topic = list.get(0);
			circleTopicService.delete(list.get(0).getId());
			Circle circle = circleService.get(topic.getCircle());
			//更新话题数
			if(null != circle) {
				Integer topicNum = circle.getTopicNum();
				topicNum--;
				circle.setTopicNum(topicNum);
				circleService.update(circle);
			}
		}
	}

	/**
	 * 通过查询删除话题关系（不删除话题）
	 */
	@Transactional
	public void delBySearch(CircleTopic search) {
		List<CircleTopic> list = circleTopicService.list(search);
		for(CircleTopic topic:list){
			circleTopicService.delete(topic.getId());
		}
		if(null != list && list.size() > 0) {
			Circle circle = circleService.get(list.get(0).getCircle());
			//更新话题数
			if (null != circle) {
				Integer topicNum = circle.getTopicNum();
				topicNum-=list.size();
				circle.setTopicNum(topicNum);
				circleService.update(circle);
			}
		}
	}
	/**
	 * 置顶
	 * @param topic
	 */
	public void toTopBiz(CircleTopic topic){
		CircleTopic search = new CircleTopic();
		search.setCircle(topic.getCircle());
		search.setIsTop(YesNoStatus.YES.getCode());
		List<CircleTopic> list = circleTopicService.list(search);
		Integer sort = 0;
		if(null != list && list.size() > 0) {
			sort = list.get(0).getSort();
		}
		List<CircleTopic> topics = circleTopicService.list(topic);
		if(null != topics && topics.size() > 0) {
			topic = topics.get(0);
			topic.setIsTop(YesNoStatus.YES.getCode());
			topic.setSort(++sort);
			circleTopicService.update(topic);
		}
	}

	/**
	 * 取消置顶
	 * @param topic
	 */
	public void cancleTopBiz(CircleTopic topic){
		List<CircleTopic> topics = circleTopicService.list(topic);
		if(null != topics && topics.size() > 0) {
			topic = topics.get(0);
			topic.setIsTop(YesNoStatus.NO.getCode());
			topic.setSort(0);
			circleTopicService.update(topic);
		}
	}

}
