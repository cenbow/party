package com.party.core.dao.read.gatherInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherInfo.GatherInfoProject;

/**
 * 人员信息采集项目
 * 
 * @author Administrator
 *
 */
@Repository
public interface GatherInfoProjectReadDao extends BaseReadDao<GatherInfoProject> {
	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<GatherInfoProject> webListPage(@Param(value = "project") GatherInfoProject project, @Param(value = "params") Map<String, Object> params,
			Page page);

}
