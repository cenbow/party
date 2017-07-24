package com.party.core.service.activity;

import java.util.List;

import com.party.core.model.activity.CrowfundResources;
import com.party.core.service.IBaseService;

/**
 * 众筹项目资源服务接口
 * 
 * @author Administrator
 *
 */
public interface ICrowfundResourcesService extends IBaseService<CrowfundResources> {
	/**
	 * 根据众筹项目id和类型获取资源
	 * 
	 * @param refId
	 *            众筹项目id
	 * @param type
	 *            1：图片 2：视频
	 * @return
	 */
	public List<CrowfundResources> findByRefId(String refId, String type);

	/**
	 * 根据众筹项目id和类型删除资源
	 * 
	 * @param id
	 * @param string
	 * @return 
	 */
	public boolean deleteByRefId(String id, String string);
}
