package com.party.core.service.activity.biz;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.StringUtils;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.service.activity.IActivityDetailService;

/**
 * 活动详情业务接口
 * 
 * @author Administrator
 *
 */
@Service
public class ActivityDetailBizService {

	@Autowired
	private IActivityDetailService activityDetailService;

	/**
	 * 转换为HTML
	 * 
	 * @param activityDetail
	 * @return
	 */
	public ActivityDetail castToHTML(ActivityDetail activityDetail) {
		// 详情
		if (StringUtils.isNotEmpty(activityDetail.getContent())) {
			activityDetail.setContent(StringUtils.stringtohtml(activityDetail.getContent()));
		}
		// 报名相关
		if (StringUtils.isNotEmpty(activityDetail.getApplyRelated())) {
			activityDetail.setApplyRelated(StringUtils.stringtohtml(activityDetail.getApplyRelated()));
		}
		// 参赛标准
		if (StringUtils.isNotEmpty(activityDetail.getMatchStandard())) {
			activityDetail.setMatchStandard(StringUtils.stringtohtml(activityDetail.getMatchStandard()));
		}
		return activityDetail;
	}

	/**
	 * 保存详情
	 * 
	 * @param activityId
	 * @param activityDetail
	 */
	public void saveBiz(String activityId, ActivityDetail activityDetail) {
		ActivityDetail ad = activityDetailService.getByRefId(activityId);
		if (ad == null) {
			ad = new ActivityDetail();
			ad.setRefId(activityId);
		}
		// 详情
		if (StringUtils.isNotEmpty(activityDetail.getContent())) {
			String content = StringEscapeUtils.escapeHtml4(activityDetail.getContent().trim());
			ad.setContent(content);
		}
		// 报名相关
		if (StringUtils.isNotEmpty(activityDetail.getApplyRelated())) {
			String applyRelated = StringEscapeUtils.escapeHtml4(activityDetail.getApplyRelated().trim());
			ad.setApplyRelated(applyRelated);
		}
		// 参赛标准
		if (StringUtils.isNotEmpty(activityDetail.getMatchStandard())) {
			String matchStandard = StringEscapeUtils.escapeHtml4(activityDetail.getMatchStandard().trim());
			ad.setMatchStandard(matchStandard);
		}
		if (StringUtils.isNotEmpty(ad.getId())) {
			activityDetailService.update(ad);
		} else {
			activityDetailService.insert(ad);
		}
	}
}
