package com.party.core.service.activity.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.party.common.utils.StringUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.model.user.User;
import com.party.core.service.activity.ICrowfundResourcesService;
import com.party.core.service.user.IUserService;

@Service
public class CrowfundResourceBizService {
	@Autowired
	private ICrowfundResourcesService crowfundResourcesService;
	@Autowired
	private IUserService userService;

	/**
	 * 保存资源业务
	 * 
	 * @param activity
	 * @param picList
	 *            图片
	 * @param videoList
	 *            视频
	 */
	@SuppressWarnings("unchecked")
	public void saveResourceBiz(Activity activity, String picList, String videoList, String memberId) {
		User user = userService.findByLoginName("admin");
		crowfundResourcesService.deleteByRefId(activity.getId(), "1");
		crowfundResourcesService.deleteByRefId(activity.getId(), "2");
		// 跑马灯
		if (StringUtils.isNotEmpty(picList)) {
			List<Map<String, String>> resultList = (List<Map<String, String>>) JSONObject.parse(picList);
			int sort = 1;
			for (Map<String, String> map : resultList) {
				saveResource(user.getId(), activity.getId(), "1", sort, map.get("pic"), map.get("remarks"), memberId);
				sort++;
			}
		}

		// 视频
		if (StringUtils.isNotEmpty(videoList)) {
			Map<String, String> map = (Map<String, String>) JSONObject.parse(videoList);
			String videoUrl = map.get("pic").replaceAll("\"", "'");
			saveResource(user.getId(), activity.getId(), "2", 1, videoUrl, map.get("remarks"), memberId);
		}
	}

	/**
	 * 保存资源
	 * 
	 * @param userId
	 * @param businessId
	 * @param type
	 * @param sort
	 * @param picUrl
	 * @param remarks
	 */
	public void saveResource(String userId, String businessId, String type, Integer sort, String picUrl, String remarks, String memberId) {
		CrowfundResources crowfundResource = new CrowfundResources();
		crowfundResource.setCreateBy(userId);
		crowfundResource.setUpdateBy(userId);
		crowfundResource.setMember(memberId);
		crowfundResource.setRefId(businessId);
		crowfundResource.setResourceUrl(picUrl);
		crowfundResource.setRemarks(remarks);
		crowfundResource.setType(type);
		crowfundResource.setSort(sort);
		crowfundResourcesService.insert(crowfundResource);
	}
}
